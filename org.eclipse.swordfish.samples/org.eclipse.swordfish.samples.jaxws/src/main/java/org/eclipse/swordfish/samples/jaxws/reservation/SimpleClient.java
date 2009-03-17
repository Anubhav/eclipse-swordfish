package org.eclipse.swordfish.samples.jaxws.reservation;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swordfish.samples.jaxws.domain.Flight;
import org.eclipse.swordfish.samples.jaxws.domain.Passenger;
import org.eclipse.swordfish.samples.jaxws.domain.Reservation;
import org.eclipse.swordfish.samples.jaxws.storage.ReservationStorageServiceHelper;

public class SimpleClient {

	public static void main(String[] args) {
		ReservationService service =
			ReservationServiceHelper.getService();
		
		List<Passenger> passengers = Arrays.asList(new Passenger(1, "Elena", "Krytelova", 24));
		Flight flight = new Flight(1, "LC023");
		
		int bookingId = service.createReservation(passengers, flight);
		Reservation newReservation = 
			ReservationStorageServiceHelper.getService().findReservation(bookingId);
		System.out.println("Server said: " + bookingId + " newReservation " + newReservation);
		System.exit(0);
	}

}
