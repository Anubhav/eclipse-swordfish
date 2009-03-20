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
