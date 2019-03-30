package com.jay.ast;

import java.util.List;

/**
 * 双目运算表达式
 * @author jay
 *
 */
public class BinaryExpr extends AstList {
    public BinaryExpr(List<AstNode> c) {
        super(c);
    }

    public AstNode left() {
        return childAt(0);
    }

    public String operator() {
        return ((AstLeaf) childAt(1)).token().getText();
    }

    public AstNode right() {
        return childAt(2);
    }
}