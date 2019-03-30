package com.jay.ast;

import java.util.List;

public class Dot extends Postfix {

    public Dot(List<AstNode> c) {
        super(c);
    }

    public String name() {
        return ((AstLeaf) childAt(0)).token().getText();
    }

    public String toString() {
        return "." + name();
    }
}