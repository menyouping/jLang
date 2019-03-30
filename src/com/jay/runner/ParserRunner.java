package com.jay.runner;

import com.jay.ast.AstNode;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.lexer.Token;
import com.jay.parser.BasicParser;

public class ParserRunner {
    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        BasicParser bp = new BasicParser();
        while (l.peek(0) != Token.EOF) {
            AstNode ast = bp.parse(l);
            System.out.println("=> " + ast.toString());
        }
    }
}
