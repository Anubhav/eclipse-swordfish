/*******************************************************************************
 * Copyright (c) 2008, 2009 SOPERA GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SOPERA GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.swordfish.plugins.resolver.proxy.local;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.registry.ServiceDescription;
import org.eclipse.swordfish.plugins.resolver.proxy.impl.AbstractDocumentProvider;
import org.eclipse.swordfish.plugins.resolver.wsdl11.WSDL11ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class FilesystemDocumentProvider extends AbstractDocumentProvider {

    private static final Logger logger = LoggerFactory.getLogger(FilesystemDocumentProvider.class);

	private static final String WSDL_STORAGE_PROPERTY = "wsdlStorage";

    private WSDLManager wsdlManager;

	public List<ServiceDescription<?>> getServiceProviderDescriptions(QName interfaceName) {
		List<ServiceDescription<?>> descriptions = new ArrayList<ServiceDescription<?>>();
		try {
			Definition definition = wsdlManager.getDefinition(interfaceName);
			if (definition != null) {
				ServiceDescription<Definition> description = new WSDL11ServiceDescription(definition);
				descriptions.add(description);
				if (logger.isDebugEnabled()) {
					logger.debug("Successfully retrieved service description: "
						+ description.getServiceName() + " for portType: " + interfaceName);
				}
			}
		} catch (WSDLException e) {
			logger.error("Error resolving endpoint - couldn't retrieve service "
				+ "description for port type " + interfaceName, e);
			throw new SwordfishException("Error resolving endpoint - couldn't retrieve "
				+ "service description for port type " + interfaceName + " message: " + e.getMessage());
		}
		return descriptions;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onReceiveConfiguration(Map<String, Object> configuration) {
		super.onReceiveConfiguration(configuration);

		if (configuration != null && configuration.containsKey(WSDL_STORAGE_PROPERTY)) {
			try {
				List<String> resourcesToLoad = (List<String>) configuration.get(WSDL_STORAGE_PROPERTY);

				wsdlManager = new WSDLManagerImpl();
				for (String nextResource : resourcesToLoad) {
					URL resourceUrl = new URL(nextResource);
					wsdlManager.setupWSDLs(resourceUrl);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Successfully initialized WSDL manager");
				}
			} catch (MalformedURLException e) {
				logger.error("Couldn't initialize filesystem "
					+ "document provider: malformed WSDL location specified.", e);
				throw new IllegalArgumentException("Couldn't initialize filesystem "
					+ "document provider: malformed WSDL location specified.", e);
			} catch (WSDLException e) {
				logger.error("Couldn't initialize filesystem "
					+ "document provider: an error occured during intialization of WSDL manager.", e);
				throw new IllegalStateException("Couldn't initialize filesystem "
					+ "document provider: an error occured during intialization of WSDL manager.", e);
			} catch (IOException e) {
				logger.error("Couldn't initialize filesystem "
					+ "document provider: an error occured during intialization of WSDL manager.", e);
				throw new IllegalStateException("Couldn't initialize filesystem "
					+ "document provider: an error occured during intialization of WSDL manager.", e);
			}
		}
	}

	public WSDLManager getWsdlManager() {
		return wsdlManager;
	}

}
