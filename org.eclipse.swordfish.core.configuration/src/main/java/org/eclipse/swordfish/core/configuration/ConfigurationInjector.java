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
package org.eclipse.swordfish.core.configuration;

import java.util.Map;

import org.eclipse.swordfish.api.context.SwordfishContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class ConfigurationInjector implements InitializingBean {
    private final Logger LOG = LoggerFactory.getLogger(ConfigurationInjector.class);

    private String id;
    private Map configuration;
    private SwordfishContext swordfishContext;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Map getConfiguration() {
        return configuration;
    }
    public void setConfiguration(Map configuration) {
        this.configuration = configuration;
    }
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(id);
        Assert.notNull(configuration);
        LOG.info(String.format("Injecting configuration [%s] for the configurationConsumer with id = [%s] ", configuration.toString(), id));
        swordfishContext.getConfigurationService().updateConfiguration(id, configuration);
    }


    public void setContext(SwordfishContext swordfishContext) {
        this.swordfishContext = swordfishContext;

    }
}
