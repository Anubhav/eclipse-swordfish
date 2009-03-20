/*******************************************************************************
* Copyright (c) 2008, 2009 SOPERA GmbH.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* SOPERA GmbH - initial API and implementation
*******************************************************************************/
package org.eclipse.swordfish.registry;

import java.util.Hashtable;

import javax.servlet.ServletException;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletActivator {
	

	private static final String WSDL_SERVLET_ALIAS = "/registry/wsdl";
	
	private static final String WSIL_SERVLET_ALIAS = "/registry/wsil";

	private static String webParam = "wsdlLocation";

	private static final String LOCATION_PROPERTY = "org.eclipse.swordfish.registry.fileLocation";
	
    private static final Logger LOGGER = LoggerFactory
    .getLogger(ServletActivator.class);

    private WSDLServlet wsdlServlet;
	
    private WSILServlet wsilServlet;

    private HttpService httpService;
	
	
	public void start() throws RegistryException {
		LOGGER.info("Starting the LookupServlet.");

		Hashtable<String, String> params = getParams();

		try {
			httpService.registerServlet(WSDL_SERVLET_ALIAS, wsdlServlet, params, null);
		} catch (ServletException e) {
			throwRegistryException("The initialization of the WSDLServlet failed.", e);
		} catch (NamespaceException e) {
			throwRegistryException("The LookupServlet cannot be registered under the alias " + WSDL_SERVLET_ALIAS + " because another servlet is already registered under this name", e);
		}
		try {
			httpService.registerServlet(WSIL_SERVLET_ALIAS, wsilServlet, params, null);
		} catch (ServletException e) {
			throwRegistryException("The initialization of the WSDLServlet failed.", e);
		} catch (NamespaceException e) {
			throwRegistryException("The LookupServlet cannot be registered under the alias " + WSIL_SERVLET_ALIAS + " because another servlet is already registered under this name", e);
		}
	}

	public void stop(){
		LOGGER.info("Stoping the LookupServlet.");
		httpService.unregister(WSDL_SERVLET_ALIAS);
		httpService.unregister(WSIL_SERVLET_ALIAS);
	}

	public void setWsdlServlet(WSDLServlet servlet) {
		this.wsdlServlet = servlet;
	}

	public void setWsilServlet(WSILServlet servlet) {
		this.wsilServlet = servlet;
	}

	public void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}

	private static Hashtable<String, String> getParams() throws RegistryException {
		String fileLocation = System.getProperty(LOCATION_PROPERTY);
		
		if (fileLocation == null) {
			throwRegistryException("The system property " + LOCATION_PROPERTY + " is not defined.", null);
		}
	
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put(webParam, fileLocation);
		return params;
	}
	
	private static void throwRegistryException(String errorMessage, Throwable e) throws RegistryException {
		LOGGER.error(errorMessage);
		throw new RegistryException(errorMessage, e);		
	}
}