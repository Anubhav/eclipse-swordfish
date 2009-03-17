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

import static org.easymock.EasyMock.*;
import static org.eclipse.swordfish.registry.TstData.ID_1;
import static org.eclipse.swordfish.registry.TstData.ID_2;
import static org.eclipse.swordfish.registry.TstUtil.wsdlResource;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.junit.Test;

public class WSDLResourceTest {

	@Test
	public void appendContentWriterShouldReturnContentFromPersistentData()
			throws Exception {
		final String content = "sassa{ŠŸ§}";
		StringWriter writer = new StringWriter();

		WSDLResource wsdlResource = new WSDLResource(new PersistentDataStub() {

			@Override
			public InputStream read() throws IOException {
				return new ByteArrayInputStream(content.getBytes("utf8"));
			}
		});

		wsdlResource.appendContent(writer);

		assertThat(writer.toString(), equalTo(content));
	}

	@Test
	public void testDelete() {
		PersistentData pDataMock = createMock(PersistentData.class);
		pDataMock.delete();
		replay(pDataMock);

		WSDLResource wsdl = new WSDLResource(pDataMock);
		wsdl.delete();

		verify(pDataMock);

	}

	@Test
	public void testEquals() {
		WSDLResource wsdl1 = wsdlResource(ID_1);
		WSDLResource wsdl2 = wsdlResource(ID_2);
		WSDLResource wsdl3 = wsdlResource(ID_1);

		assertTrue("A WSDLResource is not equal to itself", wsdl1.equals(wsdl1));
		assertFalse("A WSDLResource must not be equal to null", wsdl1
				.equals(null));
		assertFalse("A WSDLResource must not be equal to another type", wsdl1
				.equals(new Object()));

		assertFalse(wsdl1 + " is equal to " + wsdl2, wsdl1.equals(wsdl2));
		assertTrue(wsdl1 + " is not equal to " + wsdl3, wsdl1.equals(wsdl3));
	}
}
