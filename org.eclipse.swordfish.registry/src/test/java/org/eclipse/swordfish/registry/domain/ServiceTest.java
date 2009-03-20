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
import static org.eclipse.swordfish.registry.TstData.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;


import java.util.Iterator;

import org.hamcrest.Matcher;


public class ServiceTest {

	private static final Matcher<Iterator<Service>> EMPTY_SERVICE = isIteratorReturning();

	private static final Matcher<Iterator<Binding>> EMPTY_BINDING = isIteratorReturning();

	private Binding binding1 = new Binding(QNAME_21);

	private Binding binding2 = new Binding(QNAME_22);

	private Service service = new Service(QNAME_31);
	
	@Test
	public void bindingsPassedWhenAddSourceShouldBeReturnedInGetBindings() {
		service.addSource(WSDL_1, binding1, binding2);
		
		assertThat(service.getBindings(), isIteratorReturning(binding1, binding2));

		assertThat(binding1.getServices(), isIteratorReturning(service));
		assertThat(binding2.getServices(), isIteratorReturning(service));
	}

	@Test
	public void GivenNoSourcesDefinedShouldNoBindingBeDefined() {
		service.addSource(WSDL_1, binding1, binding2);
		service.removeSource(WSDL_1);
		
		assertThat(service.getBindings(), EMPTY_BINDING);
		assertThat(binding1.getServices(), EMPTY_SERVICE);
		assertThat(binding2.getServices(), EMPTY_SERVICE);
	}

}
