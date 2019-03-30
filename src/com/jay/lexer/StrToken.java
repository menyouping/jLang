package com.jay.lexer;

/**
 * 字符串字面量
 * 
 * @author jay
 *
 */
public class StrToken extends Token {
    private String literal;

    public StrToken(int line, String literal) {
        super(line);
        this.literal = literal;
    }
    
    public boolean isString() {
        return true;
    }

    public String getText() {
        return literal;
    }

    @Override
    public String toString() {
        return "StrToken [literal=" + literal + "]";
    }

}