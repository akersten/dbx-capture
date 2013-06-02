/*
 Project: dbxcapture
 File: KeyValuePair.java (com.dividebyxero.dbxcapture.config)
 Author: Alex Kersten
 */
package com.dividebyxero.dbxcapture.config;

/**
 * Key-Value object for Configuration class.
 * 
 * @author Alex Kersten
 */
class KeyValuePair {

    private String key, value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
