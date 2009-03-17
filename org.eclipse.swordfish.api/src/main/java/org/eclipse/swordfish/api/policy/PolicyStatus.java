package org.eclipse.swordfish.api.policy;

/**
 * Enum defining the format of a described policy.
 */
public enum PolicyStatus {

	/**
	 * Normalized policy.
	 */
	NORMALIZED,

	/**
	 * Specifically de-normalized as Swordfish service consumer or provider policy. 
	 */
	SWORDFISH_PARTICIPANT,

	/**
	 * Specifically de-normalized as Swordfish agreed policy.
	 */
	SWORDFISH_AGREED,

	/**
	 * Arbitrarily de-normalized.
	 */
	UNKNOWN
}
