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
 * Policy-Related Constants.
 */
public final class PolicyConstants {

	/**
	 * Property set in the message exchange as key for the consumer policy.
	 * Setting this property activates policy support in the ServiceResolver.
	 * The value of this property must be a QName identifying a policy which
	 * can be retrieved by the Policy Definition Provider under the given
	 * QName.
	 */
	public static final String POLICY_CONSUMER_NAME =
		"org.eclipse.swordfish.policy.ConsumerName";
	
	/**
	 * Not for Instantiation.
	 */
	private PolicyConstants() {}
}
