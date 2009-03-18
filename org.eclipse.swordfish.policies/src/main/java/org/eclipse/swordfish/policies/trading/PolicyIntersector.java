package org.eclipse.swordfish.policies.trading;

import org.apache.neethi.Policy;

public interface PolicyIntersector {
    public Policy intersect(Policy firstPolicy, Policy secondPolicy);
}
