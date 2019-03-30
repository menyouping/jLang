package com.jay.ast;

import java.util.List;

public class ClassStmnt extends AstList {

    public ClassStmnt(List<AstNode> c) {
        super(c);
    }

    public String name() {
        return ((AstLeaf) childAt(0)).token().getText();
    }

    public String superClass() {
        if (numChildren() < 3) {
            return null;
        } else {
            return ((AstLeaf) childAt(1)).token().getText();
        }
    }

    public ClassBody body() {
        return (ClassBody) childAt(numChildren() - 1);
    }

    public String toStirng() {
        String parent = superClass();
        if (parent == null) {
            parent = "*";
        }
        return "(class " + name() + " " + parent + " " + body() + ")";
    }
}