/*
 * Copyright (c) 2017 Ericsson (China) Communication Co. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.nfvo.vnfmdriver.process;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.nfvo.vnfmdriver.common.constant.Constant;
import org.openo.nfvo.vnfmdriver.common.restfulutil.HttpRestfulAPIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/**
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version NFVO 0.5 Feb 28, 2017
 */
@Service("nslcmServiceProcessor")
public class NSLCMServiceProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(NSLCMServiceProcessor.class);

    /**
     * <br>
     *
     * @param jsonInstantiateOfReq
     * @return
     * @since NFVO 0.5
     */
    public JSONObject grantVnf(JSONObject jsonInstantiateOfReq) {
        RestfulResponse rsp = null;
        JSONObject restJson = new JSONObject();
        restJson.put(Constant.RETCODE, Constant.REST_FAIL);

        try {
            String url = Constant.NSLCM_URL_BASE+ Constant.GRANT_VNF_URL;
            LOG.info("Request NSLCM Grant VNF Lifecycle API. URL:{}, Type:{}, Request body:{}",
                    url, "POST", jsonInstantiateOfReq.toString());

            rsp = HttpRestfulAPIUtil.getRemoteResponse(url, Constant.POST, jsonInstantiateOfReq.toString());

            if(null == rsp) {
                LOG.error("Receive null response");
                restJson.put(Constant.RESP_STATUS, Constant.HTTP_NOTFOUND);
                return restJson;
            } else {
                LOG.info("Receive response of Grant VNF Lifecycle. Status:{}, body:{}",
                        rsp.getStatus(), rsp.getResponseContent());

                restJson.put(Constant.RETCODE, Constant.HTTP_OK);
                restJson.put(Constant.RESP_STATUS, rsp.getStatus());
                restJson.put(Constant.DATA, rsp.getResponseContent());
            }
        } catch(JSONException e) {
            restJson.put(Constant.RESP_STATUS, Constant.HTTP_INNERERROR);
            LOG.error("JSONException!" + e.getMessage());
        }
        return restJson;
    }
}
