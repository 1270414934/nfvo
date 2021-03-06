/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

package org.openo.nfvo.vimadapter.service.openstack.resourcemgr;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.nfvo.vimadapter.common.servicetoken.VimRestfulUtil;
import org.openo.nfvo.vimadapter.service.constant.Constant;
import org.openo.nfvo.vimadapter.service.openstack.api.ConnectInfo;
import org.openo.nfvo.vimadapter.service.openstack.connectmgr.ConnectionMgr;
import org.openo.nfvo.vimadapter.service.openstack.connectmgr.OpenstackConnection;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class CpuLimitQueryTest {

    private CpuLimitQuery cpuLimitQuery;

    @Before
    public void setUp() {
        Map<String, String> conMap = new HashMap<String, String>();
        conMap.put("url", "url");
        conMap.put("userName", "userName");
        conMap.put("userPwd", "userPwd");
        conMap.put("queryId", "queryId");
        conMap.put("vimId", "vimId");
        conMap.put("vimName", "vimName");
        cpuLimitQuery = new CpuLimitQuery(conMap);
    }

    @After
    public void tearDown() {
        cpuLimitQuery = null;
    }

    @Test
    public void testGetLimits() {
        new MockUp<ConnectionMgr>() {

            @Mock
            public OpenstackConnection getConnection(ConnectInfo info) {

                return new OpenstackConnection();
            }
        };
        new MockUp<OpenstackConnection>() {

            @Mock
            public String getServiceUrl(String serviceName) {
                return "url";
            }
        };
        new MockUp<VimRestfulUtil>() {

            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params,
                    String domainTokens) {
                RestfulResponse rsp = new RestfulResponse();
                String responseString = "{\"limits\":{}}";
                rsp.setResponseJson(responseString);
                return rsp;
            }
        };
        JSONObject result = cpuLimitQuery.getLimits();

        JSONObject resObj = new JSONObject();
        resObj.put("vimId", "vimId");
        resObj.put("vimName", "vimName");
        resObj.put("limits", new JSONObject());
        resObj.put(Constant.RETCODE, Constant.REST_SUCCESS);
        //assertEquals(resObj, result);
    }

    @Test
    public void testGetLimitsByFail() {
        new MockUp<ConnectionMgr>() {

            @Mock
            public OpenstackConnection getConnection(ConnectInfo info) {

                return new OpenstackConnection();
            }
        };
        new MockUp<OpenstackConnection>() {

            @Mock
            public String getServiceUrl(String serviceName) {
                return "url";
            }
        };
        new MockUp<VimRestfulUtil>() {

            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params,
                    String domainTokens) {
                RestfulResponse rsp = new RestfulResponse();
                String responseString = "{\"limit\":{}}";
                rsp.setResponseJson(responseString);
                return rsp;
            }
        };
        JSONObject result = cpuLimitQuery.getLimits();

        JSONObject resObj = new JSONObject();
        resObj.put(Constant.RETCODE, Constant.REST_FAIL);
        assertEquals(resObj, result);
    }

    @Test
    public void testGetLimitsByResponseNull() {
        new MockUp<ConnectionMgr>() {

            @Mock
            public OpenstackConnection getConnection(ConnectInfo info) {

                return new OpenstackConnection();
            }
        };
        new MockUp<OpenstackConnection>() {

            @Mock
            public String getServiceUrl(String serviceName) {
                return "url";
            }
        };
        new MockUp<VimRestfulUtil>() {

            @Mock
            public RestfulResponse getRemoteResponse(Map<String, String> paramsMap, String params,
                    String domainTokens) {
                return null;
            }
        };
        JSONObject result = cpuLimitQuery.getLimits();

        JSONObject resObj = new JSONObject();
        resObj.put(Constant.RETCODE, Constant.REST_FAIL);
        assertEquals(resObj, result);
    }
}
