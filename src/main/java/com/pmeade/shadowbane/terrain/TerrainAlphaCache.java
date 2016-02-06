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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static com.pmeade.shadowbane.terrain.TerrainMapSpec.COLUMN_MAJOR;
import static com.pmeade.shadowbane.util.Data.UB;

/**
 * TerrainAlphaCache represents "TerrainAlpha.cache", the cache archive
 * containing TerrainAlpha resources. This class provides a method to
 * export a terrain resource to a file.
 * @author pmeade
 */
public class TerrainAlphaCache extends CacheArchive
{
    /**
     * Specification for terrain maps made up of TerrainAlpha resources.
     * These specifications might be somewhere in the Shadowbane files,
     * but I haven't found them yet. So, for now, we'll use our best guess
     * to say which TerrainAlpha resources go together to make up a map
     */
    public static TerrainMapSpec[] MAPS = new TerrainMapSpec[] {
        new TerrainMapSpec(    0, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec(  196, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec(  392, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec(  588, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec(  784, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec(  980, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 1176, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 1372, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 1568, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 1764, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 1960, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 2156, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 2352, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 2548, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 2744, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 2940, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec( 3136, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 3392, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 3648, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 3904, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 4160, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 4416, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 4672, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 4928, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 5184, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 5440, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 5696, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 5952, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 6208, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 6464, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 6720, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 6976, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 7232, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 7488, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 7744, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 8000, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 8256, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 8512, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 8768, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 9024, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 9280, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 9536, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec( 9792, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(10048, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(10304, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(10560, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(10816, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(11072, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(11382, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(11584, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(11840, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(12096, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(12352, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(12608, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(12864, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(13120, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(13376, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(13632, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(13888, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(14144, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(14400, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(14656, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(14912, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(15168, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(15424, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(15680, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(15936, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(16192, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(16448, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(16704, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(16960, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(17216, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(17472, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(17728, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(17984, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(18240, 1, 1, COLUMN_MAJOR),
        new TerrainMapSpec(18241, 1, 1, COLUMN_MAJOR),
        new TerrainMapSpec(18242, 1, 1, COLUMN_MAJOR),
        new TerrainMapSpec(18243, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18307, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18371, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18435, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18499, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18563, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18627, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18691, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18755, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18819, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18883, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(18947, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(19011, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(19075, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(19139, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(19395, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(19651, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(19907, 16, 16, COLUMN_MAJOR),
        new TerrainMapSpec(20163, 3, 3, COLUMN_MAJOR),
        new TerrainMapSpec(20172, 3, 3, COLUMN_MAJOR),
        new TerrainMapSpec(20181, 3, 3, COLUMN_MAJOR),
        new TerrainMapSpec(20190, 1, 1, COLUMN_MAJOR),
        new TerrainMapSpec(20191, 3, 3, COLUMN_MAJOR),
        new TerrainMapSpec(20200, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec(20396, 8, 8, COLUMN_MAJOR),
        new TerrainMapSpec(20460, 14, 14, COLUMN_MAJOR),
        new TerrainMapSpec(20656, 16, 16, COLUMN_MAJOR)
    };

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

    /**
     * Export all of the TerrainAlpha resources to terrain map images.
     * @param outputDir the output directory
     */
    public void exportTerrainMaps(File outputDir) {
        File resourceDir = new File(outputDir, "TerrainAlpha");
        resourceDir.mkdir();
        for(TerrainMapSpec map : MAPS) {
            exportTerrainMap(resourceDir, map);
        }
    }

    /**
     * Export a PNG map for the provided TerrainMapSpec to the provided output
     * directory.
     * @param outputDir File representing the output directory
     * @param map TerrainMapSpec specifying the map to be exported
     */
    private void exportTerrainMap(File outputDir, TerrainMapSpec map) {
        // do some initial calculations on the map
        int mapWidth = map.width * TerrainAlphaResource.WIDTH;
        int mapHeight = map.height * TerrainAlphaResource.HEIGHT;
        BufferedImage image = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_BYTE_GRAY);

        // if the map has COLUMN_MAJOR ordering (pro tip: they all do)
        if(map.ordering == COLUMN_MAJOR) {
            // then for each TerrainAlpha resource that makes up the map
            for(int i=0; i<map.width; i++) {
                for(int j=0; j<map.height; j++) {
                    // load the resource from the cache
                    CacheResource resource = resources.get(map.startIndex + i*map.height + j);
                    loadResource(resource);

                    // then start putting the pixel data onto our big map
                    int dataIndex = 26;
                    for(int y=0; y<TerrainAlphaResource.HEIGHT; y++) {
                        for(int x=0; x<TerrainAlphaResource.WIDTH; x++) {
                            int g = UB(resource.data[dataIndex]); dataIndex++;
                            int argb = (0xff000000) // a
                                     | (g << 16)    // r
                                     | (g <<  8)    // g
                                     | (g <<  0);   // b
                            int pi = i;
                            int pj = (map.height-1)-j;
                            int px = x;
                            int py = (TerrainAlphaResource.HEIGHT-1)-y;
                            image.setRGB(
                                TerrainAlphaResource.WIDTH*pi+px,  // x
                                TerrainAlphaResource.HEIGHT*pj+py, // y
                                argb);                             // argb
                        }
                    }
                }
            }

            // we've put all the TerrainAlpha blocks into the terrain map
            // so now it's time to write the image out to a PNG file
            try {
                ImageIO.write(
                    image,
                    "png",
                    new File(
                        outputDir,
                        String.format("TerrainMap_%05d.png",
                            map.startIndex
                )));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("BAD MOJO: map.ordering:" + map.ordering + " vs. " + COLUMN_MAJOR);
        }
    }
}
