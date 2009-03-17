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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.xml.sax.InputSource;

public class WSDLResource implements Resource{
	
	private PersistentData persistent;

/*
	public WSDLResource() {
		this(null);
	}
*/
	public WSDLResource(PersistentData persistent) {
		this.persistent = persistent;		
	}

	public void setData(PersistentData persistent) {
		this.persistent = persistent;
	}
	
	public String getId() {
		return persistent != null ? persistent.getId() : "";
	}
	
	public String getContentType() {
		return "application/xml";
//		return "application/wsdl+xml";
	}

	public String getCharacterEncoding() {
		return "UTF-8";
	}

	@SuppressWarnings("unchecked")
	public void register(InMemoryRepository repository) throws RegistryException {
		
		if (persistent != null) {
			Definition wsdl = null;

			try {
				wsdl = definition(persistent.read());
			} catch (WSDLException e) {
				throw new InvalidFormatException(e);
			} catch (IOException e) {
				throw new RegistryException(e);
			}
			repository.unregisterAll(getId());

			repository.registerById(getId(), this);

			Collection<PortType> portTypes = wsdl.getPortTypes().values();
			for (PortType portType : portTypes) {
				if (! portType.isUndefined()) {
					repository.registerByPortTypeName(portType.getQName(), this);
				}
			}

			List<Port> ports = new ArrayList<Port>();
			Collection<Service> services = wsdl.getServices().values();
			for (Service service : services) {
				ports.addAll(service.getPorts().values());
			}

			List<Binding> bindings = new ArrayList<Binding>();
			for (Port port : ports) {
				bindings.add(port.getBinding());
			}

			for (Binding binding : bindings) {
				repository.registerServiceRefPortType(binding.getPortType().getQName(), this);
			}
		}		
	}

	public void appendContent(Writer writer) throws IOException {
		InputStreamReader reader = new InputStreamReader(persistent
				.read(), "utf8");
		try {
			swap(reader, writer);
		} finally {
			reader.close();
		}
	}

	public void persist(Reader reader)  throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(persistent.write(),"utf8");
		try {
			swap(reader, writer);
		} finally {
			writer.close();
		}
	}
	

	public void delete() {
		persistent.delete();
	}	

	private Definition definition(InputStream stream) throws WSDLException {
		WSDLFactory factory = WSDLFactory.newInstance();
		InputSource inputSource = new InputSource(stream);
		
		WSDLReader reader = factory.newWSDLReader();
		reader.setFeature("javax.wsdl.importDocuments", false);
		return reader.readWSDL(null, inputSource);
	}
	private static void swap(Reader reader, Writer writer) throws IOException {
			char[] buffer = new char[1000];
			int length = 0;
			do {
				length = reader.read(buffer);
				if (length > 0) {
					writer.write(buffer, 0, length);
				}
			} while (length >= 0);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || ! (obj instanceof WSDLResource)) {
			return false;
		}
		
		WSDLResource other = (WSDLResource) obj;
		
		return getId().equals(other.getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return "WSDLResource[" + getId() + "]";
	}
}
