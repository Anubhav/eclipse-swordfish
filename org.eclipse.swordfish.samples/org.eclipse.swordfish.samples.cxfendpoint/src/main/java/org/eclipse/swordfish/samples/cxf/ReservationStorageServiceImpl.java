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
package org.eclipse.swordfish.samples.cxf;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.eclipse.swordfish.samples.cxf.domain.Reservation;




/**
 * @author akopachevsky
 */
@WebService(endpointInterface = "org.eclipse.swordfish.samples.cxf.ReservationStorageService", serviceName = "ReservationStorageServiceImpl" )
public class ReservationStorageServiceImpl implements ReservationStorageService {

	public int addReservation(@WebParam(name = "reservation")Reservation reservation) {
		System.out.println("Hello word");
		return 100;
	}


}
