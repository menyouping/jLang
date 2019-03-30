package com.jay.parser.element;

import com.jay.ast.AstLeaf;
import com.jay.lexer.Token;

public class NumToken extends AToken {
    public NumToken(Class<? extends AstLeaf> type) {
        super(type);
    }

    public boolean test(Token t) {
        return t.isNumber();
    }
}