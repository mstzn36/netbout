/**
 * Copyright (c) 2009-2011, NetBout.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the NetBout.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.netbout.spi.cpa;

import com.netbout.spi.Token;
import com.netbout.spi.TypeMapper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Classpath annotations helper.
 *
 * @author Yegor Bugayenko (yegor@netbout.com)
 * @version $Id$
 */
final class HelpTarget {

    /**
     * The farm.
     */
    private final transient Object farm;

    /**
     * The method to call.
     */
    private final transient Method method;

    /**
     * Private ctor, use {@link #build(Object,Method)} instead.
     * @param frm Farm object
     * @param mtd Method to call on this farm
     */
    private HelpTarget(final Object frm, final Method mtd) {
        this.farm = frm;
        this.method = mtd;
    }

    /**
     * Build new object.
     * @param frm Farm object
     * @param mtd Method to call on this farm
     * @return The target created
     */
    public static HelpTarget build(final Object frm, final Method mtd) {
        return new HelpTarget(frm, mtd);
    }

    /**
     * Execute it with arguments.
     * @param token The token
     */
    public void execute(final Token token) {
        Object result;
        try {
            result = this.method.invoke(
                this.farm,
                this.converted(
                    token,
                    this.method.getParameterTypes()
                )
            );
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(ex);
        } catch (java.lang.reflect.InvocationTargetException ex) {
            throw new IllegalStateException(ex);
        }
        token.result(TypeMapper.toText(result));
    }

    /**
     * Convert argument types.
     * @param token The token
     * @param types Expected types for every one of them
     * @param annotations Parameter annotations
     * @return Array of properly types args
     */
    public Object[] converted(final Token token, final Class[] types) {
        final Object[] converted = new Object[types.length];
        for (int pos = 0; pos < types.length; pos += 1) {
            converted[pos] = TypeMapper.toObject(token.arg(pos), types[pos]);
        }
        return converted;
    }

}
