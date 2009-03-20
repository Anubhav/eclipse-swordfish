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
package org.eclipse.swordfish.samples.dynamic.service.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.JAXB;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.swordfish.api.SwordfishException;
import org.eclipse.swordfish.samples.dynamic.service.domain.Flight;
import org.eclipse.swordfish.samples.dynamic.service.domain.Flights;
import org.eclipse.swordfish.samples.dynamic.service.domain.Location;
import org.springframework.util.Assert;

/**
 *
 */
public class FlightServiceHelper {

	private List<Flight> availableFlights = new ArrayList<Flight>();

	private URL dataUrl;

	public void initFlightsData() {
		try {
			Assert.notNull(getDataUrl());
			Reader dataReader = new InputStreamReader(getDataUrl().openStream());
			Flights unmarshaledflights = JAXB.unmarshal(dataReader, Flights.class);
			availableFlights.addAll(unmarshaledflights.getFlight());
		} catch (IOException e) {
			throw new SwordfishException(e);
		}
	}

	public Flights getFlightsFor(Location departurePoint,
			Location destinationPoint, Date departureDate) {
		List<Flight> foundFlights = new ArrayList<Flight>();

		Flights flights = new Flights();
		flights.setFlight(foundFlights);

		for (Flight nextFlight : getAvailableFlights()) {
			if (departurePoint.equals(nextFlight.getDeparture())
				&& destinationPoint.equals(nextFlight.getDestination())) {
				XMLGregorianCalendar flightDate = nextFlight.getDate();
				Calendar calendar = Calendar.getInstance(
					TimeZone.getTimeZone("GMT" + flightDate.getTimezone()));

				int year = flightDate.getYear();
				int month = flightDate.getMonth();
				int day = flightDate.getDay();

				// month returned by XMLGregorianCalendar is within 1-12
				// and Calendar's one is within 0-11
				calendar.set(year, month - 1, day, 0, 0, 0);
				calendar.set(Calendar.MILLISECOND, 0);

				Calendar depDateCalendar = Calendar.getInstance();
				depDateCalendar.setTime(departureDate);

				if (depDateCalendar.compareTo(calendar) == 0) {
					foundFlights.add(nextFlight);
				}
			}
		}
		return flights;
	}

	public List<Flight> getAvailableFlights() {
		return availableFlights;
	}

	public URL getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(URL dataUrl) {
		this.dataUrl = dataUrl;
	}

}
