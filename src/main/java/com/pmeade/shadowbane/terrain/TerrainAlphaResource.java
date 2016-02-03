/*
 * TerrainAlphaResource.java
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

import com.pmeade.shadowbane.CacheResource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.imageio.ImageIO;

import static com.pmeade.shadowbane.util.Data.UB;

/**
 * TerrainAlphaResource represents a terrain resource. Terrain resources
 * are exported as grayscale images of size 128x128.
 * @author pmeade
 */
public class TerrainAlphaResource
{
    /**
     * Decorate a CacheResource as a TerrainAlphaResource.
     * @param resource CacheResource to be decorated
     */
    public TerrainAlphaResource(CacheResource resource) {
        this.resource = resource;
    }

    /**
     * Read the header and data of the TerrainAlpha resource. This method is
     * called to populate the fields of the TerrainResource.
     */
    public void read() {
        ByteBuffer buffer = ByteBuffer.wrap(resource.data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        
        width = buffer.getInt(0);
        if(width != 0x80) {
            System.err.println("BAD MOJO: width:" + width + " vs. 128");
        }
        
        height = buffer.getInt(4);
        if(height != 0x80) {
            System.err.println("BAD MOJO: height:" + height + " vs. 128");
        }

        unk3 = buffer.getInt(8);
        if(unk3 != 1) {
            System.err.println("BAD MOJO: unk3:" + unk3 + " vs. 1");
        }
        
        unk4 = buffer.getInt(12);
        if(unk4 != 1) {
            System.err.println("BAD MOJO: unk4:" + unk4 + " vs. 1");
        }
        
        unk5 = buffer.getInt(16);
        if(unk5 != 0) {
            System.err.println("BAD MOJO: unk5:" + unk5 + " vs. 0");
        }
        
        flag1 = buffer.get(20);
        if(flag1 != 1) {
            System.err.println("BAD MOJO: flag1:" + flag1 + " vs. 1");
        }
        
        flag2 = buffer.get(21);
        if(flag2 != 1) {
            System.err.println("BAD MOJO: flag2:" + flag2 + " vs. 1");
        }
        
        length = buffer.getInt(22);
        if(length != 0x4000) {
            System.err.println("BAD MOJO: length:" + length + " vs. 16384");
        }
        
        byte[] data = new byte[width*height];
        buffer.position(26);
        buffer.get(data);
        
        image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        
        int dataIndex = 0;
        for(int y=0; y<height; y++) {
            for(int x=0; x<width; x++) {
                int g = UB(data[dataIndex]); dataIndex++;
                int rgba = (0xff000000)
                         | (g << 16)
                         | (g <<  8)
                         | (g <<  0);
                image.setRGB(x, y, rgba);
            }
        }
        
        if(dataIndex != data.length) {
            System.err.println("BAD MOJO: dataIndex:" + dataIndex + " vs. data.length:" + data.length);
        }
        
        if(buffer.position() != resource.data.length) {
            System.err.println("BAD MOJO: buffer.position():" + buffer.position() + " vs. resource.data.length:" + resource.data.length);
        }
    }

    /**
     * Export the TerrainAlpha resource to a PNG file.
     * @param outputDir directory where resource output goes
     */
    public void exportToPng(File outputDir) {
        this.read();
        try {
            ImageIO.write(
                image,
                "png",
                new File(
                    outputDir,
                    String.format("TerrainAlpha_%05d_%010d.png",
                        resource.index, resource.id
            )));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * CacheResource to be decorated as a TerrainAlphaResource.
     */
    private final CacheResource resource;

    //
    // Note: I'm not going to provide JavaDoc for these guys yet. This
    //       class is still a work-in-progress. These names are my best
    //       guess of my own observations of the TerrainAlpha.cache
    //       resource data.
    //
    
    private int width;
    private int height;
    private int unk3;
    private int unk4;
    private int unk5;
    private int flag1;
    private int flag2;
    private int length;
    
    /**
     * The TerrainAlpha resource data visualized as pixel data.
     */
    private BufferedImage image;
}
