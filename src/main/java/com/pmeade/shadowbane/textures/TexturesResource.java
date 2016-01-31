/*
 * TexturesResource.java
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

package com.pmeade.shadowbane.textures;

import com.pmeade.shadowbane.CacheResource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;

/**
 * TexturesResource represents a texture resource. Texture resources are
 * typically ARGB, RGB, or grayscale images of varying sizes.
 * @author pmeade
 */
public class TexturesResource
{
    public static final int[] IMAGE_TYPE = {
        BufferedImage.TYPE_CUSTOM,      // 0
        BufferedImage.TYPE_BYTE_GRAY,   // 1
        BufferedImage.TYPE_CUSTOM,      // 2
        BufferedImage.TYPE_INT_RGB,     // 3
        BufferedImage.TYPE_INT_ARGB     // 4
    };
    
    /**
     * Decorate a CacheResource as a TexturesResource.
     * @param resource CacheResource to be decorated
     */
    public TexturesResource(CacheResource resource) {
        this.resource = resource;
    }

    /**
     * Read the header and data of the Textures resource. This method is
     * called to populate the fields of the TextureResource.
     */
    public void read() {
        ByteBuffer buffer = ByteBuffer.wrap(resource.data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        IntBuffer ib = buffer.asIntBuffer();
        
        width = ib.get();
        height = ib.get();
        depth = ib.get();

        byte[] data = new byte[width*height*depth];
        buffer.position(26);
        buffer.get(data);
        
        image = new BufferedImage(width, height, IMAGE_TYPE[depth]);
        
        int dataIndex = 0;
        for(int y=0; y<height; y++) {
            for(int x=0; x<width; x++) {
                int a,b,d,g,r,rgba;
                switch(depth)
                {
                    case 1:
                        g = data[dataIndex]; dataIndex++;
                        rgba = (0xff000000)
                             | ((g & 0xff) << 16)
                             | ((g & 0xff) <<  8)
                             | (g & 0xff);
                        image.setRGB(x, (height-1)-y, rgba);
                        break; 
                    case 3:
                        r = data[dataIndex]; dataIndex++;
                        g = data[dataIndex]; dataIndex++;
                        b = data[dataIndex]; dataIndex++;
                        rgba = (0xff000000)
                             | ((r & 0xff) << 16)
                             | ((g & 0xff) <<  8)
                             | (b & 0xff);
                        image.setRGB(x, (height-1)-y, rgba);
                        break;
                    case 4:
                        r = data[dataIndex]; dataIndex++;
                        g = data[dataIndex]; dataIndex++;
                        b = data[dataIndex]; dataIndex++;
                        a = data[dataIndex]; dataIndex++;
                        rgba = ((a & 0xff) << 24)
                             | ((r & 0xff) << 16)
                             | ((g & 0xff) <<  8)
                             | (b & 0xff);
                        image.setRGB(x, (height-1)-y, rgba);
                        break;
                }
            }
        }
        
        if(dataIndex != data.length) {
            System.err.println("BAD MOJO: dataIndex:" + dataIndex + " vs. data.length:" + data.length);
        }
    }

    /**
     * Export the Textures resource to a PNG file.
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
                    String.format("Texture_%04d_%07d.png",
                        resource.index, resource.id
            )));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * CacheResource to be decorated as a TexturesResource.
     */
    private final CacheResource resource;

    /**
     * Width of the texture resource, in pixels.
     */
    private int width;
    
    /**
     * Height of the texture resource, in pixels.
     */
    private int height;
    
    /**
     * Depth (bytes per pixel) of the texture resource.
     * 1 = grayscale bytes: [GG]             -> 0xFFGGGGGG
     * 3 = RGB       bytes: [RR][GG][BB]     -> 0xFFRRGGBB
     * 4 = RGBA      bytes: [RR][GG][BB][AA] -> 0xAARRGGBB
     */
    private int depth;
    
    /**
     * The actual pixel data.
     */
    private BufferedImage image;
}
