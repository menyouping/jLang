package com.jay.parser.element;

import java.util.List;

import com.jay.ast.AstNode;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;

public abstract class Element {
    /**
     * 语法分析
     * @param lexer
     * @param res
     * @throws ParseException
     */
    public abstract void parse(Lexer lexer, List<AstNode> res) throws ParseException;

    public abstract boolean match(Lexer lexer) throws ParseException;
}