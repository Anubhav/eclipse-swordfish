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
package org.eclipse.swordfish.plugins.resolver.wsdl;

import org.eclipse.swordfish.api.registry.EndpointDescription;
import org.eclipse.swordfish.api.registry.ServiceDescription;

/**
 *
 */
public class WSDLEndpointDescription implements EndpointDescription {

	private String address;

	private String endpointTransport;

	private String name;

	private ServiceDescription<?> description;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEndpointTransport() {
		return endpointTransport;
	}

	public void setEndpointTransport(String endpointTransport) {
		this.endpointTransport = endpointTransport;
	}

	public ServiceDescription<?> getServiceDescription() {
		return description;
	}

	public void setServiceDescription(ServiceDescription<?> description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
