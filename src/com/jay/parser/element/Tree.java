package com.jay.parser.element;

import java.util.List;

import com.jay.ast.AstNode;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.parser.Parser;

public class Tree extends Element {
    protected Parser parser;

    public Tree(Parser p) {
        parser = p;
    }

    public void parse(Lexer lexer, List<AstNode> res) throws ParseException {
        res.add(parser.parse(lexer));
    }

    public boolean match(Lexer lexer) throws ParseException {
        return parser.match(lexer);
    }
}