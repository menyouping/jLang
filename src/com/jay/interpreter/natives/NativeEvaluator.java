package com.jay.interpreter.natives;

import java.util.List;

import com.jay.ast.AstNode;
import com.jay.exception.StoneException;
import com.jay.interpreter.basic.Environment;
import com.jay.interpreter.basic.BasicEvaluator.AstNodeEx;
import com.jay.interpreter.func.FuncEvaluator;

import javassist.gluonj.*;

@Require(FuncEvaluator.class)
@Reviser
public class NativeEvaluator {
    @Reviser
    public static class NativeArgEx extends FuncEvaluator.ArgumentsEx {
        public NativeArgEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment callerEnv, Object value) {
            if (!(value instanceof NativeFunction)) {
                return super.eval(callerEnv, value);
            }
            NativeFunction func = (NativeFunction) value;
            int nparams = func.numOfParameters();
            if (size() != nparams) {
                throw new StoneException("bad number of arguments", this);
            }
            Object[] args = new Object[nparams];
            int num = 0;
            for (AstNode a : this) {
                AstNodeEx ae = (AstNodeEx) a;
                args[num++] = ae.eval(callerEnv);
            }
            return func.invoke(args, this);
        }
    }
}