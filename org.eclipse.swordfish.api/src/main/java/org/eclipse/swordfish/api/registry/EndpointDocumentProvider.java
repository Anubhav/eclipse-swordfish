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

import java.util.Collection;
import javax.xml.namespace.QName;

/**
 * Base interface of services responsible for retrieval of service information
 * (descriptions, policies) from different kinds of location.
 */
public interface EndpointDocumentProvider {

	/**
	 *
	 * @param portType A qualified name of port type element
	 *
	 * @return A <code>Collection</code> of service descriptions providing operations
         * specified by given port type. Returns an empty collection if no service
         * descriptions were found.
	 */
	Collection<ServiceDescription<?>> getServiceProviderDescriptions(QName portType);

}
