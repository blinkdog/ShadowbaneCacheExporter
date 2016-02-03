/*
 * MeshAfter.java
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

package com.pmeade.shadowbane.type;

import java.nio.ByteBuffer;

/**
 * MeshAfter represents some extra data at the end of a Mesh resource. The
 * structure seems clear, but the content of the data (i.e.: what does this
 * data represent? what does this data mean?) is unknown.
 * @author pmeade
 */
public class MeshAfter
{
    /**
     * Read a MeshAfter from the current position of the provided ByteBuffer.
     * @param buffer ByteBuffer from which to read the MeshAfter data
     * @return MeshAfter as read from the buffer
     */
    public static MeshAfter getMeshAfter(ByteBuffer buffer) {
        MeshAfter meshAfter = new MeshAfter();
        meshAfter.u1 = buffer.getInt();
        meshAfter.u2 = buffer.getInt();
        meshAfter.shortCount = buffer.getInt();
        meshAfter.shorts = new short[meshAfter.shortCount];
        for(int i=0; i<meshAfter.shortCount; i++) {
            meshAfter.shorts[i] = buffer.getShort();
        }
        return meshAfter;
    }

    //
    // Note: I'm not going to provide JavaDoc for these guys yet. This
    //       class is still a work-in-progress. These names are my best
    //       guess given my observations of the Mesh.cache resource data.
    //
    
    public int u1;
    public int u2;
    public int shortCount;
    public short[] shorts;
}
