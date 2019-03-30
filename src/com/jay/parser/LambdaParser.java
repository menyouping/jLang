package com.jay.parser;

import static com.jay.parser.Parser.rule;

import com.jay.ast.Lambda;

public class LambdaParser extends FuncParser {
    public LambdaParser() {
        primary.insertChoice(rule(Lambda.class).ast(paramList).sep("=>").ast(block));
    }
}