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
package org.eclipse.swordfish.plugins.resolver.wsdl;

import org.eclipse.swordfish.plugins.resolver.wsdl11.WSDL11ServiceDescriptionReader;

/**
 *
 */
public class ServiceDescriptionReaderFactory {

	private ServiceDescriptionReaderFactory() {
	}

	public static ServiceDescriptionReader<?> createWSDL11Reader() {
		return new WSDL11ServiceDescriptionReader();
	}
}
