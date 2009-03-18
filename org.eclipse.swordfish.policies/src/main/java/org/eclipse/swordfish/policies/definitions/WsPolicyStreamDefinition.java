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
