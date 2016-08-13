package org.openo.nfvo.vimadapter.service.openstack.entry;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.nfvo.vimadapter.service.openstack.resourcemgr.NetworkQuery;
import org.openo.nfvo.vimadapter.service.openstack.resourcemgr.RpQuery;
import org.openo.nfvo.vimadapter.service.openstack.resourcemgr.VendorQuery;
import org.openo.nfvo.vimadapter.service.openstack.resourcemgr.VmQuery;

import mockit.Mock;
import mockit.MockUp;
import net.sf.json.JSONObject;

public class ResourceMgrOpenstackTest {

	@Test
    public void testGetRps() throws Exception {
		ResourceMgrOpenstack resourceMgrOpenstack = new ResourceMgrOpenstack();
		Map<String, String> conMap = new HashMap<String, String>();
		conMap.put("extraInfo", "{}");
		conMap.put("domainName", "domainName");
		conMap.put("authenticMode", "authenticMode");
		conMap.put("url", "url");
		conMap.put("userName", "userName");
		conMap.put("userPwd", "userPwd");
		conMap.put("vimId", "vimId");
		conMap.put("authenticateMode", "authenticateMode");
		conMap.put("vimName", "vimName");
		new MockUp<RpQuery>(){
			@Mock
		    public List<JSONObject> getRps() {
				return null;
			}
		};
		List<JSONObject> result = resourceMgrOpenstack.getRps(conMap, null);
		List<JSONObject> expectedResult = null;
		assertEquals(expectedResult, result);
	}
	
	@Test
    public void testGetVendors() throws Exception {
		ResourceMgrOpenstack resourceMgrOpenstack = new ResourceMgrOpenstack();
		Map<String, String> conMap = new HashMap<String, String>();
		conMap.put("extraInfo", "{}");
		conMap.put("domainName", "domainName");
		conMap.put("authenticMode", "authenticMode");
		conMap.put("url", "url");
		conMap.put("userName", "userName");
		conMap.put("userPwd", "userPwd");
		conMap.put("vimId", "vimId");
		conMap.put("authenticateMode", "authenticateMode");
		conMap.put("vimName", "vimName");
		new MockUp<VendorQuery>(){
			@Mock
		    public List<JSONObject> getVendors() {
				return null;
			}
		};
		List<JSONObject> result = resourceMgrOpenstack.getVendors(conMap, null);
		List<JSONObject> expectedResult = null;
		assertEquals(expectedResult, result);
	}
	
	@Test
    public void testGetVms() throws Exception {
		ResourceMgrOpenstack resourceMgrOpenstack = new ResourceMgrOpenstack();
		Map<String, String> conMap = new HashMap<String, String>();
		conMap.put("extraInfo", "{}");
		conMap.put("domainName", "domainName");
		conMap.put("authenticMode", "authenticMode");
		conMap.put("url", "url");
		conMap.put("userName", "userName");
		conMap.put("userPwd", "userPwd");
		conMap.put("vimId", "vimId");
		conMap.put("authenticateMode", "authenticateMode");
		conMap.put("vimName", "vimName");
		new MockUp<VmQuery>(){
			@Mock
		    public List<JSONObject> getVms() {
				return null;
			}
		};
		List<JSONObject> result = resourceMgrOpenstack.getVms(conMap, null);
		List<JSONObject> expectedResult = null;
		assertEquals(expectedResult, result);
	}
	
	@Test
    public void testGetNetworks() throws Exception {
		ResourceMgrOpenstack resourceMgrOpenstack = new ResourceMgrOpenstack();
		Map<String, String> conMap = new HashMap<String, String>();
		conMap.put("extraInfo", "{}");
		conMap.put("domainName", "domainName");
		conMap.put("authenticMode", "authenticMode");
		conMap.put("url", "url");
		conMap.put("userName", "userName");
		conMap.put("userPwd", "userPwd");
		conMap.put("vimId", "vimId");
		conMap.put("authenticateMode", "authenticateMode");
		conMap.put("vimName", "vimName");
		new MockUp<NetworkQuery>(){
			@Mock
		    public List<JSONObject> getNetworks() {
				return null;
			}
		};
		List<JSONObject> result = resourceMgrOpenstack.getNetworks(conMap, null);
		List<JSONObject> expectedResult = null;
		assertEquals(expectedResult, result);
	}
	
}
