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


import static org.easymock.EasyMock.*;
import static org.eclipse.swordfish.registry.TstData.*;
import static org.eclipse.swordfish.registry.TstUtil.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLWriter;
import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;


public class WSDLResourceRegisteringDeregisteringTest {

	private final WSDLResource wsdl = new WSDLResource(null);

	private final InMemoryRepository repositoryMock = createMock(InMemoryRepository.class);
	
	WSDLFactory factory;

	@Before
	public void setUp() throws Exception{
		factory = WSDLFactory.newInstance();
	}

	@Test
	public void shouldRegisterWithAllPortTypNamesAtRepository() throws Exception {
		Definition definition = createWSDlWithPortType(NAME_SPACE_1, LOCAL_NAME_1, LOCAL_NAME_2);

		wsdl.setData(createPersistentData(definition, ID_1));

		repositoryMock.registerPortType(PORT_TYPE_NAME_11, wsdl);
		repositoryMock.registerPortType(PORT_TYPE_NAME_12, wsdl);
		
		replay(repositoryMock);
	    
		wsdl.register(repositoryMock);

		verify(repositoryMock);
	}

	@Test
	public void shouldNotRegisterWithPortTypeNameAtRepositoryWhenNoneDefined() throws Exception {
		Definition definition = createWSDlWithPortType(NAME_SPACE_1);

		wsdl.setData(createPersistentData(definition, ID_1));

		replay(repositoryMock);
			
		wsdl.register(repositoryMock);
		
		verify(repositoryMock);
	}

	@Test
	public void shouldRegisterWithServiceRefPortTypeAtRepository() throws Exception {
		Definition definition = createWSDlWithServiceRefPortType(NAME_SPACE_1, QNAME_21);
		
		wsdl.setData(createPersistentData(definition, ID_1));
		repositoryMock.registerBinding((QName)anyObject(), eq(wsdl), eq(QNAME_21));
		repositoryMock.registerService((QName)anyObject(), eq(wsdl), (QName)anyObject());

		replay(repositoryMock);

		wsdl.register(repositoryMock);

		verify(repositoryMock);
	}

	@Test
	public void shouldNotRegisterWithRefPortTypeWhenNoServiceDefined() throws Exception {
		Definition definition = createWSDlWithBindingRefPortType(NAME_SPACE_1, QNAME_21);
		
		wsdl.setData(createPersistentData(definition, ID_1));

		repositoryMock.registerBinding((QName)anyObject(), eq(wsdl), eq(QNAME_21));

		replay(repositoryMock);

		wsdl.register(repositoryMock);

		verify(repositoryMock);

	}
	
	@Test
	public void shouldDeregisterWithAllPortTypNamesAtRepository() throws Exception {
		Definition definition = createWSDlWithPortType(NAME_SPACE_1, LOCAL_NAME_1, LOCAL_NAME_2);

		wsdl.setData(createPersistentData(definition, ID_1));

		repositoryMock.deregisterPortType(QNAME_11, wsdl);
		repositoryMock.deregisterPortType(QNAME_12, wsdl);
		
		replay(repositoryMock);
	    
		wsdl.deregister(repositoryMock);

		verify(repositoryMock);
	}

	@Test
	public void shouldDeRegisterWithServiceRefPortTypeAtRepository() throws Exception {
		Definition definition = createWSDlWithServiceRefPortType(NAME_SPACE_1, QNAME_21);
		
		wsdl.setData(createPersistentData(definition, ID_1));
		repositoryMock.deregisterBinding((QName)anyObject(), eq(wsdl));
		repositoryMock.deregisterService((QName)anyObject(), eq(wsdl));

		replay(repositoryMock);

		wsdl.deregister(repositoryMock);

		verify(repositoryMock);
	}

	public  Definition createWSDlWithBindingRefPortType(String targetNamespace, QName portTypeName) throws WSDLException, IOException {
		Definition definition = createDefinition(targetNamespace);
		
		definition.addNamespace("pttns", portTypeName.getNamespaceURI());
		
		PortType pt = definition.createPortType();
		pt.setQName(portTypeName);

		Binding bi = definition.createBinding();
		bi.setQName(new QName(targetNamespace, "binding"));
		bi.setPortType(pt);
		bi.setUndefined(false);
		definition.addBinding(bi);

		definition2Stream(definition);

		return definition;
	}

	public  Definition createWSDlWithServiceRefPortType(String targetNamespace, QName portTypeName) throws WSDLException, IOException {
		Definition definition = createDefinition(targetNamespace);

		definition.addNamespace("pttns", portTypeName.getNamespaceURI());
		definition.addNamespace("tns", targetNamespace);
		
		PortType pt = definition.createPortType();
		pt.setQName(portTypeName);

		Binding bi = definition.createBinding();
		bi.setQName(new QName(targetNamespace, "binding"));
		bi.setPortType(pt);
		bi.setUndefined(false);
		definition.addBinding(bi);
		
		Port port = definition.createPort();
		port.setBinding(bi);
		
		Service srv = definition.createService();
		srv.setQName(new QName(targetNamespace, "service"));
		srv.addPort(port);
		definition.addService(srv);
		
		definition2Stream(definition);
		
		return definition;
	}
	
	private Definition createDefinition(String targetNamespace) throws WSDLException {
		Definition result = factory.newDefinition();
		result.setTargetNamespace(targetNamespace);

		return result;
	}
	
	private  InputStream definition2Stream(Definition definition) throws IOException {
		WSDLWriter writer = factory.newWSDLWriter();
		writer = factory.newWSDLWriter();
		
		StringWriter writerStr = new StringWriter(); 
		try {
			writer.writeWSDL(definition, writerStr);
		} catch (WSDLException e) {
			throw new IOException();
		}
		
		return new ByteArrayInputStream(writerStr.toString().getBytes("utf8"));
	}

	private PersistentData createPersistentData(final Definition definition, final String id) {
		return new PersistentDataStub() {
			@Override
			public String getId() {
				return id;
			}

			@Override
			public InputStream read() throws IOException {
				return definition2Stream(definition);
			}
		};
	}
}
