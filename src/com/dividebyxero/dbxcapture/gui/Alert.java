/*
 Project: dbx-capture
 File: Alert.java (com.dividebyxero.dbxcapture.gui)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture.gui;

/**
 * An Alert object encapsulates the AlertPanel into an undecorated JFrame and is
 * responsible for keeping track of things like remaining life of the alert, and
 * terminating the alert once it's expired.
 *
 * @author Alex Kersten
 */
public class Alert {

    /**
     * Constructs an Alert with the specified message and time to live.
     *
     * @param message The message of the Alert.
     * @param ttl How long the Alert should stay on-screen.
     */
    public Alert(String message, long ttl) {
    }
}
