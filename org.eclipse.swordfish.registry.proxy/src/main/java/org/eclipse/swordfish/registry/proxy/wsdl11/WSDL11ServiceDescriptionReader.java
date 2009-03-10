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

import java.io.Reader;
import java.net.URL;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import org.eclipse.swordfish.api.registry.ServiceDescription;
import org.eclipse.swordfish.registry.proxy.wsdl.ServiceDescriptionReader;
import org.xml.sax.InputSource;

/**
 *
 */
public class WSDL11ServiceDescriptionReader implements
		ServiceDescriptionReader<Definition> {

	private WSDLFactory factory;

	public WSDL11ServiceDescriptionReader() {
		try {
			factory = WSDLFactory.newInstance();
		} catch (WSDLException e) {
			throw new IllegalStateException(e.getMessage());
		}
	}

	public ServiceDescription<Definition> readDescription(Reader description) {
		ServiceDescription<Definition> resultDescription;

		try {
	        InputSource inputSource = new InputSource(description);
	        WSDLReader reader = factory.newWSDLReader();
			reader.setFeature("javax.wsdl.verbose", false);
	        reader.setFeature("javax.wsdl.importDocuments", false);
			Definition definition = reader.readWSDL(null, inputSource);
			resultDescription = new WSDL11ServiceDescription(definition);
		} catch (WSDLException e) {
			throw new IllegalStateException(e.getMessage());
		}
		return resultDescription;
	}

	public ServiceDescription<Definition> readDescription(URL descriptionURL) {
		ServiceDescription<Definition> resultDescription;

		try {
			WSDLReader reader = factory.newWSDLReader();
			reader.setFeature("javax.wsdl.verbose", false);
			reader.setFeature("javax.wsdl.importDocuments", false);
			Definition definition = reader.readWSDL(descriptionURL.toString());
			resultDescription = new WSDL11ServiceDescription(definition);
		} catch (WSDLException e) {
			throw new IllegalStateException(e.getMessage());
		}
		return resultDescription;
	}

}
