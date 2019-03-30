package com.jay.ast;

import com.jay.lexer.Token;

public class NumberLiteral extends AstLeaf {
    public NumberLiteral(Token t) {
        super(t);
    }

    public Number value() {
        return token().getNumber();
    }
}