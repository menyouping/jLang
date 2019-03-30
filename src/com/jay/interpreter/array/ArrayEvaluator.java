package com.jay.interpreter.array;

import java.util.List;

import com.jay.ast.ArrayLiteral;
import com.jay.ast.ArrayRef;
import com.jay.ast.AstNode;
import com.jay.ast.PrimaryExpr;
import com.jay.exception.StoneException;
import com.jay.interpreter.basic.BasicEvaluator;
import com.jay.interpreter.basic.Environment;
import com.jay.interpreter.basic.BasicEvaluator.AstNodeEx;
import com.jay.interpreter.func.FuncEvaluator;
import com.jay.interpreter.func.FuncEvaluator.PrimaryEx;
import com.jay.parser.ArrayParser;

import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

@Require({ FuncEvaluator.class, ArrayParser.class })
@Reviser
public class ArrayEvaluator {
    @Reviser
    public static class ArrayLitEx extends ArrayLiteral {
        public ArrayLitEx(List<AstNode> list) {
            super(list);
        }

        public Object eval(Environment env) {
            int s = numChildren();
            Object[] res = new Object[s];
            int i = 0;
            for (AstNode t : this) {
                res[i++] = ((AstNodeEx) t).eval(env);
            }
            return res;
        }
    }

    @Reviser
    public static class ArrayRefEx extends ArrayRef {
        public ArrayRefEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env, Object value) {
            if (value instanceof Object[]) {
                Object index = ((AstNodeEx) index()).eval(env);
                if (index instanceof Number) {
                    return ((Object[]) value)[((Number) index).intValue()];
                }
            }

            throw new StoneException("bad array access", this);
        }
    }

    @Reviser
    public static class AssignEx extends BasicEvaluator.BinaryEx {
        public AssignEx(List<AstNode> c) {
            super(c);
        }

        @Override
        protected Object computeAssign(Environment env, Object rvalue) {
            AstNode le = left();
            if (le instanceof PrimaryExpr) {
                PrimaryEx p = (PrimaryEx) le;
                if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) {
                    Object a = ((PrimaryEx) le).evalSubExpr(env, 1);
                    if (a instanceof Object[]) {
                        ArrayRef aref = (ArrayRef) p.postfix(0);
                        Object index = ((AstNodeEx) aref.index()).eval(env);
                        if (index instanceof Number) {
                            ((Object[]) a)[((Number) index).intValue()] = rvalue;
                            return rvalue;
                        }
                    }
                    throw new StoneException("bad array access", this);
                }
            }
            return super.computeAssign(env, rvalue);
        }
    }
}