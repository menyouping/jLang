package com.jay.ast;

import java.util.List;

public class WhileStmnt extends AstList {
    public WhileStmnt(List<AstNode> c) {
        super(c);
    }

    public AstNode condition() {
        return childAt(0);
    }

    public AstNode body() {
        return childAt(1);
    }

    public String toString() {
        return "(while " + condition() + " " + body() + ")";
    }
}