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



@WebService
public interface ReservationService {
	public int createReservation(@WebParam(name = "passengers")List<Passenger> passengers, @WebParam(name = "flight")Flight flight);
}
