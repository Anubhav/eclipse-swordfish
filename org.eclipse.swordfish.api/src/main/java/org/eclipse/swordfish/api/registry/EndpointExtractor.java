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

/**
 * Responsible for extracting endpoint parts (endpoint address,
 * transport protocol) from the service description document.
 */
public interface EndpointExtractor<T extends ServiceDescription> {

	/**
	 * Extracts endpoint part from the given service description.
	 * @param endpointDocument A service description to parse.
	 * @return endpoint description, not <code>null</code>
	 */
	EndpointDescription<?, ?> extractEndpoint(T endpointDocument);

	/**
	 * Returns collection of service description types the endpoint extractor can handle.
	 * @return A collection of <code>Class</code> objects of supported types
	 * or empty collection if the endpoint extractor doesn't support any.
	 */
	Collection<Class<T>> getSupportedTypes();

}
