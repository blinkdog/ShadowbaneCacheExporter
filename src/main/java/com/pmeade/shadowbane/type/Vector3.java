/*
 * Vector3.java
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

/**
 * Vector3 represents a 3-tuple of float values. This could be either a
 * 3D point or a 3D vector.
 * @author pmeade
 */
public class Vector3
{
    /**
     * Read a Vector3 from the provided ByteBuffer.
     * @param buffer ByteBuffer from which to read the Vector3
     * @return Vector3 containing a 3-tuple of float values
     */
    public static Vector3 getVector3(ByteBuffer buffer) {
        Vector3 v3 = new Vector3();
        v3.x = buffer.getFloat();
        v3.y = buffer.getFloat();
        v3.z = buffer.getFloat();
        return v3;
    }

    /**
     * Convert the Vector3 to a representation suitable for Blender Python.
     * @return String containing a Blender Python representation of the Vector3
     */
    public String toPython() {
        return "("+x+","+y+","+z+")";
    }
    
    /** X-Coordinate of the tuple */
    public float x;
    
    /** Y-Coordinate of the tuple */
    public float y;
    
    /** Z-Coordinate of the tuple */
    public float z;
}
