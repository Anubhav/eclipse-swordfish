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
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swordfish.api.configuration.ConfigurationConsumer;
import org.eclipse.swordfish.core.util.RegistryImpl;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.util.Assert;

public class ConfigurationConsumerRegistry extends RegistryImpl<ConfigurationConsumer> implements BundleContextAware{
    protected ConcurrentHashMap<ConfigurationConsumer, ServiceRegistration> registrations = new ConcurrentHashMap<ConfigurationConsumer, ServiceRegistration>();
    private BundleContext bundleContext;

    @Override
    protected void doRegister(ConfigurationConsumer configurationConsumer, Map<String, ?> properties) throws Exception {
        Assert.notNull(configurationConsumer);
        Dictionary props = new Hashtable();
        LOG.info("Registering configurationConsumer with id = " + configurationConsumer.getId());
        Assert.notNull(configurationConsumer.getId());
        props.put(Constants.SERVICE_PID, configurationConsumer.getId());
        registrations.put(configurationConsumer, bundleContext.registerService(ManagedService.class.getName(), new ManagedServiceAdapter(configurationConsumer), props));
        super.doRegister(configurationConsumer, properties);
    }
    @Override
    protected void doUnregister(ConfigurationConsumer key, Map<String, ?> properties) throws Exception {
        ServiceRegistration serviceRegistration = registrations.get(key);
        Assert.notNull(serviceRegistration, "serviceRegistration for the configurationConsumer with id = ["+ key.getId() + "] can not be found");
        serviceRegistration.unregister();
        super.doUnregister(key, properties);
    }
    @Override
    protected void doDestroy() throws Exception {
        for (ServiceRegistration registration : registrations.values()) {
            registration.unregister();
        }
        super.doDestroy();
    }



    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

}
