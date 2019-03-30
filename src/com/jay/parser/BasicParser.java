/**
 * A basic Parser for Stone grammatical analysis
 */
package com.jay.parser;

import static com.jay.parser.Parser.rule;

import java.util.HashSet;
import java.util.Set;

import com.jay.ast.AstNode;
import com.jay.ast.BinaryExpr;
import com.jay.ast.BlockStmnt;
import com.jay.ast.IfStmnt;
import com.jay.ast.Name;
import com.jay.ast.NegativeExpr;
import com.jay.ast.NullStmnt;
import com.jay.ast.NumberLiteral;
import com.jay.ast.PrimaryExpr;
import com.jay.ast.ReturnStmnt;
import com.jay.ast.StringLiteral;
import com.jay.ast.WhileStmnt;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.lexer.Token;
import com.jay.parser.element.Operators;

public class BasicParser {
    Set<String> reserved = new HashSet<String>();
    Operators operators = new Operators();
    Parser expr0 = rule();
    Parser primary = rule(PrimaryExpr.class)//
            .or(rule().sep("(").ast(expr0).sep(")"), //
                    rule().number(NumberLiteral.class), //
                    rule().identifier(Name.class, reserved), //
                    rule().string(StringLiteral.class));
    Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary), //
            primary);
    Parser expr = expr0.expression(BinaryExpr.class, factor, operators);

    Parser statement0 = rule();
    Parser block = rule(BlockStmnt.class)//
            .sep("{").option(statement0)//
            .repeat(rule().sep(";", Token.EOL).option(statement0))//
            .sep("}");
    Parser simple = rule(PrimaryExpr.class).ast(expr);//
    Parser statement = statement0.or(//
            rule(IfStmnt.class).sep("if").ast(expr).ast(block)//
                    .repeat(rule().sep("elif").ast(expr).ast(block))//
                    .option(rule().sep("else").ast(block)), //
            rule(WhileStmnt.class).sep("while").ast(expr).ast(block), //
//            rule(WhileStmnt.class).sep("for").//
//            repeat(rule().ast(expr))//
//            .ast(block), //
            rule(ReturnStmnt.class).sep("return").option(expr), //
            simple);

    Parser program = rule().or(statement, rule(NullStmnt.class)).sep(";", Token.EOL);

    public BasicParser() {
        reserved.add(";");
        reserved.add("}");
        reserved.add(Token.EOL);

        operators.add("=", 1, Operators.RIGHT);
        operators.add("==", 2, Operators.LEFT);
        operators.add(">", 2, Operators.LEFT);
        operators.add("<", 2, Operators.LEFT);
        operators.add("+", 3, Operators.LEFT);
        operators.add("-", 3, Operators.LEFT);
        operators.add("*", 4, Operators.LEFT);
        operators.add("/", 4, Operators.LEFT);
        operators.add("%", 4, Operators.LEFT);
        operators.add("^", 5, Operators.LEFT);
    }

    public AstNode parse(Lexer lexer) throws ParseException {
        return program.parse(lexer);
    }
}
