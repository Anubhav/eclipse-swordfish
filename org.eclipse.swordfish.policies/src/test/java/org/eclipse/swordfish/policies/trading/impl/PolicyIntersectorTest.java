package org.eclipse.swordfish.policies.trading.impl;


import java.io.InputStream;
import java.util.Arrays;

import javax.xml.namespace.QName;

import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.PolicyBuilderImpl;
import org.apache.cxf.ws.policy.PolicyConstants;
import org.apache.cxf.ws.policy.PolicyUtils;
import org.apache.cxf.ws.policy.builder.xml.XMLPrimitiveAssertionBuilder;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.eclipse.swordfish.policies.trading.impl.PolicyIntersectorImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PolicyIntersectorTest extends Assert {

    private PolicyBuilderImpl builder;
    private PolicyIntersectorImpl policyIntercector;

    @Before
    public void setUp() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/cxf/cxf-extension-policy.xml");
        builder = (PolicyBuilderImpl)applicationContext.getBean("org.apache.cxf.ws.policy.PolicyBuilder");
        XMLPrimitiveAssertionBuilder primitiveAssertionBuilder = new XMLPrimitiveAssertionBuilder();
         XMLPrimitiveAssertionBuilder xmlPrimitiveAssertionBuilder = new  XMLPrimitiveAssertionBuilder();
        xmlPrimitiveAssertionBuilder.setKnownElements(Arrays.asList(
                new QName("http://schemas.xmlsoap.org/ws/2002/12/secext", "SecurityToken"),
                new QName("http://schemas.xmlsoap.org/ws/2002/12/secext", "SecurityTokenType"),
                new QName("http://schemas.xmlsoap.org/ws/2002/12/secext", "Integrity")));
        AssertionBuilderRegistry assertionBuilderRegistry = builder.getAssertionBuilderRegistry();

        assertionBuilderRegistry.setIgnoreUnknownAssertions(false);
        for (QName elem : primitiveAssertionBuilder.getKnownElements()) {
          //  assertionBuilderRegistry.register(elem, primitiveAssertionBuilder);
        }
        for (QName elem : xmlPrimitiveAssertionBuilder.getKnownElements()) {
            assertionBuilderRegistry.register(elem, xmlPrimitiveAssertionBuilder);
        }
        PolicyConstants constants = new PolicyConstants();
        constants.setNamespace(PolicyConstants.NAMESPACE_XMLSOAP_200409);
        builder.getBus().setExtension(constants, PolicyConstants.class);
        policyIntercector = new PolicyIntersectorImpl();
        policyIntercector.setAssertionBuilderRegistry(builder.getAssertionBuilderRegistry());
    }

    @Test
    public void test1PartialMatch() throws Exception {
        String name = "/PartialMatch/PolicyA.xml";
        InputStream is = PolicyIntersectorTest.class.getResourceAsStream(name);
        Policy p1 = builder.getPolicy(is);
        name = "/PartialMatch/PolicyB.xml";
        is = PolicyIntersectorTest.class.getResourceAsStream(name);
        builder.getBus().getExtension(PolicyConstants.class).setNamespace(PolicyConstants.NAMESPACE_XMLSOAP_200409);
        Policy p2 = builder.getPolicy(is);
        Policy policy =  policyIntercector.intersect(p1, p2);
        assertNotNull(policy);
        ExactlyOne policyComponent = (ExactlyOne) policy.getFirstPolicyComponent();
        assertNotNull(policyComponent);
        PolicyUtils.printPolicyComponent(policy);
        assertEquals(policyComponent.getPolicyComponents().size(), 2);

    }
    @Test
    public void test2NoMatch() throws Exception {
        String name = "/NoMatch/PolicyA.xml";
        InputStream is = PolicyIntersectorTest.class.getResourceAsStream(name);
        Policy p1 = builder.getPolicy(is);
        name = "/NoMatch/PolicyB.xml";
        is = PolicyIntersectorTest.class.getResourceAsStream(name);
        builder.getBus().getExtension(PolicyConstants.class).setNamespace(PolicyConstants.NAMESPACE_XMLSOAP_200409);
        Policy p2 = builder.getPolicy(is);
        Policy policy =  policyIntercector.intersect(p1, p2);
        assertNull(policy);
    }

    @Test
    public void test3CompleteMatch() throws Exception {
        String name = "/CompleteMatch/PolicyA.xml";
        InputStream is = PolicyIntersectorTest.class.getResourceAsStream(name);
        Policy p1 = builder.getPolicy(is);
        name = "/CompleteMatch/PolicyB.xml";
        is = PolicyIntersectorTest.class.getResourceAsStream(name);
        builder.getBus().getExtension(PolicyConstants.class).setNamespace(PolicyConstants.NAMESPACE_XMLSOAP_200409);
        Policy p2 = builder.getPolicy(is);
        Policy policy =  policyIntercector.intersect(p1, p2);
        assertNotNull(policy);
        ExactlyOne policyComponent = (ExactlyOne) policy.getFirstPolicyComponent();
        assertNotNull(policyComponent);
        PolicyUtils.printPolicyComponent(policy);
        assertEquals(policyComponent.getPolicyComponents().size(), 2);
    }
}
