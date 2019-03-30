package com.jay.ast;

import java.util.List;

public class ArrayRef extends Postfix {

    public ArrayRef(List<AstNode> c) {
        super(c);
    }

    public AstNode index() {
        return childAt(0);
    }

    public String toString() {
        return "[" + index() + "]";
    }
}
