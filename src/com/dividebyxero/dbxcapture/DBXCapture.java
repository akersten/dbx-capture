/*
 Project: dbxcapture
 File: DBXCapture.java (com.dividebyxero.dbxcapture.dbxcapture)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture;

import com.dividebyxero.dbxcapture.Platforming.Platform;
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
 * I'll try to keep everything as platform independent as possible, but it'll be
 * a bit challenging in the native implementation. The libraries will just have
 * to be separate and I'll only include the Windows ones by default. Maybe once
 * I know more about OpenGL I'll write some for non-Windows stuff.
 *
 * Everything in Java (that is, this project) though should be 100% independent,
 * It automatically selects the platform and tries to load the corresponding
 * native library.
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

    //Keeps track of which platform we're running on. Gets set by the static
    //block in Platforming.java.
    public static Platform platform;

    //Singleton for the runtime, set once at the beginning here and probably
    //won't change.
    private static DBXCRuntime runtime;

    /**
     * Main method for DBXCapture. Set the Swing look-and-feel to the platform's
     * native style and launch a singleton runtime which takes care of the rest
     * of the program execution.
     *
     * The initial directory structure creation and verification is a little
     * spread out, but here's a rundown of what happens:
     *
     * First, the static block in Platforming.java runs - this checks the system
     * platform and determines what native library it's looking for. It creates
     * ~/dbx/DBXCapture, and, if the platform is Windows, drops the correct DLL
     * in that folder - if not, it prompts the user to compile their own based
     * on the GitHub source and place it there.
     *
     * Then, the first stuff that runs in DBXCRuntime does creation of the
     * settings.cfg and scripts.cfg file, as well as the content and scripts
     * subdirectories (it also drops the default script in .../scripts).
     *
     * @param args No command line arguments have been specified so far.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {

            System.err.println("Couldn't set the system look and feel.");
        }

        DBXCapture.runtime = new DBXCRuntime(DEBUG_BUILD);
    }
}
