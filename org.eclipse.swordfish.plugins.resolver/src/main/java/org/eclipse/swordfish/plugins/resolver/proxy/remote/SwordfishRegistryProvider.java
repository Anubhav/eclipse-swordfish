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

import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.configuration.ConfigurationConsumer;
import org.eclipse.swordfish.api.registry.EndpointDocumentProvider;
import org.eclipse.swordfish.api.registry.ServiceDescription;
import org.eclipse.swordfish.plugins.resolver.proxy.ClientRequest;
import org.eclipse.swordfish.plugins.resolver.proxy.ClientResponse;
import org.eclipse.swordfish.plugins.resolver.proxy.RegistryProxy;
import org.eclipse.swordfish.plugins.resolver.proxy.WSDLList;
import org.eclipse.swordfish.plugins.resolver.proxy.ProxyConstants.Status;
import org.eclipse.swordfish.plugins.resolver.wsdl.ServiceDescriptionReader;
import org.eclipse.swordfish.plugins.resolver.wsdl.ServiceDescriptionReaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SwordfishRegistryProvider implements EndpointDocumentProvider, ConfigurationConsumer<String> {

    private final Logger logger = LoggerFactory.getLogger(SwordfishRegistryProvider.class);

	private static final String REGISTRY_URL_PROPERTY = "registryURL";

	private URL registryURL;

	public List<ServiceDescription<?>> getServiceProviderDescriptions(QName interfaceName) {
		List<ServiceDescription<?>> descriptions = new ArrayList<ServiceDescription<?>>();

		try {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("type", "portType");
			properties.put("targetNamespace", interfaceName.getNamespaceURI());
			properties.put("name", interfaceName.getLocalPart());

			ClientRequest request = RegistryProxyFactory.getInstance().createRequest();
			request.setProperties(properties);
			request.setEntityType(WSDLList.class);
			request.setURI(new URI("wsdl"));

			RegistryProxy proxy = RegistryProxyFactory.getInstance().createProxy(getRegistryURL());
			ClientResponse response = proxy.get(request);

			if (!response.getStatus().equals(Status.SUCCESS) &&
				!response.getStatus().equals(Status.NOT_FOUND)) {
				throw getExceptionFromResponse(response);
			}

			ServiceDescriptionReader<?> reader = ServiceDescriptionReaderFactory.createWSDL11Reader();
			WSDLList descriptionUrls = (WSDLList) response.getEntity();
			for (String url : descriptionUrls.getUrl()) {
				request = RegistryProxyFactory.getInstance().createRequest();
				request.setURI(new URI("wsdl/" + url));
				response = proxy.get(request);

				if (!response.getStatus().equals(Status.SUCCESS)) {
					throw getExceptionFromResponse(response);
				}

				Reader entityReader = new StringReader((String) response.getEntity());
				ServiceDescription<?> description = reader.readDescription(entityReader);
				descriptions.add(description);
				if (logger.isDebugEnabled()) {
					logger.debug("Successfully retrieved service description: "
						+ description.getServiceName() + " for portType: " + interfaceName);
				}
			}
		} catch (Exception e) {
			logger.error("Error resolving endpoint - couldn't retrieve "
				+ "service description for port type " + interfaceName, e);
			throw new SwordfishException("Error resolving endpoint - couldn't retrieve "
				+ "service description for port type " + interfaceName, e);
		}
		return descriptions;
	}

	public void onReceiveConfiguration(Map<String, String> configuration) {
		if (configuration != null &&
			configuration.containsKey(REGISTRY_URL_PROPERTY)) {
			try {
				registryURL = new URL(configuration.get(REGISTRY_URL_PROPERTY));
				if (logger.isDebugEnabled()) {
					logger.debug("Service registry URL has been set to: " + registryURL);
				}
			} catch (MalformedURLException e) {
				logger.error("Couldn't initialize Swordfish "
					+ "registry proxy: malformed registry URL specified", e);
				throw new IllegalArgumentException("Couldn't initialize Swordfish "
					+ "registry proxy: malformed registry URL specified", e);
			}
		}
	}

	private RuntimeException getExceptionFromResponse(ClientResponse response) {
		RuntimeException exception = null;
		Throwable remoteException = null;

		if (response.getEntity() != null &&
			Throwable.class.isAssignableFrom(response.getEntity().getClass())) {
			remoteException = (Throwable) response.getEntity();
		}

		if (response.getStatus().equals(Status.MALFORMED_QUERY)) {
			exception = new SwordfishException("Request has not been recognized by "
				+ "remote registry - wrong query specified", remoteException);
		} else {
			exception = new SwordfishException("Error resolving endpoint - cannot retrieve "
				+ "service description", remoteException);
		}
		return exception;
	}

	public String getId() {
		return getClass().getName();
	}

	public URL getRegistryURL() {
		return registryURL;
	}

	public void setRegistryURL(URL registryURL) {
		this.registryURL = registryURL;
	}
}
