package com.jay.parser;

import static com.jay.parser.Parser.rule;

import com.jay.ast.ArrayLiteral;
import com.jay.ast.ArrayRef;

import javassist.gluonj.Reviser;

@Reviser
public class ArrayParser extends FuncParser {
    Parser elements = rule(ArrayLiteral.class).ast(expr).repeat(rule().sep(",").ast(expr));

    public ArrayParser() {
        reserved.add("]");
        primary.insertChoice(rule().sep("[").maybe(elements).sep("]"));
        postfix.insertChoice(rule(ArrayRef.class).sep("[").ast(expr).sep("]"));
    }
}