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
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.easymock.IArgumentMatcher;

public final class TstUtil {

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
		};
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
				List<T> result = new ArrayList<T>();
				for (T item : items) {
					result.add(item);
				}
				return result.iterator();
			}
		};		
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
