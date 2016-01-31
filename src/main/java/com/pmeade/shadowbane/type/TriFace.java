/*
 * TriFace.java
 * Copyright 2016 Patrick Meade.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pmeade.shadowbane.type;

import java.nio.ByteBuffer;

import static com.pmeade.shadowbane.util.Data.US;

/**
 * TriFace represents a triangle face; a part of a polygon mesh. A TriFace
 * is defined by the three vertices that make up the corners of the triangle.
 * @author pmeade
 */
public class TriFace
{
    /**
     * Read a TriFace from the current position of the provided ByteBuffer.
     * @param buffer ByteBuffer from which to read the TriFace data
     * @return TriFace as read from the buffer
     */
    public static TriFace getTriFace(ByteBuffer buffer) {
        TriFace triFace = new TriFace();
        triFace.v1 = US(buffer.getShort());
        triFace.v2 = US(buffer.getShort());
        triFace.v3 = US(buffer.getShort());
        return triFace;
    }

    /**
     * Convert the TriFace to a representation suitable for Blender Python.
     * @return String containing a Blender Python representation of the TriFace
     */
    public String toPython() {
        return "("+v1+","+v2+","+v3+")";
    }

    /** Vertex 1 */
    public int v1;

    /** Vertex 2 */
    public int v2;

    /** Vertex 3 */
    public int v3;
}
