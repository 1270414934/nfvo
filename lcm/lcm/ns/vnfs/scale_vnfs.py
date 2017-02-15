# Copyright 2017 ZTE Corporation.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
import json
import logging
import threading
import traceback

from lcm.ns.vnfs.const import VNF_STATUS
from lcm.ns.vnfs.wait_job import wait_job_finish
from lcm.pub.database.models import NfInstModel
from lcm.pub.exceptions import NSLCMException
from lcm.pub.msapi.vnfmdriver import send_nf_scaling_request
from lcm.pub.utils.jobutil import JobUtil, JOB_TYPE, JOB_MODEL_STATUS
from lcm.pub.utils.values import ignore_case_get

JOB_ERROR = 255
SCALE_TYPE = ("SCALE_OUT", "SCALE_IN")
logger = logging.getLogger(__name__)


class NFManualScaleService(threading.Thread):
    def __init__(self, vnf_instance_id, data):
        super(NFManualScaleService, self).__init__()
        self.vnf_instance_id = vnf_instance_id
        self.data = data
        self.job_id = JobUtil.create_job("NF", JOB_TYPE.MANUAL_SCALE_VNF, vnf_instance_id)
        self.scale_vnf_data = ''
        self.nf_model = {}
        self.nf_scale_params = []
        self.m_nf_inst_id = ''
        self.vnfm_inst_id = ''

    def run(self):
        try:
            self.do_biz()
        except NSLCMException as e:
            JobUtil.add_job_status(self.job_id, JOB_ERROR, e.message)
        except:
            logger.error(traceback.format_exc())
            JobUtil.add_job_status(self.job_id, JOB_ERROR, 'nf scale fail')
        finally:
            self.update_nf_status()

    def do_biz(self):
        self.update_nf_status(VNF_STATUS.SCALING)
        self.get_and_check_params()
        self.send_nf_scaling_requests()

    def get_and_check_params(self):
        nf_info = NfInstModel.objects.filter(nfinstid=self.vnf_instance_id)
        if not nf_info:
            logger.error('NF instance[id=%s] does not exist' % self.vnf_instance_id)
            raise NSLCMException('NF instance[id=%s] does not exist' % self.vnf_instance_id)
        self.nf_model = json.loads(nf_info[0].vnfd_model)
        self.m_nf_inst_id = nf_info[0].mnfinstid
        self.vnfm_inst_id = nf_info[0].vnfm_inst_id
        self.scale_vnf_data = ignore_case_get(self.data, 'scaleVnfData')
        if not self.scale_vnf_data:
            logger.error('scaleVnfData parameter does not exist or value incorrect')
            raise NSLCMException('scaleVnfData parameter does not exist or value incorrect')
        for vnf_data in self.scale_vnf_data:
            type = ignore_case_get(vnf_data, 'type')
            aspect_id = ignore_case_get(vnf_data, 'aspectId')
            number_of_steps = ignore_case_get(vnf_data, 'numberOfSteps')
            self.nf_scale_params.append({
                'type': type,
                'aspectId': aspect_id,
                'numberOfSteps': number_of_steps,
                'additionalParam': {}
            })

    def get_vdus(self, aspect_id):
        associated_group = ''
        members = []
        vnf_flavours = self.nf_model['vnf_flavours']
        for _ in vnf_flavours:
            scaling_aspects = self.nf_model['scaling_aspects']
            for aspect in scaling_aspects:
                if aspect_id == aspect['id']:
                    associated_group = aspect['associated_group']
                    break
        if not associated_group:
            logger.error('Cannot find the corresponding element group')
            raise NSLCMException('Cannot find the corresponding element group')
        for element_group in self.nf_model['element_groups']:
            if element_group['group_id'] == associated_group:
                members = element_group['members']
        if not members:
            logger.error('Cannot find the corresponding members')
            raise NSLCMException('Cannot find the corresponding members')
        return members

    def send_nf_scaling_requests(self):
        for i in range(len(self.nf_scale_params)):
            progress_range = [10 + 80 / len(self.nf_scale_params) * i, 10 + 80 / len(self.nf_scale_params) * (i + 1)]
            self.send_nf_scaling_request(self.nf_scale_params[i], progress_range)

    def send_nf_scaling_request(self, scale_param, progress_range):
        req_param = json.JSONEncoder().encode(scale_param)
        rsp = send_nf_scaling_request(self.vnfm_inst_id, self.m_nf_inst_id, req_param)
        vnfm_job_id = ignore_case_get(rsp, 'jobId')
        ret = wait_job_finish(self.vnfm_inst_id, self.job_id, vnfm_job_id, progress_range=progress_range, timeout=1200,
                              mode='1')
        if ret != JOB_MODEL_STATUS.FINISHED:
            logger.error('[NF scale] nf scale failed')
            raise NSLCMException("nf scale failed")

    def update_nf_status(self, status=VNF_STATUS.ACTIVE):
        NfInstModel.objects.filter(nfinstid=self.vnf_instance_id).update(status=status)
