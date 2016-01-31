/*
 * MeshResource.java
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

import com.pmeade.shadowbane.CacheResource;
import com.pmeade.shadowbane.type.TriFace;
import com.pmeade.shadowbane.type.Vector2;
import com.pmeade.shadowbane.type.Vector3;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import static com.pmeade.shadowbane.type.TriFace.getTriFace;
import static com.pmeade.shadowbane.type.Vector2.getVector2;
import static com.pmeade.shadowbane.type.Vector3.getVector3;
import static com.pmeade.shadowbane.util.Data.UI;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * MeshResource represents a mesh resource. A mesh is 3D data for a
 * shape made of corners (vertices) and faces (triangles). There are
 * many proprietary 3D editors, and twice as many 3D formats.
 * 
 * This class will export the mesh resource it represents as a python
 * script that can be run in Blender. The script creates the vertices
 * and faces as a mesh in Blender.
 * 
 * This class is still a work in progress, as the format of a Mesh.cache
 * resource is not yet fully understood.
 * @author pmeade
 */
public class MeshResource
{
// join() polyfill; free to a good home
//    public static String join(String[] array, String joiner) {
//        StringBuilder sb = new StringBuilder();
//        for(int i=0; i<array.length-1; i++) {
//            sb.append(array[i]);
//            sb.append(joiner);
//        }
//        if(array.length > 0) {
//            sb.append(array[array.length-1]);
//        }
//        return sb.toString();
//    }
    
    /**
     * Python code added before the polygon mesh data. It imports the
     * necessary libraries and objects and defines a function to transform
     * our polygon data into a Blender mesh.
     * 
     * Note: The python code is lifted from the Blender wiki.
     * @see http://wiki.blender.org/index.php/Dev:Py/Scripts/Cookbook/Code_snippets/Three_ways_to_create_objects
     */
    public static final String[] PYTHON_HEADER = new String[] {
        "", 
        "import bpy",
        "import mathutils",
        "from mathutils import Vector",
        "", 
        "def createMeshFromData(name, origin, verts, faces):",
        "    # Create mesh and object",
        "    me = bpy.data.meshes.new(name+'Mesh')",
        "    ob = bpy.data.objects.new(name, me)",
        "    ob.location = origin",
        "    ob.show_name = True",
        "", 
        "    # Link object to scene and make active",
        "    scn = bpy.context.scene",
        "    scn.objects.link(ob)",
        "    scn.objects.active = ob",
        "    ob.select = True",
        "", 
        "    # Create mesh from given verts, faces.",
        "    me.from_pydata(verts, [], faces)",
        "    # Update mesh with new data",
        "    me.update()    ",
        "    return ob",
        "",
        "def run(origo):",
        "    origin = Vector(origo)",
    };
   
    /**
     * Python code added after the polygon mesh data. It finishes the
     * function necessary to transform our polygon data into a Blender mesh.
     * It also includes a statement to run the function, when the script
     * is being executed in Blender.
     * 
     * Note: The python code is lifted from the Blender wiki.
     * @see http://wiki.blender.org/index.php/Dev:Py/Scripts/Cookbook/Code_snippets/Three_ways_to_create_objects
     */
    public static final String[] PYTHON_FOOTER = new String[] {
        "",
        "    sbMesh = createMeshFromData('SBMesh', origin, verts, faces)",
        "    return",
        "",
        "if __name__ == \"__main__\":",
        "    run((0,0,0))",
        ""
    };
    
    /**
     * Decorate a CacheResource as a MeshResource.
     * @param resource CacheResource to be decorated
     */
    public MeshResource(CacheResource resource) {
        this.resource = resource;
        this.vertices = new ArrayList();
        this.normals = new ArrayList();
        this.uvs = new ArrayList();
        this.triFaces = new ArrayList();
    }

