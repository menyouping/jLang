package com.jay.interpreter.claze;

import com.jay.interpreter.basic.Environment;
import com.jay.interpreter.func.FuncEvaluator.EnvEx;

public class StoneObject {
    public static class AccessException extends Exception {

        private static final long serialVersionUID = 1L;
    }

    protected Environment env;

    public StoneObject(Environment e) {
        env = e;
    }

    public String toString() {
        return "<object:" + hashCode() + ">";
    }

    public Object read(String member) throws AccessException {
        return getEnv(member).get(member);
    }

    public void write(String member, Object value) throws AccessException {
        ((EnvEx) getEnv(member)).putNew(member, value);
    }

    protected Environment getEnv(String member) throws AccessException {
        Environment e = ((EnvEx) env).where(member);
        if (e != null && e == env) {
            return e;
        } else {
            throw new AccessException();
        }
    }
}