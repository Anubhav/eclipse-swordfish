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
package org.eclipse.swordfish.plugins.resolver.test;

import javax.xml.namespace.QName;

import org.eclipse.swordfish.api.registry.ServiceDescription;

/**
 *
 */
public class ServiceDescriptionStub implements ServiceDescription<Object> {

	private QName serviceName;

	public ServiceDescriptionStub(QName serviceName) {
		this.serviceName = serviceName;
	}

	public QName getServiceName() {
		return serviceName;
	}

	public Class<Object> getType() {
		return Object.class;
	}
}
