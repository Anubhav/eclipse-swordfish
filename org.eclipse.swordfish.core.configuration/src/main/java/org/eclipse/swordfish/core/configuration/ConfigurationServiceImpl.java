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

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.configuration.ConfigurationService;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.springframework.util.Assert;

public class ConfigurationServiceImpl implements ConfigurationService {
    private ConfigurationAdmin configurationAdmin;
    public ConfigurationAdmin getConfigurationAdmin() {
        return configurationAdmin;
    }
    public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
        this.configurationAdmin = configurationAdmin;
    }
    public <T> void updateConfiguration(String id, Map<String, T> configurationData) {
        try {
            Configuration configuration = configurationAdmin.getConfiguration(id);
            Assert.notNull(configuration, "Could npot find configuration by id = " + id);
            if (configurationData == null) {
                configuration.update(null);
                return;
            }
            Dictionary properties = new Hashtable();
            for (Object key : configurationData.keySet()) {
                properties.put(key, configurationData.get(key));
            }
            configuration.update(properties);
        } catch (Exception ex) {
            throw new SwordfishException(ex);
        }


    }

}
