package com.jay.ast;

import java.util.List;

public class Lambda extends AstList {

    public Lambda(List<AstNode> c) {
        super(c);
    }

    public ParameterList parameters() {
        return (ParameterList) childAt(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt) childAt(1);
    }

    public String toString() {
        return "(" + parameters() + " => " + body() + ")";
    }
}