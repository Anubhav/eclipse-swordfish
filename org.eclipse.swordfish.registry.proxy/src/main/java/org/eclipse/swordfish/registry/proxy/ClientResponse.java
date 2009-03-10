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
package org.eclipse.swordfish.registry.proxy;

import org.eclipse.swordfish.registry.proxy.ProxyConstants.Status;

/**
 * Entity for in-bound response from RESTful service
 */
public interface ClientResponse {

	/**
	 *
	 * @return
	 */
	Status getStatus();

	/**
	 *
	 * @param status
	 */
	void setStatus(Status status);

	Object getEntity();

	void setEntity(Object entity);

}
