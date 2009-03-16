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

import java.net.URI;
import java.util.Map;

import org.eclipse.swordfish.plugins.resolver.proxy.ProxyConstants.Method;

/**
 * Entity for out-bound request to RESTful service
 */
public interface ClientRequest {

	/**
	 * Get the method used to invoke remote service.
	 * @return a <code>ProxyConstants.Method</code> constant representing a HTTP method.
	 */
	Method getMethod();

	/**
	 * Set the method used to invoke remote service.
	 * @return a <code>ProxyConstants.Method</code> constant representing a HTTP method.
	 */
	void setMethod(Method method);

	/**
	 * Get properties for the request.
	 * @return a <code>Map</code> containing request specific properties.
	 */
	Map<String, String> getProperties();

	/**
	 * Set properties for the request.
	 * @return a <code>Map</code> containing request specific properties.
	 */
	void setProperties(Map<String, String> properties);

	Object getEntity();

	void setEntity(Object entity);

	Class<?> getEntityType();

	void setEntityType(Class<?> entityType);

	/**
	 * Get the URI (absolute or relative) of remote service.
	 * @return a <code>URL</code> of a service.
	 */
	URI getURI();

	/**
	 * Set the URI of remote service.
	 * @return a <code>URL</code> of a service.
	 */
	void setURI(URI uri);

}
