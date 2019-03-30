package com.jay.interpreter.func;

import java.util.HashMap;
import java.util.Map;

import com.jay.interpreter.basic.Environment;
import com.jay.interpreter.func.FuncEvaluator.EnvEx;

public class NestedEnv implements Environment {

    protected Map<String, Object> values;
    protected Environment outer;

    public NestedEnv() {
        this(null);
    }

    public NestedEnv(Environment e) {
        values = new HashMap<String, Object>();
        outer = e;
    }

    public void setOuter(Environment e) {
        outer = e;
    }

    public void put(String name, Object value) {
        Environment e = where(name);
        if (e == null) {
            e = this;
        }
        ((EnvEx) e).putNew(name, value);
    }

    public void putNew(String name, Object value) {
        values.put(name, value);
    }

    public Environment where(String name) {
        if (values.containsKey(name)) {
            return this;
        } else if (outer == null) {
            return null;
        }
        return ((EnvEx) outer).where(name);
    }

    public Object get(String name) {
        if (values.containsKey(name)) {
            return values.get(name);
        }
        if (outer != null) {
            return outer.get(name);
        }
        return null;
    }
}