/*
 * TerrainMapSpec.java
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

package com.pmeade.shadowbane.terrain;

/**
 * TerrainMapSpec is a specification for a terrain map. It indicates
 * which TerrainAlpha resources are involved and how they are organized.
 * @author pmeade
 */
public class TerrainMapSpec
{
    /**
     * Map tiles are arranged in a column-major way (elements are ordered
     * in sequence by column).
     * @see https://en.wikipedia.org/wiki/Row-major_order
     */
    public static final int COLUMN_MAJOR = 0;

    /**
     * Map tiles are arranged in a row-major way (elements are ordered
     * in sequence by row).
     * @see https://en.wikipedia.org/wiki/Row-major_order
     */
    public static final int ROW_MAJOR = 1;

    /**
     * Construct a TerrainMapSpec to specify a terrain map.
     * @param startIndex the TerrainAlpha index where this map begins
     * @param width the width of the map, in TerrainAlpha resources
     * @param height the height of the map, in TerrainAlpha resources
     * @param ordering The ordering of the map; column or row major
     */
    public TerrainMapSpec(int startIndex, int width, int height, int ordering)
    {
        this.startIndex = startIndex;
        this.width = width;
        this.height = height;
        this.ordering = ordering;
    }

    /** The TerrainAlpha index where this map begins. */
    public int startIndex;

    /** The width of the map, in TerrainAlpha resources. */
    public int width;

    /** The height of the map, in TerrainAlpha resources. */
    public int height;

    /** The ordering of the map; column or row major. */
    public int ordering;
}
