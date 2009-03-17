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
package org.eclipse.swordfish.samples.jaxws.storage;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.eclipse.swordfish.samples.jaxws.domain.Reservation;




/**
 * @author akopachevsky
 */
@WebService(endpointInterface = "org.eclipse.swordfish.samples.jaxws.storage.ReservationStorageService", serviceName = "ReservationStorageServiceImpl" )
public class ReservationStorageServiceImpl implements ReservationStorageService {
	private static Map<Integer, Reservation> reservationStorage = new HashMap<Integer, Reservation>();
	private static int bookingId;
	public int addReservation(@WebParam(name = "reservation")Reservation reservation) {
		int reservationId = ++bookingId;
		reservation.setId(reservationId);
		reservationStorage.put(reservationId, reservation);
		return reservationId;
	}

	public Reservation findReservation(int reservationId) {
		return reservationStorage.get(reservationId);
	}

}
