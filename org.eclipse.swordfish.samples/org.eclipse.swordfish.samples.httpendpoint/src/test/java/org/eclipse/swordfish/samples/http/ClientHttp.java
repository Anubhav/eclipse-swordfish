package org.eclipse.swordfish.samples.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class ClientHttp {
	private static final String REQUEST_BODY =
  "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
  "" +
  "<soap:Body><ns2:createReservation xmlns:ns2=\"http://elven/laba/webservices\">" +
  "<passengers><age>24</age><firstName>Volodymyr</firstName><id>1</id>" +
  "<lastName>Zhabiuk</lastName></passengers><flight><flightNumber>LC023</flightNumber>" +
  "<id>1</id></flight></ns2:createReservation></soap:Body></soap:Envelope>";



	public static void main(String[] args) throws Exception  {
		System.out.println("Response status code: " + REQUEST_BODY);
		String strURL = "http://localhost:8192/cxfsample/";

     // Prepare HTTP post
     PostMethod post = new PostMethod(strURL);
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
         System.out.println("Response status code: " + result);
         // Display response
         System.out.println("Response body: ");
         System.out.println(post.getResponseBodyAsString());
     } finally {
         // Release current connection to the connection pool once you are done
         post.releaseConnection();
     }
}
}
