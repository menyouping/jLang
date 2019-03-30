package com.jay.interpreter.natives;

import java.lang.reflect.Method;

import com.jay.ast.AstNode;
import com.jay.exception.StoneException;

public class NativeFunction {
    protected Method method;
    protected String name;
    protected int numParams;

    public NativeFunction(String n, Method m) {
        name = n;
        method = m;
        numParams = m.getParameterTypes().length;
    }

    @Override
    public String toString() {
        return "<native:" + hashCode() + ">";
    }

    public int numOfParameters() {
        return numParams;
    }

    public Object invoke(Object[] args, AstNode tree) {
        try {
            return method.invoke(null, args);
        } catch (Exception e) {
            throw new StoneException("bad native function call: " + name, tree);
        }
    }
}
