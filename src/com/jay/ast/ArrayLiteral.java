package com.jay.ast;

import java.util.List;

public class ArrayLiteral extends AstList {

    public ArrayLiteral(List<AstNode> c) {
        super(c);
    }

    public int size() {
        return numChildren();
    }
}