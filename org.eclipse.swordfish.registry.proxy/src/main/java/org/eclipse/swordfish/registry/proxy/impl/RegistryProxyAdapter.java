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
package org.eclipse.swordfish.registry.proxy.impl;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swordfish.registry.proxy.ClientRequest;
import org.eclipse.swordfish.registry.proxy.ClientResponse;
import org.eclipse.swordfish.registry.proxy.RegistryProxy;
import org.eclipse.swordfish.registry.proxy.ProxyConstants.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class RegistryProxyAdapter implements RegistryProxy {

    private final Logger logger = LoggerFactory.getLogger(RegistryProxyAdapter.class);
	
	private AbstractProxy proxy;
	private URL baseURL;

	public RegistryProxyAdapter(AbstractProxy proxy, URL baseURL) {
		this.proxy = proxy;
		this.baseURL = baseURL;
	}

	public ClientResponse get(ClientRequest request) {
		ClientResponse response = null;
		try {
			if (request.getMethod() == null) {
				request.setMethod(Method.GET);
			}
			if (request.getProperties() == null) {
				request.setProperties(new HashMap<String, String>());
			}
			if (request.getURI() == null || !request.getURI().isAbsolute()) {
				request.setURI(prepareURL(request.getURI(), request.getProperties()));
			}
			response = proxy.invoke(request);
		} catch (Exception e) {
			logger.error("Couldn't execute " + request.getMethod() + " request: ", e);
			throw new IllegalStateException("Couldn't execute " + request.getMethod() + " request: ", e);
		}
		return response;
	}

	public ClientResponse post(ClientRequest request) {
		throw new IllegalStateException("Not yet implemented");
	}

	private URI prepareURL(URI relative, Map<String, String> properties) throws Exception {
		StringBuilder url = new StringBuilder(getBaseURL().toString());
		if (url.charAt(url.length() - 1) == '/') {
			url.setLength(url.length() - 1);
		}

		if (relative != null) {
			if (url.charAt(url.length() - 1) != '/') {
				url.append('/');
			}
			String identityPart = relative.toString();
			if (identityPart.startsWith(".")) {
				identityPart = identityPart.substring(1);
			}
			if (identityPart.startsWith("/")) {
				identityPart = identityPart.substring(1);
			}
			url.append(identityPart);
		}

		if (properties.size() > 0) {
			url.append("?");
			int keyValueIndex = 0;
			for (Map.Entry<String, String> property : properties.entrySet()) {
				url.append(property.getKey()).append("=").append(property.getValue());
				if (keyValueIndex < properties.size() - 1) {
					url.append("&");
					keyValueIndex++;
				}
			}
		}
		return new URI(url.toString());
	}

	public AbstractProxy getProxy() {
		return proxy;
	}

	public URL getBaseURL() {
		return baseURL;
	}
}
