package com.jay.lexer;

import com.jay.exception.StoneException;

public abstract class Token {
    /**
     * 程序结束end of file
     */
    public static final Token EOF = new Token(-1) {
    };
    /**
     * 换行符end of line
     */
    public static final String EOL = "\\n";
    private int lineNumber;

    protected Token(int line) {
        lineNumber = line;
    }

    /**
     * 行号
     * 
     * @return
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * 是否标识符，标识符是变量名、函数名或类名等名称，标点符号与保留字也都作为标识符处理
     * 
     * @return
     */
    public boolean isIdentifier() {
        return false;
    }

    /**
     * 是否整型字面量，integer literal
     * 
     * @return
     */
    public boolean isNumber() {
        return false;
    }

    /**
     * 是否字符串字面量，String literal
     * 
     * @return
     */
    public boolean isString() {
        return false;
    }

    public Number getNumber() {
        throw new StoneException("not number token");
    }

    public String getText() {
        return "";
    }
}