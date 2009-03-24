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

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import static org.eclipse.swordfish.registry.TestData.*;
import static org.eclipse.swordfish.registry.TestUtilities.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

public class WSDLServletPutTest {

	private HttpServletRequestStub request;

	private HttpServletResponseStub response = new HttpServletResponseStub();

	private WSDLRepository repositoryMock = createMock(WSDLRepository.class);

	private WSDLReader readerMock = createMock(WSDLReader.class);

	private WSDLServlet servlet = new WSDLServlet();

	private String httpBody = "body";
	
	@Test
	public void givenWSDLInBodyShouldBesRegisteredAsWSDlResourceInRepository()
			throws Exception {
		request = createPutRequest(ID_1, httpBody);

		WSDLResource wsdl = new WSDLResource(null);
		expect(readerMock.readFrom(eq(ID_1), (Reader) eqReader(httpBody)))
				.andReturn(wsdl);
		repositoryMock.add(wsdl);

		replay(readerMock, repositoryMock);

		servlet.setWsdlReader(readerMock);
		servlet.setRepository(repositoryMock);

		servlet.doPut(request, response);

		verify(readerMock, repositoryMock);
	}

	@Test
	public void givenReaderThrowsExceptionShouldReturnStatusCodeBadArgument()
			throws Exception {
		request = createPutRequest(ID_1, httpBody);

		expect(readerMock.readFrom(eq(ID_1), (Reader) eqReader(httpBody))).andThrow(
				new InvalidFormatException());

		replay(readerMock, repositoryMock);

		servlet.setWsdlReader(readerMock);
		servlet.setRepository(repositoryMock);

		servlet.doPut(request, response);

		assertThat("Wrong HTTP code: ", response.getError(),
				equalTo(SC_BAD_REQUEST));
		verify(readerMock, repositoryMock);
	}

	private static HttpServletRequestStub createPutRequest(final String id, final String httpBody) {

		return new HttpServletRequestStub() {
			@Override
			public BufferedReader getReader() throws IOException {
				return new BufferedReader(new StringReader(httpBody));
			}
			
			@Override
			public String getPathInfo() {
				return "/" + id;
			}
		};
	}
}
