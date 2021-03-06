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
package org.eclipse.swordfish.policies.processor;

import org.apache.neethi.Policy;
import org.eclipse.swordfish.api.policy.PolicyDescription;
import org.eclipse.swordfish.api.policy.PolicyRole;
import org.eclipse.swordfish.api.policy.PolicyStatus;

public class WsPolicyDescription implements PolicyDescription<Policy> {

	private Policy policy;
	private PolicyRole policyRole;
	private PolicyStatus policyStatus;
	
	public WsPolicyDescription() {
		super();
	}

	public Policy getPolicy() {
		return policy;
	}

	public PolicyRole getPolicyRole() {
		return policyRole;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicy(final Policy policy) {
		this.policy = policy;
	}

	public void setPolicyRole(final PolicyRole policyRole) {
		this.policyRole = policyRole;
	}

	public void setPolicyStatus(final PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}
}
