package com.example.javaformatter;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.intellij.psi.PsiFile;

public class JavaFormatter {
    public void format(PsiFile file) {

        // Parse the file with JavaParser
        CompilationUnit cu = StaticJavaParser.parse(file.getText());

        // Enable lexical preserving printing
        LexicalPreservingPrinter.setup(cu);

        // Replace the file text with the formatted code
        file.getViewProvider().getDocument().setText(LexicalPreservingPrinter.print(cu));

    }
}
