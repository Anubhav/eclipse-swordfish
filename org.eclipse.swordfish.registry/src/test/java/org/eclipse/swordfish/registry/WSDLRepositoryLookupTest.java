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

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.eclipse.swordfish.registry.TstData.*;
import static org.eclipse.swordfish.registry.TstUtil.wsdlResource;
import static org.eclipse.swordfish.registry.IsIteratorReturning.isIteratorReturning;

import org.junit.Assert;
import org.junit.Test;

public class WSDLRepositoryLookupTest {

	private InMemoryRepositoryImpl wsdlRepository = new InMemoryRepositoryImpl();

	private WSDLResource wsdl_1 = wsdlResource(ID_1);
	private WSDLResource wsdl_2 = wsdlResource(ID_2);
	private WSDLResource wsdl_3 = wsdlResource(ID_3);
	private WSDLResource wsdl_4 = wsdlResource(ID_4);

	@Test
	public void shouldReturnWSDLsWithMatchingPortTypeNames() {
		wsdlRepository.registerByPortTypeName(PORT_TYPE_NAME_11, wsdl_1);
		wsdlRepository.registerByPortTypeName(PORT_TYPE_NAME_11, wsdl_2);
		wsdlRepository.registerByPortTypeName(PORT_TYPE_NAME_12, wsdl_3);
		wsdlRepository.registerByPortTypeName(PORT_TYPE_NAME_11, wsdl_4);
		wsdlRepository.unregisterAll(ID_4);

		assertThatLR(wsdlRepository.getByPortTypeName(PORT_TYPE_NAME_11),
				contains(wsdl_1, wsdl_2));
	}

	@Test
	public void shouldReturnWSDLsReferencingSpecifiedPortType() {
		wsdlRepository.registerServiceRefPortType(PORT_TYPE_NAME_11, wsdl_1);
		wsdlRepository.registerServiceRefPortType(PORT_TYPE_NAME_11, wsdl_2);
		wsdlRepository.registerServiceRefPortType(PORT_TYPE_NAME_12, wsdl_3);
		wsdlRepository.registerServiceRefPortType(PORT_TYPE_NAME_11, wsdl_4);
		wsdlRepository.unregisterAll(ID_4);

		assertThatLR(wsdlRepository.getReferencingPortType(PORT_TYPE_NAME_11),
				contains(wsdl_1, wsdl_2));
	}

	@Test
	public void shouldReturnWSDLsWithMatchingId() {
		wsdlRepository.registerById(ID_1, wsdl_1);
		wsdlRepository.registerById(ID_2, wsdl_2);

		assertThat(wsdlRepository.getWithId(ID_1), equalTo(wsdl_1));
	}

	@Test
	public void GivenGetAllCalledShouldReturnAllWSDLsRegisteredById() {
		wsdlRepository.registerById(ID_1, wsdl_1);
		wsdlRepository.registerById(ID_2, wsdl_2);
		wsdlRepository.registerById(ID_3, wsdl_3);
		wsdlRepository.registerById(ID_4, wsdl_4);

		assertThatLR(wsdlRepository.getAll(), contains(wsdl_1, wsdl_2, wsdl_3,
				wsdl_4));
	}

	@Test
	public void givenUnregisterCalledGetWithIdShouldReturnNull() {
		wsdlRepository.registerById(ID_1, wsdl_1);
		wsdlRepository.unregisterAll(ID_1);

		assertThat(wsdlRepository.getWithId(ID_1), nullValue());
	}

	@Test
	public void givenDeleteCalledShouldRemoveAllOccurences() {
		wsdlRepository.registerServiceRefPortType(PORT_TYPE_NAME_11, wsdl_1);
		wsdlRepository.registerById(ID_1, wsdl_1);
		wsdlRepository.registerByPortTypeName(PORT_TYPE_NAME_11, wsdl_1);

		boolean deleted = wsdlRepository.delete(ID_1);

		assertTrue(deleted);
		assertThatLR(wsdlRepository.getByPortTypeName(PORT_TYPE_NAME_11),
				contains());
		assertThatLR(wsdlRepository.getReferencingPortType(PORT_TYPE_NAME_11),
				contains());
		assertThat(wsdlRepository.getWithId(ID_1), nullValue());
	}

	@Test
	public void givenDeleteWithNonExistingIdCalledShouldReturnFalse() {
		assertFalse(wsdlRepository.delete(ID_1));
	}

	public static <T extends Resource> void assertThatLR(
			ListResource<T> actual, T... expected) {
		Assert.assertThat(actual.getResources(), isIteratorReturning(expected));
	}

	public static WSDLResource[] contains(WSDLResource... resources) {
		return resources;
	}

	private static <T> List<T> asList(T... objects) {
		List<T> result = new ArrayList<T>();
		Collections.addAll(result, objects);
		return result;
	}
}
