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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.registry.WSDLResource;

public class PortType extends WSDLElement {

	private Set<Binding> bindings = new HashSet<Binding>();

	public PortType(QName portTypeName) {
		super(portTypeName);
	}
	

	@Override
	public void addSource(WSDLResource wsdl) {
		super.addSource(wsdl);
	}


	public Iterator<Binding> getBindings() {
		return 	bindings.iterator();
	}

	public void addBinding(Binding binding) {
		bindings.add(binding);		
	}

	public Iterator<Service> getServices() {
		Set<Service> refServices = new HashSet<Service>();
		for (Binding binding : bindings) {
			Iterator<Service> servicesIter = binding.getServices();
			while(servicesIter.hasNext()) {
				refServices.add(servicesIter.next());
			}
		}
		return refServices.iterator();
	}

	public void removeBinding(Binding binding) {
		bindings.remove(binding);
	}
}
