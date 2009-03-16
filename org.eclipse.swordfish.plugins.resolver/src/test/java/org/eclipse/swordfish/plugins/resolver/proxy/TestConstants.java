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
package org.eclipse.swordfish.plugins.resolver.proxy;

import javax.xml.namespace.QName;

public abstract class TestConstants {

	public static final QName BOOKINGSERVICE_PORTTYPE_NAME = new QName("http://cxf.samples.swordfish.eclipse.org/", "BookingService");

	public static final QName BOOKINGSERVICE_SERVICE_NAME = new QName("http://cxf.samples.swordfish.eclipse.org/", "BookingServiceImpl");

	public static final QName FAKE_PORTTYPE_NAME = new QName("http://fake.namespace/", "FakeService");

}