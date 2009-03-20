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

public class Binding extends WSDLElement {

	private PortType portType;
	
	private Set<Service> services = new HashSet<Service>();

	public Binding(QName bindingName) {
		super(bindingName);
	}

	public Iterator<Service> getServices() {
		return 	services.iterator();
	}

	public void addService(Service service) {
		services.add(service);
	}

	public void removeService(Service service) {
		services.remove(service);		
	}

	public PortType getPortType() {
		return portType;
	}

	@Override
	public void removeSource(WSDLResource wsdl) {
		super.removeSource(wsdl);
		if (! sourcesDefined() && portType != null) {
			portType.removeBinding(this);
			portType = null;
		}
	}

	public void addSource(WSDLResource wsdl, PortType portType) {
		super.addSource(wsdl);
		this.portType = portType;
		portType.addBinding(this);
	}	
}
