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
package org.eclipse.swordfish.samples.dynamic.service;

import java.util.Date;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.eclipse.swordfish.samples.dynamic.service.domain.Flights;
import org.eclipse.swordfish.samples.dynamic.service.domain.Location;
import org.eclipse.swordfish.samples.dynamic.service.util.FlightServiceHelper;

/**
 *
 */
@WebService(
		endpointInterface = "org.eclipse.swordfish.samples.dynamic.service.FlightService",
        serviceName = "FlightServiceImpl")
public class FlightServiceImpl implements FlightService {

	private FlightServiceHelper helper;

	@WebResult(name="flights")
	public Flights searchFlights(
			@WebParam(name = "departure") Location departurePoint,
			@WebParam(name = "destination") Location destinationPoint,
			@WebParam(name = "date") Date departureDate) {

		if (departurePoint == null) {
			throw new IllegalArgumentException("The supplied departure point is null");
		}

		if (destinationPoint == null) {
			throw new IllegalArgumentException("The supplied destination is null");
		}

		if (departureDate == null) {
			throw new IllegalArgumentException("The supplied departure date is null");
		}

		return getHelper().getFlightsFor(departurePoint, destinationPoint, departureDate);
	}

	public FlightServiceHelper getHelper() {
		return helper;
	}

	public void setHelper(FlightServiceHelper helper) {
		this.helper = helper;
	}

}
