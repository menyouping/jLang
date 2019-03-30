package com.jay.ast;

import java.util.List;

public class FuncStmnt extends AstList {

    public FuncStmnt(List<AstNode> list) {
        super(list);
    }

    public String name() {
        return ((AstLeaf) childAt(0)).token().getText();
    }

    public ParameterList parameters() {
        return (ParameterList) childAt(1);
    }

    public BlockStmnt body() {
        return (BlockStmnt) childAt(2);
    }

    public String toString() {
        return "(func " + name() + " " + parameters() + " " + body() + ")";
    }
}