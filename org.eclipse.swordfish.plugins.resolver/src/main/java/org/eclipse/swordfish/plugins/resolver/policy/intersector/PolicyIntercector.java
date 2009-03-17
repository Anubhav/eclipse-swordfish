package org.eclipse.swordfish.plugins.resolver.policy.intersector;

import org.apache.neethi.Policy;

public interface PolicyIntercector {
    public Policy intersect(Policy firstPolicy, Policy secondPolicy);
}
