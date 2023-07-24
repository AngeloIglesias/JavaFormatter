package com.example.javaformatter;

import com.example.javaformatter.Formatter.Method;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiFile;

import static com.example.javaformatter.FormattingRules.*;

/**
 * Class for reformatting the code
 */
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

        CompilationUnit cu;

        System.out.println(file.getText()); //DEBUG

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
        // Apply formatting rules
/*        formatPublicMethods(cu);
//        formatLineComments(cu);
        prettyPrint(cu);*/

        Method.format(cu);

        // Print the formatted code
        String print = LexicalPreservingPrinter.print(cu);
        return print;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
