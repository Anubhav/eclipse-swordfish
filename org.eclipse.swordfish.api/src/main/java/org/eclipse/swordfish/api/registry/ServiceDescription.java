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

import javax.xml.namespace.QName;

/**
 * A basic entity used to present a service description.
 *
 * @param <T> A service description wrapped by this interface.
 */
public interface ServiceDescription<T> {

	/**
	 * Get an actual type of the description object, used by
	 * <code>EndpointExtractor</code> to identify the supported descriptions.
	 * @return A <code>Class</code> object on an entity.
	 */
	Class<T> getType();

	/**
	 * Get the qualified name of the service the service description presents.
	 * @return A qualified name of the service, not <code>null</code>.
	 */
	QName getServiceName();

}
