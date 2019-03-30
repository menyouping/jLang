package com.jay.exception;

import com.jay.ast.AstNode;

public class StoneException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public StoneException(String m) {
        super(m);
    }

    public StoneException(String m, AstNode t) {
        super(m + " " + t.location());
    }
}