package com.jay.ast;

import com.jay.lexer.IdToken;
import com.jay.lexer.Token;

public class Name extends AstLeaf {
    private static final String STR_BREAK = "break";

    public static final Name Break = new Name(new IdToken(-1, STR_BREAK));

    public Name(Token t) {
        super(t);
    }

    public static Name create(Token t) {
        if (STR_BREAK.equals(t.getText())) {
            return Break;
        }
        return new Name(t);
    }

    public String name() {
        return token().getText();
    }
}