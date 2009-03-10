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
package org.eclipse.swordfish.registry.proxy.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXB;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.eclipse.swordfish.registry.proxy.ClientRequest;
import org.eclipse.swordfish.registry.proxy.ClientResponse;
import org.eclipse.swordfish.registry.proxy.ProxyConstants.Method;
import org.eclipse.swordfish.registry.proxy.ProxyConstants.Status;

/**
 *
 */
public class HttpCilentProxy extends AbstractProxy {

	private HttpClient client;

	public HttpCilentProxy() {
		client = new HttpClient();
	}

	@Override
	public ClientResponse invoke(ClientRequest request) {
		ClientResponse response = new ClientResponseImpl();
		HttpMethodBase method = getMethod(request.getMethod());

		try {
			method.setURI(new URI(request.getURI().toString(), true));
			int statusCode = getClient().executeMethod(method);
			response.setStatus(Status.get(statusCode));

			String responseBody = method.getResponseBodyAsString();
			if (request.getEntityType() != null) {
				Reader responseReader = new StringReader(responseBody);
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				try {
					Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
					response.setEntity(JAXB.unmarshal(responseReader, request.getEntityType()));
				} finally {
					Thread.currentThread().setContextClassLoader(cl);
				}
			} else {
				response.setEntity(responseBody);
			}
		} catch (HttpException e) {
			response.setStatus(Status.ERROR);
			response.setEntity(e);
		} catch (IOException e) {
			response.setStatus(Status.ERROR);
			response.setEntity(e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return response;
	}

	private HttpMethodBase getMethod(Method method) {
		HttpMethodBase result = null;

		if (method.equals(Method.GET)) {
			result = new GetMethod();
		} else if (method.equals(Method.POST)) {
			result = new PostMethod();
		} else if (method.equals(Method.DELETE)) {
			result = new DeleteMethod();
		} else if (method.equals(Method.PUT)) {
			result = new PutMethod();
		}
		return result;
	}

	public HttpClient getClient() {
		return client;
	}
}
