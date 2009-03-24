package org.eclipse.swordfish.core.test;

import org.eclipse.swordfish.api.context.SwordfishContext;
import org.eclipse.swordfish.core.test.util.OsgiSupport;
import org.eclipse.swordfish.core.test.util.base.TargetPlatformOsgiTestCase;
import org.osgi.framework.Bundle;
import org.springframework.osgi.util.OsgiBundleUtils;

/**
 * The central sentinel test that checks if platform has been started correctly. The test is launched after all bundles are started
 * and all spring application contexts created
 *
 */
public class CentralSentinelTest extends TargetPlatformOsgiTestCase {
    public void test1SwordfishHasStarted() {
        for (Bundle bundle : bundleContext.getBundles()) {
            if (OsgiBundleUtils.isFragment(bundle)) {
                assertEquals(String.format("Fragment bundle [%s] should be in the INSTALLED state", bundle.getSymbolicName()), Bundle.INSTALLED, bundle.getState());
            } else {
                assertEquals(String.format("Bundle [%s] should be in the ACTIVE state", bundle.getSymbolicName()), Bundle.ACTIVE, bundle.getState());

            }
        }
        SwordfishContext swordfishContext = OsgiSupport.getReference(bundleContext, SwordfishContext.class);
        assertNotNull(swordfishContext);
        assertNotNull(swordfishContext.getConfigurationService());
        assertNotNull(swordfishContext.getEventService());
    }
}
