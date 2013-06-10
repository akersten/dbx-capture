/*
 Project: dbxcapture
 File: DBXCNativeInterface.java (com.dividebyxero.dbxcapture.jni)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture.jni;

/**
 * The interface providing methods that get executed in native code.
 *
 * @author Alex Kersten
 */
public abstract class DBXCNativeInterface {

    /**
     * This will just test to see if DirectX is even a feasible way of capturing
     * the screen by dumping the video buffer out to a bitmap in the working
     * directory. Not used by the actual program (we'll use PNG) but useful for
     * testing.
     */
    public static native void debugDumpBuffer();

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Blocks the current thread until the user presses the specified key (via
     * global keyboard hook).
     *
     * @param vk Which key to wait for.
     */
    public static native void blockUntilKeypress(int vk);

    /**
     * Returns the bitmap of the entire visible screen as a 2d byte array, 8-bit
     * RGB encoded.
     *
     * @return 8-bit RGB array of the screen.
     */
    public static native byte[][] dumpScreenBits();
}
