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
