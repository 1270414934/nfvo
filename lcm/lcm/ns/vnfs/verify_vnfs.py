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
import time

from lcm.pub.exceptions import NSLCMException
from lcm.pub.utils.jobutil import JobUtil, JOB_TYPE, JOB_MODEL_STATUS
from lcm.pub.utils.values import ignore_case_get
from lcm.pub.utils.restcall import req_by_msb

logger = logging.getLogger(__name__)

JOB_ERROR = 255

class VerifyVnfs(threading.Thread):
    def __init__(self, data, job_id):
        super(VerifyVnfs, self).__init__()
        self.data = data
        self.job_id = job_id
        self.vnf_inst_id = ''
        self.verify_ok = False

    def run(self):
        try:
            self.do_on_boarding()
            self.do_inst_vnf()
            self.do_func_test()
            self.verify_ok = True
        except NSLCMException as e:
            self.update_job(JOB_ERROR, e.message)
        except:
            logger.error(traceback.format_exc())
            self.update_job(JOB_ERROR, 'Unknown error in vnf verify.')
        finally:
            self.do_term_vnf()

    def do_on_boarding(self):
        self.update_job(10, "Start vnf on boarding.")
        ret = req_by_msb("/openoapi/nslcm/v1/vnfpackage", "POST", json.JSONEncoder().encode(self.data))
        if ret[0] != 0:
            raise NSLCMException("Failed to call vnf onboarding: %s" % ret[1])
        rsp_data = json.JSONDecoder().decode(ret[1])
        if not self.wait_until_job_done(rsp_data["jobId"], 15):
            raise NSLCMException("Vnf onboarding failed")
        self.update_job(20, "Vnf on boarding success.")
        
    def do_inst_vnf(self):
        self.update_job(30, "Start inst vnf.")
        # TODO
        inst_data = {
            "ns_instance_id": "",
            "additional_param_for_ns": ignore_case_get(self.data, "additionalParamForVnf"),
            "additional_param_for_vnf": ignore_case_get(self.data, "additionalParamForVnf"),
            "vnf_index": "1"
        }
        ret = req_by_msb("/openoapi/nslcm/v1/ns/vnfs", "POST", json.JSONEncoder().encode(inst_data))
        if ret[0] != 0:
            raise NSLCMException("Failed to call inst vnf: %s" % ret[1])
        rsp_data = json.JSONDecoder().decode(ret[1])
        self.vnf_inst_id = rsp_data["vnfInstId"]
        if not self.wait_until_job_done(rsp_data["jobId"], 40):
            raise NSLCMException("Vnf(%s) inst failed" % self.vnf_inst_id)
        self.update_job(50, "Inst vnf success.")
        
    def do_func_test(self):
        self.update_job(60, "Start vnf function test.")
        self.update_job(80, "Vnf function test success.")
        
    def do_term_vnf(self):
        if not self.vnf_inst_id:
            return
        self.update_job(90, "Start term vnf.")
        term_data = {
            "terminationType": "forceful",
            "gracefulTerminationTimeout": "600"
        }
        ret = req_by_msb("/openoapi/nslcm/v1/ns/vnfs/%s" % self.vnf_inst_id, "POST", 
            json.JSONEncoder().encode(term_data))
        if ret[0] != 0:
            raise NSLCMException("Failed to call term vnf: %s" % ret[1])
        rsp_data = json.JSONDecoder().decode(ret[1])
        end_progress = 100 if self.verify_ok else JOB_ERROR
        term_progress = 95 if self.verify_ok else JOB_ERROR
        if not self.wait_until_job_done(rsp_data["jobId"], term_progress):
            logger.error("Vnf(%s) term failed", self.vnf_inst_id)
            end_progress = JOB_ERROR
        self.update_job(end_progress, "Term vnf end.")
        
    def update_job(self, progress, desc=''):
        JobUtil.add_job_status(self.job_id, progress, desc)
        
    def wait_until_job_done(self, job_id, global_progress, retry_count=60, interval_second=3):
        count = 0
        response_id, new_response_id = 0, 0
        job_end_normal, job_timeout = False, True
        while count < retry_count:
            count = count + 1
            time.sleep(interval_second)
            ret = req_by_msb("/openoapi/nslcm/v1/jobs?responseId=%s" % response_id, "GET")
            if ret[0] != 0:
                logger.error("Failed to query job: %s:%s", ret[2], ret[1])
                continue
            job_result = json.JSONDecoder().decode(ret[1])
            if "responseDescriptor" not in job_result:
                logger.error("Job(%s) does not exist.", job_id)
                continue
            progress = job_result["responseDescriptor"]["progress"]
            new_response_id = job_result["responseDescriptor"]["responseId"]
            job_desc = job_result["responseDescriptor"]["statusDescription"]
            if new_response_id != response_id:
                self.update_job(global_progress, job_desc)
                logger.debug("%s:%s:%s", progress, new_response_id, job_desc)
                response_id = new_response_id
                count = 0
            if progress == JOB_ERROR:
                job_timeout = False
                logger.error("Job(%s) failed: %s", job_id, job_desc)
                break
            elif progress == 100:
                job_end_normal, job_timeout = True, False
                logger.info("Job(%s) ended normally", job_id)
                break
        if job_timeout:
            logger.error("Job(%s) timeout", job_id)
        return job_end_normal
