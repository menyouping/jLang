package com.jay.ast;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.jay.lexer.Token;

/**
 * 叶节点（不含树枝的节点）的父类
 * @author jay
 *
 */
public class AstLeaf extends AstNode {
    private static final List<AstNode> empty = Collections.emptyList();
    protected Token token;

    public AstLeaf(Token t) {
        token = t;
    }

    public AstNode childAt(int i) {
        throw new IndexOutOfBoundsException();
    }

    public int numChildren() {
        return 0;
    }

    public Iterator<AstNode> children() {
        return empty.iterator();
    }

    public String toString() {
        return token.getText();
    }

    public String location() {
        return "at line " + token.getLineNumber();
    }

    public Token token() {
        return token;
    }
}