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

import java.util.Collection;

import javax.xml.namespace.QName;

/**
 * Interface for policy definition retrieving components.
 *
 */
public interface PolicyDefinitionProvider {

	/**
	 * Retrieve the Policies for a service provider.
	 *
	 * @param serviceProviderName the provider WSDL service name.
	 * @return assigned policies, an empty Collection if there are none.
	 */
	Collection<PolicyDefinitionDescription> getPolicyDefinitions(QName serviceProviderName);

	/**
	 * Get the provider's priority. If several policy definition providers are registered
	 * at runtime the one with the highest priority will be chosen.
	 */
	int getPriority();
}
