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
package org.eclipse.swordfish.plugins.resolver.proxy.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import static org.eclipse.swordfish.plugins.resolver.proxy.ProxyConstants.Status;
import static org.eclipse.swordfish.plugins.resolver.proxy.TestConstants.BOOKINGSERVICE_PORTTYPE_NAME;
import static org.eclipse.swordfish.plugins.resolver.proxy.TestConstants.BOOKINGSERVICE_SERVICE_NAME;
import static org.eclipse.swordfish.plugins.resolver.proxy.TestConstants.FAKE_PORTTYPE_NAME;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.registry.ServiceDescription;
import org.eclipse.swordfish.plugins.resolver.proxy.ClientRequest;
import org.eclipse.swordfish.plugins.resolver.proxy.ClientResponse;
import org.eclipse.swordfish.plugins.resolver.proxy.WSDLList;
import org.eclipse.swordfish.plugins.resolver.proxy.impl.AbstractProxy;
import org.eclipse.swordfish.plugins.resolver.proxy.impl.ClientResponseImpl;
import org.eclipse.swordfish.plugins.resolver.proxy.remote.RegistryProxyFactory;
import org.eclipse.swordfish.plugins.resolver.proxy.remote.SwordfishRegistryProvider;
import org.eclipse.swordfish.plugins.resolver.proxy.remote.mock.RegistryProxyMock;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class SwordfishRegistryProviderTest {

	private static final String REGISTRY_URL = "http://localhost:9001/registry";

	@BeforeClass
	public static void initRegistryProxyMock() {
		RegistryProxyFactory.getInstance().setProxyClass(RegistryProxyMock.class);
	}

	@Test
	public void testProviderInitialization() {
		SwordfishRegistryProvider provider = new SwordfishRegistryProvider();

		Map<String, Object> providerPorps = new HashMap<String, Object>();
		providerPorps.put("registryURL", REGISTRY_URL);
		provider.onReceiveConfiguration(providerPorps);

		assertNotNull(provider.getRegistryURL());
		assertEquals(REGISTRY_URL, provider.getRegistryURL().toString());

		providerPorps.put("registryURL", "non-existing url");
		try {
			provider.onReceiveConfiguration(providerPorps);
			fail("Provider initialization should fail");
		} catch (IllegalArgumentException e) {
			// expected result
		}
	}

	@Test
	public void testSuccessfullServiceDescriptionRetrieval() {
		RegistryProxyMock.setProxyHandler(new AbstractProxy () {
			@Override
			public ClientResponse invoke(ClientRequest request) {
				ClientResponse response = new ClientResponseImpl();
				response.setStatus(Status.SUCCESS);
				if (WSDLList.class.equals(request.getEntityType())) {
					WSDLList list = new WSDLList();
					list.getUrl().add("test-description-id");
					response.setEntity(list);
				}
				if (request.getURI().toString().endsWith("test-description-id")) {
					response.setEntity(getResourceAsString("BookingServiceImpl.wsdl"));
				}
				return response;
			}
		});

		SwordfishRegistryProvider provider = new SwordfishRegistryProvider();
		Map<String, Object> providerPorps = new HashMap<String, Object>();
		providerPorps.put("registryURL", REGISTRY_URL);
		provider.onReceiveConfiguration(providerPorps);

		List<ServiceDescription<?>> descriptions =
			provider.getServiceProviderDescriptions(BOOKINGSERVICE_PORTTYPE_NAME);

		assertNotNull(descriptions);
		assertEquals(1, descriptions.size());
		assertEquals(BOOKINGSERVICE_SERVICE_NAME, descriptions.get(0).getServiceName());
	}

	@Test
	public void testFailedServiceDescriptionRetrieval() {
		final ClientResponse response = new ClientResponseImpl();
		RegistryProxyMock.setProxyHandler(new AbstractProxy () {
			@Override
			public ClientResponse invoke(ClientRequest request) {
				return response;
			}
		});

		SwordfishRegistryProvider provider = new SwordfishRegistryProvider();
		Map<String, Object> providerPorps = new HashMap<String, Object>();
		providerPorps.put("registryURL", REGISTRY_URL);
		provider.onReceiveConfiguration(providerPorps);

		response.setStatus(Status.NOT_FOUND);
		response.setEntity(new WSDLList());

		List<ServiceDescription<?>> descriptions =
			provider.getServiceProviderDescriptions(FAKE_PORTTYPE_NAME);
		assertNotNull(descriptions);
		assertEquals(0, descriptions.size());

		response.setStatus(Status.MALFORMED_QUERY);
		try {
			descriptions = provider.getServiceProviderDescriptions(FAKE_PORTTYPE_NAME);
			fail("Service description retrieval should fail");
		} catch (SwordfishException e) {
			// expected result
		}
	}

	private String getResourceAsString(String resource) {
		Reader resourceReader = new InputStreamReader(getClass().getResourceAsStream(resource));
		BufferedReader reader = new BufferedReader(resourceReader);
		StringBuilder sb = new StringBuilder();
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
	           sb.append(line + "\n");
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
		return sb.toString();
	}
}
