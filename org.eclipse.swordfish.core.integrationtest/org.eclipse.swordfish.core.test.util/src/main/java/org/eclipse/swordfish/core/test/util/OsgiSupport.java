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
package org.eclipse.swordfish.core.test.util;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class OsgiSupport {
    public static <T extends Object> T getReference(BundleContext bundleContext, Class<T> cls) {
        ServiceReference[] references;
        try {
            references = bundleContext.getAllServiceReferences(cls.getCanonicalName(), null);
        } catch (InvalidSyntaxException ex) {
          throw new RuntimeException(ex);
        }
        if (references.length == 0) {
            return null;
        }
        return (T)bundleContext.getService(references[0]);
    }
}
