package org.eclipse.swordfish.core.test;

import org.eclipse.swordfish.api.context.SwordfishContext;
import org.eclipse.swordfish.core.test.util.OsgiSupport;
import org.eclipse.swordfish.core.test.util.base.TargetPlatformOsgiTestCase;

/**
 * The central sentinel test that checks if platform has been started correctly. The test is launched after all bundles are started
 * and all spring application contexts created
 *
 */
public class CentralSentinelTest extends TargetPlatformOsgiTestCase {
    public void test1SwordfishHasStarted() {
        SwordfishContext swordfishContext = OsgiSupport.getReference(bundleContext, SwordfishContext.class);
        assertNotNull(swordfishContext);
        assertNotNull(swordfishContext.getConfigurationService());
        assertNotNull(swordfishContext.getEventService());
    }
}
