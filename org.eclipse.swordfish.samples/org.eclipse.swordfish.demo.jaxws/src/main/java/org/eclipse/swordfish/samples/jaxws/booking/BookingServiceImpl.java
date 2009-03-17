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
package org.eclipse.swordfish.samples.jaxws.booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.eclipse.swordfish.samples.jaxws.domain.Flight;
import org.eclipse.swordfish.samples.jaxws.domain.Passenger;
import org.eclipse.swordfish.samples.jaxws.domain.Reservation;
import org.eclipse.swordfish.samples.jaxws.storage.ReservationStorageServiceHelper;

/**
 *
 * The class is not threadsafe
 * @author vzhabiuk
 *
 */
@WebService(endpointInterface = "org.eclipse.swordfish.samples.cxf.BookingService",
        serviceName = "BookingServiceImpl" )
public class BookingServiceImpl implements BookingService {
	private static int passengerId;
	private static int flightId;
	private static int bookingId;
	private static Map<Integer, Reservation> reservationStorage = new HashMap<Integer, Reservation>();
	public int createReservation(@WebParam(name = "passengers") List<Passenger> passengers, @WebParam(name = "flight") Flight flight) {
		if (flight == null) {
			throw new IllegalArgumentException("The supplied flight is null");
		}


			int reservationId = ++bookingId;
			Reservation reservation = new Reservation();
			reservation.setId(reservationId);
			reservation.setFlight(flight);
			reservation.setPassengers(passengers);
			ReservationStorageServiceHelper.httpAddReservation(reservation);
			return reservationId;

	}

	public Reservation findReservation(int reservationId) {
		return ReservationStorageServiceHelper.getService().findReservation(reservationId);
	}

}
