package com.example.javaformatter;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.intellij.psi.PsiFile;

public class JavaFormatter {
    private static JavaFormatter instance = null;

    private boolean enabled = true;

    // Private constructor to prevent instantiation
    private JavaFormatter() {}

    // Method to get the singleton instance
    public static synchronized JavaFormatter getInstance() {
        if (instance == null) {
            instance = new JavaFormatter();
        }
        return instance;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void format(PsiFile file) {
        if (!enabled) {
            return;
        }

        // Parse the file with JavaParser
        CompilationUnit cu = StaticJavaParser.parse(file.getText());

        // Enable lexical preserving printing
        LexicalPreservingPrinter.setup(cu);

        // Replace the file text with the formatted code
        file.getViewProvider().getDocument().setText(LexicalPreservingPrinter.print(cu));
    }

    public boolean isEnabled() {
        return enabled;
    }
}
