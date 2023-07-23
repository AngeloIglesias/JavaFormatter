package com.example.javaformatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomFormattingModelBuilder implements FormattingModelBuilder {

    @Override
    public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
        PsiElement element = formattingContext.getPsiElement();
        PsiFile file = element.getContainingFile();

        // Use the JavaFormatter instance
        JavaFormatter.getInstance().format(file);

        return FormattingModelProvider.createFormattingModelForPsiFile(file,
                new CustomBlock(file.getNode(), Wrap.createWrap(WrapType.NONE, false),
                        Alignment.createAlignment()), formattingContext.getCodeStyleSettings());
    }

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, PlainTextLanguage.INSTANCE)
                .around(TokenType.WHITE_SPACE).spaces(1);
    }
}
