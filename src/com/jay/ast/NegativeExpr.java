package com.jay.ast;

import java.util.List;

public class NegativeExpr extends AstList {
    public NegativeExpr(List<AstNode> c) {
        super(c);
    }

    public AstNode operand() {
        return childAt(0);
    }

    public String toString() {
        return "-" + operand();
    }
}