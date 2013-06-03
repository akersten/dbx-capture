/*
 Project: dbxcapture
 File: DBXCapture.java (com.dividebyxero.dbxcapture.dbxcapture)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The main class for DBXCapture. Launches a runtime which acts as a context for
 * the program instance.
 *
 * Version 2.0 philosophy: Let's go with Windows only for now (there's great
 * applications available for Linux already) and use DirectX native calls to
 * generate the screenshot. Should be faster and more reliable than the
 * java.awt.robot and we might even be able to do screencasting if it's fast
 * enough...
 *
 * @author Alex Kersten
 */
public class DBXCapture {

    //Set if this build should run the debug interface instead of the normal
    //keyboard listeners. Should always be false in production, otherwise DBXC
    //doesn't do much.
    private static final boolean DEBUG_BUILD = true;

    //Global version string for DBXCapture, appears in many places.
    public static final String VERSION = "2.0.0 Dev";

    //Singleton for the runtime, set once at the beginning here and probably
    //won't change.
    private static DBXCRuntime runtime;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {

            System.err.println("Couldn't set the system look and feel.");
        }

        runtime = new DBXCRuntime(DEBUG_BUILD);
    }
}
