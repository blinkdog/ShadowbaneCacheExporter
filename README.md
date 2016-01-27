# ShadowbaneCacheExporter
Export the resources from Shadowbane's .cache files

## Usage
Shadowbane's resources are ~830 MB compressed. After decompression
and exporting to standard file formats, they will be ~2 GB. Make
sure that your destination has enough space available.

Running the program is straightforward. Run the jar, supplying the
path to Shadowbane's cache directory and the path to where the
resources should be exported.

    java -jar ShadowbaneCacheExporter-X.Y.Z.jar /path/to/cache /path/to/output

## Resources
Shadowbane had 13 .cache files containing well over 100,000 resources.
The export format for each is listed below:

    CObjects.cache
    CZone.cache
    Dungeon.cache
    Mesh.cache
    Motion.cache
    Palette.cache
    Render.cache
    Skeleton.cache
    Sound.cache             =>      .wav File
    TerrainAlpha.cache
    Textures.cache          =>      .png File
    Tile.cache
    Visual.cache

I have tried to select file formats that are well-supported by browsers,
free/open source software (FOSS) editing tools, programming language
libraries, etc. If you think my choice of format was a poor one, then
please open an issue on GitHub so that we can discuss alternatives.

## Where do I get the Shadowbane .cache files?
Shadowbane was free-to-play (f2p) during the last part of its official run.
Ubisoft allowed anybody who wanted the client to download it free of charge.
As of this writing, a download mirror still exists at FilePlanet.com:
[Shadowbane Game Clients Downloads](http://www.fileplanet.com/95662/0/0/0/1/section/Game_Clients)

Alternatively, the original game DVDs are still sold on Amazon.com:
* [Shadowbane](http://www.amazon.com/dp/B000067FDX)
* [Shadowbane: The Rise of Chaos](http://www.amazon.com/dp/B0000DK54B)
* [Shadowbane: Throne of Oblivion](http://www.amazon.com/dp/B00068A0G0)

Note: The official Shadowbane servers were shut down a long time ago.
Don't buy the DVDs expecting to play the game; that won't happen.

Finally, there are two major Shadowbane server emulator projects
out there on the Internet.
* [Magicbane](http://www.magicbane.com/)
* [Shadowbane Emulator](http://shadowbaneemulator.com/)

Either or both of them may have links to Shadowbane clients, and/or
forums where one may inquire after such.

## Technical Documentation
First off: Thank you to [Steve Hoff](https://github.com/shoff) for
[ShadowbaneCacheViewer](https://github.com/shoff/ShadowbaneCacheViewer).
The code contained in that repository was a tremendous help in understanding
and decoding Shadowbane's .cache files.

### Cache Archive
A cache archive has the following rough format:

    Offset      Size        Description
    0           16          Cache Archive Header
    16          N*20        Cache Archive Directory
    N*20+16     ... EOF     Cache Resource Data

Where N is the number of resources contained in the archive.

#### .cache Archive Header
A cache archive header is 16 bytes long, and has the following
format:

    Offset      Size    Type    Description
    0           4       UI32    Number of resources in the .cache archive
    4           4       UI32    Offset to the start of resource data
    8           4       UI32    Total size of the .cache archive file
    12          4       ???     Unknown

#### .cache Archive Directory Entry
Each cache archive directory entry is 20 bytes long, and has
the following format:

    Offset      Size    Type    Description
    0           4       ???     Unknown
    4           4       UI32    ID (Note: NOT always unique!)
    8           4       UI32    Offset to beginning of resource data
    12          4       UI32    Total size of the resource (uncompressed)
    16          4       UI32    Size of the resource as stored (compressed)

Note 1: The offset to the beginning of the resource data is measured
relative to the beginning of the .cache archive file.

Note 2: If the uncompressed size matches the compressed size, then
the resource is NOT compressed, but rather stored as-is without any
compression whatsoever.

#### .cache Resource Entry
Each cache archive resource entry has a variable offset and length.
The corresponding entry in the cache directory has the offset to
the beginning of the data, the compressed length (the data as stored
in the .cache archive), and the uncompressed length of the data.

If the uncompressed size matches the compressed size, then the
resource is NOT compressed, but rather stored as-is without any
compression whatsoever.

If the resource data is compressed, it is with the Deflate algorithm.
For ShadowbaneCacheExporter, I use the [Inflater](http://docs.oracle.com/javase/7/docs/api/java/util/zip/Inflater.html)
class from the Java standard library to decompress the resource data.

The format of a particular kind of resource (Sound, Textures, etc.)
is documented in sub-sections given below.

### CObjects Resources
TODO: Technical documentation

### CZone Resources
TODO: Technical documentation

### Dungeon Resources
TODO: Technical documentation

### Mesh Resources
TODO: Technical documentation

### Motion Resources
TODO: Technical documentation

### Palette Resources
TODO: Technical documentation

### Render Resources
TODO: Technical documentation

### Skeleton Resources
TODO: Technical documentation

### Sound Resources
Each Sound resource entry has a variable length. There is a 16 byte
header followed by the actual sound data, in the following format:

    Offset      Size    Type    Description
    0           4       UI32    Length of sound data (in bytes)
    4           4       UI32    Bit rate (16000Hz or 22050Hz)
    8           4       UI32    Number of channels (1=Mono, 2=Stereo)
    12          4       UI32    Sample resolution (16=16-bit)
    16          N       U8      Sound data in WAVE format

Note 1: According to the documentation URL below, it appears WAVE format
uses unsigned bytes (0 to 255) when dealing with 8-bit samples and signed
shorts (-32768 to 32767) when dealing with 16-bit samples. All Shadowbane
samples are 16-bit, so the SoundResource class always sets signed = true.

See: http://www.blitter.com/~russtopia/MIDI/~jglatt/tech/wave.htm
        
### TerrainAlpha Resources
TODO: Technical documentation

### Textures Resources
TODO: Technical documentation

### Tile Resources
TODO: Technical documentation

### Visual Resources
TODO: Technical documentation

## Development
You can build the program with the following command:

    mvn clean install

You can then run the program from the project root directory with
the following command:

    java -jar target/ShadowbaneCacheExporter-X.Y.Z.jar /path/to/cache /path/to/output

## License
ShadowbaneCacheExporter 
Copyright 2016 Patrick Meade.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
