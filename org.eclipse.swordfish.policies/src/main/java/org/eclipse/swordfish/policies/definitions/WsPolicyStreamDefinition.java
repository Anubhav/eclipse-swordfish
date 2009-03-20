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
package org.eclipse.swordfish.policies.definitions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.policy.PolicyDefinitionDescription;

public class WsPolicyStreamDefinition implements PolicyDefinitionDescription {

	private final QName serviceQName;

	private final InputStream policyData;

	public WsPolicyStreamDefinition(final QName serviceQName, final InputStream policyData) {
		this.serviceQName = serviceQName;
		this.policyData = policyData;
	}

	public WsPolicyStreamDefinition(final QName serviceQName, final byte[] policyData) {
		this.serviceQName = serviceQName;
		this.policyData = new ByteArrayInputStream(policyData);
	}

	public QName getServiceQName() {
		return serviceQName;
	}

	public InputStream getPolicyData() {
		return policyData;
	}
}
