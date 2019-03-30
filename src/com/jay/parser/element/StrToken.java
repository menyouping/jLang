package com.jay.parser.element;

import com.jay.ast.AstLeaf;
import com.jay.lexer.Token;

public class StrToken extends AToken {
    public StrToken(Class<? extends AstLeaf> type) { super(type); }
    protected boolean test(Token t) { return t.isString(); }
}