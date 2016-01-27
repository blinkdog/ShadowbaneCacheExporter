/*
 * SoundResource.java
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

package com.pmeade.shadowbane.sound;

import com.pmeade.shadowbane.CacheResource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * SoundResource represents a sound resource. Sound resources are
 * typically RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit,
 * mono 22050 Hz files, although there are some exceptions.
 * @see https://docs.oracle.com/javase/tutorial/sound/converters.html#114640
 * @author pmeade
 */
public class SoundResource
{
    /**
     * Decorate a CacheResource as a SoundResource.
     * @param resource CacheResource to be decorated
     */
    public SoundResource(CacheResource resource) {
        this.resource = resource;
    }

    /**
     * Read the header and data of the Sound resource. This method is called
     * to populate the fields of the SoundResource.
     */
    public void read() {
        ByteBuffer buffer = ByteBuffer.wrap(resource.data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        IntBuffer ib = buffer.asIntBuffer();
        
        dataLength = ib.get();
        bitRate = ib.get();
        numChannels = ib.get();
        resolution = ib.get();
        
        if(dataLength != (resource.data.length-16)) {
            System.err.println("BAD MOJO: dataLength:" + dataLength + " vs. resource.data.length:" + (resource.data.length-16));
        }
        
        data = new byte[dataLength];
        buffer.position(16);
        buffer.get(data);
    }

    /**
     * Export the Sound resource to a WAVE file.
     * @param outputDir directory where resource output goes
     */
    public void exportToWave(File outputDir) {
        this.read();
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        
        AudioFormat audioFormat = new AudioFormat(
            (float)bitRate, // float sampleRate,
            resolution,     // int sampleSizeInBits,
            numChannels,    // int channels,
            true,           // boolean signed,
            false           // boolean bigEndian
        );        
        
        AudioInputStream audioStream = new AudioInputStream(stream, audioFormat, dataLength);
        
        try {
            AudioSystem.write(
                audioStream,
                AudioFileFormat.Type.WAVE,
                new File(
                    outputDir,
                    String.format("Sound_%04d_%04d.wav",
                        resource.index, resource.id
            )));
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * CacheResource to be decorated as a SoundResource.
     */
    private final CacheResource resource;

    /**
     * Length of the sound data (in bytes).
     */
    private int dataLength;
    
    /**
     * Bit rate of the sound data. 16000 Hz and 22050 Hz are common.
     */
    private int bitRate;
    
    /**
     * Number of sound channels.
     * 1 = Mono
     * 2 = Stereo
     */
    private int numChannels;
    
    /**
     * Resolution (sample bit size) of the sound data. Usually 16-bit.
     */
    private int resolution;
    
    /**
     * The actual bytes of sound data.
     */
    private byte[] data;
}
