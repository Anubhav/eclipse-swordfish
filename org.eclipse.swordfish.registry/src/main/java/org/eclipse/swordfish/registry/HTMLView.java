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

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import org.eclipse.swordfish.registry.domain.PortType;
import org.eclipse.swordfish.registry.domain.Service;

public class HTMLView {

	public static final String PREFIX = 
		"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"" +
	    "\"http://www.w3.org/TR/html4/loose.dtd\">\n" +
		"<html>\n" +
		"<head>\n" +
		"<title>Registry</title>\n" +
		"</head>\n" +
		"<body>\n" +
		"<table>\n";

	public static final String POSTFIX = 
		"</table>\n" +
		"</body>\n" + 
		"</html>\n";
	
	public void createView(WSDLRepository repository, Writer writer) throws IOException {
		writer.append(PREFIX);
		
		Iterator<PortType> portTypeIter = repository.getAllPortTypes();
		while(portTypeIter.hasNext()) {
			PortType portType = portTypeIter.next();
			writer.append("<tr> <td>");
			writer.append(portType.getName().toString());
			writer.append("</td><td></td><td></td></tr>");
			Iterator<Service> serviceIter = portType.getServices();
			while (serviceIter.hasNext()) {
				Service service = serviceIter.next();
				writer.append("<tr> <td></td><td></td><td>");
				writer.append(service.getName().toString());
				writer.append("</td></tr>");
			}
		}
		writer.append(POSTFIX);
	}
}
