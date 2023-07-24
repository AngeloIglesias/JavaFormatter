package com.example.javaformatter;

import com.example.javaformatter.nodes.Clazz;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiFile;

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

        String print = applyFormattingRules(cu);

        // Replace the file text with the formatted code
        //Use WriteCommandAction to ensure that this is run within a write action
        WriteCommandAction.runWriteCommandAction(file.getProject(), () ->
                file.getViewProvider().getDocument().setText(print)
        );
    }

    private static String applyFormattingRules(CompilationUnit cu) {

        StringBuilder stringBuilder = new StringBuilder();
        processNode(cu, 0, stringBuilder);

        String result = stringBuilder.toString();
//        System.out.println(result); //DEBUG
        return result;
    }

    public static void processNode(Node node, int indentLevel, StringBuilder stringBuilder) {
        /*if (node instanceof ClassOrInterfaceDeclaration classOrInterfaceNode) {
            node.replace(Clazz.format(classOrInterfaceNode, indentLevel));
        }
        //ToDo: Implement all if-cases*/

        for (Node child : node.getChildNodes()) {
            int newIndentLevel = getIndentLevel(child, indentLevel);
            processNode(child, newIndentLevel, stringBuilder);
        }

        String str = postProcessNode(node, indentLevel);
        System.out.println(str);
        stringBuilder.append(str);
    }

    /**
     * Kann je nach Regeln angepasst werden, um die gewünschten Einrückungen (Indents) zu erhalten
     */
    private static int getIndentLevel(Node node, int currentIndentLevel) {
        if (shouldIncreaseIndent(node)) {
            return currentIndentLevel + 1;
        }
        return currentIndentLevel;
    }

    private static boolean shouldIncreaseIndent(Node node) {
        return node instanceof BlockStmt
                || node instanceof ArrayInitializerExpr
                || node instanceof InitializerDeclaration
                || node instanceof WhileStmt
                || node instanceof ForStmt
                || node instanceof TryStmt;
    }

    public static String postProcessNode(Node node, int indentLevel) {
        // If the node is a block statement, format it:
        if (node instanceof BlockStmt
                || node instanceof ClassOrInterfaceDeclaration
                || node instanceof MethodDeclaration
            // ... Add any other node types you wish to format
        ) {
            // Extract code for this node:
            String code = node.toString();

            // Set indentation level:
            return setIndentation(code, indentLevel);
        }

        // If the node is not a type you want to format, return it unchanged:
        return "";
    }


    private static String setIndentation(String code, int indentLevel) {
        // Create indentation as a series of tabs:
        String indentation = "\t".repeat(indentLevel);

        // Break code into lines:
        String[] lines = code.split("\n");

        // Remove existing indentation and add desired indentation:
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("^\\s*", indentation);
        }

        // Join lines back together:
        String newline = System.lineSeparator();
        return String.join(newline, lines);
    }

    private static Node parseCode(String code, Class<? extends Node> nodeType) {
        if (nodeType == MethodDeclaration.class) {
            return StaticJavaParser.parseMethodDeclaration(code);
        } else if (nodeType == BlockStmt.class) {
            return StaticJavaParser.parseBlock(code);
        }
        // TODO: Add more cases for other node types
        throw new RuntimeException("Don't know how to parse nodes of type " + nodeType.getName());
    }



    public boolean isEnabled() {
        return enabled;
    }
}
