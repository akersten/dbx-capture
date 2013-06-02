/*
 Project: dbxcapture
 File: Configuration.java (com.dividebyxero.dbxcapture.config)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture.config;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class will allow us to easily save program settings by writing/reading
 * key-value pairs to/from disk.
 *
 * Setting names are not case sensitive. A setting query will return null if it
 * is not set.
 *
 * @author Alex Kersten
 */
public class Configuration {

    private ArrayList<KeyValuePair> settings = new ArrayList<>();

    private static final short FILE_VERSION = 1;

    public Configuration() {
    }

    public Configuration(File f) {
        this.loadSettings(f);
    }

    public boolean isSettingSet(String settingKey) {
        for (KeyValuePair s : settings) {
            if (s.getKey().equalsIgnoreCase(settingKey)) {
                return true;
            }
        }
        return false;
    }

    public String getSetting(String settingKey) {
        for (KeyValuePair s : settings) {
            if (s.getKey().equalsIgnoreCase(settingKey)) {
                return s.getValue();
            }
        }

        return null;
    }

    public void setSetting(String settingKey, String newValue) {
        for (KeyValuePair s : settings) {
            if (s.getKey().equalsIgnoreCase(settingKey)) {
                s.setValue(newValue);
                return;
            }
        }

        settings.add(new KeyValuePair(settingKey, newValue));
    }

    public final boolean loadSettings(File f) {
        settings.clear();

        if (!f.exists()) {
            return false;
        }

        DataInputStream dIn = null;
        short settingsCount;
        short version;

        try {
            dIn = new DataInputStream(new FileInputStream(f));
            byte[] headerBuffer = new byte[settingsFileHeader.length];
            dIn.read(headerBuffer);
            if (!Arrays.equals(headerBuffer, settingsFileHeader)) {
                throw new RuntimeException("File was not a settings file.");
            }

            settingsCount = dIn.readShort();

            if (settingsCount < 0) {
                throw new RuntimeException("Negative number of settings.");
            }

            version = dIn.readShort();

            if (version != FILE_VERSION) {
                throw new RuntimeException("Settings file version mismatch.\n"
                                           + "\tCurrent version: "
                                           + FILE_VERSION + "\tFile version: "
                                           + version);
            }


            for (int i = 0; i < settingsCount; i++) {
                String keyData = dIn.readUTF();
                String valueData = dIn.readUTF();
                setSetting(keyData, valueData);
            }

        } catch (IOException | RuntimeException e) {
            System.err.println("Problem loading settings:\n" + e.getMessage());
            return false;
        } finally {
            if (dIn != null) {
                try {
                    dIn.close();
                } catch (IOException ex) {
                    //No consequences
                }
            }
        }

        return true;
    }
    private static final byte[] settingsFileHeader = {
        "D".getBytes()[0], "B".getBytes()[0], "X".getBytes()[0],
        "S".getBytes()[0], "F".getBytes()[0], "i".getBytes()[0],
        "l".getBytes()[0], "e".getBytes()[0], 1, 3, 3, 7
    };

    public boolean saveSettings(File f) {
        if (settings.size() > (Math.pow(2, 16) / 2) - 1) {
            System.err.println("Couldn't save settings file: "
                    + "settings array too large.");
            
            return false;
        }

        DataOutputStream dOut;

        try {
            dOut = new DataOutputStream(new FileOutputStream(f));
            dOut.write(settingsFileHeader);
            dOut.writeShort(settings.size());
            dOut.writeShort(FILE_VERSION);

            for (int i = 0; i < settings.size(); i++) {
                KeyValuePair s = settings.get(i);
                dOut.writeUTF(s.getKey());
                dOut.writeUTF(s.getValue());
            }

            dOut.flush();
            dOut.close();
        } catch (Exception e) {
            System.err.println("Problem saving settings:\n" + e.getMessage());
            return false;
        }

        return true;
    }

    public String[] getFieldList() {
        String[] t = new String[settings.size()];
        for (int i = 0; i < t.length; i++) {
            t[i] = settings.get(i).getKey();
        }
        return t;
    }

    public void removeField(String s) {
        for (int i = 0; i < settings.size(); i++) {
            if (settings.get(i).getKey().equalsIgnoreCase(s)) {
                settings.remove(i);
                break;
            }
        }
    }
}
