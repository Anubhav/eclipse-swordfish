/*******************************************************************************
 * Copyright (c) 2008, 2009 SOPERA GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     SOPERA GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.swordfish.core.util;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

/**
 * Implementation taken from Spring DM
 * 
 * Dictionary implementation backed by a map instance. While the JDK provides a
 * Dictionary implementation through Hashtable, the class itself is always
 * synchronized and does not maintain the internal order.
 * 
 * <p/> This simple wrapper, accepts any type of Map as backing storage allowing
 * more options in choosing the appropriate implementation. By default, a
 * {@link java.util.LinkedHashMap} is used, if no Map is specified.
 * 
 * <p/> This implementation will enforce the Dictionary behaviour over the map
 * when it comes to handling null values. As opposed to a Map, the Dictionary
 * always throws {@link NullPointerException} if a given argument is null.
 * 
 * @see java.util.Map
 * @see java.util.Dictionary
 * @author Costin Leau
 */
public class MapBasedDictionary extends Dictionary implements Map {

	private Map map;

	/**
	 * Enumeration wrapper around an Iterator.
	 * 
	 * @author Costin Leau
	 * 
	 */
	private static class IteratorBasedEnumeration implements Enumeration {

		private Iterator it;

		public IteratorBasedEnumeration(Iterator it) {
			Assert.notNull(it);
			this.it = it;
		}

		public IteratorBasedEnumeration(Collection col) {
			this(col.iterator());
		}

		public boolean hasMoreElements() {
			return it.hasNext();
		}

		public Object nextElement() {
			return it.next();
		}

	}

	public MapBasedDictionary(Map map) {
		this.map = (map == null ? new LinkedHashMap() : map);
	}

	/**
	 * Default constructor.
	 * 
	 */
	public MapBasedDictionary() {
		this.map = new LinkedHashMap();
	}

	public MapBasedDictionary(int initialCapacity) {
		this.map = new LinkedHashMap(initialCapacity);
	}

	/**
	 * Constructor for dealing with existing Dictionary. Will copy the content
	 * into the inner Map.
	 * 
	 * @param dictionary
	 */
	public MapBasedDictionary(Dictionary dictionary) {
		this(new LinkedHashMap(), dictionary);
	}

	public MapBasedDictionary(Map map, Dictionary dictionary) {
		this(map);
		if (dictionary != null)
			putAll(dictionary);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set entrySet() {
		return map.entrySet();
	}

	public Object get(Object key) {
		if (key == null)
			throw new NullPointerException();
		return map.get(key);
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set keySet() {
		return map.keySet();
	}
	
	public Object put(Object key, Object value) {
		if (key == null || value == null)
			throw new NullPointerException();

		return map.put(key, value);
	}

	public void putAll(Map t) {
		map.putAll(t);
	}

	public void putAll(Dictionary dictionary) {
		if (dictionary != null)
			// copy the dictionary
			for (Enumeration enm = dictionary.keys(); enm.hasMoreElements();) {
				Object key = enm.nextElement();
				map.put(key, dictionary.get(key));
			}
	}

	public Object remove(Object key) {
		if (key == null)
			throw new NullPointerException();

		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Collection values() {
		return map.values();
	}

	public Enumeration elements() {
		return new IteratorBasedEnumeration(map.values());
	}

	public Enumeration keys() {
		return new IteratorBasedEnumeration(map.keySet());
	}

	public String toString() {
		return map.toString();
	}

	public boolean equals(Object obj) {
		// this should work nicely since the Dictionary implementations inside
		// the JDK are Maps also
		return map.equals(obj);
	}

	public int hashCode() {
		return map.hashCode();
	}

}
