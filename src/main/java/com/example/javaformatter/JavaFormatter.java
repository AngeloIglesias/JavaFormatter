package com.example.javaformatter;

import com.example.javaformatter.nodes.Clazz;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.stmt.*;
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

        cu.findAll(MethodDeclaration.class).forEach(method -> {
            method.getChildNodes().stream()
                    .filter(child -> child instanceof BlockStmt)
                    .map(child -> (BlockStmt) child)
                    .forEach(block -> {
                        NodeList<Statement> statements = block.getStatements();
                        for (int i = 0; i < statements.size(); i++) {
                            Statement stmt = statements.get(i);
                            Statement indentedStatement = StaticJavaParser.parseStatement(
                                    stmt.toString().replaceAll("([ab])", " ")
                            );
                            statements.set(i, indentedStatement);
                        }
                    });
        });



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        String print = LexicalPreservingPrinter.print(cu);

        System.out.println(print); //DEBUG

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
