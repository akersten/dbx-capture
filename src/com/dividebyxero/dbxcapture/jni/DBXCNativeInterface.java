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

    /**
     * Blocks until the user presses PrintScreen and then returns with the path
     * to a created PNG file on the hard drive.
     *
     * @return The path to the created PNG file. NOTE: This should be in QUOTES.
     */
    public static native String waitForScreenshotActionAndReturnPath(
            String contentDirectory, int triggerVK);
}
