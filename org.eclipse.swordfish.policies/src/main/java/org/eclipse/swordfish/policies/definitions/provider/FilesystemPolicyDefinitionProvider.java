package org.eclipse.swordfish.policies.definitions.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.configuration.ConfigurationConsumer;
import org.eclipse.swordfish.api.policy.PolicyDefinitionDescription;
import org.eclipse.swordfish.api.policy.PolicyDefinitionProvider;
import org.eclipse.swordfish.policies.definitions.WsPolicyStreamDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesystemPolicyDefinitionProvider implements
		PolicyDefinitionProvider, ConfigurationConsumer<String> {

	private final Logger logger = LoggerFactory.getLogger(
			FilesystemPolicyDefinitionProvider.class);

	private static final String POLICY_STORAGE_PROPERTY = "policyStorage";

	private static final int DEFAULT_BUFFER_SIZE = 4096;

	private URL policyStorage;

	private Properties directory;

	private Map<String, byte[]> policies;

	public Collection<PolicyDefinitionDescription> getPolicyDefinitions(
			final QName serviceProviderName) {
		final String key = serviceProviderName.toString();
		final String values = directory.getProperty(key);
		if (values == null) {
			return emptyList();
		}
		final StringTokenizer t = new StringTokenizer(",");
		final List<PolicyDefinitionDescription> result =
			new LinkedList<PolicyDefinitionDescription>();
		while (t.hasMoreTokens()) {
			final String k = t.nextToken().trim();
			final byte[] b = policies.get(k);
			if (b == null) {
				continue;
			}
			final PolicyDefinitionDescription d =
				new WsPolicyStreamDefinition(serviceProviderName, b);
			result.add(d);
		}
		return result;
	}

	public String getId() {
		return getClass().getName();
	}

	public void onReceiveConfiguration(final Map<String, String> configuration) {
		if (configuration == null || !configuration.containsKey(
				POLICY_STORAGE_PROPERTY)) {
			final String errorMsg = "Invalid configuration " +
					"for FilesystemPolicyDefinitionProvider. ";
			logger.error(errorMsg);
			throw new SwordfishException(errorMsg);
		}
		try {
			policyStorage = new URL(configuration.get(POLICY_STORAGE_PROPERTY));
		} catch (MalformedURLException e) {
			final String errorMsg = "Bad policy storage property value. ";
			logger.error(errorMsg);
			throw new SwordfishException(errorMsg);
		}
		final String urlName = policyStorage.toString();
		if (!(urlName.endsWith(".zip") || (urlName.endsWith(".jar")))) {
			throw new SwordfishException(
					"Currently only policy storage in ZIP files supported. ");
		}
		try {
			final InputStream is = policyStorage.openStream();
			try {
				final ZipInputStream in = new ZipInputStream(is);
				readOut(in);
			} finally {
				is.close();
			}
		} catch (IOException e) {
			throw new SwordfishException("Policy storage read error. ", e);
		}
	}

	private void readOut(final ZipInputStream in) throws IOException {
		for (ZipEntry e = in.getNextEntry(); e != null; e = in.getNextEntry()) {
			if (e.isDirectory()) {
				continue;
			}
			final String n = e.getName();
			if (n.startsWith("_") || n.startsWith(".")) {
				continue;
			}
			if (n.endsWith("policies.dir")) {
				Properties p = new Properties();
				p.load(in);
				continue;
			}
			final byte[] bytes = streamToByteArray(in);
			policies.put(n, bytes);
		}
	}

	private static byte[] streamToByteArray(final InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
  		byte[] buffer=new byte[DEFAULT_BUFFER_SIZE];
		for (;;) {
			int bytesRead = is.read(buffer, 0, DEFAULT_BUFFER_SIZE);
			if (bytesRead == -1)
				break;
			bos.write(buffer, 0, bytesRead);
		}
		return bos.toByteArray();
	}

	@SuppressWarnings("unchecked")
	private static List<PolicyDefinitionDescription> emptyList() {
		return (List<PolicyDefinitionDescription>) Collections.EMPTY_LIST;
	}
}
