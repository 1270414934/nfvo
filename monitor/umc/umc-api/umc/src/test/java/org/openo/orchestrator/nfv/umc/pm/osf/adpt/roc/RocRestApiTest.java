package org.openo.orchestrator.nfv.umc.pm.osf.adpt.roc;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.openo.orchestrator.nfv.umc.pm.adpt.roc.RocAdptImpl;
import org.openo.orchestrator.nfv.umc.pm.adpt.roc.RocConfiguration;
import org.openo.orchestrator.nfv.umc.pm.task.PmTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocRestApiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RocRestApiTest.class);
	@Test
	public void testGetCommPara()
	{
		RocConfiguration.setRocServerAddr("127.0.0.1:8087");
		try {
			Properties p = RocAdptImpl.getCommPara("fab77cbc-b7a5-4667-9f60-01c465f2ffe3", "nfv.vdu.linux");
			assertEquals(p.getProperty("RESID"), "fab77cbc-b7a5-4667-9f60-01c465f2ffe3");
		} catch (PmTaskException e) {
			Assert.fail("Exception" + e.getMessage());
		}
		try {
			Properties p = RocAdptImpl.getCommPara("2e394a8b-0cb3-4094-b577-0d56096dd882", "nfv.host.linux");
			assertEquals(p.getProperty("RESID"), "2e394a8b-0cb3-4094-b577-0d56096dd882");
		} catch (PmTaskException e) {
			Assert.fail("Exception" + e.getMessage());
		}

		String name = RocAdptImpl.getResourceName("2e394a8b-0cb3-4094-b577-0d56096dd882", "nfv.host.linux");
		LOGGER.info("test getResourceName:" + name);
	}
}
