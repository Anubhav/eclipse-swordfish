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
package org.eclipse.swordfish.registry.proxy.wsdl;

import java.io.Reader;
import java.net.URL;

import org.eclipse.swordfish.api.registry.ServiceDescription;

/**
 *
 */
public interface ServiceDescriptionReader<T> {

	ServiceDescription<T> readDescription(Reader description);

	ServiceDescription<T> readDescription(URL descriptionURL);

}
