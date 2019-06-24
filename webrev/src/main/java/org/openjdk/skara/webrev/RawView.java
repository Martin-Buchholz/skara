/*
 * Copyright (c) 2018, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.skara.webrev;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class RawView implements View {
    private final Path out;
    private final Path file;
    private final List<String> text;
    private final byte[] binary;

    public RawView(Path out, Path file, List<String> text) {
        this.out = out;
        this.file = file;
        this.text = text;
        this.binary = null;
    }

    public RawView(Path out, Path file, byte[] binary) {
        this.out = out;
        this.file = file;
        this.binary = binary;
        this.text = null;
    }

    public void render(Writer w) throws IOException {
        var rawFile = out.resolve(file.toString());
        Files.createDirectories(rawFile.getParent());

        if (binary != null) {
            Files.write(rawFile, binary);
        } else {
            Files.write(rawFile, text);
        }

        w.write("<a href=\"");
        w.write(Webrev.relativeToIndex(out, rawFile));
        w.write("\">Raw</a>\n");
    }
}
