package com.example.javaformatter;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class JavaFormatterConfigurable implements Configurable {

    private JavaFormatterSettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "JavaFormatter";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new JavaFormatterSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        JavaFormatter javaFormatter = JavaFormatter.getInstance();
        boolean isEnableFormatting = settingsComponent.getEnableFormatting();
        return javaFormatter.isEnabled() != isEnableFormatting;
    }

    @Override
    public void apply() {
        JavaFormatter javaFormatter = JavaFormatter.getInstance();
        javaFormatter.setEnabled(settingsComponent.getEnableFormatting());
    }

    @Override
    public void reset() {
        JavaFormatter javaFormatter = JavaFormatter.getInstance();
        settingsComponent.setEnableFormatting(javaFormatter.isEnabled());
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
