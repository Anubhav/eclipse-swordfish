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
package org.eclipse.swordfish.registry.proxy.remote.mock;

import static org.junit.Assert.assertNotNull;

import org.eclipse.swordfish.registry.proxy.ClientRequest;
import org.eclipse.swordfish.registry.proxy.ClientResponse;
import org.eclipse.swordfish.registry.proxy.impl.AbstractProxy;

/**
 *
 */
public class RegistryProxyMock extends AbstractProxy {

	private static AbstractProxy proxy;

	@Override
	public ClientResponse invoke(ClientRequest request) {
		assertNotNull(proxy);
		return proxy.invoke(request);
	}

	public static void setProxyHandler(AbstractProxy proxy) {
		RegistryProxyMock.proxy = proxy;
	}

}
