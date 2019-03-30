package com.jay.parser.element;

import java.util.List;

import com.jay.ast.AstNode;
import com.jay.lexer.Token;

public class Skip extends Leaf {
    public Skip(String[] t) { super(t); }
    protected void find(List<AstNode> res, Token t) {}
}