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
package com.netbout.inf.index;

import com.netbout.inf.Index;
import com.netbout.inf.SqlEngine;
import com.netbout.inf.TextEngine;
import com.netbout.inf.jdbc.HsqlEngine;
import com.netbout.inf.lucene.LuceneEngine;
import com.ymock.util.Logger;
import java.io.File;

/**
 * Index in file-system.
 *
 * @author Yegor Bugayenko (yegor@netbout.com)
 * @version $Id$
 */
public final class FsIndex implements Index {

    /**
     * The folder to use.
     */
    private final transient Folder folder;

    /**
     * SQL engine.
     */
    private final transient SqlEngine sengine;

    /**
     * Texts engine.
     */
    private final transient TextEngine tengine;

    /**
     * Default public ctor.
     */
    public FsIndex() {
        this(new EbsVolume());
    }

    /**
     * Public ctor.
     * @param fldr The Folder to use
     */
    public FsIndex(final Folder fldr) {
        this.folder = fldr;
        this.sengine = new HsqlEngine(new File(this.folder.path(), "hsql"));
        this.tengine = new LuceneEngine(new File(this.folder.path(), "lucene"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws java.io.IOException {
        this.folder.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String statistics() {
        return new StringBuilder()
            .append(this.folder.statistics())
            .append(Logger.format("\n%[type]s (SQL):\n", this.sengine))
            .append(this.sengine.statistics())
            .append(Logger.format("\n%[type]s (Text):\n", this.tengine))
            .append(this.tengine.statistics())
            .toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlEngine sql() {
        return this.sengine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextEngine texts() {
        return this.tengine;
    }

}
