package org.eclipse.swordfish.samples.http;

import java.util.Arrays;

import org.eclipse.swordfish.samples.cxf.BookingService;
import org.eclipse.swordfish.samples.cxf.domain.Flight;
import org.eclipse.swordfish.samples.cxf.domain.Passenger;
import org.eclipse.swordfish.samples.cxf.domain.Reservation;




public class BookingServiceClient {
    private static final String REQUEST_BODY =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">" +
        "<soap:Header><wsa:Action>http://cxf.samples.swordfish.eclipse.org//BookingService/createReservation</wsa:Action></soap:Header>" +
        "<soap:Body><ns2:createReservation xmlns:ns2=\"http://cxf.samples.swordfish.eclipse.org/\"><passengers><age>24</age><firstName>Volodymyr</firstName><id>1</id><lastName>Zhabiuk</lastName></passengers><flight><flightNumber>LC023</flightNumber><id>1</id></flight></ns2:createReservation>" +
        "</soap:Body></soap:Envelope>";

    public static void main(String[] args) {
	//At first use the http client to send to invoke the webservice
        String response = ClientUtil.sendRequest("http://localhost:8192/cxfsample/", REQUEST_BODY);
        System.out.println("The response is: " + response);

        //Now we will leverage cxf to access the jaxws based webservice
        String wsdlUrl = BookingServiceClient.class.getClassLoader().getResource("./BookingService.wsdl").toString();
        BookingService bookingService = ClientUtil.createWebServiceStub(BookingService.class, wsdlUrl);
        int bookingId = bookingService.createReservation(Arrays.asList(new Passenger(1, "Elena", "Krytelyova", 24)), new Flight(1, "LC023"));
        Reservation reservation = bookingService.findReservation(bookingId);
        System.out.println("Server said: " + bookingId);

    }

}
