/*
 * TerrainAlphaCache.java
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

import com.pmeade.shadowbane.CacheArchive;
import com.pmeade.shadowbane.CacheResource;
import java.io.File;

/**
 * TerrainAlphaCache represents "TerrainAlpha.cache", the cache archive
 * containing TerrainAlpha resources. This class provides a method to
 * export a terrain resource to a file.
 * @author pmeade
 */
public class TerrainAlphaCache extends CacheArchive
{
    /**
     * Construct a TerrainAlphaCache archive.
     * @param cacheDir the cache directory, where TerrainAlpha.cache is located
     */
    public TerrainAlphaCache(File cacheDir) {
        super(new File(cacheDir, "TerrainAlpha.cache"));
        read();
    }

    /**
     * Determine the number of resources present in the terrain cache.
     * @return the number of resources present in the terrain cache
     */
    public int size() {
        return resources.size();
    }

    /**
     * Export the indicated resource to a file.
     * @param index index of the resource to be exported
     * @param outputDir the output directory
     */
    public void export(int index, File outputDir) {
        File resourceDir = new File(outputDir, "TerrainAlpha");
        resourceDir.mkdir();
        CacheResource resource = resources.get(index);
        loadResource(resource);
        TerrainAlphaResource terrain = new TerrainAlphaResource(resource);
        terrain.exportToPng(resourceDir);
    }
}
