package com.example.javaformatter;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;

public class FormattingRules {

    public static void addCommentsToPublicMethods(CompilationUnit cu) {
        cu.findAll(MethodDeclaration.class)
                .stream()
                .filter(method -> method.isPublic() && !method.getComment().isPresent())
                .forEach(method -> method.setComment(new JavadocComment("Dr. Me")));
    }

}
