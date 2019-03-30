package com.jay.parser.element;

import java.util.List;

import com.jay.ast.AstNode;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.parser.Parser;

public class OrTree extends Element {
    protected Parser[] parsers;
    public OrTree(Parser[] p) { parsers = p; }
    public void parse(Lexer lexer, List<AstNode> res)
        throws ParseException
    {
        Parser p = choose(lexer);
        if (p == null)
            throw new ParseException(lexer.peek(0));
        else
            res.add(p.parse(lexer));
    }
    public boolean match(Lexer lexer) throws ParseException {
        return choose(lexer) != null;
    }
    protected Parser choose(Lexer lexer) throws ParseException {
        for (Parser p: parsers)
            if (p.match(lexer))
                return p;

        return null;
    }
    public void insert(Parser p) {
        Parser[] newParsers = new Parser[parsers.length + 1];
        newParsers[0] = p;
        System.arraycopy(parsers, 0, newParsers, 1, parsers.length);
        parsers = newParsers;
    }
}