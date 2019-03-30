package com.jay.interpreter.basic;

public interface Environment {
    void put(String name, Object value);

    Object get(String name);
}