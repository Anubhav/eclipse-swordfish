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
package org.eclipse.swordfish.plugins.resolver.proxy.local;

import java.net.URL;
import java.util.List;
import java.util.Vector;

import org.springframework.util.Assert;

public class BatchResourceToStringConverter {

    private List<String> resources;
    private List<String> resourceUrls;

    public BatchResourceToStringConverter() {
    }

    public void init() {
    	Assert.notNull(resources, "The resources property can not be null");
    	Assert.notEmpty(resources, "The resources liist can not be empty");

    	resourceUrls = new Vector<String>(resources.size());
    	for (String nextResource : resources) {
            try {
            	URL resourceUrl = getClass().getClassLoader().getResource(nextResource);
            	resourceUrls.add(resourceUrl.toString());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
    	}
    }

    public List<String> getResourceUrls() {
        return resourceUrls;
    }

    public void setResourceUrls(List<String> resourceUrls) {
        this.resourceUrls = resourceUrls;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

}