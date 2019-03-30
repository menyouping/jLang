package com.jay.ast;

import java.util.Iterator;

/**
 * 抽象语法树抽象父类
 * @author jay
 *
 */
public abstract class AstNode implements Iterable<AstNode> {
    /**
     * 第i个子节点
     * @param i
     * @return
     */
    public abstract AstNode childAt(int i);

    /**
     * 子节点的个数
     * @return
     */
    public abstract int numChildren();

    public abstract Iterator<AstNode> children();

    public abstract String location();

    public Iterator<AstNode> iterator() {
        return children();
    }
}