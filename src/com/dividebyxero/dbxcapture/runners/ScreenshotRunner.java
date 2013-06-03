/*
 Project: dbx-capture
 File: ScreenshotRunner.java (com.dividebyxero.dbxcapture.runners)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture.runners;

import com.dividebyxero.dbxcapture.DBXCRuntime;

/**
 * Class which triggers the native key listening and screenshot action, then
 * executes the selected script on it.
 *
 * @author Alex Kersten
 */
public class ScreenshotRunner implements Runnable {

    //The runtime context.
    private DBXCRuntime context;

    //If the program should process screenshots.
    private boolean running = true;

    //Local thread
    private Thread thisThread;

    /**
     * Constructs the screenshot runner and starts it listening for a screenshot
     * keypress and then executes the selected script. Does this automatically
     * and indefinitely until stop() is invoked.
     *
     * @param context
     */
    public ScreenshotRunner(DBXCRuntime context) {
        if (context == null) {
            return;
        }

        this.context = context;

        thisThread = new Thread(this);
        thisThread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            //Wait on native implementation. Be careful if you comment out the
            //native call to put a wait in here manually or we'll just 100% CPU
            //spin.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
            }

//             TODO: Uncomment this and switch to the non-debug native method
//            String unprocessedPath =
//                   DBXCNativeInterface.waitForScreenshotActionAndReturnPath(
//                    context.getSettings().getSetting("sContentDirectory"),
//                    Integer.parseInt(
//                    context.getSettings().getSetting("iScreenshotKey")));
//            
            //Check if we stopped running while waiting for C++ to return
            if (!running) {
                break;
            }

            //Execute processing on this path (usually upload or desktop copy)
            //by invoking the specified script in the scripts directory.

        }
    }
}
