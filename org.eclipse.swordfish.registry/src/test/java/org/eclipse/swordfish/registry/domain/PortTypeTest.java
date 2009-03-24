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
import static org.junit.Assert.*;

import java.util.Iterator;

import org.hamcrest.Matcher;
import org.junit.Test;

public class PortTypeTest {

	private Binding binding1 = new Binding(QNAME_11);

	private Binding binding2 = new Binding(QNAME_12);
	
	private Service service1 = new Service(QNAME_21);
	
	private Service service2 = new Service(QNAME_22);

	private PortType portType = new PortType(QNAME_11);

	@Test
	public void givenNoBindingsAddedGetBindingsShouldReturnEmpty() {
		Matcher<Iterator<Binding>> empty = isIteratorReturning();
		assertThat(portType.getBindings(), empty);
	}

	@Test
	public void givenBindingsAddedGetBindingsShouldReturnThem() {
		portType.addBinding(binding1);
		portType.addBinding(binding2);

		assertThat(portType.getBindings(), isIteratorReturning(binding1, binding2));
	}	

	@Test
	public void givenServicesAreAddToBindingWhisAreAddedToPortTypesGetServicesShouldReturnThem() {
		portType.addBinding(binding1);
		portType.addBinding(binding2);
	
		binding1.addService(service1);
		binding2.addService(service2);
		

		assertThat(portType.getServices(), isIteratorReturning(service1, service2));
	}	
}
