package com.jay.interpreter.basic;

import com.jay.ast.AstNode;
import com.jay.ast.NullStmnt;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.lexer.Token;
import com.jay.parser.BasicParser;
import com.jay.runner.CodeDialog;

public class BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new BasicParser(), new BasicEnv());
    }

    @SuppressWarnings("static-access")
    public static void run(BasicParser bp, Environment env) throws ParseException {
        boolean isdebugle = Boolean.TRUE.parseBoolean((String) env.get("debugable"));
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            AstNode t = bp.parse(lexer);
            if (!(t instanceof NullStmnt)) {
                Object r = ((BasicEvaluator.AstNodeEx) t).eval(env);
                if (isdebugle) {
                    System.out.println(t.location() + " => " + r);
                }
            }
        }
    }
}