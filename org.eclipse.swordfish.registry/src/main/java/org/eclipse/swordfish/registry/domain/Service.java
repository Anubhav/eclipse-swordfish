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

public class Service extends WSDLElement {

	private Set<Binding> bindings = new HashSet<Binding>();
	
	public Service(QName name) {
		super(name);
	}

	public void addSource(WSDLResource wsdl, Binding... bindings) {
		super.addSource(wsdl);
		
		for (Binding binding : bindings) {
			this.bindings.add(binding);
			binding.addService(this);
		}
	}
	
	@Override
	public void removeSource(WSDLResource wsdl) {
		super.removeSource(wsdl);
		if (!sourcesDefined()) {
			for (Binding binding : bindings) {
				binding.removeService(this);
			}
			bindings.clear();
		}
	}

	public Iterator<Binding> getBindings() {
		return bindings.iterator();
	}
}
