package org.eclipse.swordfish.api.policy;

import java.util.List;

/**
 * Interface of the policy processor component.
 *
 * @param <P> Platform policy type.
 */
public interface PolicyProcessor<P> {

	/**
	 * Trade a list of provider policies against a consumer policy.
	 * Return an agreed policy for the first matching provider policy.
	 * @param consumerPolicy consumer policy.
	 * @param providerPolicies provider policy.
	 * @return agreed policy or <code>null</code> if policies do not match.
	 */
	PolicyDescription<P> tradeAgreedPolicy(
			PolicyDescription<?> consumerPolicy, List<PolicyDescription<?>> providerPolicies);

	/**
	 * Trade a provider against a consumer policy.
	 * @param consumerPolicy consumer policy.
	 * @param providerPolicy provider policy.
	 * @return agreed policy or <code>null</code> if policies do not match.
	 */
	PolicyDescription<P> tradeAgreedPolicy(
			PolicyDescription<?> consumerPolicy, PolicyDescription<?> providerPolicy);

	/**
	 * Get type of the underlying platform policy.
	 * @return class of platform policy.
	 */
	Class<P> getPlatformPolicyClass();
}
