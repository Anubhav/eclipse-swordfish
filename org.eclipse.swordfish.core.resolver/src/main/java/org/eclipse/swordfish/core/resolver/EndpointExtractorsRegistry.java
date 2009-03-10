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

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.registry.EndpointExtractor;

/**
 *
 */
public class EndpointExtractorsRegistry {

	private Collection<EndpointExtractor<?>> extractors;

	public EndpointExtractor getExtractorByType(Class<?> type) {
		EndpointExtractor compatibleExtractor = null;

		if (extractors == null) {
			throw new SwordfishException("Couldn't find matching extractor, "
				+ "extractors registry is not initialized properly");
		}

		for (EndpointExtractor extractor : extractors) {
			if (extractor.getSupportedTypes().contains(type)) {
				compatibleExtractor = extractor;
				break;
			}
		}
		return compatibleExtractor;
	}

	public Collection<EndpointExtractor<?>> getExtractors() {
		return extractors;
	}

	public void setExtractors(Collection<EndpointExtractor<?>> extractors) {
		this.extractors = extractors;
	}
}
