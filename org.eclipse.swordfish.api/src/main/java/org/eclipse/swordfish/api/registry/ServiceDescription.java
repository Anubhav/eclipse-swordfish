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
package org.eclipse.swordfish.api.registry;

import javax.xml.namespace.QName;

/**
 * A basic entity used to present a service description.
 */
public interface ServiceDescription<T> {

	/**
	 *
	 * @return
	 */
	Class<T> getType();

	/**
	 * @return A qualified name of the service, not <code>null</code>.
	 */
	QName getServiceName();

}
