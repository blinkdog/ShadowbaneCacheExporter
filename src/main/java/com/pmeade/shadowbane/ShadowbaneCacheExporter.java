/*
 * ShadowbaneCacheExporter.java
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

package com.pmeade.shadowbane;

import java.io.File;

/**
 * ShadowbaneCacheExporter is a command-line tool to export resources
 from Shadowbane .cache files.
 * @author pmeade
 */
public class ShadowbaneCacheExporter implements Runnable {
    /** Standard code for successful program termination. */
    public static final int EXIT_SUCCESS = 0;
    
    /** Standard code for program termination due to error. */
    public static final int EXIT_FAILURE = 1;

    /**
     * Entry point for ShadowbaneCacheExporter.
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        // if we weren't provided with enough arguments
        if(args.length < 2) {
            System.out.println("Usage: java -jar ShadowbaneCacheExporter-X.Y.Z.jar /path/to/cache /path/to/output");
            System.exit(EXIT_SUCCESS);
        }
        
        // create and check the provided cache directory
        File cacheDir = new File(args[0]);
        if(cacheDir.exists() == false) {
            System.err.println(String.format(
                "ShadowbaneCacheExporter: %s: No such file or directory",
                args[0]
            ));
            System.exit(EXIT_FAILURE);
        }
        if(cacheDir.isDirectory() == false) {
            System.err.println(String.format(
                "ShadowbaneCacheExporter: %s: Not a directory",
                args[0]
            ));
            System.exit(EXIT_FAILURE);
        }
        if(cacheDir.canRead() == false) {
            System.err.println(String.format(
                "ShadowbaneCacheExporter: cannot open directory %s: Permission denied",
                args[0]
            ));
            System.exit(EXIT_FAILURE);
        }

        // create and check the provided output directory
        File outputDir = new File(args[1]);
        if(outputDir.exists() == false) {
            System.err.println(String.format(
                "ShadowbaneCacheExporter: %s: No such file or directory",
                args[1]
            ));
            System.exit(EXIT_FAILURE);
        }
        if(outputDir.isDirectory() == false) {
            System.err.println(String.format(
                "ShadowbaneCacheExporter: %s: Not a directory",
                args[1]
            ));
            System.exit(EXIT_FAILURE);
        }
        if(outputDir.canWrite() == false) {
            System.err.println(String.format(
                "ShadowbaneCacheExporter: cannot write to directory %s: Permission denied",
                args[1]
            ));
            System.exit(EXIT_FAILURE);
        }
        
        // create and run the ShadowbaneCacheExporter
        ShadowbaneCacheExporter sce = new ShadowbaneCacheExporter();
        sce.setCache(cacheDir);
        sce.setOutput(outputDir);
        sce.run();
    }

    @Override
    public final void run() {
        System.out.println(String.format(
            "I will examine %s and output resources to %s",
            cache.getAbsolutePath(), output.getAbsolutePath()
        ));
    }

    /**
     * Set the File object representing Shadowbane's cache directory.
     * This is the directory that contains the .cache resource files.
     * @param cacheDir File object representing Shadowbane's cache directory
     */
    public final void setCache(final File cacheDir) {
        this.cache = cacheDir;
    }

    /**
     * Set the File object representing the output directory. This is the
     * directory where the Shadowbane resources will be exported.
     * @param outputDir File object representing the output directory
     */
    public final void setOutput(final File outputDir) {
        this.output = outputDir;
    }
    
    /**
     * File object representing Shadowbane's cache directory. This
     * is the directory that contains the .cache resource files.
     */
    private File cache;

    /**
     * File object representing the output directory. This is the
     * directory where the Shadowbane resources will be exported.
     */
    private File output;
}
