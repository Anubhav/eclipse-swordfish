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

import static org.eclipse.swordfish.registry.IsIteratorReturning.isIteratorReturning;
import static org.eclipse.swordfish.registry.TestData.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Iterator;

import org.hamcrest.Matcher;
import org.junit.Test;

public class BindingTest {

	private static final Matcher<Iterator<Service>> EMPTY_SERVICE = isIteratorReturning();

	private static final Matcher<Iterator<Binding>> EMPTY_BINDING = isIteratorReturning();

	private Service service1 = new Service(QNAME_31);

	private Service service2 = new Service(QNAME_32);
	
	private PortType portType = new PortType(QNAME_11);
	
	private Binding binding = new Binding(QNAME_22);

	@Test
	public void portTypePassedWhenAddSourceShouldBeReturnedInGetPortType() {
		binding.addSource(WSDL_1, portType);
		
		assertThat(binding.getPortType(), equalTo(portType));
		assertThat(portType.getBindings(), isIteratorReturning(binding));
	}

	@Test
	public void GivenNoSourcesDefinedShouldNoPortTypeBeDefined() {
		binding.addSource(WSDL_1, portType);
		binding.removeSource(WSDL_1);
		
		assertThat(binding.getPortType(), nullValue());
		assertThat(portType.getBindings(), EMPTY_BINDING);
	}

	@Test
	public void givenNoServicesAddedGetServicesShouldReturnEmpty() {
		assertThat(binding.getServices(), EMPTY_SERVICE);
	}

	@Test
	public void givenServicesAddedGetServicesShouldReturnThem() {
		binding.addService(service1);
		binding.addService(service2);

		assertThat(binding.getServices(), isIteratorReturning(service1, service2));
	}	
}
