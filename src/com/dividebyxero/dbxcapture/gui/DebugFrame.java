/*
 Project: dbx-capture
 File: DebugFrame.java (com.dividebyxero.dbxcapture.gui)
 Author: Alex Kersten
 */

/*
 * DebugFrame.java
 *
 * Created on Jun 3, 2013, 12:40:20 AM
 */
package com.dividebyxero.dbxcapture.gui;

import com.dividebyxero.dbxcapture.DBXCRuntime;
import javax.imageio.ImageIO;

/**
 *
 * @author Alex Kersten
 */
@SuppressWarnings("serial")
public class DebugFrame extends javax.swing.JFrame {

    private DBXCRuntime context;

    /**
     * Creates new form DebugFrame
     */
    public DebugFrame(DBXCRuntime context) {
        if (context == null) {
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        debugLabel = new javax.swing.JLabel();
        dumpBitmapButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DBXCapture - Debug");
        setResizable(false);

        debugLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dividebyxero/dbxcapture/gui/icon128.png"))); // NOI18N
        debugLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        dumpBitmapButton.setText("Native bitmap dump");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(debugLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dumpBitmapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(debugLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dumpBitmapButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel debugLabel;
    private javax.swing.JButton dumpBitmapButton;
    // End of variables declaration//GEN-END:variables

}