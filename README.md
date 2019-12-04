# ShadowbaneCacheExporter
Export the resources from Shadowbane's .cache files

## Usage
Shadowbane's resources are ~830 MB compressed. After decompression
and exporting to standard file formats, they will be ~2 GB. Make
sure that your destination has enough space available.

Running the program is straightforward. Run the jar, supplying the
path to Shadowbane's cache directory and the path to where the
resources should be exported.

    java -jar target/ShadowbaneCacheExporter-X.Y.Z.jar /path/to/cache /path/to/output

## Resources
Shadowbane has 13 .cache files containing well over 100,000 resources.

### Export formats
The export format and count for each resource type is listed below:

    CObjects.cache
    CZone.cache
    Dungeon.cache
    Mesh.cache              =>      .py File [Blender Python] (24387)
    Motion.cache
    Palette.cache
    Render.cache
    Skeleton.cache
    Sound.cache             =>      .wav File (1086)
    TerrainAlpha.cache      =>      .png File (20912)
    Textures.cache          =>      .png File (9680)
    Tile.cache
    Visual.cache

I have tried to select file formats that are well-supported by browsers,
free/open source software (FOSS) editing tools, programming language
libraries, etc. If you think my choice of format was a poor one, then
please open an issue on GitHub so that we can discuss alternatives.

### Export status
ShadowbaneCacheExporter is a work in progress. Most of the work involves
decoding the packing format of the resource, so that it can be exported
to a more common/useful format.

* CObjects.cache - 0%
* CZone.cache - 0%
* Dungeon.cache - 0%
* Mesh.cache - 100% (mesh data not fully understood)
* Motion.cache - 0%
* Palette.cache - 0%
* Render.cache - 0% (some code, but nothing usable)
* Skeleton.cache
* Sound.cache - 100%
* TerrainAlpha.cache - 100% (but is .png the right format?)
* Textures.cache - 100%
* Tile.cache - 0%
* Visual.cache - 0%

