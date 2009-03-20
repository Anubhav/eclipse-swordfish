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

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.registry.domain.PortType;

public interface WSDLRepository {
	
	ListResource<WSDLResource> getByPortTypeName(QName portTypeName);

	ListResource<WSDLResource> getReferencingPortType(QName portTypeName);

	Iterator<QName> getAllPortTypeNames();

	Iterator<PortType> getAllPortTypes();

	WSDLResource getWithId(String id);

	void add(WSDLResource wsdl) throws RegistryException;

	ListResource<WSDLResource> getAll();

	boolean delete(String id) /*throws RegistryException */;
}
