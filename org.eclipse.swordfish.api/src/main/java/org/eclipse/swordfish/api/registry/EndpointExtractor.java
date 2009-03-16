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
 * Responsible for extracting endpoint parts (endpoint address,
 * transport protocol) from the service description document.
 */
public interface EndpointExtractor {

	/**
	 * Checks if the passed description can be processed by this type of extractor.
	 * @param description A <code>ServiceDescription</code> to check.
	 * @return <code>true</code> if the extractor can handle passed service description,
	 * <code>false</code> otherwise.
	 */
	boolean canHandle(ServiceDescription<?> description);

	/**
	 * Extracts endpoint part from the given service description.
	 * @param endpointDocument A service description to parse.
	 * @return endpoint description, not <code>null</code>
	 */
	EndpointDescription extractEndpoint(ServiceDescription<?> description);

}
