package com.jay.interpreter.basic;

import java.util.HashMap;
import java.util.Map;

public class BasicEnv implements Environment {
    protected Map<String, Object> values;

    public BasicEnv() {
        values = new HashMap<String, Object>();
    }

    public void put(String name, Object value) {
        values.put(name, value);
    }

    public Object get(String name) {
        return values.get(name);
    }
}
