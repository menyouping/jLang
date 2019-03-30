package com.jay.ast;

import com.jay.lexer.Token;

public class StringLiteral extends AstLeaf {
    public StringLiteral(Token t) {
        super(t);
    }

    public String value() {
        return token().getText();
    }
}