    /**
     * Read the header and data of the Mesh resource. This method is called
     * to populate the fields of the MeshResource.
     */
    public void read() {
        ByteBuffer buffer = ByteBuffer.wrap(resource.data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        //System.out.println("buffer.position[start] = " + buffer.position());
        null1 = UI(buffer.getInt());
        unixUpdatedTimeStamp = UI(buffer.getInt());
        unk3 = UI(buffer.getInt());
        unixCreatedTimeStamp = UI(buffer.getInt());
        unk5 = UI(buffer.getInt());
        min = getVector3(buffer);
        max = getVector3(buffer);
        flag1 = buffer.get();
        flag2 = buffer.get();

        if(null1 != 0) {
            System.err.println("BAD MOJO: null1:" + null1 + " vs. 0");
        }
        if((flag1 != 0) && (flag1 != 1)) {
            System.err.println("BAD MOJO: flag1:" + flag1 + " vs. 0 or 1");
        }
        if((flag2 != 0) && (flag2 != 1)) {
            System.err.println("BAD MOJO: flag2:" + flag2 + " vs. 0 or 1");
        }

        //System.out.println("buffer.position[vertices] = " + buffer.position());
        numVertices = buffer.getInt();
        for(int i=0; i<numVertices; i++) {
            vertices.add(getVector3(buffer));
        }
        
        //System.out.println("buffer.position[normals] = " + buffer.position());
        numNormals = buffer.getInt();
        for(int i=0; i<numNormals; i++) {
            normals.add(getVector3(buffer));
        }

        if(numVertices != numNormals) {
            System.err.println("BAD MOJO: numVertices:" + numVertices + " vs. numNormals:" + numNormals);
        }

        //System.out.println("buffer.position[UVs] = " + buffer.position());
        numUV = buffer.getInt();
        for(int i=0; i<numUV; i++) {
            uvs.add(getVector2(buffer));
        }

        if(numUV != numNormals) {
            System.err.println("BAD MOJO: numUV:" + numUV + " vs. numNormals:" + numNormals);
        }

        //System.out.println("buffer.position[TriFaces] = " + buffer.position() + " [0x" + Integer.toHexString(buffer.position()) + "]");
        if(flag2 == 0) {
            numTriFaces = buffer.getInt();
            if(numTriFaces % 3 == 0) {
                numTriFaces /= 3;
                for(int i=0; i<numTriFaces; i++) {
                    TriFace triFace = getTriFace(buffer);
                    if(   (triFace.v1 >= numVertices)
                       || (triFace.v2 >= numVertices)
                       || (triFace.v3 >= numVertices))
                    {
                        System.err.println("BAD MOJO: TriFace " + i);
                        System.err.println("BAD MOJO: triFace.v1:" + triFace.v1 + " vs. numVertices:" + numVertices);
                        System.err.println("BAD MOJO: triFace.v2:" + triFace.v2 + " vs. numVertices:" + numVertices);
                        System.err.println("BAD MOJO: triFace.v3:" + triFace.v3 + " vs. numVertices:" + numVertices);
                        triFaces.clear();
                        break;
                    }
                    triFaces.add(triFace);
                }
            } else {
//                System.err.println("BAD MOJO: [" + resource.index + "] numTriFaces:" + numTriFaces + " vs. % 3:" + (numTriFaces % 3));
//                System.err.println("\\__ buffer.position[TriFaces-Data] = " + buffer.position() + " [0x" + Integer.toHexString(buffer.position()) + "]");
            }
        }
    }

    /**
     * Export the Mesh resource to a Blender Python (.py) file.
     * @param outputDir directory where resource output goes
     */
    public void exportToBlenderPython(File outputDir) {
        this.read();
        if(triFaces.isEmpty()) { return; }
        
        String scriptName = String.format(
            "Mesh_%05d_%08d.py",
            resource.index, resource.id
        );

        File scriptFile = new File(outputDir, scriptName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(scriptFile);
        } catch (FileNotFoundException e) {
            return;
        }
        
        PrintStream ps = new PrintStream(fos);
        
        ps.println("#-----------------------------------------------------------------------");
        ps.println("# File: " + scriptName);
        ps.println("#-----------------------------------------------------------------------");
        ps.println("# Contains polygon mesh data from Shadowbane, published by Ubisoft, Inc.");
        ps.println("# Shadowbane is Copyright 2003-2004 Wolfpack Studios, Inc.");
        ps.println("#-----------------------------------------------------------------------");
        ps.println(join(PYTHON_HEADER, "\n"));
        
        String[] vertStr = new String[vertices.size()];
        for(int i=0; i<vertices.size(); i++) {
            Vector3 v3 = vertices.get(i);
            vertStr[i] = v3.toPython();
        }
        ps.print("    verts = (");
        ps.print(join(vertStr, ","));
        ps.println("    )");
        
        String[] faceStr = new String[triFaces.size()];
        for(int i=0; i<triFaces.size(); i++) {
            TriFace triFace = triFaces.get(i);
            faceStr[i] = triFace.toPython();
        }
        ps.print("    faces = (");
        ps.print(join(faceStr, ","));
        ps.println("    )");
        
        ps.println(join(PYTHON_FOOTER, "\n"));
        ps.flush();
        ps.close();
    }
    
    /**
     * CacheResource to be decorated as a MeshResource.
     */
    private final CacheResource resource;

    //
    // Note: I'm not going to provide JavaDoc for these guys yet. This
    //       class is still a work-in-progress. These names are my best
    //       guess between Steve Hoff's ShadowbaneCacheViewer and my own
    //       observations of the Mesh.cache resource data.
    //
    
    private long null1;
    private long unixUpdatedTimeStamp;
    private long unk3;
    private long unixCreatedTimeStamp;
    private long unk5;
    private Vector3 min;
    private Vector3 max;
    private int flag1;
    private int flag2;
    
    private int numVertices;
    private final List<Vector3> vertices;
    
    private int numNormals;
    private final List<Vector3> normals;

    private int numUV;
    private final List<Vector2> uvs;
    
    private int numTriFaces;
    private final List<TriFace> triFaces;
}
