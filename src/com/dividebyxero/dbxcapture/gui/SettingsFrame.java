/*
 Project: dbxcapture
 File: SettingsFrame.java (com.dividebyxero.dbxcapture.gui)
 Author: Alex Kersten
 */

/*
 * SettingsFrame.java
 *
 * Created on Jun 1, 2013, 11:16:55 AM
 */
package com.dividebyxero.dbxcapture.gui;

import com.dividebyxero.dbxcapture.DBXCRuntime;
import com.dividebyxero.dbxcapture.DBXCapture;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * Frame for modifying configuration of DBXCapture. Create it once at the
 * program startup and then hide it... Save the reference. Or don't, doesn't
 * really matter, it's just the better way.
 *
 * @author Alex Kersten
 */
@SuppressWarnings("serial")
public class SettingsFrame extends javax.swing.JFrame {

    private DBXCRuntime context;

    /**
     * Creates new form SettingsFrame. Only create after context.getSettings()
     * is accessible and loaded.
     *
     * Any state that needs to be read from the settings in the runtime context
     * is done here (i.e. looks for which scripts are active and populates the
     * dropdown menu with those, sets it to the right one based on current
     * index, etc.)
     */
    public SettingsFrame(DBXCRuntime context) {
        if (context == null) {
            JOptionPane.showMessageDialog(null, "No context for SettingsFrame!",
                                          "DBXCapture - Warning",
                                          JOptionPane.WARNING_MESSAGE);
            return;
        }

        this.context = context;

        initComponents();
        
        try {
            setIconImage(ImageIO.read(
                    this.getClass().getResource("icon16.png")));
        } catch (Exception e) {
            System.err.println("Can't load frame icon.");
        }

        this.setLocationRelativeTo(null);

        setupFromLoadedSettings();
        versionLabel.setText(DBXCapture.VERSION);

        //When hiding the window (default behavior is to hide), discard any
        //intermediate changes (as if the user had hit the discard button).
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent evt) {
                setToLastKnown();
            }
        });
    }

    /**
     * Internal method for populating fields based on loaded settings read from
     * the runtime context.
     */
    @SuppressWarnings("unchecked")
    private void setupFromLoadedSettings() {
        //All we need to do here is populate the combobox model, and then set
        //things to the last known configuration (which is what we loaded).

        //Create model for script dropdown and set its index to the proper one.
        DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<>();

        for (int i = 0; i < context.getPostProcessScripts().length; i++) {
            newModel.addElement(context.getPostProcessScripts()[i]);
        }

        currentScriptComboBox.setModel(newModel);

        setToLastKnown();
    }

    /**
     * Sets the settings back to the last known configuration (like if we hide
     * the form when the cancel button was pressed, so we don't go back to
     * intermediate settings when we re-show it).
     */
    private void setToLastKnown() {
        //Populate fields based on current settings.
        confirmExitCheckBox.setSelected(context.getSettings().
                getSetting("bConfirmExit").equalsIgnoreCase("true"));

        contentDirectoryTextField.setText(context.getSettings().
                getSetting("sContentDirectory"));

        //We already know this index is within range - it was checked when the
        //settings were loaded.
        currentScriptComboBox.setSelectedIndex(
                Integer.parseInt(
                context.getSettings().getSetting("iUploadScript")));

        localmodeCheckBox.setSelected(context.getSettings().
                getSetting("bLocalMode").equalsIgnoreCase("true"));

        triggerTextField.setText(
                context.getSettings().getSetting("iScreenshotKey"));

    }

    /**
     * Verify and apply settings changes.
     *
     * @return True if settings applied and saved successfully, false if there
     * was a problem parsing one of the new settings or saving them.
     */
    private boolean setAndSaveSettings() {
        context.getSettings().setSetting("bConfirmExit",
                                         confirmExitCheckBox.isSelected()
                                         ? "true" : "false");

        //Verify that the content directory is actually a directory.
        Path testp = Paths.get(contentDirectoryTextField.getText());
        if (!Files.isDirectory(testp)) {
            return false;
        }

        context.getSettings().setSetting("sContentDirectory",
                                         contentDirectoryTextField.getText());

        context.getSettings().setSetting(
                "iUploadScript", "" + currentScriptComboBox.getSelectedIndex());

        context.getSettings().setSetting("bLocalMode",
                                         localmodeCheckBox.isSelected()
                                         ? "true" : "false");

        int testssk;
        try {
            testssk = Integer.parseInt(triggerTextField.getText());
        } catch (NumberFormatException nfe) {
            return false;
        }

        context.getSettings().setSetting("iScreenshotKey", "" + testssk);

        return context.getSettings().
                saveSettings(new File(DBXCRuntime.SETTINGS_FILE_NAME));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        captureMethodButtonGroup = new javax.swing.ButtonGroup();
        editingModeButtonGroup = new javax.swing.ButtonGroup();
        videoResolutionButtonGroup = new javax.swing.ButtonGroup();
        settingsTabbedPane = new javax.swing.JTabbedPane();
        dbxcSettingsPanel = new javax.swing.JPanel();
        generalSettingsPanel = new javax.swing.JPanel();
        confirmExitCheckBox = new javax.swing.JCheckBox();
        contentDirectoryPanel = new javax.swing.JPanel();
        contentDirectoryTextField = new javax.swing.JTextField();
        screenshotSettingsPanel = new javax.swing.JPanel();
        croppingModePanel = new javax.swing.JPanel();
        fullscreenCropRadioButton = new javax.swing.JRadioButton();
        dedicatedCropRadioButton = new javax.swing.JRadioButton();
        noCropRadioButton = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        triggerTextField = new javax.swing.JTextField();
        triggerFinishedButton = new javax.swing.JButton();
        resetTriggerButton = new javax.swing.JButton();
        trigger3Label = new javax.swing.JLabel();
        scriptsSettingsPanel = new javax.swing.JPanel();
        scriptinfo1Label = new javax.swing.JLabel();
        scriptinfo2Label = new javax.swing.JLabel();
        example1Label = new javax.swing.JLabel();
        example2Label = new javax.swing.JLabel();
        currentScriptPanel = new javax.swing.JPanel();
        currentScriptComboBox = new javax.swing.JComboBox();
        scriptinfo3Label = new javax.swing.JLabel();
        scriptVariablesPanel = new javax.swing.JPanel();
        imagepath1Label = new javax.swing.JLabel();
        imagepath2Label = new javax.swing.JLabel();
        localmodeCheckBox = new javax.swing.JCheckBox();
        localmodeLabel = new javax.swing.JLabel();
        videoPanel = new javax.swing.JPanel();
        videoTriggerPanel = new javax.swing.JPanel();
        videoTriggerTextField = new javax.swing.JTextField();
        videoTriggerFinishedButton = new javax.swing.JButton();
        videoTriggerResetButton = new javax.swing.JButton();
        videoTriggerLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        aboutPanel = new javax.swing.JPanel();
        iconLabel = new javax.swing.JLabel();
        dbxcaptureLabel = new javax.swing.JLabel();
        versionLabel = new javax.swing.JLabel();
        authorLabel = new javax.swing.JLabel();
        githubButton = new javax.swing.JButton();
        dividebyxeroButton = new javax.swing.JButton();
        alexkerstenButton = new javax.swing.JButton();
        byLabel = new javax.swing.JLabel();
        githubLabel = new javax.swing.JLabel();
        dividebyxeroLabel = new javax.swing.JLabel();
        alexkerstenLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();

        setTitle("DBXCapture - Options");
        setResizable(false);

        generalSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));

        confirmExitCheckBox.setText("Confirm Exit");

        javax.swing.GroupLayout generalSettingsPanelLayout = new javax.swing.GroupLayout(generalSettingsPanel);
        generalSettingsPanel.setLayout(generalSettingsPanelLayout);
        generalSettingsPanelLayout.setHorizontalGroup(
            generalSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(confirmExitCheckBox)
        );
        generalSettingsPanelLayout.setVerticalGroup(
            generalSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(confirmExitCheckBox)
        );

        contentDirectoryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Content Directory"));

        contentDirectoryTextField.setText("<content directory>");

        javax.swing.GroupLayout contentDirectoryPanelLayout = new javax.swing.GroupLayout(contentDirectoryPanel);
        contentDirectoryPanel.setLayout(contentDirectoryPanelLayout);
        contentDirectoryPanelLayout.setHorizontalGroup(
            contentDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentDirectoryTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );
        contentDirectoryPanelLayout.setVerticalGroup(
            contentDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout dbxcSettingsPanelLayout = new javax.swing.GroupLayout(dbxcSettingsPanel);
        dbxcSettingsPanel.setLayout(dbxcSettingsPanelLayout);
        dbxcSettingsPanelLayout.setHorizontalGroup(
            dbxcSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dbxcSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dbxcSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contentDirectoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dbxcSettingsPanelLayout.createSequentialGroup()
                        .addComponent(generalSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dbxcSettingsPanelLayout.setVerticalGroup(
            dbxcSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dbxcSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(generalSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentDirectoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );

        settingsTabbedPane.addTab("DBXC", dbxcSettingsPanel);

        croppingModePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Cropping Mode"));

        editingModeButtonGroup.add(fullscreenCropRadioButton);
        fullscreenCropRadioButton.setText("Fullscreen Overlay (Freeze entire screen after capture)");

        editingModeButtonGroup.add(dedicatedCropRadioButton);
        dedicatedCropRadioButton.setText("Dedicated Windows (Every screenshot has an individual window for cropping)");

        editingModeButtonGroup.add(noCropRadioButton);
        noCropRadioButton.setText("Don't Crop (Saves entire screen)");

        javax.swing.GroupLayout croppingModePanelLayout = new javax.swing.GroupLayout(croppingModePanel);
        croppingModePanel.setLayout(croppingModePanelLayout);
        croppingModePanelLayout.setHorizontalGroup(
            croppingModePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(noCropRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dedicatedCropRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
            .addComponent(fullscreenCropRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        croppingModePanelLayout.setVerticalGroup(
            croppingModePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(croppingModePanelLayout.createSequentialGroup()
                .addComponent(fullscreenCropRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dedicatedCropRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noCropRadioButton))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Trigger Key"));

        triggerTextField.setBackground(new java.awt.Color(255, 255, 204));
        triggerTextField.setEditable(false);
        triggerTextField.setText("<trigger>");
        triggerTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                triggerTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                triggerTextFieldFocusLost(evt);
            }
        });
        triggerTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                triggerTextFieldKeyPressed(evt);
            }
        });

        triggerFinishedButton.setText("Finished");
        triggerFinishedButton.setEnabled(false);

        resetTriggerButton.setText("Reset to PrintScreen (44)");
        resetTriggerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetTriggerButtonActionPerformed(evt);
            }
        });

        trigger3Label.setText("Change will go into effect after next screenshot or program restart.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(triggerTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(triggerFinishedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetTriggerButton))
            .addComponent(trigger3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(triggerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(triggerFinishedButton)
                    .addComponent(resetTriggerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trigger3Label))
        );

        javax.swing.GroupLayout screenshotSettingsPanelLayout = new javax.swing.GroupLayout(screenshotSettingsPanel);
        screenshotSettingsPanel.setLayout(screenshotSettingsPanelLayout);
        screenshotSettingsPanelLayout.setHorizontalGroup(
            screenshotSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(screenshotSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(screenshotSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(croppingModePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        screenshotSettingsPanelLayout.setVerticalGroup(
            screenshotSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(screenshotSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(croppingModePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        settingsTabbedPane.addTab("Screenshot Options", screenshotSettingsPanel);

        scriptinfo1Label.setText("After capturing a screenshot and saving it to a file, dbxcapture passes the file path to a script for");

        scriptinfo2Label.setText("further processing. The default script uploads the screenshot to Imgur.");

        example1Label.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        example1Label.setText("Example:");

        example2Label.setText("python uploadScrypt.py %imagepath%");

        currentScriptPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Upload Script"));

        currentScriptComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<selected script>" }));

        javax.swing.GroupLayout currentScriptPanelLayout = new javax.swing.GroupLayout(currentScriptPanel);
        currentScriptPanel.setLayout(currentScriptPanelLayout);
        currentScriptPanelLayout.setHorizontalGroup(
            currentScriptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(currentScriptPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(currentScriptComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        currentScriptPanelLayout.setVerticalGroup(
            currentScriptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(currentScriptComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        scriptinfo3Label.setText("These scripts are specified in scripts.cfg in the dbxcapture installation directory, one per line.");

        scriptVariablesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Script Variables"));

        imagepath1Label.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        imagepath1Label.setText("%imagepath%");

        imagepath2Label.setText("The absolute path to the most recent screenshot.");

        localmodeCheckBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        localmodeCheckBox.setText("%localmode%");

        localmodeLabel.setText("Save to desktop instead of upload.");

        javax.swing.GroupLayout scriptVariablesPanelLayout = new javax.swing.GroupLayout(scriptVariablesPanel);
        scriptVariablesPanel.setLayout(scriptVariablesPanelLayout);
        scriptVariablesPanelLayout.setHorizontalGroup(
            scriptVariablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scriptVariablesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(scriptVariablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scriptVariablesPanelLayout.createSequentialGroup()
                        .addComponent(imagepath1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(imagepath2Label))
                    .addGroup(scriptVariablesPanelLayout.createSequentialGroup()
                        .addComponent(localmodeCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(localmodeLabel)))
                .addContainerGap())
        );
        scriptVariablesPanelLayout.setVerticalGroup(
            scriptVariablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scriptVariablesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(scriptVariablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imagepath1Label)
                    .addComponent(imagepath2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scriptVariablesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localmodeCheckBox)
                    .addComponent(localmodeLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout scriptsSettingsPanelLayout = new javax.swing.GroupLayout(scriptsSettingsPanel);
        scriptsSettingsPanel.setLayout(scriptsSettingsPanelLayout);
        scriptsSettingsPanelLayout.setHorizontalGroup(
            scriptsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scriptsSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(scriptsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scriptVariablesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scriptinfo1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scriptinfo2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(scriptsSettingsPanelLayout.createSequentialGroup()
                        .addComponent(example1Label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(example2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(currentScriptPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scriptinfo3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        scriptsSettingsPanelLayout.setVerticalGroup(
            scriptsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scriptsSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scriptinfo1Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scriptinfo2Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(currentScriptPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scriptinfo3Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scriptsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(example1Label)
                    .addComponent(example2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scriptVariablesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        settingsTabbedPane.addTab("Screenshot Scripts", scriptsSettingsPanel);

        videoTriggerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Video Trigger Key"));

        videoTriggerTextField.setBackground(new java.awt.Color(255, 255, 204));
        videoTriggerTextField.setEditable(false);
        videoTriggerTextField.setText("<trigger>");
        videoTriggerTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                videoTriggerTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                videoTriggerTextFieldFocusLost(evt);
            }
        });
        videoTriggerTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                videoTriggerTextFieldKeyPressed(evt);
            }
        });

        videoTriggerFinishedButton.setText("Finished");
        videoTriggerFinishedButton.setEnabled(false);

        videoTriggerResetButton.setText("Reset to Pause (19)");
        videoTriggerResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videoTriggerResetButtonActionPerformed(evt);
            }
        });

        videoTriggerLabel.setText("Change effective after next video or program restart.");

        javax.swing.GroupLayout videoTriggerPanelLayout = new javax.swing.GroupLayout(videoTriggerPanel);
        videoTriggerPanel.setLayout(videoTriggerPanelLayout);
        videoTriggerPanelLayout.setHorizontalGroup(
            videoTriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(videoTriggerPanelLayout.createSequentialGroup()
                .addComponent(videoTriggerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(videoTriggerFinishedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(videoTriggerResetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(videoTriggerPanelLayout.createSequentialGroup()
                .addComponent(videoTriggerLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        videoTriggerPanelLayout.setVerticalGroup(
            videoTriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(videoTriggerPanelLayout.createSequentialGroup()
                .addGroup(videoTriggerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(videoTriggerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(videoTriggerFinishedButton)
                    .addComponent(videoTriggerResetButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(videoTriggerLabel))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Resolution"));

        videoResolutionButtonGroup.add(jRadioButton1);
        jRadioButton1.setText("Full");

        videoResolutionButtonGroup.add(jRadioButton2);
        jRadioButton2.setText("Half");

        videoResolutionButtonGroup.add(jRadioButton3);
        jRadioButton3.setText("Quarter");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Timing (Advanced)"));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Capture Rate"));

        jTextField1.setText("24");

        jLabel2.setText("fps");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jTextField1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Encode (Playback) Rate"));

        jLabel3.setText("⁻¹spf");

        jTextField2.setText("24");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Calculated Time Scale"));

        jLabel4.setText("1x: 15 seconds elapsed -> 15 second video");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Audio Capture"));

        jRadioButton4.setText("None");

        jRadioButton5.setText("WASAPI");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
        );

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Video capture not implemented yet!");

        javax.swing.GroupLayout videoPanelLayout = new javax.swing.GroupLayout(videoPanel);
        videoPanel.setLayout(videoPanelLayout);
        videoPanelLayout.setHorizontalGroup(
            videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(videoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(videoPanelLayout.createSequentialGroup()
                        .addComponent(videoTriggerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        videoPanelLayout.setVerticalGroup(
            videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(videoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(videoTriggerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        settingsTabbedPane.addTab("Video Options", videoPanel);

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dividebyxero/dbxcapture/gui/icon128.png"))); // NOI18N

        dbxcaptureLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dbxcaptureLabel.setText("DBXCapture");

        versionLabel.setText("<version>");

        authorLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        authorLabel.setText("Alex Kersten");

        githubButton.setText("GitHub Page");
        githubButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                githubButtonActionPerformed(evt);
            }
        });

        dividebyxeroButton.setText("dividebyxero.com");
        dividebyxeroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dividebyxeroButtonActionPerformed(evt);
            }
        });

        alexkerstenButton.setText("alexkersten.com");
        alexkerstenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alexkerstenButtonActionPerformed(evt);
            }
        });

        byLabel.setText("by");

        githubLabel.setText("Source code and revision history");

        dividebyxeroLabel.setText("Blog and DBXCapture project page");

        alexkerstenLabel.setText("Information about me and other projects");

        javax.swing.GroupLayout aboutPanelLayout = new javax.swing.GroupLayout(aboutPanel);
        aboutPanel.setLayout(aboutPanelLayout);
        aboutPanelLayout.setHorizontalGroup(
            aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconLabel)
                .addGap(18, 18, 18)
                .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(versionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(aboutPanelLayout.createSequentialGroup()
                        .addComponent(dbxcaptureLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(byLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(authorLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(aboutPanelLayout.createSequentialGroup()
                        .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(githubButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(alexkerstenButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dividebyxeroButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(githubLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dividebyxeroLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(alexkerstenLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))))
                .addContainerGap())
        );
        aboutPanelLayout.setVerticalGroup(
            aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(aboutPanelLayout.createSequentialGroup()
                        .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dbxcaptureLabel)
                            .addComponent(authorLabel)
                            .addComponent(byLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(versionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(githubButton)
                            .addComponent(githubLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dividebyxeroButton)
                            .addComponent(dividebyxeroLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(alexkerstenButton)
                            .addComponent(alexkerstenLabel)))
                    .addComponent(iconLabel))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        settingsTabbedPane.addTab("About", aboutPanel);

        cancelButton.setText("Discard Changes");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        applyButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        applyButton.setText("Apply Changes");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(applyButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(settingsTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(settingsTabbedPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(applyButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void alexkerstenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alexkerstenButtonActionPerformed
        try {
            Desktop.getDesktop().browse(
                    new URI("http://alexkersten.com"));
        } catch (IOException | URISyntaxException ex) {
            JOptionPane.showMessageDialog(this, "Couldn't open browser.",
                                          "DBXCapture - Warning",
                                          JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_alexkerstenButtonActionPerformed

    private void triggerTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_triggerTextFieldKeyPressed
        triggerTextField.setText(evt.getKeyCode() + "");
    }//GEN-LAST:event_triggerTextFieldKeyPressed

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        if (!setAndSaveSettings()) {
            JOptionPane.showMessageDialog(
                    this, "There was a problem applying changes, please make "
                          + "sure all inputs are valid.\nAlso, be sure that "
                          + "specified directories actually exist.",
                    "DBXCapture - Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        setVisible(false);
    }//GEN-LAST:event_applyButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        setToLastKnown();
        setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void githubButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_githubButtonActionPerformed
        try {
            Desktop.getDesktop().browse(
                    new URI("https://github.com/akersten/dbx-capture"));
        } catch (IOException | URISyntaxException ex) {
            JOptionPane.showMessageDialog(this, "Couldn't open browser.",
                                          "DBXCapture - Warning",
                                          JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_githubButtonActionPerformed

    private void triggerTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_triggerTextFieldFocusGained
        triggerTextField.setBackground(new Color(255, 204, 255));
        //The finished button doesn't actually do anything, it just works as
        //a nice placebo to take the focus away from the form to prevent
        //accidental changes.
        triggerFinishedButton.setEnabled(true);

    }//GEN-LAST:event_triggerTextFieldFocusGained

    private void triggerTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_triggerTextFieldFocusLost
        triggerTextField.setBackground(new Color(255, 255, 204));
        triggerFinishedButton.setEnabled(false);
    }//GEN-LAST:event_triggerTextFieldFocusLost

    private void resetTriggerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetTriggerButtonActionPerformed
        triggerTextField.setText("44");
    }//GEN-LAST:event_resetTriggerButtonActionPerformed

    private void dividebyxeroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dividebyxeroButtonActionPerformed
        try {
            Desktop.getDesktop().browse(
                    new URI("http://dividebyxero.com"));
        } catch (IOException | URISyntaxException ex) {
            JOptionPane.showMessageDialog(this, "Couldn't open browser.",
                                          "DBXCapture - Warning",
                                          JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_dividebyxeroButtonActionPerformed

    private void videoTriggerTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_videoTriggerTextFieldFocusGained
        videoTriggerTextField.setBackground(new Color(255, 204, 255));
        //The finished button doesn't actually do anything, it just works as
        //a nice placebo to take the focus away from the form to prevent
        //accidental changes.
        videoTriggerFinishedButton.setEnabled(true);
    }//GEN-LAST:event_videoTriggerTextFieldFocusGained

    private void videoTriggerTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_videoTriggerTextFieldFocusLost
        videoTriggerTextField.setBackground(new Color(255, 255, 204));
        videoTriggerFinishedButton.setEnabled(false);
    }//GEN-LAST:event_videoTriggerTextFieldFocusLost

    private void videoTriggerTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_videoTriggerTextFieldKeyPressed
        videoTriggerTextField.setText(evt.getKeyCode() + "");
    }//GEN-LAST:event_videoTriggerTextFieldKeyPressed

    private void videoTriggerResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_videoTriggerResetButtonActionPerformed
        videoTriggerTextField.setText("19");
    }//GEN-LAST:event_videoTriggerResetButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aboutPanel;
    private javax.swing.JButton alexkerstenButton;
    private javax.swing.JLabel alexkerstenLabel;
    private javax.swing.JButton applyButton;
    private javax.swing.JLabel authorLabel;
    private javax.swing.JLabel byLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.ButtonGroup captureMethodButtonGroup;
    private javax.swing.JCheckBox confirmExitCheckBox;
    private javax.swing.JPanel contentDirectoryPanel;
    private javax.swing.JTextField contentDirectoryTextField;
    private javax.swing.JPanel croppingModePanel;
    private javax.swing.JComboBox currentScriptComboBox;
    private javax.swing.JPanel currentScriptPanel;
    private javax.swing.JPanel dbxcSettingsPanel;
    private javax.swing.JLabel dbxcaptureLabel;
    private javax.swing.JRadioButton dedicatedCropRadioButton;
    private javax.swing.JButton dividebyxeroButton;
    private javax.swing.JLabel dividebyxeroLabel;
    private javax.swing.ButtonGroup editingModeButtonGroup;
    private javax.swing.JLabel example1Label;
    private javax.swing.JLabel example2Label;
    private javax.swing.JRadioButton fullscreenCropRadioButton;
    private javax.swing.JPanel generalSettingsPanel;
    private javax.swing.JButton githubButton;
    private javax.swing.JLabel githubLabel;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel imagepath1Label;
    private javax.swing.JLabel imagepath2Label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JCheckBox localmodeCheckBox;
    private javax.swing.JLabel localmodeLabel;
    private javax.swing.JRadioButton noCropRadioButton;
    private javax.swing.JButton resetTriggerButton;
    private javax.swing.JPanel screenshotSettingsPanel;
    private javax.swing.JPanel scriptVariablesPanel;
    private javax.swing.JLabel scriptinfo1Label;
    private javax.swing.JLabel scriptinfo2Label;
    private javax.swing.JLabel scriptinfo3Label;
    private javax.swing.JPanel scriptsSettingsPanel;
    private javax.swing.JTabbedPane settingsTabbedPane;
    private javax.swing.JLabel trigger3Label;
    private javax.swing.JButton triggerFinishedButton;
    private javax.swing.JTextField triggerTextField;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JPanel videoPanel;
    private javax.swing.ButtonGroup videoResolutionButtonGroup;
    private javax.swing.JButton videoTriggerFinishedButton;
    private javax.swing.JLabel videoTriggerLabel;
    private javax.swing.JPanel videoTriggerPanel;
    private javax.swing.JButton videoTriggerResetButton;
    private javax.swing.JTextField videoTriggerTextField;
    // End of variables declaration//GEN-END:variables

}
