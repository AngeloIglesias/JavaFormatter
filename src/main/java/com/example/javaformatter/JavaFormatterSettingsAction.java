package com.example.javaformatter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBCheckBox;

import javax.swing.*;

public class JavaFormatterSettingsAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        DialogBuilder builder = new DialogBuilder();

        JBCheckBox checkBox = new JBCheckBox("Enable Java Formatting", JavaFormatter.getInstance().isEnabled());
        builder.setCenterPanel(checkBox);
        builder.setTitle("Java Formatter Settings");
        builder.addOkAction();
        builder.setOkOperation(() -> {
            JavaFormatter.getInstance().setEnabled(checkBox.isSelected());
            builder.getDialogWrapper().close(DialogWrapper.OK_EXIT_CODE);
        });

        builder.show();
    }
}
