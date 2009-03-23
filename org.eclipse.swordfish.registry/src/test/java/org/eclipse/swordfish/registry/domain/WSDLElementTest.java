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
package org.eclipse.swordfish.registry.domain;

// import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.eclipse.swordfish.registry.TstData.*;
import static org.eclipse.swordfish.registry.TstUtil.asSet;
import static org.eclipse.swordfish.registry.TstUtil.wsdlResource;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swordfish.registry.WSDLResource;
import org.junit.Test;

public class WSDLElementTest {

	public static Set<WSDLResource> EMPTY_SET = emptySet();

	private WSDLResource wsdl_1 = wsdlResource(ID_1);

	private WSDLResource wsdl_2 = wsdlResource(ID_2);

	private WSDLElement wsdlElement = new WSDLElement(PORT_TYPE_NAME_11);

	@Test
	public void shouldReturnQNameDefinedDuringCreation() {
		assertThat(wsdlElement.getName(), equalTo(PORT_TYPE_NAME_11));
	}

	@Test
	public void givenJustCreatedGetSourcesShouldReturnEmpty() {
		assertThat(getSources(wsdlElement), equalTo(EMPTY_SET));
	}

	@Test
	public void givenAddedSourceShouldReturnInGetSources() {
		wsdlElement.addSource(wsdl_1);
		wsdlElement.addSource(wsdl_2);
		// BAD TEST: WHERE AN ORDER IS NOT ASSUMED, AN ORDER MUST NOT BE TESTED:
		// assertThat(getSources(wsdlElement), equalTo(list(wsdl_1, wsdl_2)));
		assertThat(getSources(wsdlElement), equalTo(asSet(wsdl_1, wsdl_2)));
	}

	@Test
	public void givenRemoveSourceShouldNotReturnInGetSources() {
		wsdlElement.addSource(wsdl_1);
		wsdlElement.addSource(wsdl_2);
		wsdlElement.removeSource(wsdl_1);

		assertThat(getSources(wsdlElement), equalTo(asSet(wsdl_2)));

	}

	@Test
	public void givenNoSourcesDefinedIsDefinedShouldBeFalse() {
		assertFalse(wsdlElement.sourcesDefined());
	}

	@Test
	public void givenSourcesDefinedIsDefinedShouldBeTrue() {
		wsdlElement.addSource(wsdl_1);
		assertTrue(wsdlElement.sourcesDefined());
	}

	/*
	// UNORDERED COLLECTiON OF SOURCES - NOT A LIST
	private static List<WSDLResource> getSources(WSDLElement element) {
		List<WSDLResource> sources = new ArrayList<WSDLResource>();
		element.sourcesAddTo(sources);
		return sources;
	}
	*/

	private static Set<WSDLResource> getSources(WSDLElement element) {
		Set<WSDLResource> sources = new HashSet<WSDLResource>();
		element.sourcesAddTo(sources);
		return sources;
	}

	@Test
	public void testEquals() {
		WSDLElement wsdlEl1 = new WSDLElement(QNAME_11);
		WSDLElement wsdlEl2 = new WSDLElement(QNAME_22);
		WSDLElement wsdlEl3 = new WSDLElement(QNAME_11);

		assertTrue("A WSDLElement is not equal to itself", wsdlEl1
				.equals(wsdlEl1));
		assertFalse("A WSDLElement must not be equal to null", wsdlEl1
				.equals(null));
		assertFalse("A WSDLResource must not be equal to another type", wsdlEl1
				.equals(new Object()));

		assertFalse(wsdlEl1 + " is equal to " + wsdlEl2, wsdlEl1
				.equals(wsdlEl2));
		assertTrue(wsdlEl1 + " is not equal to " + wsdlEl3, wsdlEl1
				.equals(wsdlEl3));

		assertFalse(
				"A WSDlElement of PortType cannot be equal to one of type Binding",
				new PortType(QNAME_11).equals(new Binding(QNAME_11)));

	}

}
