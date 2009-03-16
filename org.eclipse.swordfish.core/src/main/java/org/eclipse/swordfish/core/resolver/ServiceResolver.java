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
package org.eclipse.swordfish.core.resolver;

import java.util.Collection;
import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.registry.EndpointDescription;
import org.eclipse.swordfish.api.registry.EndpointDocumentProvider;
import org.eclipse.swordfish.api.registry.EndpointExtractor;

/**
 * Resolves information about service provider endpoints for specified service.
 */
public interface ServiceResolver {

	/**
	 * @param portType a qualified name of portType
	 * @return a collection of <code>EndpointDescription</code> specifying provider
	 *  endpoints matching a given portType.
	 */
	Collection<EndpointDescription> getEndpointsFor(QName portType);

	EndpointDocumentProvider getServiceDescriptionProvider();

	Collection<EndpointExtractor> getEndpointExtractors();

}
