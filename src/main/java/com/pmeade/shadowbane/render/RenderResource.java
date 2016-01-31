/*
 * RenderResource.java
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

import com.pmeade.shadowbane.CacheResource;
import com.pmeade.shadowbane.mesh.MeshCache;
import com.pmeade.shadowbane.mesh.MeshResource;
import com.pmeade.shadowbane.type.Vector3;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.pmeade.shadowbane.type.Vector3.getVector3;
import static com.pmeade.shadowbane.util.Data.UI;

/**
 * RenderResource represents a render resource. Render resources are
 * still a little bit unknown to me. It seems to be data for representing
 * (rendering) a 3D model in the game. Most of the information here was
 * learned from Steve Hoff's ShadowbaneCacheViewer, and it is still a
 * work-in-progress.
 * @author pmeade
 */
public class RenderResource
{
    /**
     * Decorate a CacheResource as a RenderResource.
     * @param resource CacheResource to be decorated
     * @param meshCache MeshCache providing Mesh resources
     */
    public RenderResource(CacheResource resource, MeshCache meshCache) {
        this.resource = resource;
        this.meshCache = meshCache;
    }

    /**
     * Read the header and data of the Render resource. This method is called
     * to populate the fields of the RenderResource.
     */
    public void read() {
        ByteBuffer buffer = ByteBuffer.wrap(resource.data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        hasMesh = buffer.getInt(35);
        if (hasMesh == 1) {
            readMesh(buffer);
        }

        scale = getVector3(buffer);
        unknown = buffer.getInt();

        position = getVector3(buffer);

        childCount = buffer.getInt();

        if(childCount < 1000) {
            childIds = new long[childCount];
            for(int i=0; i<childIds.length; i++) {
                int null1 = buffer.getInt();
                if(null1 != 0) {
                    System.err.println("BAD MOJO: read.null1:" + null1 + " vs. 0");
                }
                childIds[i] = UI(buffer.getInt());
            }
        } else {
            //System.err.println("BAD MOJO: childCount:" + childCount);
        }

        // TODO: TO BE CONTINUED...
    }

    /**
     * Helper method to read a Mesh resource reference from the provided
     * ByteBuffer. Note this is still a work-in-progress and so doesn't
     * work as well as it should.
     * @param buffer ByteBuffer from which to read the Mesh resource reference
     */
    private void readMesh(ByteBuffer buffer) {
        int null1 = buffer.getInt();
        if(null1 != 0) {
            System.err.println("BAD MOJO: readMesh.null1:" + null1 + " vs. 0");
        }

        meshId = UI(buffer.getInt());

        if(meshId == 0) {
            //System.err.println("BAD MOJO: meshId:" + meshId + " is 0");
            return;
        }

        mesh = meshCache.findById(meshId);

        int null2 = buffer.getShort();
        if(null2 != 0) {
            System.err.println("BAD MOJO: readMesh.null2:" + null2 + " vs. 0");
        }

        int size = buffer.getInt();
        StringBuilder jointNameBuilder = new StringBuilder();
        boolean finished = false;
        while(!finished) {
            char c = buffer.getChar();
            if(c != '\0') {
                jointNameBuilder.append(c);
            } else {
                finished = true;
            }
        }
        jointName = jointNameBuilder.toString();
        if(jointName.length() != size) {
            System.err.println("BAD MOJO: jointName.length():" + jointName.length() + " vs. size:" + size);
        }
    }

    /**
     * Export the Render resource to a JSON file. Note that this is still
     * a work-in-progress, so it doesn't actually export data to a file yet.
     * @param outputDir directory where resource output goes
     */
    public void exportToJson(File outputDir) {
        this.read();
//        if(jointName != null && jointName.isEmpty() == false)
        if(childCount > 0)
        {
            System.out.println("Render Index:" + resource.index + " - " + jointName + "(" + childCount + " children)");
        }
    }

    /**
     * CacheResource to be decorated as a RenderResource.
     */
    private final CacheResource resource;

    /**
     * MeshCache to provide MeshResources referenced by the RenderResource.
     */
    private final MeshCache meshCache;

    //
    // Note: I'm not going to provide JavaDoc for these guys yet. This
    //       class is still a work-in-progress. These names are my best
    //       guess between Steve Hoff's ShadowbaneCacheViewer and my own
    //       observations of the Render.cache resource data.
    //

    private int hasMesh;

    private long meshId;

    private MeshResource mesh;

    private String jointName;

    private Vector3 scale;
    private int unknown;

    private Vector3 position;

    private int childCount;
    private long[] childIds;
}
