/*
 Project: dbxcapture
 File: DBXCRuntime.java (com.dividebyxero.dbxcapture)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture;

import com.dividebyxero.dbxcapture.config.Configuration;
import com.dividebyxero.dbxcapture.gui.DBXCTrayComponent;
import com.dividebyxero.dbxcapture.gui.SettingsFrame;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * The runtime container context for DBXCapture.
 *
 * @author Alex Kersten
 */
public class DBXCRuntime {

    //Name of the settings file within the working directory
    public static final String SETTINGS_FILE_NAME = "settings.cfg";

    //Name of scripts file containing command line actions for postprocessing.
    private static final String SCRIPTS_FILE_NAME = "scripts.cfg";

    //Settings singleton to query and update during program operation. Should
    //save it when it's updated.
    private Configuration settings;

    //Command lines that are loaded at the program start for what the possible
    //post-process scripts are.
    private String[] postProcessScripts;

    //The settings frame for DBXCapture.
    private SettingsFrame settingsFrame;

    //The tray component reference.
    private DBXCTrayComponent trayComponent;

    //The default script command line which will be put into scripts.cfg if
    //it doesn't exist.
    private static final String DEFAULT_SCRIPT =
                                "java ImgurScript %imagepath% %localmode%";

    /**
     * (Re)sets settings to default values. Should invoke if settings file
     * doesn't exist.
     */
    private void setDefaultSettings() {
        getSettings().setSetting("bConfirmExit", "false");
        getSettings().setSetting("sContentDirectory",
                                 "C:\\dbx\\DBXCapture\\content");
        getSettings().setSetting("iUploadScript", "0");
        getSettings().setSetting("bLocalMode", "false");
        getSettings().setSetting("iScreenshotKey", "44");

        if (!settings.saveSettings(new File(SETTINGS_FILE_NAME))) {
            JOptionPane.showMessageDialog(
                    null,
                    "Couldn't write to " + SETTINGS_FILE_NAME + "\n"
                    + "Please check write permissions on working directory.",
                    "DBXCapture - Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }

    /**
     * This method:
     *
     * <ul><li>Ensures the configuration file exists, and loads it if it
     * does.</li><li>If not, generates the default one.</li><li>Ensures the
     * scripts folder exists.</li><li>Ensures the specified content directory
     * exists.</li><li>Loads all command lines from scripts.cfg into
     * memory.</li><li>Creates default scripts.cfg if it does not
     * exist.</li><li>Makes sure the specified selected script is within
     * range.</li></ul>
     *
     * If one or more of these is an issue, it will do its best to remedy the
     * problem by making directories/files or notifying the user.
     */
    private void checkDirectoryStructureAndLoadConfiguration()
            throws IOException {
        //First see if the settings file exists at all - make it if it doesn't.
        if (!settings.loadSettings(new File(SETTINGS_FILE_NAME))) {
            System.out.println(
                    "Settings file does not exist, making with defaults...");

            setDefaultSettings();
        }

        //If it did exist, we'll have loaded the settings implicitly already.
        //Now let's check that scripts.cfg exists, and load the command line
        //actions from it. If it doesn't, create it and load the default.
        Path scriptsFile = Paths.get(SCRIPTS_FILE_NAME);


        if (!Files.exists(scriptsFile)) {
            System.out.println(
                    "Scripts file does not exist, making with defaults...");

            //Create the file and dump the defualt command line into it.
            Files.createFile(scriptsFile);

            if (!Files.exists(scriptsFile)) {
                //The file still doesn't exist despite creation attempt
                throw new IOException("Couldn't create scripts.cfg file.\n"
                                      + "Check working directory permissions.");
            }

            Files.write(scriptsFile, DEFAULT_SCRIPT.getBytes());
        }

        //Load the command lines into postProcessScripts, line by line.
        List<String> tmpScriptLines =
                     Files.readAllLines(scriptsFile, Charset.defaultCharset());

        if (tmpScriptLines.isEmpty()) {
            System.out.println(
                    "Scripts file empty, deleting and using default...");

            Files.delete(scriptsFile);
            postProcessScripts = new String[1];
            postProcessScripts[0] = DEFAULT_SCRIPT;
        } else {
            postProcessScripts = new String[tmpScriptLines.size()];

            //Read each item out of the list into the script array.
            int idx = 0;
            Iterator<String> itr = tmpScriptLines.iterator();
            while (itr.hasNext()) {
                postProcessScripts[idx] = itr.next();
                idx++;
            }
        }


        //Make sure our selected script is within range of how many scripts
        //there actually are.
        int currentSetting =
            Integer.parseInt(getSettings().getSetting("iUploadScript"));

        if ((currentSetting < 0)
            || (currentSetting > (tmpScriptLines.size() - 1))) {
            System.out.println(
                    "Selected script out of range, resetting to default...");

            getSettings().setSetting("iUploadScript", "0");
            getSettings().saveSettings(new File(SETTINGS_FILE_NAME));
        }

        Path scriptsFolder = Paths.get("scripts/");
        if (!Files.isDirectory(scriptsFolder)) {
            JOptionPane.showMessageDialog(
                    null, "No postprocessing scripts folder was found. You"
                          + " might need to reinstall DBXCapture.",
                    "DBXCapture - Warning", JOptionPane.WARNING_MESSAGE);

            //TODO: Check for default script and insert it if necessary.
        }


        //Check if the content directory exists and is writable.
        Path contentDirectory =
             Paths.get(getSettings().getSetting("sContentDirectory"));

        if (!Files.isDirectory(contentDirectory)) {
            System.out.println("Content directory did not exist, creating...");
            Files.createDirectories(contentDirectory);
        }
    }

    public DBXCRuntime() {
        //Debug
        Path workingDir = Paths.get(".");
        System.out.println("Working directory is "
                           + workingDir.toAbsolutePath().toString());

        //Initialize the configuration object - this gets loaded from disk next.
        settings = new Configuration();

        try {
            checkDirectoryStructureAndLoadConfiguration();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(
                    null,
                    "I/O error during working directory configuration. Make "
                    + "sure " + workingDir.toAbsolutePath().toString()
                    + " exists and is writable. If this problem persists, try "
                    + "deleting " + SETTINGS_FILE_NAME, "DBXCapture - Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }

        //Create the settings frame with the default settings loaded. Don't
        //show it yet though. The settings frame will take care of selecting
        //the correct options based on the current setup.
        settingsFrame = new SettingsFrame(this);


        //Sanity checks should all be done by now, so start the key listener and
        //show the tray icon.
        trayComponent = new DBXCTrayComponent(this);






    }

    /**
     * @return the settings
     */
    public Configuration getSettings() {
        return settings;
    }

    /**
     * @return the settingsFrame
     */
    public SettingsFrame getSettingsFrame() {
        return settingsFrame;
    }

    /**
     * @return the postProcessScripts
     */
    public String[] getPostProcessScripts() {
        return postProcessScripts;
    }
}
