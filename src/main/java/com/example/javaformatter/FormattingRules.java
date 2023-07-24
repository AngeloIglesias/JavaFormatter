package com.example.javaformatter;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;

import com.github.javaparser.printer.configuration.PrettyPrinterConfiguration;


public class FormattingRules {

    public static String prettyPrint(CompilationUnit cu) {

        // Configure the pretty printer
        PrettyPrinterConfiguration config = new PrettyPrinterConfiguration();
        config.setIndentSize(4);  // Set the number of characters for each indentation level
        config.setTabWidth(4);  // Set the width of a tab character

        // Return the formatted code
        return cu.toString(config);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void formatPublicMethods(CompilationUnit cu) {
        cu.findAll(MethodDeclaration.class)
                .stream()
                .filter(method -> method.isPublic() && !method.getComment().isPresent())
                .forEach(method -> method.setComment(new JavadocComment("Dr. Me")));
    }


    //Line Comments are not supported in used version of the JavaParser
    public static void formatLineComments(CompilationUnit cu) {
        for (Comment comment : cu.getAllComments()) {
            if (comment instanceof LineComment) {
                LineComment lineComment = (LineComment) comment;
                lineComment.setContent(formatLineCommentContent(lineComment.getContent()));
            }
        }
    }


    //// UTIL:
    private static String formatLineCommentContent(String content) {
        String[] words = content.split(" ");
        StringBuilder newContent = new StringBuilder();
        StringBuilder line = new StringBuilder("//");

        for (String word : words) {
            if (line.length() + word.length() > 120) {
                newContent.append(line).append("\n");
                line = new StringBuilder("//");
            }
            line.append(word).append(" ");
        }
        newContent.append(line);

        return newContent.toString();
    }


    //Block Comments are not supported in used version of the JavaParser
//    public static void formatComments(CompilationUnit cu, int maxLineLength) {
//        for (Comment comment : cu.getAllComments()) {
//            String content = comment.getContent();
//            if (content.length() <= maxLineLength) {
//                continue;
//            }
//
//            String[] words = content.split(" ");
//            StringBuilder newContent = new StringBuilder();
//            StringBuilder line = new StringBuilder();
//
//            for (String word : words) {
//                if (line.length() + word.length() > maxLineLength) {
//                    newContent.append(line.toString().trim()).append("\n");
//                    line = new StringBuilder();
//                }
//                line.append(word).append(" ");
//            }
//
//            // Add any remaining text
//            newContent.append(line.toString().trim());
//
//            comment.setContent(newContent.toString());
//        }
//    }


}
