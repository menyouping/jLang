package com.jay.parser.element;

import java.util.HashSet;
import java.util.Set;

import com.jay.ast.AstLeaf;
import com.jay.lexer.Token;

public class IdToken extends AToken {
    Set<String> reserved;

    public IdToken(Class<? extends AstLeaf> type, Set<String> r) {
        super(type);
        reserved = r != null ? r : new HashSet<String>();
    }
    protected boolean test(Token t) {
        return t.isIdentifier() && !reserved.contains(t.getText());
    }
}