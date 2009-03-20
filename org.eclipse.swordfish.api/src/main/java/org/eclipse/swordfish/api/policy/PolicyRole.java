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
