package org.eclipse.swordfish.plugins.resolver;

import java.util.Collection;

import org.eclipse.swordfish.api.policy.PolicyDefinitionProvider;

public class PolicyProvidersRegistry {

	private Collection<PolicyDefinitionProvider> providers;

	public PolicyProvidersRegistry() {
		super();
	}

	public PolicyDefinitionProvider getPreferredProvider() {
		if (null == providers) {
			return null;
		}
		PolicyDefinitionProvider preferredProvider = null;
		for (final PolicyDefinitionProvider nextProvider : providers) {
			if (preferredProvider == null ||
					nextProvider.getPriority() > preferredProvider.getPriority()) {
				preferredProvider = nextProvider;
			}
		}
		return preferredProvider;
	}

	public Collection<PolicyDefinitionProvider> getProviders() {
		return providers;
	}

	public void setProviders(final Collection<PolicyDefinitionProvider> providers) {
		this.providers = providers;
	}

	public boolean isEmpty() {
		return providers == null || providers.isEmpty();
	}
}
