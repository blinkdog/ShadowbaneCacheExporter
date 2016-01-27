/*
 * CacheResource.java
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

package com.pmeade.shadowbane;

/**
 * CacheResource represents a resource stored in a cache archive file.
 * @author pmeade
 */
public class CacheResource
{
    /**
     * Index of the resource. This is the position of the resource
     * as listed in the directory of the cache archive file.
     */
    public int index;
    
    /**
     * ID of the resource. This is the identity of the resource. Note
     * that despite the name "identity", it is not guaranteed to be
     * unique across (or within a single!) cache archive file.
     */
    public long id;
    
    /**
     * Offset to the resource data. This offset is measured relative
     * to the beginning of the cache archive file.
     */
    public long dataOffset;
    
    /**
     * Total size of the resource data. This is the size of the resource
     * data after it has been decompressed (if necessary).
     */
    public long size;
    
    /**
     * The compressed size of the resource data. This is the size of the
     * resource data as it is stored in the cache archive file. If this
     * size is equal to "size", that means the data is NOT compressed,
     * but instead stored as-is in the cache archive file.
     */
    public long zipSize;
    
    /**
     * The data of the resource. After CacheArchive.loadResource(), this
     * field will be populated with the bytes that make up the resource.
     * All decompression is handled by the loading routine, if necessary.
     */
    public byte[] data;
}
