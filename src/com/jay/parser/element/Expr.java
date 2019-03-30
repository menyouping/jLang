package com.jay.parser.element;

import java.util.ArrayList;
import java.util.List;

import com.jay.ast.AstLeaf;
import com.jay.ast.AstNode;
import com.jay.exception.ParseException;
import com.jay.lexer.Lexer;
import com.jay.lexer.Token;
import com.jay.parser.Factory;
import com.jay.parser.Parser;

public class Expr extends Element {
    protected Factory factory;
    protected Operators ops;
    protected Parser factor;
    public Expr(Class<? extends AstNode> clazz, Parser exp,
                   Operators map)
    {
        factory = Factory.getForASTList(clazz);
        ops = map;
        factor = exp;
    }
    public void parse(Lexer lexer, List<AstNode> res) throws ParseException {
        AstNode right = factor.parse(lexer);
        Precedence prec;
        while ((prec = nextOperator(lexer)) != null)
            right = doShift(lexer, right, prec.value);

        res.add(right);
    }
    private AstNode doShift(Lexer lexer, AstNode left, int prec)
        throws ParseException
    {
        ArrayList<AstNode> list = new ArrayList<AstNode>();
        list.add(left);
        list.add(new AstLeaf(lexer.read()));
        AstNode right = factor.parse(lexer);
        Precedence next;
        while ((next = nextOperator(lexer)) != null
               && rightIsExpr(prec, next))
            right = doShift(lexer, right, next.value);

        list.add(right);
        return factory.make(list);
    }
    private Precedence nextOperator(Lexer lexer) throws ParseException {
        Token t = lexer.peek(0);
        if (t.isIdentifier())
            return ops.get(t.getText());
        else
            return null;
    }
    private static boolean rightIsExpr(int prec, Precedence nextPrec) {
        if (nextPrec.leftAssoc)
            return prec < nextPrec.value;
        else
            return prec <= nextPrec.value;
    }
    public boolean match(Lexer lexer) throws ParseException {
        return factor.match(lexer);
    }
}