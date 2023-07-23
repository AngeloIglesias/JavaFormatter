package com.example.javaformatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.formatter.common.AbstractBlock;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Just an example for reformatting a block
 */
public class CustomBlock extends AbstractBlock {
    protected CustomBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }

    @Override
    protected List<Block> buildChildren() {
        // We return an empty list since we do not consider children in this implementation
        return Collections.emptyList();
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        // Return default spacing
        return Spacing.createSpacing(1, 1, 1, true, 1);
    }
}
