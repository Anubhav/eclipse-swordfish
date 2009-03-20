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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for flight complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="flight">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="departure" type="{}location"/>
 *         &lt;element name="destination" type="{}location"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="flightNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flight", propOrder = {
    "departure",
    "destination",
    "date"
})
public class Flight {

    @XmlElement(required = true)
    protected Location departure;
    @XmlElement(required = true)
    protected Location destination;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlAttribute(required = true)
    protected int id;
    @XmlAttribute(required = true)
    protected String flightNumber;

    /**
     * Gets the value of the departure property.
     *
     * @return
     *     possible object is
     *     {@link Location }
     *
     */
    public Location getDeparture() {
        return departure;
    }

    /**
     * Sets the value of the departure property.
     *
     * @param value
     *     allowed object is
     *     {@link Location }
     *
     */
    public void setDeparture(Location value) {
        this.departure = value;
    }

    /**
     * Gets the value of the destination property.
     *
     * @return
     *     possible object is
     *     {@link Location }
     *
     */
    public Location getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     *
     * @param value
     *     allowed object is
     *     {@link Location }
     *
     */
    public void setDestination(Location value) {
        this.destination = value;
    }

    /**
     * Gets the value of the date property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the id property.
     *
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the flightNumber property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the value of the flightNumber property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFlightNumber(String value) {
        this.flightNumber = value;
    }

}
