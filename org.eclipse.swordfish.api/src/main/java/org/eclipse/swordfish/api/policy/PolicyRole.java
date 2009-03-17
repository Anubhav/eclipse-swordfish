package org.eclipse.swordfish.api.policy;

/**
 * Enum of possible roles of a Swordfish policy.
 */
public enum PolicyRole {

	/**
	 * Policy demanded by a Swordfish service consumer.
	 */
	CONSUMER_POLICY,

	/**
	 * Policy demanded by a Swordfish service provider.
	 */
	PROVIDER_POLICY,

	/**
	 * Agreed policy traded between a Swordfish service consumer and provider.
	 */
	AGREED_POLICY,

	/**
	 * Role not (yet) defined.
	 */
	UNKNOWN
}
