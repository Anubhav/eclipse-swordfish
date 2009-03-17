package org.eclipse.swordfish.plugins.resolver;

import java.util.Collection;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.policy.PolicyDefinitionDescription;
import org.eclipse.swordfish.api.policy.PolicyExtractor;

/**
 * Internal registry for policy extractors.
 */
public class PolicyExtractorsRegistry {

	/**
	 * Internal extractors collection.
	 */
	private Collection<PolicyExtractor<? extends PolicyDefinitionDescription, ?>> extractors;

	/**
	 * Standard constructor.
	 */
	public PolicyExtractorsRegistry() {
		super();
	}

	/**
	 * Look up the extractor.
	 * @param <P> Platform policy type
	 * @param definitionDescriptorType type of the PolicyDefinitionDescriptor
	 *         for which an extractor is needed.
	 * @param platformPolicyType platform policy type of the policy to be returned
	 *         by the extractor.
	 * @return suitable policy extractor.
	 */
	public <P> PolicyExtractor<? extends PolicyDefinitionDescription, P> getExtractor(
			final Class<? extends PolicyDefinitionDescription> definitionDescriptorType,
			final Class<P> platformPolicyType) {
		if (null == extractors) {
			throw new SwordfishException("Couldn't find matching policy extractor, "
					+ "extractors registry is not initialized properly. ");

		}
		for (final PolicyExtractor<?, ?> x : extractors) {
			final PolicyExtractor<? extends PolicyDefinitionDescription, P> result =
				toCheckedPE(x, definitionDescriptorType, platformPolicyType);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Getter for internal registry collection.
	 * @return internal registry collection.
	 */
	public Collection<PolicyExtractor<? extends PolicyDefinitionDescription, ?>> getExtractors() {
		return extractors;
	}

	/**
	 * Setter for internal registry collection.
	 * @param extractors internal registry collection.
	 */
	public void setExtractors(
			final Collection<PolicyExtractor<? extends PolicyDefinitionDescription, ?>> extractors) {
		this.extractors = extractors;
	}

	/**
	 * Specific checked cast.
	 * @param <P> platform policy type.
	 * @param extractor policy extractor to be casted.
	 * @param definitionDescriptorType runtime type of policy definition descriptor.
	 * @param platformPolicyType runtime type of platform policy.
	 * @return extractor if conformant or <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	private <P> PolicyExtractor<? extends PolicyDefinitionDescription, P> toCheckedPE(
			final PolicyExtractor<? extends PolicyDefinitionDescription, ?> extractor,
			final Class<?> definitionDescriptorType,
			Class<P> platformPolicyType) {

		if (extractor.getSupportedTypes().contains(definitionDescriptorType) &&
				extractor.getPlatformPolicyType() == platformPolicyType) {
			final PolicyExtractor<? extends PolicyDefinitionDescription, P> result =
				(PolicyExtractor<? extends PolicyDefinitionDescription, P>) extractor;
			return result;
		}
		return null;
	}
}