### Interesting exports
Some resources contain easter eggs (messages, photographs, etc.) that
weren't visible during Shadowbane game play. I've submitted some of
my more interesting finds to [The Cutting Room Floor](https://tcrf.net/Shadowbane).
Perhaps you'll discover an easter egg in the Shadowbane resources?

## Where do I get the Shadowbane .cache files?
Shadowbane was free-to-play (f2p) during the last part of its official run.
Ubisoft allowed anybody who wanted the client to download it free of charge.
As of this writing, a download mirror still exists at FilePlanet.com:
[Shadowbane Free Game](https://www.fileplanet.com/archive/p-62669/Shadowbane-Free-Game-Win32)

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
    0           4       U32     Number of resources in the .cache archive
    4           4       U32     Offset to the start of resource data
    8           4       U32     Total size of the .cache archive file
    12          4       ???     Unknown

#### .cache Archive Directory Entry
Each cache archive directory entry is 20 bytes long, and has
the following format:

    Offset      Size    Type    Description
    0           4       ???     Unknown
    4           4       U32     ID (Note: NOT always unique!)
    8           4       U32     Offset to beginning of resource data
    12          4       U32     Total size of the resource (uncompressed)
    16          4       U32     Size of the resource as stored (compressed)

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
This documentation is a work in progress. All of the Mesh resource
format is understood, but not the meaning of all the data sections.

Mesh resources have a variable length. There is a 46 byte header
followed by the actual mesh data, in the following format:

    Offset      Size    Type    Description
    0           46      Header  Mesh resource header
    46          N       Data    Mesh resource data

#### Mesh resource Header
A Mesh resource header is 46 bytes long, and has the following
format:

    Offset      Size    Type    Description
    0           4       U32     null1 - Always 0
    4           4       U32     Modification Time (unix timestamp)
    8           4       U32     unk3 - Unknown Field #3
    12          4       U32     Creation Time (unix timestamp)
    16          4       U32     unk5 - Unknown Field #5
    20          12      V3      Bounding Box: minimum corner
    32          12      V3      Bounding Box: maximum corner
    44          1       U8      Mesh Flag 1
    45          1       U8      Mesh Flag 2

Note 1: V3 is Vector3 format. This is a tuple of 3 float values: x, y, and z.
Each float is 4 bytes, so 4*3 = 12 bytes total for a Vector3.

Note 2: Mesh flags are described in the subsection below.

##### Mesh Header Flags
The mesh flags fields seems to contain some metadata about the Mesh
resource. Observed values for each flag are 0x00 (0) and 0x01 (1).

Flag 1: Meaning Unknown
* 0x00: Meaning Unknown
* 0x01: Meaning Unknown

Flag 2: Mesh includes extra data
* 0x00: Mesh does not contain Extras
* 0x01: Mesh contains Extras

#### Mesh resource data
After the Mesh resource header, starting at Offset 46, begins the
Mesh data. The structure of the Mesh data seems to be understood;
how many sections, the kind and count of data in each section, etc.
However, the MEANING of the data in each section is not yet fully
understood.

The oddball section is the "Extras Data". This section appears
(and only appears) when Mesh Flag 2 is equal to 1. This flag
being set indicates that the data will appear, otherwise it
is omitted entirely.

    Mesh Data Sections (in order of appearance)
    * Vertices Data
    * Normals Data
    * UVs Data
    * Extras Data [Optional: Appears iff flag2 is 1]
    * TriFaces Data
    * After Data

##### Mesh Data: Vertices
The vertices data indicates the points that make up the polygon mesh.

    Offset      Size    Type    Description
    46          4       U32     Number of vertices contained in the mesh
    50          N*12    V3      Mesh vertices

The vertices data has been exported to Blender, so I'm very confident
about this data.

##### Mesh Data: Normals
The Offset is listed as ?? because it immediately follows the vertices
data, which has a variable length (i.e.: depends on the number of vertices)

    Offset      Size    Type    Description
    ??          4       U32     Number of normals contained in the mesh
    ??          N*12    V3      Mesh normals

Steve Hoff's ShadowbaneCacheViewer labeled this data as normal vectors
and I have adopted that name. I have NOT used the normal vectors in
Blender, so I don't know if this is what they are or not.

Note 1: In all Mesh resources, the number of normal vectors has always
been observed to be equal to the number of vertices.

##### Mesh Data: UVs
Steve Hoff's ShadowbaneCacheViewer labeled this data as texture coordinates.
I changed the name to "UVs" because Blender calls this UV mapping. I have
NOT used this UV data Blender, so I don't know if this is what it is or not.

    Offset      Size    Type    Description
    ??          4       U32     Number of UVs contained in the mesh
    ??          N*8     V2      UVs

Note 1: V2 is Vector2 format. This is a tuple of 2 float values: u and v.
Each float is 4 bytes, so 4*2 = 8 bytes total for a Vector2.

Note 2: In all Mesh resources, the number of UVs has always been observed
to be equal to the number of vertices.

##### Mesh Data: Extras
This section DOES NOT appear in all Mesh resources. If flag2 is set
(is equal to 1) in the Mesh header, then this section will always
appear after the UVs and before the TriFaces. If flag2 is clear
(is equal to 0) then this section is omitted entirely, and DOES NOT
appear in the Mesh data at all.

The Offset is listed as ?? because it immediately follows the UVs data,
which has a variable length (i.e.: depends on the number of UVs)

    Offset      Size    Type    Description
    ??          4       U32     Number of extras contained in the mesh
    ??          N*12    V3      Mesh extras

This data is pretty obviously Vector3s (3-tuple float values), but what
these represent are as yet unknown to me.

Note 1: In all Mesh resources, the number of extra vectors has always
been observed to be equal to the number of vertices.

##### Mesh Data: TriFaces
The TriFace data indicates the faces that make up the polygon mesh.

    Offset      Size    Type    Description
    ??          4       U32     Number of face indices contained in the mesh
    ??          N*2     TF      Mesh faces

The TriFace data has been exported to Blender, so I'm very confident
about this data.

Note 1: The number of face indices is THREE TIMES the number of faces
contained in the mesh. This value must be divided by three in order
to get an accurate count of the faces.

Note 2: TF is TriFace format. This is a tuple of 3 short (U16) values: v1, v2,
and v3. They are simply indices into the list of vertices. The three
vertices specify a triangle, which is a single face in the mesh.

##### Mesh Data: After Data
I call this the "after data" because it appears after the TriFace
data. I do not know what this data represents, but I think maybe the
short values are indices, perhaps to vertices or faces.

After Data consists of a 4 byte header, followed by the specified number
of After Data structures. If the specified number is 0, then no After Data
structures will follow, because the end of the mesh data has been reached.

    Offset      Size    Type    Description
    0           4       U32     Count of After Data structures = N
    4           ??      AD      After Data structures

The size of an After Data structure depends on how many short values
are part of the After Data. Each After Data structure has the following
format:

    Offset      Size    Type    Description
    0           4       U32     Unknown (Always equals 1)
    4           4       U32     Unknown (Always equals 0 or 1)
    8           4       U32     Number of short values = N
    12          N*2     U16     short data

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
    0           4       U32     Length of sound data (in bytes)
    4           4       U32     Bit rate (16000Hz or 22050Hz)
    8           4       U32     Number of channels (1=Mono, 2=Stereo)
    12          4       U32     Sample resolution (16=16-bit)
    16          N       U8      Sound data in WAVE format

Note 1: According to the documentation URL below, it appears WAVE format
uses unsigned bytes (0 to 255) when dealing with 8-bit samples and signed
shorts (-32768 to 32767) when dealing with 16-bit samples. All Shadowbane
samples are 16-bit, so the SoundResource class always sets signed = true.

See: http://www.blitter.com/~russtopia/MIDI/~jglatt/tech/wave.htm

### TerrainAlpha Resources
Each TerrainAlpha resource entry is exactly 16410 bytes in length.
There is a 26 byte header followed by the terrain data, in the
following format:

    Offset      Size    Type    Description
    0           4       U32     Width of the terrain data (always 128)
    4           4       U32     Height of the terrain data (always 128)
    8           4       U32     Unknown Field 3 (always 1)
    12          4       U32     Unknown Field 4 (always 1)
    16          4       U32     Unknown Field 5 (always 0)
    20          1       U8      Terrain Flag 1 (always 1)
    21          1       U8      Terrain Flag 2 (always 1)
    22          4       U32     Length of terrain data in bytes (always 16384)
    26          16384   U8      Terrain data (maybe height?)

Note 1: For every TerrainAlpha resource, the 26 byte header always contains
the same information. Thus it is hard to know what a field actually means.
For example, the field I've called "width" is always 128, but so is the
field that I've called "height"; what if they are actually backwards? :-)

Note 2: The terrain data itself varies between the 20912 terrain resources.
The squares are quite obviously spatial data of some kind; they form blocks
that seem to fit together. My best guess is that the terrain data represents
a height map of a 128x128 region of the world.

#### Terrain Maps
The 20912 terrain resources arrange to form a larger pictures. They vary
between a 1x1 map (containing a single TerrainAlpha resource) to 16x16
maps (a 2048x2048 map made up of 256 individual TerrainAlpha resources).

The resources seem to be in [column major](https://en.wikipedia.org/wiki/Row-major_order)
order. That is sequential blocks build up columns, from top to bottom.
The columns then build up a terrain map, from left to right.

This version of ShadowbaneCacheExporter exports the TerrainAlpha resources
as these larger terrain maps. The source code still contains the code needed
to output a single block as a PNG, but advanced users will probably just
want to get directly at the bytes anyway.

Note: Compare the export of TerrainMap_06208.png to [Sevaath Mere](https://morloch.shadowbaneemulator.com/index.php?title=Sevaath_Mere).

### Textures Resources
Each Textures resource entry has a variable length. There is a 26 byte
header followed by the actual pixel data, in the following format:

    Offset      Size    Type    Description
    0           4       U32     Width [W] of the texture (in pixels)
    4           4       U32     Height [H] of the texture (in pixels)
    8           4       U32     Depth [D] of the texture (in bytes)
    12          14      ???     Unknown
    26          W*H*D   U8      Pixel data; format depends on depth

Note 1: Depth determines how many bytes make up each pixel.

When Depth is 1, there is one byte [GG] per pixel, and the
format is rgba(GG, GG, GG, 255).

When Depth is 3, there are three bytes [RR], [GG], [BB] per pixel,
and the format is rgba(RR, GG, BB, 255).

When Depth is 4, there are four bytes [RR], [GG], [BB], [AA] per pixel,
and the format is rgba(RR, GG, BB, AA).

Note 2: The texture image is stored "upside down"; the first pixel is
the lower-left corner of the image. The first row of pixels is the
BOTTOM row of the image. The last pixel in the texture is the upper-right
corner of the texture image.

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
