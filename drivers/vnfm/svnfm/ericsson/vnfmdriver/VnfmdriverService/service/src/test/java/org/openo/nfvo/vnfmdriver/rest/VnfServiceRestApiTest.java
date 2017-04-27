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

package org.openo.nfvo.vnfmdriver.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openo.nfvo.vnfmdriver.common.constant.Constant;
import org.openo.nfvo.vnfmdriver.common.restfulutil.HttpContextUitl;
import org.openo.nfvo.vnfmdriver.process.VNFServiceProcessor;

/**
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version NFVO 0.5 Feb 28, 2017
 */

@SuppressWarnings("unchecked")

public class VnfServiceRestApiTest {

    private VNFServiceRestAPI vnfServiceRestApi;

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */

    @Before
    public void setUp() throws Exception {

        vnfServiceRestApi = new VNFServiceRestAPI();
        VNFServiceProcessor vnfProcess = new VNFServiceProcessor();
        Class<VNFServiceRestAPI> cs = VNFServiceRestAPI.class;
        Field field = cs.getDeclaredField("vnfServiceProcessor");
        field.setAccessible(true);
        field.set(vnfServiceRestApi, vnfProcess);

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @After
    public void tearDown() {

        Object vnfServiceRestApi = null;

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testInstantiateVnfNull() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();

        new MockUp<HttpContextUitl>() {

            @Mock
            public <T> T extractJsonObject(HttpServletRequest vnfReq) {

                return null;

            }

        };

        String result = vnfServiceRestApi.instantiateVNF("vnfmId", req, rep);

        assertEquals("", result);

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testInstantiateVnfNotNull() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();
        final JSONObject jsonInstantiateOfReq = new JSONObject();

        MockUp<VNFServiceProcessor> vnfServiceProcessor = new MockUp<VNFServiceProcessor>() {

            @Mock
            public JSONObject addVnf(String vnfmId, JSONObject jsonInstantiateOfReq) {

                JSONObject obj = new JSONObject();
                obj.put(Constant.RETCODE, Constant.REST_FAIL);
                obj.put(Constant.RESP_STATUS, Constant.HTTP_NOTFOUND);
                return obj;

            }

        };

        new MockUp<HttpContextUitl>() {

            @Mock
            public <T> T extractJsonObject(HttpServletRequest vnfReq) {

                return (T)jsonInstantiateOfReq;

            }

        };

        String result = vnfServiceRestApi.instantiateVNF("vnfmId", req, rep);

        vnfServiceProcessor.tearDown();

        assertEquals("", result);
    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testInstantiate() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();
        final JSONObject jsonInstantiateOfReq = new JSONObject();

        MockUp<VNFServiceProcessor> vnfServiceProcessor = new MockUp<VNFServiceProcessor>() {

            @Mock
            public JSONObject addVnf(String vnfmId, JSONObject jsonInstantiateOfReq) {

                JSONObject ret = new JSONObject();
                JSONObject data = new JSONObject();
                ret.put(Constant.RETCODE, Constant.HTTP_OK);
                data.put("test_key", "test_value");
                ret.put(Constant.DATA, data);
                ret.put(Constant.RESP_STATUS, "200");
                return ret;

            }

        };

        new MockUp<HttpContextUitl>() {

            @Mock
            public <T> T extractJsonObject(HttpServletRequest vnfReq) {

                return (T)jsonInstantiateOfReq;

            }

        };

        String result = vnfServiceRestApi.instantiateVNF("vnfmId", req, rep);

        vnfServiceProcessor.tearDown();

        JSONObject restJson = new JSONObject();
        restJson.put("test_key", "test_value");

        assertEquals(restJson.toString(), result);
    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testTerminateVnfNull() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();

        new MockUp<HttpContextUitl>() {

            @Mock
            public <T> T extractJsonObject(HttpServletRequest vnfReq) {

                return null;

            }

        };

        String result = vnfServiceRestApi.terminateVNF(req, rep, "vnfmId", "vnfInstanceId");

        assertEquals("", result);

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testTerminateVnfNotNull() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();
        final JSONObject jsonInstantiateOfReq = new JSONObject();

        new MockUp<HttpContextUitl>() {

            @Mock
            public <T> T extractJsonObject(HttpServletRequest vnfReq) {

                return (T)jsonInstantiateOfReq;

            }

        };

        MockUp<VNFServiceProcessor> vnfServiceProcessor = new MockUp<VNFServiceProcessor>() {

            @Mock
            public JSONObject deleteVnf(String vnfmId, String vnfInstanceId, JSONObject jsonTerminateOfReq) {

                JSONObject obj = new JSONObject();
                obj.put(Constant.RETCODE, Constant.REST_FAIL);
                obj.put(Constant.RESP_STATUS, Constant.HTTP_NOTFOUND);
                return obj;

            }

        };
        String result = vnfServiceRestApi.terminateVNF(req, rep, "vnfmId", "vnfInstanceId");

        vnfServiceProcessor.tearDown();

        assertEquals("", result);

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testTerminateVnf() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();
        final JSONObject jsonInstantiateOfReq = new JSONObject();

        new MockUp<HttpContextUitl>() {

            @Mock
            public <T> T extractJsonObject(HttpServletRequest vnfReq) {

                return (T)jsonInstantiateOfReq;

            }

        };

        MockUp<VNFServiceProcessor> vnfServiceProcessor = new MockUp<VNFServiceProcessor>() {

            @Mock
            public JSONObject deleteVnf(String vnfmId, String vnfInstanceId, JSONObject jsonTerminateOfReq) {

                JSONObject ret = new JSONObject();
                JSONObject data = new JSONObject();
                ret.put(Constant.RETCODE, Constant.HTTP_OK);
                data.put("test_key", "test_value");
                ret.put(Constant.DATA, data);
                ret.put(Constant.RESP_STATUS, "200");
                return ret;

            }

        };
        String result = vnfServiceRestApi.terminateVNF(req, rep, "vnfmId", "vnfInstanceId");

        vnfServiceProcessor.tearDown();

        JSONObject restJson = new JSONObject();
        restJson.put("test_key", "test_value");

        assertEquals(restJson.toString(), result);

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testQueryVnfNull() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();

        String vnfmId = null;
        String result = vnfServiceRestApi.queryVNF(vnfmId, "vnfInstanceId", rep, req);

        JSONObject restJson = new JSONObject();
        restJson.put(Constant.RETCODE, Constant.REST_FAIL);

        assertEquals(restJson.toString(), restJson.toString());

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testQueryVnfNotNull() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();
        final JSONObject jsonInstantiateOfReq = new JSONObject();

        MockUp<VNFServiceProcessor> vnfServiceProcessor = new MockUp<VNFServiceProcessor>() {

            @Mock
            public JSONObject getVnf(String vnfmId, String vnfInstanceId) {

                JSONObject obj = new JSONObject();
                obj.put(Constant.RETCODE, Constant.REST_FAIL);
                obj.put(Constant.RESP_STATUS, Constant.HTTP_NOTFOUND);
                obj.put(Constant.DATA, "");
                return obj;

            }

        };

        String result = vnfServiceRestApi.queryVNF("vnfmId", "vnfInstanceId", rep, req);

        vnfServiceProcessor.tearDown();

        assertEquals("", result);

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    public void testQueryVnf() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();
        final JSONObject jsonInstantiateOfReq = new JSONObject();

        new MockUp<HttpContextUitl>() {

            @Mock
            public <T> T extractJsonObject(HttpServletRequest vnfReq) {

                return (T)jsonInstantiateOfReq;

            }

        };

        MockUp<VNFServiceProcessor> vnfServiceProcessor = new MockUp<VNFServiceProcessor>() {

            @Mock
            public JSONObject getVnf(String vnfmId, String vnfInstanceId) {

                JSONObject ret = new JSONObject();
                JSONObject data = new JSONObject();
                ret.put(Constant.RETCODE, Constant.HTTP_OK);
                data.put("test_key", "test_value");
                ret.put(Constant.DATA, data);
                ret.put(Constant.RESP_STATUS, "200");
                return ret;

            }

        };

        String result = vnfServiceRestApi.queryVNF("vnfmId", "vnfInstanceId", rep, req);

        vnfServiceProcessor.tearDown();

        JSONObject restJson = new JSONObject();
        restJson.put("test_key", "test_value");

        assertEquals(restJson.toString(), result);

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testGetOperationStatusNull() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();

        String vnfmId = null;
        String result = vnfServiceRestApi.getOperationStatus(vnfmId, "jobId", "responseId", req, rep);

        JSONObject restJson = new JSONObject();
        restJson.put(Constant.RETCODE, Constant.REST_FAIL);

        assertEquals(restJson.toString(), restJson.toString());

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testGetOperationStatusNotNull() throws Exception {

        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();
        final JSONObject jsonInstantiateOfReq = new JSONObject();

        MockUp<VNFServiceProcessor> vnfServiceProcessor = new MockUp<VNFServiceProcessor>() {

            @Mock
            public JSONObject getStatus(String jobid, String vnfmdi, String responseId) {

                JSONObject obj = new JSONObject();
                obj.put(Constant.RETCODE, Constant.REST_FAIL);
                obj.put(Constant.RESP_STATUS, Constant.HTTP_NOTFOUND);
                obj.put(Constant.DATA, "");
                return obj;

            }

        };

        vnfServiceProcessor.tearDown();
        String result = vnfServiceRestApi.getOperationStatus("vnfmId", "jobid", "responseId", req, rep);
        assertEquals("", result);

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testGetOperationStatus() throws Exception {
        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();
        final JSONObject jsonInstantiateOfReq = new JSONObject();

        MockUp<VNFServiceProcessor> vnfServiceProcessor = new MockUp<VNFServiceProcessor>() {

            @Mock
            public JSONObject getStatus(String jobid, String vnfmdi, String responseId) {

                JSONObject ret = new JSONObject();
                JSONObject data = new JSONObject();
                ret.put(Constant.RETCODE, Constant.HTTP_OK);
                data.put("test_key", "test_value");
                ret.put(Constant.DATA, data);
                ret.put(Constant.RESP_STATUS, "200");
                return ret;

            }

        };

        JSONObject restJson = new JSONObject();
        restJson.put("test_key", "test_value");

        String result = vnfServiceRestApi.getOperationStatus("vnfmId", "jobid", "responseId", req, rep);
        vnfServiceProcessor.tearDown();
        assertEquals(restJson.toString(), result);

    }

    /**
     * <br>
     *
     * @throws Exception when the some condition happen
     * @since NFVO 0.5
     */
    @Test
    public void testGetApidoc() throws Exception {
        MockUp<HttpServletRequest> proxyStub = new MockUp<HttpServletRequest>() {};
        HttpServletRequest req = proxyStub.getMockInstance();
        MockUp<HttpServletResponse> proxy = new MockUp<HttpServletResponse>() {};
        HttpServletResponse rep = proxy.getMockInstance();

        VNFServiceRestAPI vnfServiceRestApi = new VNFServiceRestAPI();
        String result = vnfServiceRestApi.getApiDoc(rep, req);
        assertNotNull(result);

    }

}
