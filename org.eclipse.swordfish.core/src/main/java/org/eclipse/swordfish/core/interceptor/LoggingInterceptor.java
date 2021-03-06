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
package org.eclipse.swordfish.core.interceptor;



import java.util.HashMap;
import java.util.Map;

import javax.jbi.messaging.MessageExchange;
import javax.xml.transform.Source;

import org.apache.servicemix.nmr.api.Exchange;
import org.apache.servicemix.nmr.api.Type;
import org.eclipse.swordfish.api.Interceptor;
import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.core.util.ServiceMixSupport;
import org.eclipse.swordfish.core.util.xml.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LoggingInterceptor implements Interceptor {
private static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptor.class);
    private Map<String, ?> properties = new HashMap<String, Object>();
    public void process(MessageExchange messageExchange) throws SwordfishException {
        try {
            Exchange exchange = ServiceMixSupport.toNMRExchange(messageExchange);
                String request = exchange.getMessage(Type.In) != null ? XmlUtil.toString((Source)exchange.getMessage(Type.In).getBody()) :
                    null;
                String response = exchange.getMessage(Type.Out) != null ? XmlUtil.toString((Source)exchange.getMessage(Type.Out).getBody()) :
                    null;
                LOG.info(String.format("Received messageExchange with request = [%s] and response = [%s]", request, response));
        } catch (Exception ex) {
		throw new RuntimeException(ex);
        }

    }

    public Map<String, ?> getProperties() {
        return properties;
    }
}
