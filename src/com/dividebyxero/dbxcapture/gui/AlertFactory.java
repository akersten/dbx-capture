/*
 Project: dbx-capture
 File: AlertFactory.java (com.dividebyxero.dbxcapture.gui)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Constructs and displays the tray alerts, because we don't want to take focus
 * away from whatever the user's doing.
 *
 * @author Alex Kersten
 */
public class AlertFactory {

    //How many milliseconds an alert stays visible by default
    public static final int DEFAULT_ALERT_TIMEOUT = 4000;

    //The default size of alerts
    public static final Dimension DEFAULT_ALERT_SIZE = new Dimension(256, 16);

    //The singleton list keeping track of all alerts given by dbxc. This is just
    //a simple alerts system so no need for multiple queues or anything like
    //that. We'll just do it the easy way.
    private static final ArrayList<Alert> alertList = new ArrayList<>();
    
    //Static Timer instance to manage Alert expiry. Do not cancel() this timer,
    //otherwise you'll put the singleton into an illegal state for future Alerts
    static final Timer alertTimer = new Timer();

    /**
     * Shows an alert with default settings and specified message.
     *
     * @param message The message to show.
     */
    public static void showAlert(String message) {
        showAlert(message, DEFAULT_ALERT_TIMEOUT);
    }

    /**
     * Shows an alert with the specified message and time to live.
     *
     * @param text The message to show.
     * @param ttl How many milliseconds the alert should stay on screen.
     */
    public static void showAlert(String text, int ttl) {
        //Add the alert to the list
        synchronized (alertList) {
            alertList.add(new Alert(text, ttl));
        }
    }

    /**
     * Called by an Alert object upon its expiration. Removes the alert from the
     * list and packs the remaining alerts together on-screen in order.
     *
     * @param alert The alert to remove.
     */
    private static void removeAlert(Alert alert) {
        synchronized (alertList) {
            alertList.remove(alert);
        }
    }
}
