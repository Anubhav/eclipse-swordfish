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

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSILServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8376659320998034145L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WSILServlet.class);

	private WSILWriter wsilWriter = new WSILWriter();;

	private WSDLRepository repository;

	public WSILServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LOGGER.info("Recieved request:\n{}", req.getRequestURL());
		ListResource<WSDLResource> resource = null;

		if (req.getParameterMap().isEmpty()) {
			String resourceId = getResourceId(req);
			if (resourceId == null) {
				resource = repository.getAll();
			} else {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

		} else {

			String type = req.getParameter("type");

			if (type == null) {
				resp
						.sendError(HttpServletResponse.SC_BAD_REQUEST,
								"If using query paramters the parameter 'type' is mandatory");
				return;
			} else if ("portType".equals(type)) {
				String portTypeName = req.getParameter("name");
				String targetNameSpace = req.getParameter("targetNamespace");

				if (portTypeName == null || targetNameSpace == null) {
					resp
							.sendError(
									HttpServletResponse.SC_BAD_REQUEST,
									"If the query parameter type is set to portType also the query parameters name and targetNamespace have to be defined");
					return;
				}

				resource = repository.getByPortTypeName(new QName(
						targetNameSpace, portTypeName));

			} else if ("service".equals(type)) {
				String portTypeNamespace = req
						.getParameter("refPortTypeNamespace");
				String portTypeName = req.getParameter("refPortTypeName");

				if (portTypeNamespace == null || portTypeName == null) {
					resp
							.sendError(
									HttpServletResponse.SC_BAD_REQUEST,
									"If the query parameter type is set to service also the query parameters refPortTypeNamespace and refPortTypeName have to be defined");
					return;
				}

				resource = repository.getReferencingPortType(new QName(
						portTypeNamespace, portTypeName));
			} else {
				resp
						.sendError(
								HttpServletResponse.SC_BAD_REQUEST,
								"Only the values portType and service are accepted for the query parameter type");
				return;
			}
		}
		resp.setContentType(resource.getContentType());
		resp.setCharacterEncoding(resource.getCharacterEncoding());
		wsilWriter.writeTo(resource, getBaseUrl(req), resp.getWriter());
	}

	public void setWsdlReader(WSDLReader wsdlReader) {

	}

	public void setRepository(WSDLRepository repository) {
		this.repository = repository;
	}

	private static String getResourceId(HttpServletRequest req) {
		String pathInfo = req.getPathInfo();
		if (pathInfo != null && pathInfo.length() > 1) {
			return pathInfo.substring(1);
		}
		return null;
	}
	
	private static String getBaseUrl(HttpServletRequest req) {
		System.out.println("requestURL: " + req.getRequestURL());
		System.out.println("requestURI: " + req.getRequestURI());
		StringBuffer rURL = req.getRequestURL();
		String base = rURL.substring(0, rURL.lastIndexOf("/") + 1);

		return base + "wsdl";
	}
	
	static class WSILWriter {

		static final public String PREFIX =
		"<?xml version=\"1.0\"?>\n" + 
		"<inspection xmlns=\"http://schemas.xmlsoap.org/ws/2001/10/inspection/\">\n"; 

		static final public String ITEM_PREFIX =
		"  <service>\n" + 
		"    <description referencedNamespace=\"http://schemas.xmlsoap.org/wsdl/\"\n" + 
		"                 location=\"";
		static final public String ITEM_POSTFIX ="\"/>\n  </service>\n";
		 
		static final public String POSTFIX =
				"</inspection>";
		
		public void writeTo(ListResource<WSDLResource>resources, String baseUrl, Writer writer) throws IOException {
			writer.append(PREFIX);
			
			Iterator<WSDLResource > resourceIter = resources.getResources();
			while (resourceIter.hasNext()) {
				writer.append(ITEM_PREFIX);
				writer.append(baseUrl + "/" + resourceIter.next().getId());
				writer.append(ITEM_POSTFIX);
			}
			writer.append(POSTFIX);
		}
	}
}
