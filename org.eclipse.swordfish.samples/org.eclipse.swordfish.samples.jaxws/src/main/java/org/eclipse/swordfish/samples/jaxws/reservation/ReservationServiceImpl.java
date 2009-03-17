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
package org.eclipse.swordfish.samples.jaxws.reservation;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.eclipse.swordfish.samples.jaxws.domain.Flight;
import org.eclipse.swordfish.samples.jaxws.domain.Passenger;
import org.eclipse.swordfish.samples.jaxws.domain.Reservation;
import org.eclipse.swordfish.samples.jaxws.storage.ReservationStorageServiceHelper;

/**
 * @author vzhabuik, akopachevsky
 */
@WebService(endpointInterface = "org.eclipse.swordfish.samples.jaxws.reservation.ReservationService", serviceName = "ReservationServiceImpl")
public class ReservationServiceImpl implements ReservationService {

	public int createReservation(@WebParam(name = "passengers") List<Passenger> passengers, 
			                     @WebParam(name = "flight") Flight flight) {
		if (flight == null) {
			throw new IllegalArgumentException("The supplied flight is null");
		}

		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassengers(passengers);

		int reservationId = ReservationStorageServiceHelper
				.httpAddReservation(reservation);
		return reservationId;
	}
}
