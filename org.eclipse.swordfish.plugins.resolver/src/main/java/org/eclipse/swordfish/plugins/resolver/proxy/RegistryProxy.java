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
package org.eclipse.swordfish.plugins.resolver.proxy;

/**
 * Base interface of client API.
 * Its instances can be retrieved by calling the <code>createProxy(URL baseUrl)</code>
 * method of <code>RegistryProxyFactory</code> from <code>org.eclipse.swordfish.registry.proxy.remote</code>.
 * package. The query parameters has to be specified as <code>ClientRequest</code> bean. The general
 * example of usage the API is shown below:
 *
 * <pre>
 *Map<String, String> properties = new LinkedHashMap<String, String>();
 *properties.put("type", "portType");
 *properties.put("targetNamespace", interfaceName.getNamespaceURI());
 *properties.put("name", interfaceName.getLocalPart());
 *
 *ClientRequest request = RegistryProxyFactory.getInstance().createRequest();
 *request.setProperties(properties);
 *request.setEntityType(WSDLList.class);
 *
 *RegistryProxy proxy = RegistryProxyFactory.getInstance().createProxy(getRegistryURL());
 *ClientResponse response = proxy.get(request);
 * </pre>
 *
 */
public interface RegistryProxy {

	/**
	 * Queries remote service using GET method.
	 * @param request A bean containing request properties
	 * @return A <code>ClientResponse</code> with invocatrion results.
	 */
	ClientResponse get(ClientRequest request);

	/**
	 *
	 * @param request
	 * @return
	 */
	ClientResponse post(ClientRequest request);

}
