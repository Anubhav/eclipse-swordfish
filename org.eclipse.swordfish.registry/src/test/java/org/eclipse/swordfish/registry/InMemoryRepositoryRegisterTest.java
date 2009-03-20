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

import static org.eclipse.swordfish.registry.TstData.*;
import static org.eclipse.swordfish.registry.TstUtil.*;
import static org.eclipse.swordfish.registry.IsIteratorReturning.isIteratorReturning;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

public class InMemoryRepositoryRegisterTest {

	private InMemoryRepositoryImpl wsdlRepository = new InMemoryRepositoryImpl();

	@Test
	public void shouldReturnWSDLsWithMatchingPortTypeNames() {
		wsdlRepository.registerPortType(QNAME_11, WSDL_1);
		wsdlRepository.registerPortType(QNAME_11, WSDL_2);
		wsdlRepository.registerPortType(QNAME_12, WSDL_3);

		assertThatLR(wsdlRepository.getByPortTypeName(QNAME_11),
				contains(WSDL_1, WSDL_2));
	}

	@Test
	public void givenPortTypeNameNotMatchingShouldReturnEmptyResourceList() {
		wsdlRepository.registerPortType(QNAME_11, WSDL_1);
		wsdlRepository.registerPortType(QNAME_11, WSDL_2);

		assertThatLR(wsdlRepository.getByPortTypeName(QNAME_12),
				contains());
	}

	@Test
	public void givenNoPortTypeNameRegisteredShouldReturnEmptySetOfPortTypeNames() {
		Matcher<Iterator<QName>> matcher = isIteratorReturning();
		Assert.assertThat(wsdlRepository.getAllPortTypeNames(), matcher);
	}

	@Test
	public void shouldReturnAllRegisteredPortTypeNames() {
		wsdlRepository.registerPortType(QNAME_11, WSDL_1);
		wsdlRepository.registerPortType(QNAME_11, WSDL_2);
		wsdlRepository.registerPortType(QNAME_12, WSDL_3);

		Assert.assertThat(wsdlRepository.getAllPortTypeNames(),
				isIteratorReturning(QNAME_11, QNAME_12));
	}
	
	@Test
	public void shouldNotReturnPortTypesRegisteredThroughBinding() {
		Matcher<Iterator<QName>> empty = isIteratorReturning();
		wsdlRepository.registerBinding(QNAME_21, WSDL_1,QNAME_11);

		assertThat(wsdlRepository.getAllPortTypeNames(), empty);
	}

	@Test
	public void shouldReturnWSDLsWithMatchingBindingNames() {
		wsdlRepository.registerBinding(QNAME_21, WSDL_1, QNAME_11);
		wsdlRepository.registerBinding(QNAME_21, WSDL_2, QNAME_11);
		wsdlRepository.registerBinding(QNAME_22, WSDL_3, QNAME_11);

		assertThatLR(wsdlRepository.getByBindingName(QNAME_21),
				contains(WSDL_1, WSDL_2));
	}

	@Test
	public void givenBindingNameNotMatchingShouldReturnEmptyResourceList() {
		wsdlRepository.registerBinding(QNAME_21, WSDL_1, QNAME_11);
		wsdlRepository.registerBinding(QNAME_21, WSDL_2, QNAME_11);

		assertThatLR(wsdlRepository.getByBindingName(QNAME_12),
				contains());
	}

	@Test
	public void shouldReturnWSDLsWithMatchingServiceNames() {
		wsdlRepository.registerService(QNAME_21, WSDL_1, QNAME_11);
		wsdlRepository.registerService(QNAME_21, WSDL_2, QNAME_11);
		wsdlRepository.registerService(QNAME_22, WSDL_3, QNAME_11);

		assertThatLR(wsdlRepository.getByServiceName(QNAME_21),
				contains(WSDL_1, WSDL_2));
	}

	@Test
	public void givenServiceNameNotMatchingShouldReturnEmptyResourceList() {
		wsdlRepository.registerService(QNAME_21, WSDL_1, QNAME_11);
		wsdlRepository.registerService(QNAME_21, WSDL_2, QNAME_11);

		assertThatLR(wsdlRepository.getByServiceName(QNAME_12),
				contains());
	}

	@Test
	public void shouldReturnWSDLsReferencingSpecifiedPortType() {
		wsdlRepository.registerPortType(QNAME_11, WSDL_1);
		wsdlRepository.registerBinding(QNAME_21, WSDL_2, QNAME_11);
		wsdlRepository.registerService(QNAME_22, WSDL_2, QNAME_21);

		wsdlRepository.registerBinding(QNAME_31, WSDL_3, QNAME_11);
		wsdlRepository.registerService(QNAME_32, WSDL_3, QNAME_31);
		wsdlRepository.registerService(QNAME_33, WSDL_3, QNAME_31);

		assertThatLR(wsdlRepository.getReferencingPortType(QNAME_11),
				contains(WSDL_2, WSDL_3));
	}


	@Test
	public void givenoMatchingReferencingPortTypeGetReferencingPortTypeShouldReturnEmptyListResource() {
		wsdlRepository.registerPortType(QNAME_11, WSDL_1);
		assertThatLR(wsdlRepository.getReferencingPortType(QNAME_12),
				contains());
	}

	@Test
	public void givenWSDLWithPortTypeUnregisteredPortTypeShouldBeRemovedFromRegistration()  throws Exception{
		WSDLResource wsdl1 = wsdlResource(ID_1, createWSDlWithPortType(NAME_SPACE_1, LOCAL_NAME_1));
		WSDLResource wsdl2 = wsdlResource(ID_2, createWSDlWithPortType(NAME_SPACE_1, LOCAL_NAME_1));
		wsdlRepository.add(wsdl1);
		wsdlRepository.add(wsdl2);

		wsdlRepository.delete(ID_1);

		assertThatLR(wsdlRepository.getByPortTypeName(QNAME_11),
				contains(WSDL_2));
	}

	@Test
	public void shouldReturnWSDLsWithMatchingId() {
		wsdlRepository.registerById(ID_1, WSDL_1);
		wsdlRepository.registerById(ID_2, WSDL_2);

		assertThat(wsdlRepository.getWithId(ID_1), equalTo(WSDL_1));
	}

	@Test
	public void GivenGetAllCalledShouldReturnAllWSDLsRegisteredById() {
		wsdlRepository.registerById(ID_1, WSDL_1);
		wsdlRepository.registerById(ID_2, WSDL_2);
		wsdlRepository.registerById(ID_3, WSDL_3);
		wsdlRepository.registerById(ID_4, WSDL_4);

		assertThatLR(wsdlRepository.getAll(), contains(WSDL_1, WSDL_2, WSDL_3,
				WSDL_4));
	}

	@Test
	public void givenUnregisterCalledGetWithIdShouldReturnNull() throws Exception {
		wsdlRepository.registerById(ID_1, WSDL_1);
		wsdlRepository.delete(ID_1);

		assertThat(wsdlRepository.getWithId(ID_1), nullValue());
	}

	@Test
	public void givenDeleteWithNonExistingIdCalledShouldReturnFalse()  throws Exception{
		assertFalse(wsdlRepository.delete(ID_1));
	}

	public static <T extends Resource> void assertThatLR(
			ListResource<T> actual, T... expected) {
		Assert.assertThat(actual.getResources(), isIteratorReturning(expected));
	}

	public static WSDLResource[] contains(WSDLResource... resources) {
		return resources;
	}

}
