/*
 * CacheArchive.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * CacheArchive represents a .cache archive file that contains
 * resources. In some .cache files, the resources are compressed.
 * In other .cache files, the resources are stored uncompressed.
 * This class reads the directory and allows loading of selected
 * resources; decompression is handled, if necessary.
 * @author pmeade
 */
abstract public class CacheArchive
{
    /**
     * Convert an int to an unsigned int.
     * @param x int to be converted to an unsigned int
     * @return long containing the unsigned int value provided in x
     */
    public static final long UI(int x) {
        return x & 0x00000000ffffffffL;
    }

    /**
     * Construct a CacheArchive for the provided file name.
     * @param cacheFileName name of the cache file
     */
    public CacheArchive(String cacheFileName) {
        this(new File(cacheFileName));
    }

    /**
     * Construct a CacheArchive for the provided File object.
     * @param cacheFile file object representing the cache file
     */
    public CacheArchive(File cacheFile) {
        this.cacheFile = cacheFile;
        this.resources = new ArrayList();
    }

    /**
     * Read the cache archive header and cache archive directory.
     * Loads all the metadata for the resources contains in the cache archive.
     */
    protected void read() {
        try {
            FileChannel fc = new FileInputStream(cacheFile).getChannel();
            buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asReadOnlyBuffer();
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            IntBuffer ib = buffer.asIntBuffer();
            ib.position(0);
            numResources = UI(ib.get());
            dataOffset = UI(ib.get());
            fileSize = UI(ib.get());
            
            if(fileSize != cacheFile.length()) {
                System.err.println("BAD MOJO: fileSize:" + fileSize + " vs. cacheFile.length():" + cacheFile.length());
            }
            
            for(int i=0; i<numResources; i++) {
                CacheResource cacheResource = new CacheResource();
                ib.position(i*5 + 5); // i*5 (5 ints per record)
                                      // + 4 (start of all records)
                                      // + 1 (skip empty field in record)
                cacheResource.index = i;
                cacheResource.id = UI(ib.get());
                cacheResource.dataOffset = UI(ib.get());
                cacheResource.size = UI(ib.get());
                cacheResource.zipSize = UI(ib.get());
                resources.add(cacheResource);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Load a CacheResource from the CacheArchive.
     * @param resource CacheResource whose data is to be loaded
     */
    protected void loadResource(CacheResource resource) {
        try {
            resource.data = new byte[(int)resource.zipSize];
            buffer.position((int)resource.dataOffset);
            buffer.get(resource.data);
            if(resource.size != resource.zipSize) {
                byte[] data = new byte[(int)resource.size];
                Inflater inflater = new Inflater();
                inflater.setInput(resource.data);
                int inflateLength = inflater.inflate(data);
                inflater.end();
                resource.data = data;

                if(inflateLength != resource.size) {
                    System.err.println("BAD MOJO: inflateLength:" + inflateLength + " vs. resource.size:" + resource.size);
                }
            }
        } catch (DataFormatException e) {
            resource.data = null;
            e.printStackTrace(System.err);
        }
    }

    /**
     * File object representing the cache archive file.
     */
    protected File cacheFile;

    /**
     * ByteBuffer that provides memory mapped access to the bytes
     * in the cache archive file.
     */
    protected ByteBuffer buffer;

    /**
     * Number of resources contained in the cache archive file.
     */
    protected long numResources;
    
    /**
     * Offset to the beginning of the cache resource data. The offset
     * is relative to the beginning of the cache archive file.
     */
    protected long dataOffset;
    
    /**
     * The size of the cache archive file.
     */
    protected long fileSize;
    
    /**
     * List of CacheResources. After the read() method is called, this list
     * is populated with the metadata for each of the resources contained in
     * the cache archive file. Each resource may then be passed to the
     * loadResource() method to load its data from the cache archive file.
     */
    protected List<CacheResource> resources;
}
