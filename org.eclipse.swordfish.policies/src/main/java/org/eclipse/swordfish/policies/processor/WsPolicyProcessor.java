package org.eclipse.swordfish.policies.processor;

import java.util.List;

import org.apache.neethi.Policy;
import org.eclipse.swordfish.api.policy.PolicyDescription;
import org.eclipse.swordfish.api.policy.PolicyProcessor;
import org.eclipse.swordfish.api.policy.PolicyRole;
import org.eclipse.swordfish.api.policy.PolicyStatus;
import org.eclipse.swordfish.policies.trading.PolicyIntersector;

public class WsPolicyProcessor implements PolicyProcessor<Policy> {

	private PolicyIntersector policyIntersector;

	public WsPolicyProcessor() {
		super();
	}

	public Class<Policy> getPlatformPolicyClass() {
		return Policy.class;
	}

	public PolicyDescription<Policy> tradeAgreedPolicy(
			final PolicyDescription<?> consumerPolicy,
			final List<PolicyDescription<?>> providerPolicies) {
		for (final PolicyDescription<?> providerPolicy : providerPolicies) {
			final PolicyDescription<Policy> cpd = asCheckedPD(consumerPolicy);
			final PolicyDescription<Policy> ppd = asCheckedPD(providerPolicy);
			final PolicyDescription<Policy> result = doTradeAgreedPolicy(cpd, ppd);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public PolicyDescription<Policy> tradeAgreedPolicy(
			final PolicyDescription<?> consumerPolicy,
			final PolicyDescription<?> providerPolicy) {
		final PolicyDescription<Policy> cpd = asCheckedPD(consumerPolicy);
		final PolicyDescription<Policy> ppd = asCheckedPD(providerPolicy);
		return doTradeAgreedPolicy(cpd, ppd);
	}

	public PolicyIntersector getPolicyIntersector() {
		return policyIntersector;
	}

	public void setPolicyIntersector(final PolicyIntersector policyIntersector) {
		this.policyIntersector = policyIntersector;
	}

	private PolicyDescription<Policy> doTradeAgreedPolicy(
			final PolicyDescription<Policy> consumerPolicy,
			final PolicyDescription<Policy> providerPolicy) {
		
		final Policy agreed = policyIntersector.intersect(
				consumerPolicy.getPolicy(), providerPolicy.getPolicy());
		if (agreed == null) {
			return null;
		}
		final WsPolicyDescription result = new WsPolicyDescription();
		result.setPolicy(agreed);
		result.setPolicyRole(PolicyRole.AGREED_POLICY);
		result.setPolicyStatus(PolicyStatus.UNKNOWN);
		return result;
	}

	@SuppressWarnings("unchecked")
	private static PolicyDescription<Policy> asCheckedPD(PolicyDescription<?> pd) {
		if (pd.getPolicy() instanceof Policy) {
			return (PolicyDescription<Policy>) pd;
		}
		throw new ClassCastException("Incompatible PolicyDescription. ");
	}
}
