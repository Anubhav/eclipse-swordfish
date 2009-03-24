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

import static java.util.Collections.emptyList;
import static javax.servlet.http.HttpServletResponse.*;
import static org.easymock.EasyMock.*;
import static org.eclipse.swordfish.registry.TestData.*;
import static org.eclipse.swordfish.registry.TestUtilities.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;

public class WSDLServletGetTest {

	private StringWriter writer = new StringWriter();

	private HttpServletRequestStub request;

	private HttpServletResponseStub response = createHttpServletResponse(writer);

	private WSDLRepository repositoryMock;

	private WSDLServlet servlet;
	
	@Before
	public void setup() {
		repositoryMock = createMock(WSDLRepository.class);
		servlet = new WSDLServlet();
		servlet.setRepository(repositoryMock);
		
	}

	@Test
	public void givenParameterTypeIsPortTypeShouldReturnPortTypeWSDLs()
			throws Exception {
		request = createPortTypeRequest(PORT_TYPE_NAME_11);

		expect(repositoryMock.getByPortTypeName(PORT_TYPE_NAME_11)).andReturn(
				createListResource(CONTENT));

		replay(repositoryMock);

		servlet.doGet(request, response);

		assertThat("Wrong response", writer.toString(), equalTo(CONTENT));
		assertThat("Wrong content type: ", response.getContentType(),
				equalTo("application/xml"));
		assertThat("Wrong character encoding: ", response
				.getCharacterEncoding(), equalTo("UTF-8"));

		verify(repositoryMock);
	}

	@Test
	public void givenNoParameterShouldReturnAllWdls()
			throws Exception {
		request = createHttpRequestWithParams(EMPTY_PARAMS);

		expect(repositoryMock.getAll()).andReturn(
				createListResource(CONTENT));

		replay(repositoryMock);

		servlet.doGet(request, response);

		assertThat("Wrong response", writer.toString(), equalTo(CONTENT));
		assertThat("Wrong content type: ", response.getContentType(),
				equalTo("application/xml"));
		assertThat("Wrong character encoding: ", response
				.getCharacterEncoding(), equalTo("UTF-8"));

		verify(repositoryMock);
	}

	@Test
	public void givenParameterTypeIsServiceShouldReturnRefPortTypeWSDLS()
			throws Exception {
		request = createRefPortTypeRequest(PORT_TYPE_NAME_11);

		expect(repositoryMock.getReferencingPortType(PORT_TYPE_NAME_11)).andReturn(
				createListResource(CONTENT));

		replay(repositoryMock);

		servlet.doGet(request, response);

		assertThat("Wrong response", writer.toString(), equalTo(CONTENT));
		assertThat("Wrong content type: ", response.getContentType(),
				equalTo("application/xml"));
		assertThat("Wrong character encoding: ", response
				.getCharacterEncoding(), equalTo("UTF-8"));

		verify(repositoryMock);
	}

	@Test
	public void givenIdInInfoPathShouldReturnMatchingWsdlFromRepository()
			throws Exception {
		request = createResourceRequest("/" + ID_1);

		expect(repositoryMock.getWithId(ID_1)).andReturn(
				wsdlResource(ID_1, CONTENT));

		replay(repositoryMock);

		servlet.doGet(request, response);

		assertThat("Wrong response", writer.toString(), equalTo(CONTENT));
		assertThat("Wrong content type: ", response.getContentType(),
				equalTo("application/xml"));
		assertThat("Wrong character encoding: ", response
				.getCharacterEncoding(), equalTo("UTF-8"));

		verify(repositoryMock);
	}

	@Test
	public void givenParameterTypeIsUnknownShouldReturnBadArgumentCode()
			throws Exception {
		request = createHttpRequestWithParams(asMap(entry("type", "unknown")));

		servlet.doGet(request, response);

		assertThat("Wrong HTTP code: ", response.getError(),
				equalTo(SC_BAD_REQUEST));
	}

	@Test
	public void givenParameterTypeIsPortTypeButOthersAreMissingShouldReturnBadArgumentCode()
			throws Exception {
		request = createHttpRequestWithParams(asMap(entry("type", "portType")));

		servlet.doGet(request, response);

		assertThat("Wrong HTTP code: ", response.getError(),
				equalTo(SC_BAD_REQUEST));
	}

	@Test
	public void givenParameterTypeIsServiceButOthersAreMissingShouldReturnBadArgumentCode()
			throws Exception {
		request = createHttpRequestWithParams(asMap(entry("type", "service")));

		servlet.doGet(request, response);

		assertThat("Wrong HTTP code: ", response.getError(),
				equalTo(SC_BAD_REQUEST));
	}

	@Test
	public void givenPathInfoDoesNotSpecifyValidIDShouldReturn404()
			throws Exception {
		request = createResourceRequest("/" + ID_1);

		expect(repositoryMock.getWithId(ID_1)).andReturn(null);

		replay(repositoryMock);

		servlet.doGet(request, response);

		assertThat("Wrong HTTP code: ", response.getError(),
				equalTo(SC_NOT_FOUND));
	}

	public HttpServletRequestStub createPortTypeRequest(final QName portTypeName) {
		Map<String, String> params = asMap(
				entry("type", "portType"),
				entry("targetNamespace", portTypeName.getNamespaceURI()),
				entry("name", portTypeName.getLocalPart()));
		return createHttpRequestWithParams(params);
	}

	public HttpServletRequestStub createRefPortTypeRequest(QName portTypeName) {
		Map<String, String> params = asMap(
				entry("type", "service"),
				entry("refPortTypeNamespace", portTypeName.getNamespaceURI()),
				entry("refPortTypeName", portTypeName.getLocalPart()));
		return createHttpRequestWithParams(params);
	}

	public HttpServletRequestStub createResourceRequest(final String id) {
		return new HttpServletRequestStub() {
			@Override
			public String getPathInfo() {
				return id;
			}
			@Override
			public String getParameter(String name) {
				return null;
			}
	
			@Override
			public Map getParameterMap() {
				return EMPTY_PARAMS;
			}
		};
	}

	private static HttpServletRequestStub createHttpRequestWithParams(
			final Map<String, String> params) {
		return new HttpServletRequestStub() {
			protected Map<String, String> parameters = params;

			@Override
			public String getParameter(String name) {
				return parameters.get(name);
			}
	
			@Override
			public Map getParameterMap() {
				return parameters;
			}
		};
	}

	private static HttpServletResponseStub createHttpServletResponse(
			final Writer writer) {
		return new HttpServletResponseStub() {
			private PrintWriter pWriter = new PrintWriter(writer);
			@Override
			public PrintWriter getWriter() {
				return pWriter;
			}
		};
	}

	public static ListResource<WSDLResource> createListResource(
			final String content) {
		List<WSDLResource> empty = emptyList();
		return new ListResource<WSDLResource>(empty) {
			@Override
			public void appendContent(Writer writer) throws IOException {
				writer.append(content);
			}
		};
	}
}
