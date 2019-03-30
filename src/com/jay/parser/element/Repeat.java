package com.jay.parser.element;

import java.util.List;

import com.jay.ast.AstList;
import com.jay.ast.AstNode;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.parser.Parser;

public class Repeat extends Element {
    protected Parser parser;
    protected boolean onlyOnce;

    public Repeat(Parser p, boolean once) {
        parser = p;
        onlyOnce = once;
    }

    public void parse(Lexer lexer, List<AstNode> res) throws ParseException {
        while (parser.match(lexer)) {
            AstNode t = parser.parse(lexer);
            if (t.getClass() != AstList.class || t.numChildren() > 0) {
                res.add(t);
            }
            if (onlyOnce) {
                break;
            }
        }
    }

    public boolean match(Lexer lexer) throws ParseException {
        return parser.match(lexer);
    }
}