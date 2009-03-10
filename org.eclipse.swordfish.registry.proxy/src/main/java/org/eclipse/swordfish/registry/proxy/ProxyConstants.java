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
package org.eclipse.swordfish.registry.proxy;

/**
 *
 */
public interface ProxyConstants {

	/**
	* Specifies names of HTTP methods used for execution of	particular request
	*/
	enum Method {
		GET, POST, PUT, DELETE
	}

	enum Resource {
		WSDL;

		public String getResourceId() {
			return toString().toLowerCase();
		}
	}

	/**
	* A status of call to remote service.
	*/
	enum Status {
		SUCCESS (200),
		NOT_FOUND (404),
		INTERNAL_SERVER_ERROR (500),
		MALFORMED_QUERY (503),
		ERROR (0),
		UNDEFINED (-1);

		int code;

		private Status(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public static Status get(int code) {
			for (Status status : values()) {
				if (status.getCode() == code) {
					return status;
				}
			}
			return UNDEFINED;
		}
	}
}
