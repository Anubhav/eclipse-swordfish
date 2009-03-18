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
package org.eclipse.swordfish.policies.helpers;

import java.net.URL;

import org.eclipse.swordfish.api.SwordfishException;
import org.springframework.core.io.Resource;

public class ResourceToStringConverter {

    private Resource resource;
    private String url;

    public ResourceToStringConverter(final String resource) {
    	final URL resUrl = getClass().getClassLoader().getResource(resource);
    	if (resUrl == null) {
    		throw new SwordfishException(
    				"Resource " + resource + " not found. ");
    	}
        url = resUrl.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(final Resource resource) {
        this.resource = resource;
    }
}
