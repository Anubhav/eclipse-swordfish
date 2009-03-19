package org.eclipse.swordfish.policies.trading.impl;

import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.Intersector;
import org.apache.cxf.ws.policy.PolicyBuilderImpl;
import org.apache.neethi.Policy;
import org.eclipse.swordfish.policies.helpers.PolicyBuilderInitializer;
import org.eclipse.swordfish.policies.trading.PolicyIntersector;
import org.springframework.util.Assert;

public class PolicyIntersectorImpl implements PolicyIntersector {

	private Intersector intersector;
    private AssertionBuilderRegistry assertionBuilderRegistry;
    private PolicyBuilderInitializer policyBuilderInitializer;

    public PolicyIntersectorImpl() {
    	super();
    }

    public Policy intersect(Policy firstPolicy, Policy secondPolicy) {
        return getIntersector().intersect(firstPolicy, secondPolicy);
    }

    private synchronized Intersector getIntersector() {
        if (intersector == null) {
        	if (assertionBuilderRegistry == null) {
                Assert.notNull(policyBuilderInitializer,
                		"policyBuilderInitializer property must be set");
                final PolicyBuilderImpl pb =
                	policyBuilderInitializer.getPolicyBuilder();
                Assert.notNull(pb,
        				"policyBuilderInitializer has no PolicyBuilder. ");
        		assertionBuilderRegistry =
        			pb.getAssertionBuilderRegistry();
        	}
            Assert.notNull(assertionBuilderRegistry,
            		"assertionBuilderRegistry property must be set");
            intersector = new Intersector(assertionBuilderRegistry);
        }
        return intersector;
    }

    public AssertionBuilderRegistry getAssertionBuilderRegistry() {
        return assertionBuilderRegistry;
    }

    public void setAssertionBuilderRegistry(
            final AssertionBuilderRegistry assertionBuilderRegistry) {
        this.assertionBuilderRegistry = assertionBuilderRegistry;
    }

    public PolicyBuilderInitializer getPolicyBuilderInitializer() {
		return policyBuilderInitializer;
	}

    public void setPolicyBuilderInitializer(
    		final PolicyBuilderInitializer policyBuilderInitializer) {
		this.policyBuilderInitializer = policyBuilderInitializer;
	}
}
