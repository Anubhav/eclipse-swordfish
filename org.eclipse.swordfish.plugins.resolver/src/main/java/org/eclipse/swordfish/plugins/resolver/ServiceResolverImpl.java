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
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.policy.PolicyDefinitionDescription;
import org.eclipse.swordfish.api.policy.PolicyDefinitionProvider;
import org.eclipse.swordfish.api.policy.PolicyDescription;
import org.eclipse.swordfish.api.policy.PolicyExtractor;
import org.eclipse.swordfish.api.policy.PolicyProcessor;
import org.eclipse.swordfish.api.registry.EndpointDescription;
import org.eclipse.swordfish.api.registry.EndpointDocumentProvider;
import org.eclipse.swordfish.api.registry.EndpointExtractor;
import org.eclipse.swordfish.api.registry.ServiceDescription;
import org.eclipse.swordfish.core.resolver.ServiceResolver;
import org.springframework.util.Assert;

/**
 *
 */
public class ServiceResolverImpl implements ServiceResolver {

	private EndpointExtractorsRegistry extractorsRegistry;

	private PolicyExtractorsRegistry policyExtractorsRegistry;

	private EndpointDocumentProvider serviceDescriptionProvider;

	private PolicyDefinitionProvider policyDefinitionProvider;

	private PolicyProcessor<?> policyProcessor;

	public List<EndpointDescription> getEndpointsFor(QName portType) {
		return getEndpointsFor(portType, null);
	}

	public List<EndpointDescription> getEndpointsFor(QName portType, QName consumer) {
		if (serviceDescriptionProvider == null) {
			throw new SwordfishException("Couldn't get endpoints for: "
				+ portType + ", no registered document providers were found");
		}

		List<EndpointDescription> endpoints = new ArrayList<EndpointDescription>();
		Collection<ServiceDescription<?>> descriptions =
			serviceDescriptionProvider.getServiceProviderDescriptions(portType);
		final boolean isPolicySupportActive = policyExtractorsRegistry != null
				&& policyDefinitionProvider != null && policyProcessor != null
				&& consumer != null;
		PolicyDescription<?> consumerPolicy = null;
		if (isPolicySupportActive) {
			try {
				consumerPolicy = queryPolicies(consumer).iterator().next();
			} catch (NoSuchElementException e) {
				throw new SwordfishException("Policy support active and no consumer policy assigned.", e);
			}
		}
		if (!descriptions.isEmpty()) {
			for (final ServiceDescription<?> description : descriptions) {
				if (isPolicySupportActive) {
					final QName serviceProviderName = description.getServiceName();
					final List<PolicyDescription<?>> policies = queryPolicies(serviceProviderName);
					final PolicyDescription<?> agreed = policyProcessor.tradeAgreedPolicy(
							consumerPolicy, policies);
					if (agreed == null) {
						continue;
					}
				}
				Collection<EndpointExtractor> extractors =
					extractorsRegistry.getExtractorsByType(description.getClass());
				Assert.isTrue(extractors != null && extractors.size() > 0, "Could not find extractor for class = " + description.getClass());
				endpoints.add(extractors.iterator().next().extractEndpoint(description));

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

	public PolicyExtractorsRegistry getPolicyExtractorsRegistry() {
		return policyExtractorsRegistry;
	}

	public void setPolicyExtractorsRegistry(
			PolicyExtractorsRegistry policyExtractorsRegistry) {
		this.policyExtractorsRegistry = policyExtractorsRegistry;
	}

	public PolicyDefinitionProvider getPolicyDefinitionProvider() {
		return policyDefinitionProvider;
	}

	public void setPolicyDefinitionProvider(
			PolicyDefinitionProvider policyDefinitionProvider) {
		this.policyDefinitionProvider = policyDefinitionProvider;
	}

	public PolicyProcessor<?> getPolicyProcessor() {
		return policyProcessor;
	}

	public void setPolicyProcessor(PolicyProcessor<?> policyProcessor) {
		this.policyProcessor = policyProcessor;
	}

	private List<PolicyDescription<?>> queryPolicies(QName name) {
		final Collection<PolicyDefinitionDescription> policyDefinitions =
			policyDefinitionProvider.getPolicyDefinitions(name);
		final List<PolicyDescription<?>> policies = new LinkedList<PolicyDescription<?>>();
		for (final PolicyDefinitionDescription pd : policyDefinitions) {
			final PolicyExtractor<?, ?> pe = policyExtractorsRegistry.getExtractor(
					pd.getClass(), policyProcessor.getPlatformPolicyClass());
			if (pe == null) {
				throw new SwordfishException("Missing policy extractor. ");
			}
			final PolicyDescription<?> p = pe.extractPolicy(pd);
			if (p != null) {
				policies.add(p);
			}
		}
		return policies;
	}

    public Collection<EndpointExtractor> getEndpointExtractors() {
        return extractorsRegistry.getExtractors();
    }
}
