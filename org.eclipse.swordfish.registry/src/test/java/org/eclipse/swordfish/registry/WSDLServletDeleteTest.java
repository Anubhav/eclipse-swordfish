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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.eclipse.swordfish.registry.TestData.ID_1;

import org.junit.Before;
import org.junit.Test;



public class WSDLServletDeleteTest {

	private HttpServletRequestStub request;

	private HttpServletResponseStub response = new HttpServletResponseStub();

	private WSDLRepository repositoryMock;

	private WSDLReader readerMock;

	private WSDLServlet servlet;

	@Before
	public void setUp() {
		repositoryMock = createMock(WSDLRepository.class);
		readerMock = createMock(WSDLReader.class);

		servlet = new WSDLServlet();
		servlet.setWsdlReader(readerMock);
		servlet.setRepository(repositoryMock);		
	}
	
	@Test
	public void givenIdExistsShouldDeleteRepository()
			throws Exception {
		request = createDeleteRequest(ID_1);

		expect(repositoryMock.delete(ID_1)).andReturn(true);

		replay(readerMock, repositoryMock);


		servlet.doDelete(request, response);

		verify(readerMock, repositoryMock);
	}

	private static HttpServletRequestStub createDeleteRequest(final String id) {

		return new HttpServletRequestStub() {
			@Override
			public String getPathInfo() {
				return "/" + id;
			}
		};
	}

}
