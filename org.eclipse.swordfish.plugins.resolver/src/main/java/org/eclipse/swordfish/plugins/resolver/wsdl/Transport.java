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
package org.eclipse.swordfish.plugins.resolver.wsdl;
/**
 * @author vzhabiuk
 * Refactor this class to enum
 */
@Deprecated
public interface Transport {

	/** HTTP string constant. */
	public static final String HTTP_STR = "HttpTransport";
	/** HTTPS string constant. */
	public static final String HTTPS_STR = "HttpsTransport";
	/** JMS string constant. */
	public static final String JMS_STR = "JmsTransport";
	/** JBI string constant. */
	public static final String JBI_STR = "JbiTransport";
	/** SBB2 string constant. */
	public static final String SBB2_STR = "Sbb2Transport";

	/**
	 * (non-Javadoc).
	 *
	 * @param obj the obj
	 *
	 * @return true, if equals
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(final Object obj);

	/**
	 * To string.
	 *
	 * @return the string
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString();

}