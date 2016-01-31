/*
 * MeshCache.java
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

package com.pmeade.shadowbane.mesh;

import com.pmeade.shadowbane.CacheArchive;
import com.pmeade.shadowbane.CacheResource;
import java.io.File;

/**
 * MeshCache represents "Mesh.cache", the cache archive containing
 * Mesh resources. This class provides a method to export a mesh
 * resource to a file.
 * @author pmeade
 */
public class MeshCache extends CacheArchive
{
    /**
     * Construct a MeshCache archive.
     * @param cacheDir the cache directory, where Mesh.cache is located
     */
    public MeshCache(File cacheDir) {
        super(new File(cacheDir, "Mesh.cache"));
        read();
    }

    /**
     * Determine the number of resources present in the mesh cache.
     * @return the number of resources present in the mesh cache
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
        File resourceDir = new File(outputDir, "Mesh");
        resourceDir.mkdir();
        CacheResource resource = resources.get(index);
        loadResource(resource);
        MeshResource mesh = new MeshResource(resource);
        mesh.exportToBlenderPython(resourceDir);
    }

    /**
     * Find a Mesh resource for the provided ID. Note that this method
     * is part of the Render resources work-in-progress.
     * @param id identity of the Mesh resource to find
     * @return MeshResource with the ID, or null if not found
     */
    public MeshResource findById(long id) {
        for(CacheResource resource : resources) {
            if(resource.id == id) {
                loadResource(resource);
                return new MeshResource(resource);
            }
        }
        System.err.println("BAD MOJO: MeshCache.findById(" + id + ") was unable to find the Mesh");
        return null;
    }
}
