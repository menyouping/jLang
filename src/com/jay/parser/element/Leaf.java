package com.jay.parser.element;

import java.util.List;

import com.jay.ast.AstLeaf;
import com.jay.ast.AstNode;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.lexer.Token;

public class Leaf extends Element {
    protected String[] tokens;

    public Leaf(String[] pat) {
        tokens = pat;
    }

    public void parse(Lexer lexer, List<AstNode> res) throws ParseException {
        Token t = lexer.read();
        if (t.isIdentifier()) {
            for (String token : tokens) {
                if (token.equals(t.getText())) {
                    find(res, t);
                    return;
                }
            }
        }

        if (tokens.length > 0) {
            throw new ParseException(tokens[0] + " expected.", t);
        } else {
            throw new ParseException(t);
        }
    }

    protected void find(List<AstNode> res, Token t) {
        res.add(new AstLeaf(t));
    }

    public boolean match(Lexer lexer) throws ParseException {
        Token t = lexer.peek(0);
        if (t.isIdentifier()) {
            for (String token : tokens) {
                if (token.equals(t.getText())) {
                    return true;
                }
            }
        }

        return false;
    }
}