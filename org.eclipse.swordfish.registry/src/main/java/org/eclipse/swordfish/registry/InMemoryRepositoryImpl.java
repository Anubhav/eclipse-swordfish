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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;

import org.eclipse.swordfish.registry.domain.Binding;
import org.eclipse.swordfish.registry.domain.PortType;
import org.eclipse.swordfish.registry.domain.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InMemoryRepositoryImpl implements InMemoryRepository {
	private static final Logger LOGGER = LoggerFactory
	.getLogger(InMemoryRepositoryImpl.class);
	
	private Map<QName, PortType> portTypes = new HashMap<QName, PortType>();
	
	private Map<QName, Binding> bindings = new HashMap<QName, Binding>();

	private Map<QName, Service> services = new HashMap<QName, Service>();

	private Map<String, WSDLResource> byId = new HashMap<String, WSDLResource>();

	/* 
	 * @see org.eclipse.swordfish.registry.WSDLRepository#getByPortTypeName(javax.xml.namespace.QName)
	 */
	public ListResource<WSDLResource> getByPortTypeName(QName portTypeName) {
		Set<WSDLResource> sources = new HashSet<WSDLResource>();
		PortType portType = portTypes.get(portTypeName);
		if (portType != null) {
			portType.sourcesAddTo(sources);
		}
		return new ListResource<WSDLResource>(sources);
	}
	
	public ListResource<WSDLResource> getByBindingName(QName bindingName) {
		Set<WSDLResource> sources = new HashSet<WSDLResource>();
		Binding binding = bindings.get(bindingName);
		if (binding != null) {
			binding.sourcesAddTo(sources);
		}
		return new ListResource<WSDLResource>(sources);
	}

	public ListResource<WSDLResource> getByServiceName(QName serviceName) {
		Set<WSDLResource> sources = new HashSet<WSDLResource>();
		Service service = services.get(serviceName);
		if (service != null) {
			service.sourcesAddTo(sources);
		}
		return new ListResource<WSDLResource>(sources);
	}
	


	public ListResource<WSDLResource> getReferencingPortType(QName portTypeName) {
		Set<WSDLResource> sources = new HashSet<WSDLResource>();
		PortType portType = portTypes.get(portTypeName);
		
		if (portType != null) {
			Iterator<Service> servicesIter = portType.getServices();
			while(servicesIter.hasNext()) {
				Service service = servicesIter.next();
				service.sourcesAddTo(sources);
			}
		}
		return new ListResource<WSDLResource>(sources);
	}

	public WSDLResource getWithId(String id) {
		return byId.get(id);
	}

	public Iterator<QName> getAllPortTypeNames() {
		Collection<PortType> definedPortTypes = getDefinedPortTypes();
		Set<QName> portTypeNames = new HashSet<QName>();
		for (PortType portType : definedPortTypes) {
			portTypeNames.add(portType.getName());
			
		}
		return portTypeNames.iterator();
	}

	public Iterator<PortType> getAllPortTypes() {
		return getDefinedPortTypes().iterator();
	}
	
	private Collection<PortType> getDefinedPortTypes() {
		Set<PortType> definedPortTypes =  new HashSet<PortType>();
		for (PortType portType : portTypes.values()) {
			if (portType.sourcesDefined()) {
				definedPortTypes.add(portType);
			}
		}
		return definedPortTypes;
	}

	public ListResource<WSDLResource> getAll() {
		return new ListResource<WSDLResource>(byId.values());
	}

	public void add(WSDLResource wsdl) throws RegistryException {
		byId.put(wsdl.getId(), wsdl);
		wsdl.register(this);
	}
	
	public boolean delete(String id) {
		WSDLResource toBeDeleted = byId.get(id);
		if (toBeDeleted != null) {
			byId.remove(id);
			try {
				toBeDeleted.deregister(this);
			} catch (RegistryException e) {
				LOGGER.error("WSDL " + id +" could not be unregistered from InMemorysRepositoryImpl", e);
			}
			toBeDeleted.delete();
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swordfish.registry.WSDLRepository#registerByPortTypeName(javax.xml.namespace.QName, org.eclipse.swordfish.registry.WSDLResource)
	 */
	public void registerPortType(QName name, WSDLResource wsdl) {
		PortType portType = getPortType(name);
		portType.addSource(wsdl);
	}

	public void registerBinding(QName bindingName, WSDLResource wsdl,
			QName portTypeName) {
		PortType portType = getPortType(portTypeName);
		Binding binding = getBinding(bindingName);
		binding.addSource(wsdl, portType);
	}

	public void registerService(QName serviceName, WSDLResource wsdl,
			QName... bindingNames) {
		Service service = getService(serviceName);

		Binding[] bindings = new Binding[bindingNames.length];
		for (int i = 0; i < bindingNames.length; i++) {
			bindings[i] = getBinding(bindingNames[i]);
		}
		service.addSource(wsdl, bindings);
	}

	public void deregisterPortType(QName portTypeName, WSDLResource wsdl) {
		PortType portType = portTypes.get(portTypeName);
		if (portType != null) {
			portType.removeSource(wsdl);
		}
	}
	
	public void deregisterBinding(QName bindingName, WSDLResource wsdl) {
		Binding binding = bindings.get(bindingName);
		if (binding != null) {
			binding.removeSource(wsdl);
		}
	}
	
	public void deregisterService(QName serviceName, WSDLResource wsdl) {
		Service service = services.get(serviceName);
		if (service != null) {
			service.removeSource(wsdl);
		}
	}

	@Deprecated
	public void registerById(String id, WSDLResource wsdl) {
		byId.put(id, wsdl);	
	}

	PortType getPortType(QName name) {
		PortType portType = portTypes.get(name);
		if (portType == null) {
			portType = new PortType(name);
			portTypes.put(name, portType);
		}
		return portType;
	}

	Binding getBinding(QName name) {
		Binding binding = bindings.get(name);
		if (binding == null) {
			binding = new Binding(name);
			bindings.put(name, binding);
		}
		return binding;
	}

	Service getService(QName name) {
		Service service = services.get(name);
		if (service == null) {
			service = new Service(name);
			services.put(name, service);
		}
		return service;
	}
}
