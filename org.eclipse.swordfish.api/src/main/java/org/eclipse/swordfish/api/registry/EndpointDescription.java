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
 * Description of particular endpoint of the service.
 */
public interface EndpointDescription {

	/**
	 *
	 * @return An endpoint address, not <code>null</code>.
	 */
	String getAddress();

	/**
	 *
	 * @return An endpoint name, not <code>null</code>.
	 */
	String getName();

	/**
	 *
	 * @return A description of the service containing endpoint.
	 */
	ServiceDescription getServiceDescription();

}
