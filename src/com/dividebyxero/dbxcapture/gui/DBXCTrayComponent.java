/*
 Project: dbxcapture
 File: DBXCTrayComponent.java (com.dividebyxero.dbxcapture.gui)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture.gui;

import com.dividebyxero.dbxcapture.DBXCRuntime;
import com.dividebyxero.dbxcapture.DBXCapture;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex Kersten
 */
public class DBXCTrayComponent {

    private DBXCRuntime context;

    private TrayIcon trayIcon;

    private PopupMenu trayMenu;

    public static final String[] TOGGLE_STRINGS = {"Switch to local mode",
                                                   "Switch to upload mode"};

    public static final String[] DISABLE_STRINGS = {"Temporarily disable dbxc",
                                                    "Re-enable dbxc"};

    public DBXCTrayComponent(DBXCRuntime context) {
        this.context = context;

        //Final context reference so inner anonymous classes can see it.
        final DBXCRuntime contextRef = context;

        if (context == null) {
            JOptionPane.showMessageDialog(null,
                                          "Tray icon with no context!",
                                          "dbxc - Error",
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Your JRE does not support system tray functionality needed"
                    + "by DBXCapture. You will not be able to utilize all of"
                    + "DBXC's functionality.", "DBXCapture - Warning",
                    JOptionPane.WARNING_MESSAGE);
        }


        try {
            //  trayIcon = new TrayIcon(ImageIO.read(new File("icon16.png")),
            //                          "DBXCapture  " + DBXCapture.VERSION);

            trayIcon = new TrayIcon(ImageIO.read(
                    this.getClass().getResource("icon16.png")),
                                    "DBXCapture " + DBXCapture.VERSION);

        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(
                    null,
                    "Couldn't load the tray icon.\n", "DBXCapture - Error",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        //Construct the pop-up menu
        trayMenu = new PopupMenu();



        //Exit button
        trayMenu.insert(new MenuItem("Exit"), 0);
        trayMenu.getItem(0).addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (contextRef.getSettings().getSetting(
                                "bConfirmExit").equalsIgnoreCase("true")) {

                            int response = JOptionPane.showConfirmDialog(
                                    null,
                                    "Are you sure you want to exit DBXCapture?",
                                    "DBXCapture - Confirm exit",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            if (response == JOptionPane.YES_OPTION) {
                                contextRef.quit();
                            }
                        } else {
                            contextRef.quit();
                        }

                    }
                });

        trayMenu.insert(new MenuItem("-"), 0);



        //Settings button
        trayMenu.insert(new MenuItem("Settings"), 0);
        trayMenu.getItem(0).addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        contextRef.getSettingsFrame().setVisible(true);
                    }
                });

        trayMenu.insert(new MenuItem("-"), 0);



        //Local/upload toggle button
        final MenuItem localToggleMenuItem = new MenuItem(TOGGLE_STRINGS[0]);
        trayMenu.insert(localToggleMenuItem, 0);
        localToggleMenuItem.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        //TODO: Implement local mode toggle
                        /*
                         * contextRef.toggleMode();
                         * localToggleMenuItem.setLabel(TOGGLE_STRINGS[contextRef.getMode()]);
                         */
                    }
                });



        //Temporary disable toggle button
        final MenuItem toggleDisabledMenuItem = new MenuItem(DISABLE_STRINGS[0]);

        trayMenu.insert(toggleDisabledMenuItem, 0);
        toggleDisabledMenuItem.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        //TODO: Implement temporary disable toggle
                        /*
                         * contextRef.toggleDisabled();
                         * toggleDisabledMenuItem.setLabel(DISABLE_STRINGS[contextRef.isTempDisabled()
                         * ? 1 : 0]);
                         *
                         */
                    }
                });


        //Shameless plug
        trayMenu.insert(new MenuItem("-"), 0);
        trayMenu.insert(new MenuItem("DBXCapture " + DBXCapture.VERSION), 0);
        trayMenu.getItem(0).setEnabled(false);


        trayIcon.setPopupMenu(trayMenu);


        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Exception while adding icon to system tray.\n"
                    + "Tray icon may not function as intended.",
                    "DBXCapture - Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Being nice to the OS and cleaning up our tray icon so it doesn't have to
     * discover that it's been removed without being notified later (aka when
     * the user puts the mouse over it and it suddenly disappears).
     */
    public void quitting() {
        if (trayIcon != null) {
            SystemTray.getSystemTray().remove(trayIcon);
        }
    }
}
