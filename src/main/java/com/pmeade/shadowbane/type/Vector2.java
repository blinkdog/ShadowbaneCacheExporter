/*
 * Vector2.java
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
 * Vector2 represents a 2-tuple of float values. This could be either a
 * 2D point or a 2D vector.
 * @author pmeade
 */
public class Vector2
{
    /**
     * Read a Vector2 from the provided ByteBuffer.
     * @param buffer ByteBuffer from which to read the Vector2
     * @return Vector2 containing the 2-tuple float values
     */
    public static Vector2 getVector2(ByteBuffer buffer) {
        Vector2 v2 = new Vector2();
        v2.x = buffer.getFloat();
        v2.y = buffer.getFloat();
        return v2;
    }

    /** X-Coordinate of the tuple */
    public float x;
    
    /** Y-Coordinate of the tuple */
    public float y;
}
