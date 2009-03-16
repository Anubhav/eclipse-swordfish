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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.registry.EndpointDescription;
import org.eclipse.swordfish.api.registry.ServiceDescription;
import org.eclipse.swordfish.api.registry.EndpointDocumentProvider;
import org.eclipse.swordfish.api.registry.EndpointExtractor;
import org.eclipse.swordfish.core.resolver.ServiceResolver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 *
 */
public class ServiceResolverImpl implements ServiceResolver, InitializingBean {

	private EndpointExtractorsRegistry extractorsRegistry;

	private EndpointDocumentProvider serviceDescriptionProvider;

	public List<EndpointDescription> getEndpointsFor(QName portType) {
		List<EndpointDescription> endpoints = new ArrayList<EndpointDescription>();

		Collection<ServiceDescription<?>> descriptions =
			getServiceDescriptionProvider().getServiceProviderDescriptions(portType);

		if (!descriptions.isEmpty()) {
			for (ServiceDescription<?> description : descriptions) {
				EndpointExtractor extractor = getCompatibleExtractor(description);
				if (extractor != null) {
					endpoints.add(extractor.extractEndpoint(description));
				} else {
					throw new SwordfishException("Couldn't process service description + ["
						+ description.getServiceName() + "], no suitable extractor found");
				}
			}
		}
		return endpoints;
	}

	public EndpointExtractor getCompatibleExtractor(ServiceDescription<?> description) {
		EndpointExtractor compatibleExtractor = null;

		for (EndpointExtractor extractor : getEndpointExtractors()) {
			if (extractor.canHandle(description)) {
				compatibleExtractor = extractor;
				break;
			}
		}
		return compatibleExtractor;
	}

	public Collection<EndpointExtractor> getEndpointExtractors() {
		return extractorsRegistry.getExtractors();
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

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(serviceDescriptionProvider,
			"Couldn't initialize ServiceResolver, no service description providers were found");
		Assert.notNull(extractorsRegistry);
	}

}
