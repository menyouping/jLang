package com.jay.interpreter.lambda;

import java.util.List;

import com.jay.ast.AstNode;
import com.jay.ast.Lambda;
import com.jay.interpreter.basic.Environment;
import com.jay.interpreter.func.FuncEvaluator;
import com.jay.interpreter.func.Function;

import javassist.gluonj.*;

@Require(FuncEvaluator.class)
@Reviser
public class LambdaEvaluator {
    @Reviser
    public static class LambdaEx extends Lambda {
        public LambdaEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env) {
            return new Function(parameters(), body(), env);
        }
    }
}