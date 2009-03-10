/*******************************************************************************
 * Copyright (c) 2008, 2009 SOPERA GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SOPERA GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.swordfish.registry.proxy.local;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import static org.eclipse.swordfish.registry.proxy.TestConstants.BOOKINGSERVICE_PORTTYPE_NAME;
import static org.eclipse.swordfish.registry.proxy.TestConstants.BOOKINGSERVICE_SERVICE_NAME;

import javax.wsdl.Definition;

import org.eclipse.swordfish.registry.proxy.local.WSDLManagerImpl;
import org.junit.Before;
import org.junit.Test;

public class WSDLManagerImplTest {

	private WSDLManagerImpl wsdlManager;

	@Before
	public void setUp() throws Exception {
		wsdlManager = new WSDLManagerImpl();
		wsdlManager.setupWSDLs(getClass().getResource("BookingServiceImpl.zip"));
	}

	@Test
	public void test1AvailableLocations() throws Exception {
		Definition description = wsdlManager.getDefinition(BOOKINGSERVICE_PORTTYPE_NAME);
		assertNotNull(description);
		assertEquals(description.getServices().size(), 1);
		assertNotNull(description.getService(BOOKINGSERVICE_SERVICE_NAME));
	}

}
