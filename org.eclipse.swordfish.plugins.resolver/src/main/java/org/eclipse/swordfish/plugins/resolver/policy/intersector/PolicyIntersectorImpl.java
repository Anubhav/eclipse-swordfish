package org.eclipse.swordfish.plugins.resolver.policy.intersector;

import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.Intersector;
import org.apache.neethi.Policy;
import org.springframework.util.Assert;

public class PolicyIntersectorImpl implements PolicyIntercector {
    private Intersector intersector;
    private AssertionBuilderRegistry assertionBuilderRegistry;

    public Policy intersect(Policy firstPolicy, Policy secondPolicy) {
        return getIntersector().intersect(firstPolicy, secondPolicy);
    }
    private synchronized Intersector getIntersector() {
        if (intersector == null) {
            Assert.notNull(assertionBuilderRegistry, "assertionBuilderRegistry property must be set");
            intersector = new Intersector(assertionBuilderRegistry);
        }
        return intersector;
    }
    public AssertionBuilderRegistry getAssertionBuilderRegistry() {
        return assertionBuilderRegistry;
    }

    public void setAssertionBuilderRegistry(
            AssertionBuilderRegistry assertionBuilderRegistry) {
        this.assertionBuilderRegistry = assertionBuilderRegistry;
    }
}
