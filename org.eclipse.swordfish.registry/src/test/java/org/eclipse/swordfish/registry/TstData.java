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

import static org.eclipse.swordfish.registry.TstData.ID_1;
import static org.eclipse.swordfish.registry.TstData.ID_2;
import static org.eclipse.swordfish.registry.TstData.ID_3;
import static org.eclipse.swordfish.registry.TstData.ID_4;
import static org.eclipse.swordfish.registry.TstData.ID_5;
import static org.eclipse.swordfish.registry.TstUtil.wsdlResource;

import java.util.Collections;
import java.util.Map;

import javax.xml.namespace.QName;

public class TstData {

	public static final String NAME_SPACE_1 = "NameSpace_1";
	public static final String NAME_SPACE_2 = "NameSpace_2";
	public static final String NAME_SPACE_3 = "NameSpace_3";

	public static final String LOCAL_NAME_1 = "localName_1";
	public static final String LOCAL_NAME_2 = "localName_2";
	public static final String LOCAL_NAME_3 = "localName_3";

	public static final QName QNAME_11 = new QName(NAME_SPACE_1, LOCAL_NAME_1);
	public static final QName QNAME_12 = new QName(NAME_SPACE_1, LOCAL_NAME_2);
	public static final QName QNAME_13 = new QName(NAME_SPACE_1, LOCAL_NAME_3);
	public static final QName QNAME_21 = new QName(NAME_SPACE_2, LOCAL_NAME_1);
	public static final QName QNAME_22 = new QName(NAME_SPACE_2, LOCAL_NAME_2);
	public static final QName QNAME_23 = new QName(NAME_SPACE_2, LOCAL_NAME_3);
	public static final QName QNAME_31 = new QName(NAME_SPACE_3, LOCAL_NAME_1);
	public static final QName QNAME_32 = new QName(NAME_SPACE_3, LOCAL_NAME_2);
	public static final QName QNAME_33 = new QName(NAME_SPACE_3, LOCAL_NAME_3);

	public static final QName PORT_TYPE_NAME_11 = QNAME_11;
	public static final QName PORT_TYPE_NAME_12 = QNAME_12;
	public static final QName PORT_TYPE_NAME_21 = QNAME_21;
	
//	public static final String WSDL_ID_1 = "wsdl_1";
//	public static final String WSDL_ID_2 = "wsdl_2";

	public static final String ID_1 = "id_1";
	public static final String ID_2 = "id_2";
	public static final String ID_3 = "id_3";
	public static final String ID_4 = "id_4";
	public static final String ID_5 = "id_5";
	
	public static final String CONTENT = "{ydvüdfskdflks";
	
	public static final Map<String, String> EMPTY_PARAMS = Collections.emptyMap();

	public static final WSDLResource WSDL_1 = wsdlResource(ID_1);

	public static final WSDLResource WSDL_2 = wsdlResource(ID_2);

	public static final WSDLResource WSDL_3 = wsdlResource(ID_3);

	public static final WSDLResource WSDL_4 = wsdlResource(ID_4);

	public static final WSDLResource WSDL_5 = wsdlResource(ID_5);

}
