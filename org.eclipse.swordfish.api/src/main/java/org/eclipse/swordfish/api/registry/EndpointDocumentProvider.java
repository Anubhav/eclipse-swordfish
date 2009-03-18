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
 * Interface of the provider responsible for retrieval of service related
 * information (descriptions, policies) from different kinds of location.
 */
public interface EndpointDocumentProvider {

	/**
	 * Perform a query to the registry it can communicate with and returns
	 * a <code>Collection</code> of <code>ServiceDescription</code> objects
	 * implementing specified interface.
	 * @param interfaceName A qualified name of the interface of the service.
	 * Corresponds to the value of <code>portType</code> element.
	 * @return A <code>Collection</code> of the service descriptions providing
	 * operations specified by given port type. Returns an empty collection if
	 * no service descriptions were found.
	 */
	Collection<ServiceDescription<?>> getServiceProviderDescriptions(QName interfaceName);

	/**
	 * Get the provider's priority. If several document providers are registered
	 * at runtime the one with the highest priority will be chosen.
	 */
	int getPriority();

}
