package com.jay.lexer;

/**
 * 整型字面量
 * @author jay
 *
 */
public class NumToken extends Token {
    private Number value;

    public NumToken(int line, Number value) {
        super(line);
        this.value = value;
    }

    public boolean isNumber() {
        return true;
    }

    public String getText() {
        return value.toString();
    }

    public Number getNumber() {
        return value;
    }

    @Override
    public String toString() {
        return "NumToken [value=" + value + "]";
    }
}