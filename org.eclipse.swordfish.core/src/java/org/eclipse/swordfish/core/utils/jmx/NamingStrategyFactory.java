/*******************************************************************************
 * Copyright (c) 2007 Deutsche Post AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Deutsche Post AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.swordfish.core.utils.jmx;

/**
 * The factory class for NamingStrategies.
 * 
 */
public class NamingStrategyFactory {

    /**
     * Returns the NamingStrategy appropriate for current container.
     * 
     * @return {@link WebSphereNamingStrategy} if IBM WAS classes present on classpath, otherwise
     *         returns {@link StandardNamingStrategy}
     */
    public static NamingStrategy getNamingStrategy() {
        if (WebSphereNamingStrategy.getInstance().isRunningOnWebSphere())
            return WebSphereNamingStrategy.getInstance();
        else
            return StandardNamingStrategy.getInstance();
    }

    /**
     * Hidden constructor.
     * 
     */
    private NamingStrategyFactory() {
    }

}