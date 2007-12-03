/*******************************************************************************
 * Copyright (c) 2007 Deutsche Post AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Deutsche Post AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.swordfish.core.interceptor.extension.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;

/**
 * Helper for attachment handling.
 */
public class PayloadDataSource implements DataSource {

    /**
     * String indicating mime type of all instances. This is the mime type for arbitrary binary data
     * as defined in RFC2046 , chapter 4.5.1 .
     */
    private static final String MIME_TYPE = "application/octet-stream";

    /** Prefixed used to build the name of this attachment in conjunction with the number. */
    private static final String NAME_PREFIX = "BinaryAttachment_";

    /** the stream holding the content of this data source. */
    private InputStream content;

    /** a number for calculating the name of this data source. */
    private int number;

    /**
     * The Constructor.
     * 
     * @param content
     *        attachment content
     * @param number
     *        number of attachments
     */
    protected PayloadDataSource(final InputStream content, final int number) {
        super();
        this.content = content;
        this.number = number;
    }

    /**
     * Gets the content type.
     * 
     * @return the content type
     * 
     * @see javax.activation.DataSource#getContentType()
     */
    public String getContentType() {
        return MIME_TYPE;
    }

    /**
     * Gets the input stream.
     * 
     * @return the input stream
     * 
     * @throws IOException
     * 
     * @see javax.activation.DataSource#getInputStream()
     */
    public InputStream getInputStream() throws IOException {
        return this.content;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     * 
     * @see javax.activation.DataSource#getName()
     */
    public String getName() {
        return NAME_PREFIX + this.number;
    }

    /**
     * Gets the output stream.
     * 
     * @return the output stream
     * 
     * @throws IOException
     * 
     * @see javax.activation.DataSource#getOutputStream()
     */
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("This data source is read only.");
    }
}