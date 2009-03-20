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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.registry.WSDLResource;

public class WSDLElement {
	
	private QName name;
	
	private Set<WSDLResource> sources = new HashSet<WSDLResource>();

	public WSDLElement(QName portTypeName) {
		name = portTypeName;
	}

	public QName getName() {
		return name;
	}

	/*public*/ void addSource(WSDLResource wsdl) {
		sources.add(wsdl);
	}

	public void removeSource(WSDLResource wsdl) {
		sources.remove(wsdl);
	}

	public boolean sourcesDefined() {
		return ! sources.isEmpty();
	}

	public void sourcesAddTo(Collection<WSDLResource> sources) {
		sources.addAll(this.sources);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass().equals(obj.getClass())) {
			WSDLElement other = (WSDLElement) obj;
			return name.equals(other.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + name + "]";
	}

	
}
