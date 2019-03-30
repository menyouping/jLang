package com.jay.exception;

public class ReturnException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Object value;

    public ReturnException(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

}