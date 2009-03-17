package org.eclipse.swordfish.samples.jaxws.reservation;


import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;


public class  ReservationServiceHelper {
	
	public static final String url = "http://localhost:8194/reservation/";
	
	public static ReservationService getService(){
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(ReservationService.class);
			factory.setAddress(url);
			return (ReservationService) factory.create();
	}
}
