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
package org.eclipse.swordfish.registry.proxy.wsdl;

import org.eclipse.swordfish.api.registry.EndpointDescription;

/**
 *
 */
public class WSDLEndpointDescription implements EndpointDescription<String, String> {

	private String endpointAddress;

	private String endpointTransport;

	public String getEndpointAddress() {
		return endpointAddress;
	}

	public void setEndpointAddress(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	public String getEndpointTransport() {
		return endpointTransport;
	}

	public void setEndpointTransport(String endpointTransport) {
		this.endpointTransport = endpointTransport;
	}
}
