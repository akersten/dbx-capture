/*
 Project: dbx-capture
 File: ImgurScript.java (com.dividebyxero.dbxcapture.scripts)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture.scripts;

/**
 * This script will upload an image path on the command line to Imgur or save to
 * the desktop based on another command line flag. Separate from the rest of the
 * DBXCapture program in the sense that this class has its own main method and
 * should be invoked from the command line automatically by the script engine.
 *
 * @author Alex Kersten
 */
public class ImgurScript {

    /**
     * This is the default script that is included with DBXCapture. The first
     * command line argument should be a path to an image to process. The second
     * command line argument is a 'true' or 'false' based on whether we are
     * operating in local mode ('true') in which case the image will be copied
     * to the desktop rather than uploaded to Imgur.
     */
    public static void main(String[] args) {
    }
}
