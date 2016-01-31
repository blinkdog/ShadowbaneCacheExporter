/*
 * RenderCache.java
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

package com.pmeade.shadowbane.render;

import com.pmeade.shadowbane.CacheArchive;
import com.pmeade.shadowbane.CacheResource;
import com.pmeade.shadowbane.mesh.MeshCache;
import java.io.File;

/**
 * RenderCache represents "Render.cache", the cache archive containing
 * Render resources. This class provides a method to export a render
 * resource to a file.
 * @author pmeade
 */
public class RenderCache extends CacheArchive
{
    /**
     * Construct a RenderCache archive.
     * @param cacheDir the cache directory, where Render.cache is located
     * @param meshCache MeshCache providing access to Mesh resources
     */
    public RenderCache(File cacheDir, MeshCache meshCache) {
        super(new File(cacheDir, "Render.cache"));
        this.meshCache = meshCache;
        read();
    }

    /**
     * Determine the number of resources present in the render cache.
     * @return the number of resources present in the render cache
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
        File resourceDir = new File(outputDir, "Render");
        resourceDir.mkdir();
        CacheResource resource = resources.get(index);
        loadResource(resource);
        RenderResource render = new RenderResource(resource, meshCache);
        render.exportToJson(resourceDir);
    }
    
    private final MeshCache meshCache;
}
