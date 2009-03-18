package org.eclipse.swordfish.policies.definitions.provider;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.PolicyBuilderImpl;
import org.apache.cxf.ws.policy.PolicyConstants;
import org.apache.cxf.ws.policy.builder.xml.XMLPrimitiveAssertionBuilder;
import org.apache.neethi.Policy;
import org.eclipse.swordfish.api.policy.PolicyDefinitionDescription;
import org.eclipse.swordfish.api.policy.PolicyDescription;
import org.eclipse.swordfish.policies.extractor.WsPolicyStreamExtractor;
import org.eclipse.swordfish.policies.helpers.ResourceToStringConverter;
import org.eclipse.swordfish.policies.processor.WsPolicyProcessor;
import org.eclipse.swordfish.policies.trading.impl.PolicyIntersectorImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FilesystemPolicyDefinitionProviderTest {

	private static final String POLICY_STORAGE_PROPERTY = "policyStorage";
	private static final String POLICY_STORAGE_RESOURCE = "policies/Policies.zip";
	private static final QName CONSUMER_NAME =
		new QName("http://org.example", "consumerEndpoint");
	private static final QName PROVIDER_NAME =
		new QName("http://org.example", "serviceProvider");
	private static final QName UNKNOWN_NAME =
		new QName("http://org.example", "unknownProvider");

	private FilesystemPolicyDefinitionProvider policyDefinitionProvider;
	private WsPolicyStreamExtractor policyExtractor;
	private WsPolicyProcessor policyProcessor;
	private Map<String, String> configuration;

	@Before
	public void setUp() {
		policyDefinitionProvider = new FilesystemPolicyDefinitionProvider();
		configuration = new HashMap<String, String>();
		final ResourceToStringConverter cv =
			new ResourceToStringConverter(POLICY_STORAGE_RESOURCE);
		configuration.put(POLICY_STORAGE_PROPERTY, cv.getUrl());
		policyDefinitionProvider.onReceiveConfiguration(configuration);
		policyExtractor = new WsPolicyStreamExtractor();
		policyExtractor.setPolicyBuilder(initPolicyBuilder());
		policyProcessor = new WsPolicyProcessor();
		final PolicyIntersectorImpl policyIntersector =
			new PolicyIntersectorImpl();
		policyIntersector.setAssertionBuilderRegistry(
				initAssertionBuilderRegistry());
		policyProcessor.setPolicyIntersector(policyIntersector);
	}

	@Test
	public void testPolicyProcessing() {
		final Collection<PolicyDefinitionDescription> consDescs =
			policyDefinitionProvider.getPolicyDefinitions(CONSUMER_NAME);
		final Collection<PolicyDefinitionDescription> provDescs =
			policyDefinitionProvider.getPolicyDefinitions(PROVIDER_NAME);
		final Collection<PolicyDefinitionDescription> noDescs =
			policyDefinitionProvider.getPolicyDefinitions(UNKNOWN_NAME);
		assertEquals(consDescs.size(), 1);
		assertEquals(provDescs.size(), 1);
		assertEquals(noDescs.size(), 0);
		final List<PolicyDescription<Policy>> consPDList =
			new LinkedList<PolicyDescription<Policy>>();
		final List<PolicyDescription<?>> provPDList =
			new LinkedList<PolicyDescription<?>>();
		for (final PolicyDefinitionDescription desc : consDescs) {
			checkPolicyExtraction(desc, consPDList);
		}
		for (final PolicyDefinitionDescription desc : provDescs) {
			checkPolicyExtraction(desc, provPDList);
		}
		assertEquals(consPDList.size(), 1);
		assertEquals(provPDList.size(), 1);
		assertEquals(policyProcessor.getPlatformPolicyClass(),
				consPDList.get(0).getPolicy().getClass());
		final PolicyDescription<Policy> agreedA =
			policyProcessor.tradeAgreedPolicy(
				consPDList.get(0), provPDList);
		final PolicyDescription<Policy> agreedB =
			policyProcessor.tradeAgreedPolicy(
				consPDList.get(0), provPDList.get(0));
		final PolicyDescription<?> pds[] = new PolicyDescription<?>[1];
		pds[0] = provPDList.get(0);
		final PolicyDescription<Policy> agreedC =
			policyProcessor.tradeAgreedPolicy(
				consPDList.get(0), pds);
		assertNotNull(agreedA.getPolicy());
		assertNotNull(agreedB.getPolicy());
		assertNotNull(agreedC.getPolicy());
	}

	private PolicyBuilderImpl initPolicyBuilder() {
        final ClassPathXmlApplicationContext ac =
        	new ClassPathXmlApplicationContext("policy-test.xml");
        final PolicyBuilderImpl pb =
        	(PolicyBuilderImpl) ac.getBean(
        			"org.apache.cxf.ws.policy.PolicyBuilder");
        assertNotNull(pb);
        return pb;
	}

	private AssertionBuilderRegistry initAssertionBuilderRegistry() {
        final ClassPathXmlApplicationContext applicationContext =
        	new ClassPathXmlApplicationContext("policy-test.xml");
        final PolicyBuilderImpl builder = (PolicyBuilderImpl)
        		applicationContext.getBean(
        				"org.apache.cxf.ws.policy.PolicyBuilder");
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
        	builder.getAssertionBuilderRegistry();

        assertionBuilderRegistry.setIgnoreUnknownAssertions(false);
        for (QName elem : xmlPrimitiveAssertionBuilder.getKnownElements()) {
            assertionBuilderRegistry.register(
            		elem, xmlPrimitiveAssertionBuilder);
        }
        PolicyConstants constants = new PolicyConstants();
        constants.setNamespace(PolicyConstants.NAMESPACE_XMLSOAP_200409);
        builder.getBus().setExtension(constants, PolicyConstants.class);
        return builder.getAssertionBuilderRegistry();
	}

	@SuppressWarnings("unchecked")
	private <T> void checkPolicyExtraction(final PolicyDefinitionDescription desc,
			final List<T> list) {
		assertTrue(policyExtractor.getSupportedTypes().contains(desc.getClass()));
		final PolicyDescription<Policy> p =
			checkPD(policyExtractor.extractPolicy(desc));
		assertNotNull(p.getPolicy());
		list.add((T) p);
	}

	@SuppressWarnings("unchecked")
	private PolicyDescription<Policy> checkPD(PolicyDescription<?> p) {
		assertTrue(p.getPolicy() instanceof Policy);
		return (PolicyDescription<Policy>) p;
	}
}
