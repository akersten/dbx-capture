dbx-capture
===========

DBXCapture is a modular desktop screenshot sharing application written in Java and C++ via JNI.

### Screenshots

The program runs and waits for a (configurable) key to be pressed - it then captures the screen and presents the user with a crop-able overlay. Once the user is satisfied with their selection, the cropped image is processed - see the 'Modularity' section below.

### Modularity

The cropped image is saved as a PNG file on disk, and then DBXCapture is out of the picture. The path to this image is passed on to a post-processing script: out of the box, the included default script uploads the image to [Imgur](http://imgur.com), but this can be changed to *any* user-specified command line execution - just write your own script, put it in `~/dbx/DBXCapture/scripts/`, and update `~/dbx/DBXCapture/scripts.cfg`. (For more information about writing post-processing scripts, see the Image Scripts section near the end of this document.)

### Platform Independence

Almost all of DBXCapture is platform-independent. However, the actual screen capturing functionality is written in native Windows code. For other platforms, a compatible native library will need to be produced - other than that, the rest of DBXCapture is entirely portable and should run fine. I've provided a Windows implementation of this functionality (see the corresponding project, [dbx-libcapture](https://github.com/akersten/dbx-libcapture)), but I haven't done any research into a native implementation on Mac or Linux. I've made the methods as general as possible to facilitate easy development of alternate implementations, but the actual capture code for Windows uses some DirectX functionality for speed reasons, so other platforms will need to find a decent dual. If I don't get around to it, refer to the Windows implementation and method descriptions in `DBXCNativeInterface.java` for information about how it should be implemented. Then, put your library at `~/dbx/DBXCapture/libcapture.so` and DBXCapture should be able to see it.

### Portability

The program is self-contained and can be run anywhere (it references its libraries in your home directory) - it will save screenshots to a content folder that it creates in your home directory.

### Getting Started

The easiest way to run DBXCapture is to download a precompiled binary at my personal website (not yet, the project isn't finshed!).

If you want to compile from source, download the source tree (this project) and build it - it should compile without any tweaks (make sure your main method is set to `com.dividebyxero.dbxcapture.DBXCapture.main`, rather than the one in `ImgurScript.java`).

If you want DBXCapture to upload things to Imgur, you should also compile `ImgurScript` and replace the dummy file (in the compiled JAR) `ImgurScript.bin` with the compiled file (note: keep the name as `ImgurScript.bin` - this file gets copied out of the JAR when needed, and renamed appropriately). If not, write your own post-processing script and put it in `~/dbx/DBXCapture/scripts` and add a command line for it in `~/dbx/DBXCapture/scripts.cfg`, as discussed above.

Next, you'll need the native libraries to capture the screen. If you're on Windows, download my implementation of [dbx-libcapture](https://github.com/akersten/dbx-libcapture) and build it against JNI. Replace the `libcapture32.bin` and `libcapture64.bin` placeholders in the JAR with your compiled files (again noting to keep the .bin extension rather than change it to .dll). On other platforms, put your version of the library at `~/dbx/DBXCapture/libcapture.so`.

The .jar should now be portable and will automatically create its directory structure in your home directory, as well as deposit the libraries you added where necessary.

### Image Scripts

After DBXCapture saves a portion of the screen to a PNG file, it invokes a user-defined command. These commands are listed in `~/dbx/DBXCapture/scripts.cfg`, one per line (selectable through the options interface - they don't *all* execute at once). Take the default `scripts.cfg` for example:

`java ImgurScript %imagepath% %localmode%`

This will be executed on the command line in the working directory of the `~/dbx/DBXCapture/scripts` directory. `%imagepath%` becomes the absolute path to the created image (in quotes), and `%localmode%` is `true` or `false` depending on whether the user has selected the Local Mode checkbox in the options interface. For the default script, Local Mode causes the image to be saved to the desktop rather than be uploaded.

A script may use any combination of these parameters, or none of them - although `%imagepath%` is recommended in order to do anything useful with the captured image.
