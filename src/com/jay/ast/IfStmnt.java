package com.jay.ast;

import java.util.List;

public class IfStmnt extends AstList {
    public IfStmnt(List<AstNode> c) {
        super(c);
        beautify();
    }

    protected void beautify() {
        if (children.size() < 3) {
            return;
        }
        // 1是if 2是then， 3 (elif then) 4 (elif then) 5 else
        // 转换为
        // 1是if 2是then， 3 elif 4 then 5 elif 6 then 7 else
        for (int i = children.size() - 1; i >= 2; i--) {
            if (children.get(i).numChildren() > 1) {
                // 不是
                AstNode cond = children.get(i).childAt(0);
                AstNode then = children.get(i).childAt(1);
                children.remove(i);
                children.add(i, then);
                children.add(i, cond);
            }
        }
    }

    public int numCondition() {
        return numChildren() / 2;
    }

    public AstNode condition(int i) {
        return childAt(2 * i);
    }

    public AstNode thenBlock(int i) {
        return childAt(2 * i + 1);
    }

    public AstNode elseBlock() {
        return numChildren() % 2 == 1 ? childAt(numChildren() - 1) : null;
    }

    public String toString() {
        int numCondition = numCondition();
        StringBuilder builder = new StringBuilder();
        builder.append("(if ").append(condition(0)).append(" ").append(thenBlock(0));
        if (numCondition > 1) {
            for (int i = 1; i < numCondition; i++) {
                builder.append(" elif ").append(condition(i)).append(" ").append(thenBlock(i));
            }
        }
        if (numChildren() % 2 == 1) {
            builder.append(" else ").append(elseBlock());
        }
        builder.append(")");
        return builder.toString();
    }
}