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
