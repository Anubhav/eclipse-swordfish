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

package org.eclipse.swordfish.api.context;

import javax.jbi.component.ComponentContext;

import org.eclipse.swordfish.api.configuration.ConfigurationService;
import org.eclipse.swordfish.api.event.EventService;

/**
 * Provides the access to the underlying Swordfish and SMX facilities.
 * Is published as the osgi service with the interface name org.eclipse.swordfish.api.context.SwordfishContext.
 * Can be also injected via {@link SwordfishContextAware} interface
 *
 */
public interface SwordfishContext {

    ConfigurationService getConfigurationService();

    /**
     * Access the JBI component context.
     * @return the platform JBI context.
     */
    ComponentContext getComponentContext();

    /**
     * Access to Swordfish EventService
	 */
    EventService getEventService();
}
