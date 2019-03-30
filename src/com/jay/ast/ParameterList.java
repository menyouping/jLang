package com.jay.ast;

import java.util.List;

public class ParameterList extends AstList {

    public ParameterList(List<AstNode> list) {
        super(list);
    }

    public String name(int i) {
        return ((AstLeaf) childAt(i)).token().getText();
    }

    public int size() {
        return numChildren();
    }
}