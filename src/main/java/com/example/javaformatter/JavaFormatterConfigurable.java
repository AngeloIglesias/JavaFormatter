package com.example.javaformatter;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Erstellt eine neue Seite unter "Settings -> Tools -> JavaFormatter".
 */
public class JavaFormatterConfigurable implements Configurable {
    @Override
    public String getDisplayName() {
        return "JavaFormatter";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return new JPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() {
    }

    @Override
    public void reset() {
    }
}
