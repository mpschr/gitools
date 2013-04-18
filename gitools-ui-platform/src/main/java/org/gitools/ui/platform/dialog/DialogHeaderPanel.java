/*
 * #%L
 * gitools-ui-platform
 * %%
 * Copyright (C) 2013 Universitat Pompeu Fabra - Biomedical Genomics group
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.gitools.ui.platform.dialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DialogHeaderPanel extends javax.swing.JPanel {

    private static final Color INFO_COLOR = Color.WHITE;
    private static final Color WARN_COLOR = new Color(250, 250, 160);
    private static final Color ERROR_COLOR = new Color(250, 150, 150);
    private static final Color PROGRESS_COLOR = new Color(100, 180, 250);

    private MessageStatus messageStatus;
    @Nullable
    private String leftLogoLink;
    @Nullable
    private String rightLogoLink;

    public DialogHeaderPanel() {
        initComponents();

        title.setFont(title.getFont().deriveFont(Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        message.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        messageStatus = MessageStatus.INFO;
        leftLogo.setText("");
        leftLogo.setVisible(false);
        rightLogo.setText("");
    }

    public DialogHeaderPanel(String header, String message, @NotNull MessageStatus status, Icon logo) {
        this();

        setTitle(header);
        setMessageStatus(status);
        setMessage(message);
        setRightLogo(logo);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        message = new javax.swing.JLabel();
        rightLogo = new javax.swing.JLabel();
        leftLogo = new javax.swing.JLabel();

        setBackground(java.awt.Color.white);
        setFocusable(false);

        title.setText("Title");
        title.setFocusable(false);
        title.setRequestFocusEnabled(false);

        message.setBackground(java.awt.Color.white);
        message.setText("Message");
        message.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        message.setFocusable(false);
        message.setOpaque(true);
        message.setRequestFocusEnabled(false);
        message.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        rightLogo.setText("RightLogo");
        rightLogo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        rightLogo.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        rightLogo.setFocusable(false);
        rightLogo.setRequestFocusEnabled(false);
        rightLogo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        rightLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rightLogoMouseClicked(evt);
            }
        });

        leftLogo.setText("LeftLogo");
        leftLogo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        leftLogo.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        leftLogo.setFocusable(false);
        leftLogo.setRequestFocusEnabled(false);
        leftLogo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        leftLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leftLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(leftLogo).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE).addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(rightLogo)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(title).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(message).addContainerGap(28, Short.MAX_VALUE)).addComponent(leftLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE).addComponent(rightLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents

    private void rightLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightLogoMouseClicked
        if (rightLogoLink != null) {
            try {
                Desktop.getDesktop().browse(new URL(rightLogoLink).toURI());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_rightLogoMouseClicked

    private void leftLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leftLogoMouseClicked
        if (leftLogoLink != null) {
            try {
                Desktop.getDesktop().browse(new URL(leftLogoLink).toURI());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_leftLogoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel leftLogo;
    private javax.swing.JLabel message;
    private javax.swing.JLabel rightLogo;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables

    public String getTitle() {
        return title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public Font getTitleFont() {
        return title.getFont();
    }

    public void setTitleFont(Font font) {
        title.setFont(font);
    }

    public Color getTitleColor() {
        return title.getForeground();
    }

    public void setTitleColor(Color color) {
        title.setForeground(color);
    }

    public String getMessage() {
        return message.getText();
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(@NotNull MessageStatus status) {
        this.messageStatus = status;
        //TODO status icon
        switch (status) {
            case INFO:
                message.setBackground(INFO_COLOR);
                break;
            case WARN:
                message.setBackground(WARN_COLOR);
                break;
            case ERROR:
                message.setBackground(ERROR_COLOR);
                break;
            case PROGRESS:
                message.setBackground(PROGRESS_COLOR);
                break;
        }
    }

    public Icon getRightLogo() {
        return rightLogo.getIcon();
    }

    public void setRightLogo(Icon logo) {
        this.rightLogo.setIcon(logo);
    }

    @Nullable
    public String getRightLogoLink() {
        return rightLogoLink;
    }

    public void setRightLogoLink(@Nullable String logoLink) {
        this.rightLogoLink = logoLink;
        if (logoLink == null) {
            rightLogo.setCursor(Cursor.getDefaultCursor());
        } else {
            rightLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    public Icon getLeftLogo() {
        return leftLogo.getIcon();
    }

    public void setLeftLogo(@Nullable Icon logo) {
        leftLogo.setIcon(logo);
        leftLogo.setVisible(logo != null);

    }

    @Nullable
    public String getLeftLogoLink() {
        return leftLogoLink;
    }

    public void setLeftLogoLink(@Nullable String logoLink) {
        this.leftLogoLink = logoLink;
        if (logoLink == null) {
            leftLogo.setCursor(Cursor.getDefaultCursor());
        } else {
            leftLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    public boolean isLeftLogoVisible() {
        return leftLogo.isVisible();
    }
}
