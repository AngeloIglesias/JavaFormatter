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

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        String formattedCode = "";

        try {

            // Set the custom indentation settings in the parser configuration
            ParserConfiguration parserConfiguration = new ParserConfiguration()
                    .setTabSize(4);
            JavaParser javaParser = new JavaParser(parserConfiguration);

            // Parse the input Java code with the custom indentation settings
            cu = javaParser.parse(cu.toString()).getResult().orElseThrow(() -> new ParseProblemException(new Exception())); //ToDo: Problems

            // Setup the LexicalPreservingPrinter to preserve lexical formatting
            LexicalPreservingPrinter.setup(cu);

            // Get the method declaration node
            MethodDeclaration methodDeclaration = cu.findFirst(MethodDeclaration.class).orElse(null);
            if (methodDeclaration != null) {
                // Change the indentation of the method body
                methodDeclaration.getBody().get().findAll(MethodDeclaration.class).forEach(m ->
                        m.setBlockComment(" This is a block comment\n    "));

                // Print the formatted code with preserved lexical formatting
                formattedCode = LexicalPreservingPrinter.print(cu);
                System.out.println(formattedCode);
            }
        } catch (ParseProblemException e) {
            e.printStackTrace();
            // Handle parsing errors
        }




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        String print = formattedCode;

        // Replace the file text with the formatted code
        //Use WriteCommandAction to ensure that this is run within a write action
        WriteCommandAction.runWriteCommandAction(file.getProject(), () ->
                file.getViewProvider().getDocument().setText(print)
        );
    }


    public boolean isEnabled() {
        return enabled;
    }
}
