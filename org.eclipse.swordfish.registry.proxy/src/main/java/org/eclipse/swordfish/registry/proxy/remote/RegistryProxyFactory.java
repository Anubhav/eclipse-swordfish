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
package org.eclipse.swordfish.registry.proxy.remote;

import java.net.URL;

import org.eclipse.swordfish.registry.proxy.ClientRequest;
import org.eclipse.swordfish.registry.proxy.RegistryProxy;
import org.eclipse.swordfish.registry.proxy.impl.AbstractProxy;
import org.eclipse.swordfish.registry.proxy.impl.ClientRequestImpl;
import org.eclipse.swordfish.registry.proxy.impl.HttpCilentProxy;
import org.eclipse.swordfish.registry.proxy.impl.RegistryProxyAdapter;

/**
 *
 */
public class RegistryProxyFactory {

	private static final RegistryProxyFactory INSTANCE = new RegistryProxyFactory();

	private Class<? extends AbstractProxy> proxyClass;

	private RegistryProxyFactory() {
		proxyClass = HttpCilentProxy.class;
	}

	public static RegistryProxyFactory getInstance() {
		return INSTANCE;
	}

	public RegistryProxy createProxy(URL baseUrl) {
		RegistryProxy registryProxy = new RegistryProxyAdapter(getProxyInstance(), baseUrl);
		return registryProxy;
	}

	public ClientRequest createRequest() {
		return new ClientRequestImpl();
	}

	protected AbstractProxy getProxyInstance() {
		AbstractProxy proxy = null;
		try {
			proxy = proxyClass.newInstance();
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Couldn't initialize remote registry proxy", e);
		} catch (InstantiationException e) {
			throw new IllegalStateException("Couldn't initialize remote registry proxy", e);
		}
		return proxy;
	}

	protected void setProxyClass(Class<? extends AbstractProxy> proxyClass) {
		this.proxyClass = proxyClass;
	}
}
