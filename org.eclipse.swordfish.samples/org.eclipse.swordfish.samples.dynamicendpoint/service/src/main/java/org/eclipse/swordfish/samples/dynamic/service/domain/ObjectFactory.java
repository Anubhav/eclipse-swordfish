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
package org.eclipse.swordfish.samples.dynamic.service.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the generated package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Flight_QNAME = new QName("", "flight");
    private final static QName _Flights_QNAME = new QName("", "flights");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Flights }
     *
     */
    public Flights createFlights() {
        return new Flights();
    }

    /**
     * Create an instance of {@link Flight }
     *
     */
    public Flight createFlight() {
        return new Flight();
    }

    /**
     * Create an instance of {@link Location }
     *
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Flight }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "", name = "flight")
    public JAXBElement<Flight> createFlight(Flight value) {
        return new JAXBElement<Flight>(_Flight_QNAME, Flight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Flights }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "", name = "flights")
    public JAXBElement<Flights> createFlights(Flights value) {
        return new JAXBElement<Flights>(_Flights_QNAME, Flights.class, null, value);
    }

}
