
package com.pmeade.shadowbane.sound;

import com.pmeade.shadowbane.CacheArchive;
import com.pmeade.shadowbane.CacheResource;
import java.io.File;

/**
 * SoundCache represents "Sound.cache", the cache archive containing
 * Sound resources. This class provides a method to export a sound
 * resource to a file.
 * @author veloxi
 */
public class SoundCache extends CacheArchive
{
    /**
     * Construct a SoundCache archive.
     * @param cacheDir the cache directory, where Sound.cache is located
     */
    public SoundCache(File cacheDir) {
        super(new File(cacheDir, "Sound.cache"));
        read();
    }

    /**
     * Determine the number of resources present in the sound cache.
     * @return the number of resources present in the sound cache
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
        File resourceDir = new File(outputDir, "Sound");
        resourceDir.mkdir();
        CacheResource resource = resources.get(index);
        loadResource(resource);
        SoundResource sound = new SoundResource(resource);
        sound.exportToWave(resourceDir);
    }
}
