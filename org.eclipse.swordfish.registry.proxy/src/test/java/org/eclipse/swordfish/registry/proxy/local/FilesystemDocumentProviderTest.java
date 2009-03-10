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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.eclipse.swordfish.registry.proxy.TestConstants.BOOKINGSERVICE_PORTTYPE_NAME;
import static org.eclipse.swordfish.registry.proxy.TestConstants.BOOKINGSERVICE_SERVICE_NAME;
import static org.eclipse.swordfish.registry.proxy.TestConstants.FAKE_PORTTYPE_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swordfish.api.registry.ServiceDescription;
import org.eclipse.swordfish.registry.proxy.local.FilesystemDocumentProvider;
import org.junit.Test;

public class FilesystemDocumentProviderTest {

	@Test
	public void testProviderInitialization() {
		FilesystemDocumentProvider provider = new FilesystemDocumentProvider();

		Map<String, String> providerPorps = new HashMap<String, String>();
		providerPorps.put("wsdlStorage", getClass().getResource("BookingServiceImpl.zip").toString());
		provider.onReceiveConfiguration(providerPorps);

		assertNotNull(provider.getWsdlManager());
		// there are 2 entries for each definition in definitions map
		assertEquals(2, provider.getWsdlManager().getDefinitions().size());

		providerPorps.put("wsdlStorage", "non-existing url");
		try {
			provider.onReceiveConfiguration(providerPorps);
			fail("Provider initialization should fail");
		} catch (IllegalArgumentException e) {
			// expected result
		}
	}

	@Test
	public void testSuccessfullServiceDescriptionRetrieval() {
		FilesystemDocumentProvider provider = new FilesystemDocumentProvider();

		Map<String, String> providerPorps = new HashMap<String, String>();
		providerPorps.put("wsdlStorage", getClass().getResource("BookingServiceImpl.zip").toString());
		provider.onReceiveConfiguration(providerPorps);

		List<ServiceDescription<?>> descriptions =
			provider.getServiceProviderDescriptions(BOOKINGSERVICE_PORTTYPE_NAME);

		assertNotNull(descriptions);
		assertEquals(1, descriptions.size());
		assertEquals(BOOKINGSERVICE_SERVICE_NAME, descriptions.get(0).getServiceQName());
	}

	@Test
	public void testNoServiceDescriptionFound() {
		FilesystemDocumentProvider provider = new FilesystemDocumentProvider();

		Map<String, String> providerPorps = new HashMap<String, String>();
		providerPorps.put("wsdlStorage", getClass().getResource("BookingServiceImpl.zip").toString());
		provider.onReceiveConfiguration(providerPorps);

		List<ServiceDescription<?>> descriptions =
			provider.getServiceProviderDescriptions(FAKE_PORTTYPE_NAME);

		assertNotNull(descriptions);
		assertEquals(0, descriptions.size());
	}

}
