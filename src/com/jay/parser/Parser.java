package com.jay.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jay.ast.AstLeaf;
import com.jay.ast.AstNode;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.parser.element.Element;
import com.jay.parser.element.Expr;
import com.jay.parser.element.IdToken;
import com.jay.parser.element.Leaf;
import com.jay.parser.element.NumToken;
import com.jay.parser.element.Operators;
import com.jay.parser.element.OrTree;
import com.jay.parser.element.Repeat;
import com.jay.parser.element.Skip;
import com.jay.parser.element.StrToken;
import com.jay.parser.element.Tree;

public class Parser {
    protected List<Element> elements;
    protected Factory factory;

    public Parser(Class<? extends AstNode> clazz) {
        reset(clazz);
    }

    protected Parser(Parser p) {
        elements = p.elements;
        factory = p.factory;
    }

    public AstNode parse(Lexer lexer) throws ParseException {
        List<AstNode> results = new ArrayList<>(elements.size());
        for (Element e : elements) {
            e.parse(lexer, results);
        }

        return factory.make(results);
    }

    public boolean match(Lexer lexer) throws ParseException {
        if (elements.isEmpty()) {
            return true;
        } else {
            Element e = elements.get(0);
            return e.match(lexer);
        }
    }

    /**
     * 开始一个规则
     * 
     * @return
     */
    public static Parser rule() {
        return rule(null);
    }

    /**
     * 开始一个规则
     * 
     * @return
     */
    public static Parser rule(Class<? extends AstNode> clazz) {
        return new Parser(clazz);
    }

    public Parser reset() {
        elements = new ArrayList<Element>();
        return this;
    }

    public Parser reset(Class<? extends AstNode> clazz) {
        elements = new ArrayList<Element>();
        factory = Factory.getForASTList(clazz);
        return this;
    }

    public Parser number() {
        return number(null);
    }

    public Parser number(Class<? extends AstLeaf> clazz) {
        elements.add(new NumToken(clazz));
        return this;
    }

    public Parser identifier(Set<String> reserved) {
        return identifier(null, reserved);
    }

    public Parser identifier(Class<? extends AstLeaf> clazz, Set<String> reserved) {
        elements.add(new IdToken(clazz, reserved));
        return this;
    }

    public Parser string() {
        return string(null);
    }

    public Parser string(Class<? extends AstLeaf> clazz) {
        elements.add(new StrToken(clazz));
        return this;
    }

    public Parser token(String... pat) {
        elements.add(new Leaf(pat));
        return this;
    }

    public Parser sep(String... pat) {
        elements.add(new Skip(pat));
        return this;
    }

    public Parser ast(Parser p) {
        elements.add(new Tree(p));
        return this;
    }

    public Parser or(Parser... p) {
        elements.add(new OrTree(p));
        return this;
    }

    public Parser maybe(Parser p) {
        Parser p2 = new Parser(p);
        p2.reset();
        elements.add(new OrTree(new Parser[] { p, p2 }));
        return this;
    }

    public Parser option(Parser p) {
        elements.add(new Repeat(p, true));
        return this;
    }

    public Parser repeat(Parser p) {
        elements.add(new Repeat(p, false));
        return this;
    }

    public Parser expression(Parser subexp, Operators operators) {
        elements.add(new Expr(null, subexp, operators));
        return this;
    }

    public Parser expression(Class<? extends AstNode> clazz, Parser subexp, Operators operators) {
        elements.add(new Expr(clazz, subexp, operators));
        return this;
    }

    public Parser insertChoice(Parser p) {
        Element e = elements.get(0);
        if (e instanceof OrTree)
            ((OrTree) e).insert(p);
        else {
            Parser otherwise = new Parser(this);
            reset(null);
            or(p, otherwise);
        }
        return this;
    }
}