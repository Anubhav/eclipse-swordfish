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

/**
 *
 */
@WebService
public interface FlightService {

	@WebResult(name="flights")
	Flights searchFlights(
			@WebParam(name = "departure")Location departurePoint,
			@WebParam(name = "destination")Location destinationPoint,
			@WebParam(name = "date")Date departureDate);

}
