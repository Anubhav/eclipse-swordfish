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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.servicedesc.ServiceEndpoint;
import javax.xml.namespace.QName;

import org.apache.servicemix.jbi.runtime.ComponentRegistry;
import org.apache.servicemix.jbi.runtime.ComponentWrapper;
import org.apache.servicemix.jbi.runtime.impl.MessageExchangeImpl;
import org.apache.servicemix.nmr.api.Endpoint;
import org.apache.servicemix.nmr.api.Exchange;
import org.apache.servicemix.nmr.api.NMR;
import org.apache.servicemix.nmr.api.Role;
import org.apache.servicemix.nmr.api.internal.InternalEndpoint;
import org.apache.servicemix.nmr.core.StaticReferenceImpl;
import org.eclipse.swordfish.api.Interceptor;
import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.api.policy.PolicyConstants;
import org.eclipse.swordfish.api.registry.EndpointDescription;
import org.eclipse.swordfish.core.resolver.ServiceResolver;
import org.eclipse.swordfish.core.resolver.ServiceResolverHolder;
import org.eclipse.swordfish.core.util.ServiceMixSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.w3c.dom.DocumentFragment;

public class EndpointResolverInterceptor<T> implements Interceptor, ServiceResolverHolder, InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(EndpointResolverInterceptor.class);

	private NMR nmr;
    private ServiceResolver serviceResolver;
    private ComponentRegistry componentRegistry;
    private Map<String, ?> properties = new HashMap<String, Object>();

	public void process(MessageExchange messageExchange) throws SwordfishException {
		Exchange exchange = ServiceMixSupport.toNMRExchange(messageExchange);
		try {
			if (exchange.getRole() != Role.Consumer) {
				return;
			}

			if (exchange.getTarget() != null && ServiceMixSupport.getEndpoint(getNmr(), exchange.getTarget()) != null) {
			    return;
			}

			QName interfaceName = (QName) exchange.getProperty(Endpoint.INTERFACE_NAME);
			if (interfaceName == null) {
				interfaceName = (QName) exchange.getProperty(MessageExchangeImpl.INTERFACE_NAME_PROP);
				if (interfaceName == null) {
					return;
				}
			}

			QName operation = exchange.getOperation();
			if (operation == null) {
				return;
			}

			EndpointDescription description =
				getEndpointDescription(interfaceName, extractConsumerName(exchange));
			if (description == null) {
				throw new SwordfishException("Error resolving endpoint - no service "
					+ "description has been fount for the interface: " + interfaceName);
			}

			InternalEndpoint serviceEndpoint = getEndpointForExchange(messageExchange, description);
			if (serviceEndpoint == null) {
				throw new SwordfishException("Error resolving endpoint for the interface + ["
					+ interfaceName + "] - no suitable endpoints have been found");
			}
			exchange.setTarget(new StaticReferenceImpl(Arrays.asList(serviceEndpoint)));
		} catch (Exception ex) {
			logger.error("The exception happened while trying to resolve service name via supplied wsdls ", ex);
		}
	}

	private EndpointDescription getEndpointDescription(QName interfaceName, QName consumerName) {
		EndpointDescription description = null;
		Collection<EndpointDescription> descriptions = getServiceResolver().getEndpointsFor(interfaceName, consumerName);
		if (descriptions.size() > 0) {
			// add policy matching logic to choose a suitable endpoint
			// for now use a first one from the list
			description = descriptions.iterator().next();
		}
		return description;
	}

	private InternalEndpoint getEndpointForExchange(MessageExchange messageExchange,
			EndpointDescription description) {
		QName serviceName = description.getServiceDescription().getServiceName();
		Map<String,Object> props = new HashMap<String, Object>();
		props.put(Endpoint.SERVICE_NAME, serviceName.toString());
		InternalEndpoint serviceEndpoint = ServiceMixSupport.getEndpoint(getNmr(), props);

		if (serviceEndpoint != null) {
			logger.info("The service endpoint for the servicename + [" + serviceName + "] has been found");
		} else {
			logger.info("No service endpoints for the service + [" + serviceName + "] have been found");
			logger.info("Trying to establish a dynamic outbound endpoint for the service: " + serviceName);

			ServiceEndpoint se = null;
			ComponentWrapper wrapper = null;
			DocumentFragment endpRef = ServiceMixSupport.getEndpointReference(description);

	        for (ComponentWrapper component : getComponentRegistry().getServices()) {
	            se = component.getComponent().resolveEndpointReference(endpRef);
	            if (se != null) {
	            	wrapper = component;
	                break;
	            }
	        }

	        if (wrapper != null && se != null) {
	        	Map<String, ?> compProps = getComponentRegistry().getProperties(wrapper);
	        	props = new HashMap<String, Object>();
	        	if (compProps.containsKey(ComponentRegistry.NAME)) {
	        		Object compName = compProps.get(ComponentRegistry.NAME);
	        		props.put(ComponentRegistry.NAME, compName);
	        	}

	        	if (compProps.containsKey(ComponentRegistry.TYPE)) {
	        		Object compType = compProps.get(ComponentRegistry.TYPE);
	        		props.put(ComponentRegistry.TYPE, compType);
	        	}

	        	serviceEndpoint = ServiceMixSupport.getEndpoint(getNmr(), props);
	        	if (serviceEndpoint != null) {
	        		logger.info("Succesfully established an outbound endpoint for the service: " + serviceName);
	        		messageExchange.setEndpoint(se);
	        	} else {
	        		logger.warn("Couldn't get an endpoint for the service: " + serviceName);
	        	}
	        } else {
        		logger.warn("Couldn't get an endpoint for the service: " + serviceName);
	        }
		}
        return serviceEndpoint;
	}

	public NMR getNmr() {
		return nmr;
	}

	public void setNmr(NMR nmr) {
		this.nmr = nmr;
	}

	public ComponentRegistry getComponentRegistry() {
		return componentRegistry;
	}

	public void setComponentRegistry(ComponentRegistry componentRegistry) {
		this.componentRegistry = componentRegistry;
	}

	public ServiceResolver getServiceResolver() {
		return serviceResolver;
	}

	public void setServiceResolver(ServiceResolver serviceResolver) {
		this.serviceResolver = serviceResolver;
	}

	public Map<String, ?> getProperties() {
	    return properties;
	}

	public String getId() {
		return getClass().getName();
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(nmr);
		Assert.notNull(componentRegistry);
		Assert.notNull(serviceResolver);
	}

	private QName extractConsumerName(final Exchange xch) {
		final Object o = xch.getProperty(PolicyConstants.POLICY_CONSUMER_NAME);
		return (o instanceof QName) ? (QName) o : null;
	}
}
