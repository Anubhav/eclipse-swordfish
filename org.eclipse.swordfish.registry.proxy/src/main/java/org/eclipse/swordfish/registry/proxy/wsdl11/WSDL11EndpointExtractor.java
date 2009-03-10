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
package org.eclipse.swordfish.registry.proxy.wsdl11;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.eclipse.swordfish.api.registry.EndpointDescription;
import org.eclipse.swordfish.api.registry.EndpointExtractor;
import org.eclipse.swordfish.registry.proxy.wsdl.WSDLEndpointDescription;

/**
 *
 */
public class WSDL11EndpointExtractor implements EndpointExtractor<WSDL11ServiceDescription> {

	public Collection<Class<WSDL11ServiceDescription>> getSupportedTypes() {
		return Arrays.asList(WSDL11ServiceDescription.class);
	}

	public EndpointDescription<?, ?> extractEndpoint(WSDL11ServiceDescription serviceDescription) {
		WSDLEndpointDescription endpoint = new WSDLEndpointDescription();
		for (Map.Entry<String, String> entry : serviceDescription.getAvailableLocations().entrySet()) {
			endpoint.setEndpointAddress(entry.getKey());
			endpoint.setEndpointTransport(entry.getValue());
			break;
		}
		return endpoint;
	}

}
