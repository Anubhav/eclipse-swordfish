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
	 * Trade a list of provider policies against a consumer policy.
	 * Return an agreed policy for the first matching provider policy.
	 * @param consumerPolicy consumer policy.
	 * @param providerPolicies provider policy.
	 * @return agreed policy or <code>null</code> if policies do not match.
	 */
	PolicyDescription<P> tradeAgreedPolicy(
			PolicyDescription<?> consumerPolicy, PolicyDescription<?> ... providerPolicies);

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
