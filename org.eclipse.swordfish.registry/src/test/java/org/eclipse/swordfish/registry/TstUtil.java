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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.PortType;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLWriter;
import javax.xml.namespace.QName;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.easymock.IArgumentMatcher;
import org.junit.Before;

public final class TstUtil {
	
	static WSDLFactory factory;

	static {
		try {
			factory = WSDLFactory.newInstance();
		} catch (WSDLException e) {
			throw new RuntimeException(e);
		}
	}


	public static WSDLResource wsdlResource(final String id) {
		return new WSDLResource(null) {
			@Override
			public String getId() {
				return id;
			}
			
			@Override
			public void delete() {}
		};
	}

	public static WSDLResource wsdlResource(final String id, final String content) {
		return new WSDLResource(null) {
			@Override
			public String getId() {
				return id;
			}

			@Override
			public void appendContent(Writer writer) throws IOException {
				writer.append(content);
			}
			@Override
			public void delete() {}
		};
	}
	
	public static WSDLResource wsdlResource(final String id, final Definition content) {
		return new WSDLResource(new PersistentDataStub() {
			@Override
			public String getId() {
				return id;
			}
			public InputStream read() throws IOException {
				return definition2Stream(content);
			}			
		});
	}

	public  static Definition createWSDlWithPortType(String targetNamespace, String... portTypeNames) throws WSDLException, IOException {
		Definition result = createDefinition(targetNamespace);
		
		for (String portTypeName : portTypeNames) {
			PortType portType = result.createPortType();
			portType.setQName(new QName(targetNamespace, portTypeName));
			portType.setUndefined(false);
			result.addPortType(portType);
		}

		definition2Stream(result);

		return result;
	}

	
	private static Definition createDefinition(String targetNamespace) throws WSDLException {
		Definition result = factory.newDefinition();
		result.setTargetNamespace(targetNamespace);

		return result;
	}

	private static InputStream definition2Stream(Definition definition) throws IOException {
		String content = definition2String(definition);
		return new ByteArrayInputStream(content.getBytes("utf8"));
	}

	private static String definition2String(Definition definition) throws IOException {
		WSDLWriter writer = factory.newWSDLWriter();
		writer = factory.newWSDLWriter();
		
		StringWriter writerStr = new StringWriter(); 
		try {
			writer.writeWSDL(definition, writerStr);
		} catch (WSDLException e) {
			throw new IOException();
		}
		
		return writerStr.toString();
	}
	
	public static <K, V> Map<K, V> asMap(Entry<K, V>...entries) {
		Map<K, V> result = new HashMap<K, V>(entries.length);
		for (Entry<K, V> entry : entries) {
			result.put(entry.key, entry.value);
		}
		return result;
	}
	
	public static <K, V> Entry<K, V> entry(K key, V value) {
		return new Entry<K, V>(key, value);
	}
	
	static class Entry<K, V> {
		public K key;
		public V value;
		
		public Entry(K k, V v) {
			key = k;
			value = v;
		}
	}
	
	public static <T> IAnswer<Iterator<T>> asIterator(final T... items) {
		return new IAnswer<Iterator<T>>() {
			public Iterator<T> answer() {
				return list(items).iterator();
			}
		};		
	}
	
	public static <T> List<T> list(T... items) {
		List<T> result = new ArrayList<T>();
		Collections.addAll(result, items);
		return result;
	}

	
	public static Reader eqReader(String expected) {
	    EasyMock.reportMatcher(new ReaderEquals(expected));
	    return null;
	}

	
	private static class ReaderEquals implements IArgumentMatcher {
	    private String expectedContent;

	    public ReaderEquals(String content) {
	        this.expectedContent = content;
	    }

	    public boolean matches(Object actual) {
	        if (actual == null || !(actual instanceof Reader)) {
	            return false;
	        }
	        
	        try {
	        char[] buffer = new char[1000];
	        StringBuffer actualStr = new StringBuffer(10000);
	        Reader actualReader = (Reader) actual;
	        int noChar = 0;
	        do {
	        	noChar = actualReader.read(buffer);
	        	if (noChar > -1) {
	        		actualStr.append(buffer, 0, noChar);
	        	}
	        } while (noChar > -1);
	        	return expectedContent.equals(actualStr.toString());
	        } catch (IOException e) {
	        	throw new RuntimeException(e); 
	        }
	    }

	    public void appendTo(StringBuffer buffer) {
	        buffer.append("Reader(");
	        buffer.append(" with content \"");
	        buffer.append(expectedContent);
	        buffer.append("\"");
	    }
	}
}
