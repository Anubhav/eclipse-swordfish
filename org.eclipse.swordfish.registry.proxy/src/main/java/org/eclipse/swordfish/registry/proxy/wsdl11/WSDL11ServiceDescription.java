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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swordfish.api.registry.ServiceDescription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.xml.namespace.QName;

/**
 *
 */
public class WSDL11ServiceDescription implements ServiceDescription<Definition> {

	private static final Logger logger = LoggerFactory.getLogger(WSDL11ServiceDescription.class);

	/** The wsdl. */
	private Definition wsdl;

	/** The port type. */
	private PortType portType;

	/** The service. */
	private Service service;

	/** The populated. */
	private boolean populated;

	private Map<String, String> availableLocations;

	public WSDL11ServiceDescription(Definition wsdl) {
		if (wsdl == null) {
			throw new IllegalArgumentException("Definition could not be null.");
		}
		this.wsdl = wsdl;

		try {
			populate();
		} catch (WSDLException e) {
			throw new IllegalStateException("Couldn't populate a service description.", e);
		}
	}

	private void populate() throws WSDLException {
		if (this.populated) {
			return;
		}
		this.portType = getServicePortType();
		// use the first (and only) Service in the SPDX
		this.service = (Service) wsdl.getServices().values().iterator().next();
		this.populated = true;
	}

	/**
	 * Gets the service port type.
	 *
	 * @return the service port type
	 */
	private PortType getServicePortType() {
		return (PortType) wsdl.getPortTypes().values().iterator().next();
	}

	public synchronized Map<String, String> getAvailableLocations() {
		if (!populated) {
			throw new IllegalStateException("Has not been populated yet");
		}
		if (availableLocations != null) {
			return availableLocations;
		}
		availableLocations = new HashMap<String, String>();
		Map ports = service.getPorts();
		try {
			for (Object portObj : service.getPorts().values()) {
				Port port = (Port) portObj;
				SOAPAddress address = null;
				for (Object soapAddr : port.getExtensibilityElements()) {
					if (soapAddr instanceof SOAPAddress) {
						address = (SOAPAddress) soapAddr;
					}
				}
				SOAPBinding soapBinding = null;
				if (address != null) {
					for (Object  soapBindObj : port.getBinding().getExtensibilityElements()) {
						if (soapBindObj instanceof SOAPBinding) {
							soapBinding = (SOAPBinding) soapBindObj;
						}
					}
				}
				if (address != null && soapBinding != null && soapBinding.getTransportURI() != null) {
					availableLocations.put(address.getLocationURI(), soapBinding.getTransportURI());
				}
			}
		} catch (Exception ex) {
			logger.warn("Ignoring the exception and returning null", ex);
			availableLocations = new HashMap<String, String>();
		}
		return availableLocations;
	}

	public Class<WSDL11ServiceDescription> getDescriptionType() {
		return WSDL11ServiceDescription.class;
	}

	public QName getServiceQName() {
		return service.getQName();
	}
}
