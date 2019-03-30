package com.jay.interpreter.func;

import java.util.List;

import com.jay.ast.*;
import com.jay.exception.ReturnException;
import com.jay.exception.StoneException;
import com.jay.interpreter.basic.BasicEvaluator;
import com.jay.interpreter.basic.Environment;
import com.jay.interpreter.basic.BasicEvaluator.AstNodeEx;
import com.jay.interpreter.basic.BasicEvaluator.BlockEx;

import javassist.gluonj.*;

@Require(BasicEvaluator.class)
@Reviser
public class FuncEvaluator {
    @Reviser
    public static interface EnvEx extends Environment {
        void putNew(String name, Object value);

        Environment where(String name);

        void setOuter(Environment e);
    }

    @Reviser
    public static class FuncStmntEx extends FuncStmnt {
        public FuncStmntEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env) {
            ((EnvEx) env).putNew(name(), new Function(parameters(), body(), env));
            return name();
        }
    }

    @Reviser
    public static class PrimaryEx extends PrimaryExpr {
        public PrimaryEx(List<AstNode> c) {
            super(c);
        }

        public AstNode operand() {
            return childAt(0);
        }

        public Postfix postfix(int nest) {
            return (Postfix) childAt(numChildren() - nest - 1);
        }

        public boolean hasPostfix(int nest) {
            return numChildren() - nest > 1;
        }

        public Object eval(Environment env) {
            return evalSubExpr(env, 0);
        }

        public Object evalSubExpr(Environment env, int nest) {
            if (hasPostfix(nest)) {
                Object target = evalSubExpr(env, nest + 1);
                return ((PostfixEx) postfix(nest)).eval(env, target);
            } else {
                return ((AstNodeEx) operand()).eval(env);
            }
        }
    }

    @Reviser
    public static abstract class PostfixEx extends Postfix {
        public PostfixEx(List<AstNode> c) {
            super(c);
        }

        public abstract Object eval(Environment env, Object value);
    }

    @Reviser
    public static class ArgumentsEx extends Arguments {
        public ArgumentsEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment callerEnv, Object value) {
            if (!(value instanceof Function)) {
                throw new StoneException("bad function", this);
            }
            Function func = (Function) value;
            ParameterList params = func.parameters();
            if (size() != params.size()) {
                throw new StoneException("bad number of arguments", this);
            }
            Environment newEnv = func.makeEnv();
            int num = 0;
            for (AstNode a : this) {
                ((ParamsEx) params).eval(newEnv, num++, ((AstNodeEx) a).eval(callerEnv));
            }
            try {
                return ((BlockEx) func.body()).eval(newEnv);
            } catch (ReturnException e) {
                return e.getValue();
            }
        }
    }

    @Reviser
    public static class ParamsEx extends ParameterList {
        public ParamsEx(List<AstNode> c) {
            super(c);
        }

        public void eval(Environment env, int index, Object value) {
            ((EnvEx) env).putNew(name(index), value);
        }
    }
}