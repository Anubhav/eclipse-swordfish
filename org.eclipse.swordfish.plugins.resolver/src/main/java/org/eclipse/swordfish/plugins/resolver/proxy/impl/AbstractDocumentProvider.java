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
package org.eclipse.swordfish.plugins.resolver.proxy.impl;

import java.util.Map;

import org.eclipse.swordfish.api.configuration.ConfigurationConsumer;
import org.eclipse.swordfish.api.registry.EndpointDocumentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public abstract class AbstractDocumentProvider implements EndpointDocumentProvider, ConfigurationConsumer<String> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDocumentProvider.class);

	private static final String PRIORITY_PROPERTY = "priority";

    private int priority;

	public void onReceiveConfiguration(Map<String, String> configuration) {
		if (configuration == null) {
			return;
		}

		if (configuration.containsKey(PRIORITY_PROPERTY)) {
			try {
				setPriority(Integer.parseInt(configuration.get(PRIORITY_PROPERTY)));
				if (logger.isDebugEnabled()) {
					logger.debug("Document provider priority has been set to: " + getPriority());
				}
			} catch (NumberFormatException e) {
				logger.error("Couldn't initialize Swordfish "
					+ "registry proxy: malformed priority value specified", e);
				throw new IllegalArgumentException("Couldn't initialize Swordfish "
					+ "registry proxy: malformed priority value specified", e);
			}
		}
	}

	public String getId() {
		return getClass().getName();
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
