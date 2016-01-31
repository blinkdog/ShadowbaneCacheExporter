/*
 * Data.java
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

package com.pmeade.shadowbane.util;

/**
 * Data holds some static methods for working with binary data.
 * @author pmeade
 */
public class Data
{
    /**
     * Convert a signed int to an unsigned int (long).
     * @param x int to be converted to an unsigned int
     * @return long containing the unsigned int value provided in x
     */
    public static final long UI(int x) {
        return x & 0x00000000ffffffffL;
    }

    /**
     * Convert a signed short to an unsigned short (int).
     * @param x short to be converted to an unsigned short
     * @return int containing the unsigned short value provided in x
     */
    public static final int US(short x) {
        return x & 0x0000ffff;
    }
}
