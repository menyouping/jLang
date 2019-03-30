package com.jay.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import com.jay.ast.AstList;
import com.jay.ast.AstNode;

public abstract class Factory {

    public static final String factoryName = "create";

    protected abstract AstNode make0(Object arg) throws Exception;

    public AstNode make(Object arg) {
        try {
            return make0(arg);
        } catch (IllegalArgumentException e1) {
            throw e1;
        } catch (Exception e2) {
            throw new RuntimeException(e2); // this compiler is broken.
        }
    }

    public static Factory getForASTList(Class<? extends AstNode> clazz) {
        Factory f = get(clazz, List.class);
        if (f == null) {
            f = new Factory() {
                @SuppressWarnings("unchecked")
                protected AstNode make0(Object arg) throws Exception {
                    List<AstNode> results = (List<AstNode>) arg;
                    if (results.size() == 1) {
                        return results.get(0);
                    } else {
                        return new AstList(results);
                    }
                }
            };
        }
        return f;
    }

    public static Factory get(Class<? extends AstNode> clazz, Class<?> argType) {
        if (clazz == null) {
            return null;
        }
        // 优先找AstNode子节点的静态create方法创建对象
        try {
            final Method m = clazz.getMethod(Factory.factoryName, new Class<?>[] { argType });
            return new Factory() {
                protected AstNode make0(Object arg) throws Exception {
                    return (AstNode) m.invoke(null, arg);
                }
            };
        } catch (NoSuchMethodException e) {
        }
        // 备用方案是构造方法
        try {
            final Constructor<? extends AstNode> c = clazz.getConstructor(argType);
            return new Factory() {
                protected AstNode make0(Object arg) throws Exception {
                    return c.newInstance(arg);
                }
            };
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}