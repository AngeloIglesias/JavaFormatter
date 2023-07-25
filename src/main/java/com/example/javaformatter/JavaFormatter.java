package com.example.javaformatter;

import com.example.javaformatter.nodes.Clazz;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.printer.PrettyPrintVisitor;
import com.github.javaparser.printer.configuration.PrettyPrinterConfiguration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for reformatting the code
 */
public class JavaFormatter {
    private static JavaFormatter instance = null;

    private boolean enabled = true;

    // Private constructor to prevent instantiation
    private JavaFormatter() {
    }

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

        CompilationUnit cu;

//        System.out.println(file.getText()); //DEBUG

        // Parse the file with JavaParser
        try {
            // Versuchen, die Formatierung auszuführen
            cu = StaticJavaParser.parse(file.getText());
        } catch (ParseProblemException e) {
            // Wrong Java Code as Input
            System.err.println("Fehler beim Parsen des Codes: " + e.getMessage());
            return;
        }

        // Enable lexical preserving printing
        LexicalPreservingPrinter.setup(cu);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Set the custom indentation settings in the parser configuration
        ParserConfiguration parserConfiguration = new ParserConfiguration()
                .setTabSize(4);
        JavaParser javaParser = new JavaParser(parserConfiguration);




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        String print2 = LexicalPreservingPrinter.print(cu);

        System.out.println(print2); //DEBUG

        // Replace the file text with the formatted code
        //Use WriteCommandAction to ensure that this is run within a write action
        WriteCommandAction.runWriteCommandAction(file.getProject(), () ->
                file.getViewProvider().getDocument().setText(print2)
        );
    }


    public boolean isEnabled() {
        return enabled;
    }
}
