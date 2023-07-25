package com.example.javaformatter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.printer.PrettyPrintVisitor;
import com.github.javaparser.printer.configuration.PrettyPrinterConfiguration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

public class Main {
    public static void main(String[] args) {
        String javaCode = "class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World!\");\n" +
                "               System.out.println(\"Hello, World!\");\n" +
                "    }\n" +
                "}";

        CompilationUnit cu;

//        System.out.println(file.getText()); //DEBUG

        // Parse the file with JavaParser
        try {
            // Versuchen, die Formatierung auszuführen
            cu = StaticJavaParser.parse(javaCode);
        } catch (ParseProblemException e) {
            // Wrong Java Code as Input
            System.err.println("Fehler beim Parsen des Codes: " + e.getMessage());
            return;
        }

        try {
            // Reformat the code using PrettyPrintVisitor
//            CompilationUnit cu = JavaParser.parse(javaCode);
//            cu.accept(new PrettyPrintVisitor(new PrettyPrinterConfiguration(), new StringBuilder()));
            javaCode = cu.toString();

            // Set the custom indentation settings in the parser configuration
            ParserConfiguration parserConfiguration = new ParserConfiguration()
                    .setTabSize(4);
            JavaParser javaParser = new JavaParser(parserConfiguration);

            // Parse the input Java code with the custom indentation settings
            cu = javaParser.parse(javaCode).getResult().orElseThrow(() -> new ParseProblemException(new Exception())); //ToDo: Problems

            // Setup the LexicalPreservingPrinter to preserve lexical formatting
            LexicalPreservingPrinter.setup(cu);

            // Get the method declaration node
            MethodDeclaration methodDeclaration = cu.findFirst(MethodDeclaration.class).orElse(null);
            if (methodDeclaration != null) {
                // Change the indentation of the method body
                methodDeclaration.getBody().get().findAll(MethodDeclaration.class).forEach(m ->
                        m.setBlockComment(" This is a block comment\n    "));

                // Print the formatted code with preserved lexical formatting
                String formattedCode = LexicalPreservingPrinter.print(cu);
                System.out.println(formattedCode);
            }
        } catch (ParseProblemException e) {
            e.printStackTrace();
            // Handle parsing errors
        }
    }
}
