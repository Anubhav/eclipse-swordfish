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
package org.eclipse.swordfish.api.registry;

/**
 * Represents an endpoint part of service descriptions. Includes endpoint address
 * and protocol used to access the service.
 */
public interface EndpointDescription<T, U> {

	/**
	 *
	 * @return T endpoint address
	 */
	T getEndpointAddress();

	/**
	 *
	 * @return U transport used to communicate with service
	 */
	U getEndpointTransport();
}
