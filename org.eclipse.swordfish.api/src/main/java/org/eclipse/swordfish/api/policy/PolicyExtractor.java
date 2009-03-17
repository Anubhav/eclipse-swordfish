package org.eclipse.swordfish.api.policy;

import java.util.Collection;

/**
 * Interface for services extracting policies from raw policy definitions.
 *
 * @param <T> the policy definition description type.
 * @param <P> the platform policy typed returned in the extracted policy description.
 */
public interface PolicyExtractor<T extends PolicyDefinitionDescription, P> {

	/**
	 * Get the extracted policy from the raw definition.
	 *
	 * @param policyDefinition the raw definition of a supported class.
	 * @return the policy definition which holds a platform policy of the
	 *         supported type.
	 */
	PolicyDescription<P> extractPolicy(PolicyDefinitionDescription policyDefinition);

	/**
	 * Get the raw policy definition description classes from which the
	 * receiver can extract policies.
	 *
	 * @return a collection which should contain at least one class
	 *         implementing the PolicyExtractor interface.
	 */
	Collection<Class<T>> getSupportedTypes();

	/**
	 * Get the class of the platform policy.
	 *
	 * @return the policy class.
	 */
	Class<P> getPlatformPolicyType();
}
