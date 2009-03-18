package org.eclipse.swordfish.policies.extractor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.cxf.ws.policy.PolicyBuilderImpl;
import org.apache.neethi.Policy;
import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.policy.PolicyDefinitionDescription;
import org.eclipse.swordfish.api.policy.PolicyDescription;
import org.eclipse.swordfish.api.policy.PolicyExtractor;
import org.eclipse.swordfish.api.policy.PolicyRole;
import org.eclipse.swordfish.api.policy.PolicyStatus;
import org.eclipse.swordfish.policies.definitions.WsPolicyStreamDefinition;
import org.eclipse.swordfish.policies.processor.WsPolicyDescription;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.emory.mathcs.backport.java.util.Collections;

public class WsPolicyStreamExtractor implements PolicyExtractor<WsPolicyStreamDefinition, Policy>{

	private static List<Class<WsPolicyStreamDefinition>> SUPPORTED_TYPES;

	private PolicyBuilderImpl policyBuilder;

	static {
		final Class<WsPolicyStreamDefinition> c = WsPolicyStreamDefinition.class;
		final List<Class<WsPolicyStreamDefinition>> l =
			new ArrayList<Class<WsPolicyStreamDefinition>>(1);
		l.add(c);
		SUPPORTED_TYPES = unmodifiableList(l);
	}

	public WsPolicyStreamExtractor() {
		super();
	}

	public PolicyDescription<Policy> extractPolicy(
			PolicyDefinitionDescription policyDefinition) {
		if (null == policyDefinition ||
				!(policyDefinition instanceof WsPolicyStreamDefinition)) {
			throw new IllegalArgumentException(
					"Bad policy definition argument provided. ");
		}
		final WsPolicyStreamDefinition pd =
			(WsPolicyStreamDefinition) policyDefinition;
		try {
	        final Policy policy = policyBuilder.getPolicy(pd.getPolicyData());
	        final WsPolicyDescription p = new WsPolicyDescription();
	        p.setPolicy(policy);
	        p.setPolicyRole(PolicyRole.UNKNOWN);
	        p.setPolicyStatus(PolicyStatus.UNKNOWN);
			return p;
		} catch (Exception e) {
			throw new SwordfishException("Corrupted policy. ", e);
		}
	}

	public Class<Policy> getPlatformPolicyType() {
		return Policy.class;
	}

	public Collection<Class<WsPolicyStreamDefinition>> getSupportedTypes() {
		return SUPPORTED_TYPES;
	}

	public PolicyBuilderImpl getPolicyBuilder() {
		return policyBuilder;
	}

	public void setPolicyBuilder(final PolicyBuilderImpl policyBuilder) {
		this.policyBuilder = policyBuilder;
	}

	@SuppressWarnings("unchecked")
	private static List<Class<WsPolicyStreamDefinition>> unmodifiableList(
			final List<Class<WsPolicyStreamDefinition>> l) {
		return (List<Class<WsPolicyStreamDefinition>>)
				Collections.unmodifiableList(l);
	}
}
