/**
 * Copyright (c) 2009-2012, Netbout.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are PROHIBITED without prior written permission from
 * the author. This product may NOT be used anywhere and on any computer
 * except the server platform of netBout Inc. located at www.netbout.com.
 * Federal copyright law prohibits unauthorized reproduction by any means
 * and imposes fines up to $25,000 for violation. If you received
 * this code occasionally and without intent to use it, please report this
 * incident to the author by email.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.netbout.inf.ray.imap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Map of values and message numbers.
 *
 * <p>Implementation must be thread-safe, except {@link #load(InputStream)}
 * and {@link #save(OutputStream)} methods.
 *
 * @author Yegor Bugayenko (yegor@netbout.com)
 * @version $Id$
 */
interface Reverse {

    /**
     * How many bytes we consume in memory.
     * @return Number of bytes
     */
    long sizeof();

    /**
     * Get value by message number (throws runtime exception if message
     * is not found).
     * @param msg The number of the message
     * @return The value
     */
    String get(long msg);

    /**
     * Put value and number.
     * @param number The number to add
     * @param value The value
     */
    void put(long number, String value);

    /**
     * Remove this message.
     * @param number The message to delete
     */
    void remove(long number);

    /**
     * Save them all to the output stream.
     * @param stream The stream to save to
     * @throws IOException If some I/O problem inside
     */
    void save(OutputStream stream) throws IOException;

    /**
     * Load from the input stream and add here.
     * @param stream The stream to load from
     * @throws IOException If some I/O problem inside
     */
    void load(InputStream stream) throws IOException;

}
