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
package org.eclipse.swordfish.core.management.messaging;

import org.eclipse.swordfish.core.management.objectname.ObjectNameFactory;

/**
 * A factory for creating MBeanBackend objects.
 */
public interface MBeanBackendFactory extends MessagingMonitorBackendFactory {

    /**
     * Sets the object name factory.
     * 
     * @param onf
     *        the new object name factory
     */
    void setObjectNameFactory(ObjectNameFactory onf);

}