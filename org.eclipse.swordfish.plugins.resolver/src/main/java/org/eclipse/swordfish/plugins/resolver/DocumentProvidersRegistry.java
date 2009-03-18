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

import org.eclipse.swordfish.api.registry.EndpointDocumentProvider;
import org.springframework.util.Assert;

/**
 *
 */
public class DocumentProvidersRegistry {

	private Collection<EndpointDocumentProvider> providers;

	public EndpointDocumentProvider getPreferredProvider() {
		EndpointDocumentProvider preferredProvider = getProviders().iterator().next();
		for (EndpointDocumentProvider nextProvider : getProviders()) {
			if (nextProvider.getPriority() > preferredProvider.getPriority()) {
				preferredProvider = nextProvider;
			}
		}
		return preferredProvider;
	}

	public Collection<EndpointDocumentProvider> getProviders() {
		return Collections.unmodifiableCollection(providers);
	}

	public void setProviders(Collection<EndpointDocumentProvider> providers) {
		this.providers = providers;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(providers,
			"Endpoint document providers registry is not initialized properly");
		Assert.notEmpty(providers,
			"Endpoint document providers is not initialized properly");
	}

}
