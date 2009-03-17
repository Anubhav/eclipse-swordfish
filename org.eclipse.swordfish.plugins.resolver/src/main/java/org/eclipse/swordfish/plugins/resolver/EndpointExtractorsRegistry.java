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
package org.eclipse.swordfish.plugins.resolver;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.swordfish.api.registry.EndpointExtractor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 *
 */
public class EndpointExtractorsRegistry implements InitializingBean {

	private Collection<EndpointExtractor> extractors;

	public Collection<EndpointExtractor> getExtractors() {
		return Collections.unmodifiableCollection(extractors);
	}


	public void setExtractors(Collection<EndpointExtractor> extractors) {
		this.extractors = extractors;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(extractors,
			"Endpoint extractors registry is not initialized properly");
		Assert.notEmpty(extractors,
			"Endpoint extractors registry is not initialized properly");
	}
}
