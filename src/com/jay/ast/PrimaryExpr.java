package com.jay.ast;

import java.util.List;

public class PrimaryExpr extends AstList {
    public PrimaryExpr(List<AstNode> c) {
        super(c);
    }

    public static AstNode create(List<AstNode> c) {
        return c.size() == 1 ? c.get(0) : new PrimaryExpr(c);
    }
}