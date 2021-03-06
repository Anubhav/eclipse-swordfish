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

import org.eclipse.swordfish.api.registry.EndpointDescription;
import org.eclipse.swordfish.api.registry.ServiceDescription;

public class EndpointDescriptionStub implements EndpointDescription {

	private ServiceDescriptionStub description;

	public EndpointDescriptionStub(ServiceDescriptionStub description) {
		this.description = description;
	}

	public String getAddress() {
		return null;
	}

	public String getName() {
		return null;
	}

	public ServiceDescription<?> getServiceDescription() {
		return description;
	}

}
