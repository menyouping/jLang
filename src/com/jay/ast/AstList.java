package com.jay.ast;

import java.util.Iterator;
import java.util.List;

/**
 * 含有树枝的节点对象的父类
 * 
 * @author jay
 *
 */
public class AstList extends AstNode {
    protected List<AstNode> children;

    public AstList(List<AstNode> list) {
        children = list;
    }

    public AstNode childAt(int i) {
        return children.get(i);
    }

    public int numChildren() {
        return children.size();
    }

    public Iterator<AstNode> children() {
        return children.iterator();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        String sep = "";
        for (AstNode t : children) {
            builder.append(sep);
            sep = " ";
            builder.append(t.toString());
        }
        return builder.append(')').toString();
    }

    public String location() {
        for (AstNode t : children) {
            String s = t.location();
            if (s != null) {
                return s;
            }
        }
        return null;
    }
}