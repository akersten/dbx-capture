/*
 Project: dbxcapture
 File: DBXCNativeInterface.java (com.dividebyxero.dbxcapture.jni)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture.jni;

/**
 * The interface providing methods that get executed in native code.
 *
 * Any library that implements these should use preprocessor directives to
 *
 * For example, on Windows, any GDI_, and DX_ methods should be implemented. On
 * Linux, any XLIB_ methods should be implemented. I'm pretty sure Mac also uses
 * Xlib, so just implement a Mac version of the keypress detection.
 *
 * @author Alex Kersten
 */
public abstract class DBXCNativeInterface {

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
