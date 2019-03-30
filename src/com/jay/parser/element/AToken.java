package com.jay.parser.element;

import java.util.List;

import com.jay.ast.AstLeaf;
import com.jay.ast.AstNode;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.lexer.Token;
import com.jay.parser.Factory;

public abstract class AToken extends Element {
    protected Factory factory;

    protected AToken(Class<? extends AstLeaf> type) {
        if (type == null) {
            type = AstLeaf.class;
        }
        factory = Factory.get(type, Token.class);
    }

    public void parse(Lexer lexer, List<AstNode> res) throws ParseException {
        Token t = lexer.read();
        if (test(t)) {
            AstNode leaf = factory.make(t);
            res.add(leaf);
        } else {
            throw new ParseException(t);
        }
    }

    public boolean match(Lexer lexer) throws ParseException {
        return test(lexer.peek(0));
    }

    protected abstract boolean test(Token t);
}