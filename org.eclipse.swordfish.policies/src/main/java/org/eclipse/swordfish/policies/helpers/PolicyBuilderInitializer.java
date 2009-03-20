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
package org.eclipse.swordfish.policies.helpers;

import java.util.Arrays;

import javax.xml.namespace.QName;

import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.PolicyBuilderImpl;
import org.apache.cxf.ws.policy.PolicyConstants;
import org.apache.cxf.ws.policy.builder.xml.XMLPrimitiveAssertionBuilder;

public class PolicyBuilderInitializer {

	private PolicyBuilderImpl policyBuilder;
	private boolean isInitialized = false;

	public PolicyBuilderInitializer() {
		super();
	}

	public PolicyBuilderImpl getPolicyBuilder() {
		initPolicyBuilder();
		return policyBuilder;
	}

	public void setPolicyBuilder(final PolicyBuilderImpl policyBuilder) {
		if (this.policyBuilder == policyBuilder) {
			return;
		}
		synchronized (this) {
			this.policyBuilder = policyBuilder;
			isInitialized = false;
		}
	}

    private synchronized void initPolicyBuilder() {
        if (null == policyBuilder) {
        	return;
        }
        if (isInitialized) {
            policyBuilder.getBus().getExtension(PolicyConstants.class)
            		.setNamespace(PolicyConstants.NAMESPACE_XMLSOAP_200409);
            return;
        }
        final XMLPrimitiveAssertionBuilder xmlPrimitiveAssertionBuilder =
        	new  XMLPrimitiveAssertionBuilder();
        xmlPrimitiveAssertionBuilder.setKnownElements(Arrays.asList(
                new QName("http://schemas.xmlsoap.org/ws/2002/12/secext",
                		"SecurityToken"),
                new QName("http://schemas.xmlsoap.org/ws/2002/12/secext",
                		"SecurityTokenType"),
                new QName("http://schemas.xmlsoap.org/ws/2002/12/secext",
                		"Integrity")));
        final AssertionBuilderRegistry assertionBuilderRegistry =
        	policyBuilder.getAssertionBuilderRegistry();

        assertionBuilderRegistry.setIgnoreUnknownAssertions(false);
        for (QName elem : xmlPrimitiveAssertionBuilder.getKnownElements()) {
            assertionBuilderRegistry.register(
            		elem, xmlPrimitiveAssertionBuilder);
        }
        PolicyConstants constants = new PolicyConstants();
        constants.setNamespace(PolicyConstants.NAMESPACE_XMLSOAP_200409);
        policyBuilder.getBus().setExtension(constants, PolicyConstants.class);
        isInitialized = true;
	}
}
