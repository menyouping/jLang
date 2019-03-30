package com.jay.lexer;

/**
 * Token的匹配符
 * 
 * @author jay
 *
 */
public enum TokenPattern {
    /**
     * 整个Token
     */
    TOTAL("TOTAL", "total",
            "(?<comment>COMMENT)|(?<string>STRING)|(?<number>NUMBER)|[A-Z_a-z][A-Z_a-z0-9]*|\\^|=>|==|<=|>=|&&|\\|\\||\\p{Punct}"), //
    /**
     * 注释
     */
    COMMENT("COMMENT", "comment", "//.*"), //
    /**
     * 字符串字面量, 首尾各一个双引号，中间与\"、\\、\n匹配，同时也与除"之外任意字符匹配
     */
    STRING("STRING", "string", "\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\""), //
    /**
     * 数字字面量
     */
    NUMBER("NUMBER", "number", "(0|([1-9]\\d*))(\\.\\d+)?");

    private TokenPattern(String id, String name, String expression) {
        this.id = id;
        this.name = name;
        this.expression = expression;
    }

    String id;
    String name;
    String expression;
}