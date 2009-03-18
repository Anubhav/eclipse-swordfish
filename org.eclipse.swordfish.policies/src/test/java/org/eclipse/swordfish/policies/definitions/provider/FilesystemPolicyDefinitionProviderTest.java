package org.eclipse.swordfish.policies.definitions.provider;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.policy.PolicyDefinitionDescription;
import org.eclipse.swordfish.policies.helpers.ResourceToStringConverter;
import org.junit.Before;
import org.junit.Test;

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
	private Map<String, String> configuration;

	@Before
	public void setUp() {
		policyDefinitionProvider = new FilesystemPolicyDefinitionProvider();
		configuration = new HashMap<String, String>();
		final ResourceToStringConverter cv =
			new ResourceToStringConverter(POLICY_STORAGE_RESOURCE);
		configuration.put(POLICY_STORAGE_PROPERTY, cv.getUrl());
		policyDefinitionProvider.onReceiveConfiguration(configuration);
	}

	@Test
	public void testPolicyDefinitionProvider() {
		final Collection<PolicyDefinitionDescription> consDescs =
			policyDefinitionProvider.getPolicyDefinitions(CONSUMER_NAME);
		final Collection<PolicyDefinitionDescription> provDescs =
			policyDefinitionProvider.getPolicyDefinitions(PROVIDER_NAME);
		final Collection<PolicyDefinitionDescription> noDescs =
			policyDefinitionProvider.getPolicyDefinitions(UNKNOWN_NAME);
		assertEquals(consDescs.size(), 1);
		assertEquals(provDescs.size(), 1);
		assertEquals(noDescs.size(), 0);
	}
}
