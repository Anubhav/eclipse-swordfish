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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for location complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="location">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "location", propOrder = {
    "name",
    "code"
})
public class Location {

    @XmlElement(required = true)
    protected String name;
    protected int code;

    public Location() {
		// TODO Auto-generated constructor stub
	}

    public Location(String name, int code) {
		this.name = name;
		this.code = code;
	}

	/**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the code property.
     *
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     */
    public void setCode(int value) {
        this.code = value;
    }

    @Override
    public boolean equals(Object obj) {
    	if (obj == this) {
    		return true;
    	}
		if (!(obj instanceof Location)) {
			return false;
		}
    	Location loc = (Location) obj;
		return name.equals(loc.name) && code == loc.code;
    }

    @Override
    public int hashCode() {
    	int result = 17;
    	result = 31 * result + name.hashCode();
    	result = 31 * result + code;
    	return result;
    }

}
