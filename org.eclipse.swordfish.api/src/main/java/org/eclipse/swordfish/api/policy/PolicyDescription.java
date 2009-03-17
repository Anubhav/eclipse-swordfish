package org.eclipse.swordfish.api.policy;

/**
 * Description object which holds and describes a policy
 * used within Swordfish.
 *
 * @param <P> "Platform" policy class.
 */
public interface PolicyDescription<P> {

	/**
	 * Access to the policy object.
	 *
	 * @return the "platform" policy.
	 */
	P getPolicy();

	/**
	 * Get the policy normalization status.
	 *
	 * @return Policy normalization status.
	 */
	PolicyStatus getPolicyStatus();

	/**
	 * Get Swordfish policy role.
	 *
	 * @return Swordfish policy role.
	 */
	PolicyRole getPolicyRole();
}
