package org.eclipse.swordfish.samples.jaxws.storage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.eclipse.swordfish.samples.jaxws.domain.Passenger;
import org.eclipse.swordfish.samples.jaxws.domain.Reservation;

public class  ReservationStorageServiceHelper {
	
	public static final String url = "http://localhost:8194/storage/";
	
	private static final String REQUEST_BODY_START =
		"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"> " +
		"<soap:Header><wsa:Action>http://storage.jaxws.samples.swordfish.eclipse.org//ReservationStorageService/addReservation</wsa:Action></soap:Header>" +
		"<soap:Body><ns2:addReservation xmlns:ns2=\"http://cxf.samples.swordfish.eclipse.org/\"><reservation>";
	
	private static final String REQUEST_PASSENGERS =
		"<passengers><age>%s</age><firstName>%s</firstName><id>%s</id><lastName>%s</lastName></passengers>";
		
	private static final String REQUEST_FLIGHT =
		"<flight><flightNumber>%s</flightNumber><id>%s</id></flight>";			
	private static final String REQUEST_BODY_END =
			"</reservation></ns2:addReservation></soap:Body></soap:Envelope>";

	public static int httpAddReservation(Reservation reservation){
		Passenger passenger = (Passenger)reservation.getPassengers().get(0);
		String passengers = String.format(REQUEST_PASSENGERS, passenger.getAge(), 
				                          passenger.getFirstName(), 
				                          passenger.getId(), passenger.getLastName());
		String flight = String.format(REQUEST_FLIGHT, reservation.getFlight().getFlightNumber(),
				                      reservation.getFlight().getId());
		String REQUEST_BODY = REQUEST_BODY_START + passengers + flight + REQUEST_BODY_END;
		try {
	     // Prepare HTTP post
	     PostMethod post = new PostMethod(url);
	     // Request content will be retrieved directly
	     // from the input stream
	     RequestEntity entity = new StringRequestEntity(REQUEST_BODY, "text/xml","UTF8");
	     post.setRequestEntity(entity);
	     // consult documentation for your web service
	     post.setRequestHeader("SOAPAction", "");
	     // Get HTTP client
	     HttpClient httpclient = new HttpClient();
	     // Execute request
	     try {
	         int result = httpclient.executeMethod(post);
	         // Display status code
	         if(result == 200){
	        	String response = post.getResponseBodyAsString();
	        	Pattern p = Pattern.compile("<return>(.*)</return>");
	        	Matcher m = p.matcher(response);
	        	m.find();
	        	String reservationId = m.group(1);
	        	return Integer.parseInt(reservationId);
	         }
	         System.out.println();
	     } finally {
	         // Release current connection to the connection pool once you are done
	         post.releaseConnection();
	     }
		} catch (IOException io){
			throw new RuntimeException(io);
		};
	    return 0;
	}
	
	
	public static ReservationStorageService getService(){
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(ReservationStorageService.class);
			factory.setAddress(url);

			return (ReservationStorageService) factory.create();

	}
}
