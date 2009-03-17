package org.eclipse.swordfish.samples.jaxws.storage;

import java.util.Arrays;

import org.eclipse.swordfish.samples.jaxws.domain.Flight;
import org.eclipse.swordfish.samples.jaxws.domain.Passenger;
import org.eclipse.swordfish.samples.jaxws.domain.Reservation;

public class SimpleClient {


	public static void main(String[] args) {
		ReservationStorageService service =
			ReservationStorageServiceHelper.getService();
		
		Reservation reservation = new Reservation();
		reservation.setPassengers(Arrays.asList(new Passenger(1, "Elena", "Krytelova", 24)));
		reservation.setFlight(new Flight(1, "LC023"));
		
		int bookingId = service.addReservation(reservation);
		Reservation newReservation = service.findReservation(bookingId);
		System.out.println("Server said: " + bookingId + " newReservation " + newReservation);
		System.exit(0);
	}

}
