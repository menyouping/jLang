package com.jay.ast;

import java.util.List;

public class ReturnStmnt extends AstList {
    public ReturnStmnt(List<AstNode> c) {
        super(c);
    }

    public AstNode value() {
        return numChildren() == 0 ? null : childAt(0);
    }

    public String toString() {
        return "(return " + value() + ")";
    }
}