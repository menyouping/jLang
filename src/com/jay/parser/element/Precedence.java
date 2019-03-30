package com.jay.parser.element;

public class Precedence {
    int value;
    boolean leftAssoc; // left associative

    public Precedence(int v, boolean a) {
        value = v;
        leftAssoc = a;
    }
}