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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSDLServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8376659320998034145L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WSDLServlet.class);

	private WSDLReader wsdlReader;

	private WSDLRepository repository;

	public WSDLServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LOGGER.info("Recieved request:\n{}", req.getRequestURL());
		Resource resource = null;

		if (req.getParameterMap().isEmpty()) {
			String resourceId = getResourceId(req);
			if (resourceId == null) {
				resource = repository.getAll();
			} else {
				resource = repository.getWithId(resourceId);
				if (resource == null) {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
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
		resource.appendContent(resp.getWriter());
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		WSDLResource wsdl = null;
		String resourceId = getResourceId(req);
		try {
			wsdl = wsdlReader.readFrom(resourceId, req.getReader());
			repository.add(wsdl);
		} catch (InvalidFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Body does not contain a valid WSDL.");
			return;
		} catch (RegistryException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e
					.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String resourceId = getResourceId(req);
		repository.delete(resourceId);
	}

	public void setWsdlReader(WSDLReader wsdlReader) {
		this.wsdlReader = wsdlReader;
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

}
