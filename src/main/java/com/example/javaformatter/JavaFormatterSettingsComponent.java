package com.example.javaformatter;

import javax.swing.*;

public class JavaFormatterSettingsComponent {
    private JPanel mainPanel;
    private JCheckBox enableFormattingCheckBox;

    public JPanel getPanel() {
        return mainPanel;
    }

    public boolean getEnableFormatting() {
        return enableFormattingCheckBox.isSelected();
    }

    public void setEnableFormatting(boolean enableFormatting) {
        enableFormattingCheckBox.setSelected(enableFormatting);
    }

    private void createUIComponents() {
        mainPanel = new JPanel();
        enableFormattingCheckBox = new JCheckBox("Enable Java Formatter");
        mainPanel.add(enableFormattingCheckBox);
    }
}
