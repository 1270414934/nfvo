/*
 * Copyright 2016, Huawei Technologies Co., Ltd.
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
package org.openo.nfvo.vimadapter.service.openstack.entry;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openo.nfvo.vimadapter.service.constant.Constant;
import org.openo.nfvo.vimadapter.service.openstack.networkmgr.OpenstackNetwork;
import org.openo.nfvo.vimadapter.service.openstack.networkmgr.OpenstackSubNet;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class NetworkMgrOpenstackTest {

	@Test
    public void testCreate() throws Exception {
		NetworkMgrOpenstack networkMgrOpenstack = new NetworkMgrOpenstack();
		JSONObject network = new JSONObject();
		Map<String, String> conInfoMap = new HashMap<String, String>();
		conInfoMap.put("extraInfo", "{}");
		conInfoMap.put("domainName", "domainName");
		conInfoMap.put("authenticMode", "authenticMode");
		conInfoMap.put("url", "url");
		conInfoMap.put("userName", "userName");
		conInfoMap.put("userPwd", "userPwd");
		new MockUp<OpenstackNetwork>(){
			@Mock
		    public JSONObject createNetwork(JSONObject network) {
				return new JSONObject();
			}
		};
		JSONObject result = networkMgrOpenstack.create(network, conInfoMap);
		JSONObject expectedResult = new JSONObject();
		assertEquals(expectedResult, result);
	}
	

	@Test
    public void testRemove() throws Exception {
		NetworkMgrOpenstack networkMgrOpenstack = new NetworkMgrOpenstack();
		JSONObject network = new JSONObject();
		Map<String, String> conInfoMap = new HashMap<String, String>();
		conInfoMap.put("extraInfo", "{}");
		conInfoMap.put("domainName", "domainName");
		conInfoMap.put("authenticMode", "authenticMode");
		conInfoMap.put("url", "url");
		conInfoMap.put("userName", "userName");
		conInfoMap.put("userPwd", "userPwd");
		new MockUp<OpenstackNetwork>(){
			@Mock
		    public int removeNetwork(JSONObject network) {
				return Constant.HTTP_OK_STATUS_CODE;
			}
		};
		int result = networkMgrOpenstack.remove(network, conInfoMap);
		int expectedResult = Constant.HTTP_OK_STATUS_CODE;
		assertEquals(expectedResult, result);
	}
	
	@Test
    public void testCreateSubNet() throws Exception {
		NetworkMgrOpenstack networkMgrOpenstack = new NetworkMgrOpenstack();
		JSONObject network = new JSONObject();
		Map<String, String> conInfoMap = new HashMap<String, String>();
		conInfoMap.put("extraInfo", "{}");
		conInfoMap.put("domainName", "domainName");
		conInfoMap.put("authenticMode", "authenticMode");
		conInfoMap.put("url", "url");
		conInfoMap.put("userName", "userName");
		conInfoMap.put("userPwd", "userPwd");
		new MockUp<OpenstackSubNet>(){
			@Mock
		    public JSONObject createSubNet(JSONObject network) {
				return new JSONObject();
			}
		};
		JSONObject result = networkMgrOpenstack.createSubNet(network, conInfoMap);
		JSONObject expectedResult = new JSONObject();
		assertEquals(expectedResult, result);
	}
	
	@Test
    public void testDeleteSubNet() throws Exception {
		NetworkMgrOpenstack networkMgrOpenstack = new NetworkMgrOpenstack();
		Map<String, String> conInfoMap = new HashMap<String, String>();
		conInfoMap.put("extraInfo", "{}");
		conInfoMap.put("domainName", "domainName");
		conInfoMap.put("authenticMode", "authenticMode");
		conInfoMap.put("url", "url");
		conInfoMap.put("userName", "userName");
		conInfoMap.put("userPwd", "userPwd");
		new MockUp<OpenstackSubNet>(){
			@Mock
		    public int deleteSubNet(String id) {
				return Constant.HTTP_OK_STATUS_CODE;
			}
		};
		int result = networkMgrOpenstack.deleteSubNet("id",conInfoMap);
		int expectedResult = Constant.HTTP_OK_STATUS_CODE;
		assertEquals(expectedResult, result);
	}
	
}
