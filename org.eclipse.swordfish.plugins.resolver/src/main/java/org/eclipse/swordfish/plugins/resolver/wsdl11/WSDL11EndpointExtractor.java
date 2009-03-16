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
package org.eclipse.swordfish.plugins.resolver.wsdl11;

import java.util.Map;

import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;

import org.eclipse.swordfish.api.registry.EndpointDescription;
import org.eclipse.swordfish.api.registry.EndpointExtractor;
import org.eclipse.swordfish.api.registry.ServiceDescription;
import org.eclipse.swordfish.plugins.resolver.wsdl.WSDLEndpointDescription;

/**
 *
 */
public class WSDL11EndpointExtractor implements EndpointExtractor {

	private WSDL11ServiceDescription instance;

	public WSDL11EndpointExtractor() {
		instance = new WSDL11ServiceDescription();
	}

	public boolean canHandle(ServiceDescription<?> description) {
		return instance.getType().equals(description.getType());
	}

	public EndpointDescription extractEndpoint(ServiceDescription<?> serviceDescription) {
		WSDL11ServiceDescription wsdlDescription = instance.getClass().cast(serviceDescription);
		WSDLEndpointDescription endpoint = new WSDLEndpointDescription();
		for (Map.Entry<SOAPAddress, SOAPBinding> entry : wsdlDescription.getAvailableLocations().entrySet()) {
			endpoint.setAddress(entry.getKey().getLocationURI());
			endpoint.setEndpointTransport(entry.getValue().getTransportURI());
			break;
		}
		endpoint.setServiceDescription(serviceDescription);
		endpoint.setName(wsdlDescription.getPortName(endpoint.getAddress(),
			endpoint.getEndpointTransport()));
		return endpoint;
	}

}
