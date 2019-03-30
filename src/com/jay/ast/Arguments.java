package com.jay.ast;

import java.util.List;

public class Arguments extends Postfix {
    public Arguments(List<AstNode> c) {
        super(c);
    }

    public int size() {
        return numChildren();
    }
}