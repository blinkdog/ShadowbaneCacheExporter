
package com.pmeade.shadowbane;

/**
 * CacheResource represents a resource stored in a cache archive file.
 * @author veloxi
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
