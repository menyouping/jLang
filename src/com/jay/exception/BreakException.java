package com.jay.exception;

public class BreakException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Object value;

    public BreakException(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

}