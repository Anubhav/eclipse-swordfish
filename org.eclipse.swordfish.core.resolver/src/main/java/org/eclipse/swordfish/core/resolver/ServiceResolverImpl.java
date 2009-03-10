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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.registry.EndpointDescription;
import org.eclipse.swordfish.api.registry.ServiceDescription;
import org.eclipse.swordfish.api.registry.EndpointDocumentProvider;
import org.eclipse.swordfish.api.registry.EndpointExtractor;
import org.eclipse.swordfish.core.resolver.api.ServiceResolver;

/**
 *
 */
public class ServiceResolverImpl implements ServiceResolver {

	private EndpointExtractorsRegistry extractorsRegistry;

	private EndpointDocumentProvider serviceDescriptionProvider;

	public List<EndpointDescription<?, ?>> getEndpointsFor(QName portType) {
		if (serviceDescriptionProvider == null) {
			throw new SwordfishException("Couldn't get endpoints for: "
				+ portType + ", no registered document providers were found");
		}

		List<EndpointDescription<?, ?>> endpoints = new ArrayList<EndpointDescription<?, ?>>();
		Collection<ServiceDescription<?>> descriptions =
			serviceDescriptionProvider.getServiceProviderDescriptions(portType);

		if (!descriptions.isEmpty()) {
			for (ServiceDescription<?> description : descriptions) {
				EndpointExtractor<ServiceDescription<?>> extractor =
					extractorsRegistry.getExtractorByType(description.getClass());
				if (extractor != null) {
					endpoints.add(extractor.extractEndpoint(description));
				}
			}
		}
		return endpoints;
	}

	public EndpointExtractorsRegistry getExtractorsRegistry() {
		return extractorsRegistry;
	}

	public void setExtractorsRegistry(EndpointExtractorsRegistry extractorsRegistry) {
		this.extractorsRegistry = extractorsRegistry;
	}

	public EndpointDocumentProvider getServiceDescriptionProvider() {
		return serviceDescriptionProvider;
	}

	public void setServiceDescriptionProvider(EndpointDocumentProvider serviceDescriptionProvider) {
		this.serviceDescriptionProvider = serviceDescriptionProvider;
	}
}
