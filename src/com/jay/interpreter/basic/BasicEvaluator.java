package com.jay.interpreter.basic;

import java.util.List;

import com.jay.ast.AstLeaf;
import com.jay.ast.AstList;
import com.jay.ast.AstNode;
import com.jay.ast.BinaryExpr;
import com.jay.ast.BlockStmnt;
import com.jay.ast.IfStmnt;
import com.jay.ast.Name;
import com.jay.ast.NegativeExpr;
import com.jay.ast.NullStmnt;
import com.jay.ast.NumberLiteral;
import com.jay.ast.ReturnStmnt;
import com.jay.ast.StringLiteral;
import com.jay.ast.WhileStmnt;
import com.jay.exception.BreakException;
import com.jay.exception.ReturnException;
import com.jay.exception.StoneException;
import com.jay.lexer.Token;

import javassist.gluonj.Reviser;

@Reviser
public class BasicEvaluator {
    @Reviser
    public static abstract class AstNodeEx extends AstNode {
        public abstract Object eval(Environment env);
    }

    @Reviser
    public static class AstListEx extends AstList {
        public AstListEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }

    @Reviser
    public static class AstLeafEx extends AstLeaf {
        public AstLeafEx(Token t) {
            super(t);
        }

        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }

    @Reviser
    public static class NumberEx extends NumberLiteral {
        public NumberEx(Token t) {
            super(t);
        }

        public Object eval(Environment e) {
            return value();
        }
    }

    @Reviser
    public static class StringEx extends StringLiteral {
        public StringEx(Token t) {
            super(t);
        }

        public Object eval(Environment e) {
            return value();
        }
    }

    @Reviser
    public static class NameEx extends Name {
        public NameEx(Token t) {
            super(t);
        }

        public Object eval(Environment env) {
            String name = name();
            if ("break".equals(name) || "return".equals(name)) {
                return 0;
            }
            Object value = env.get(name);
            if (value == null) {
                throw new StoneException("undefined name: " + name(), this);
            } else {
                return value;
            }
        }
    }

    @Reviser
    public static class NegativeEx extends NegativeExpr {
        public NegativeEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object v = ((AstNodeEx) operand()).eval(env);
            if (v instanceof Long) {
                return -((Long) v);
            } else if (v instanceof Double) {
                return -((Double) v);
            } else {
                throw new StoneException("bad type for -", this);
            }
        }
    }

    @Reviser
    public static class BinaryEx extends BinaryExpr {
        public BinaryEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env) {
            String op = operator();
            if ("=".equals(op)) {
                Object right = ((AstNodeEx) right()).eval(env);
                return computeAssign(env, right);
            } else {
                Object left = ((AstNodeEx) left()).eval(env);
                Object right = ((AstNodeEx) right()).eval(env);
                return computeOp(left, op, right);
            }
        }

        protected Object computeAssign(Environment env, Object rvalue) {
            AstNode l = left();
            if (l instanceof Name) {
                env.put(((Name) l).name(), rvalue);
                return rvalue;
            } else {
                throw new StoneException("bad assignment", this);
            }
        }

        protected Object computeOp(Object left, String op, Object right) {
            if (left instanceof Number && right instanceof Number) {
                return computeNumber((Number) left, op, (Number) right);
            } else if (op.equals("+")) {
                return (left == null ? "null" : left.toString()) + (right == null ? "null" : right.toString());
            } else if (op.equals("==")) {
                if (left == null) {
                    return right == null;
                } else {
                    return left.equals(right);
                }
            } else {
                throw new StoneException("bad type", this);
            }
        }

        protected Object computeNumber(Number left, String op, Number right) {
            Double result = 0d;
            double a = left.doubleValue();
            double b = right.doubleValue();
            switch (op) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            case "%":
                result = a % b;
                break;
            case "^":
                result = Math.pow(a, b);
                break;
            case "==":
                return a == b;
            case ">":
                return a > b;
            case "<":
                return a < b;
            default:
                throw new StoneException("bad operator", this);
            }
            if (left instanceof Long && right instanceof Long) {
                return result.longValue();
            }
            return result;
        }
    }

    @Reviser
    public static class BlockEx extends BlockStmnt {
        public BlockEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object result = 0;
            for (AstNode t : this) {
                if (t instanceof NullStmnt) {
                    continue;
                }
                if (Name.Break == t) {
                    throw new BreakException(result);
                }
                result = ((AstNodeEx) t).eval(env);
            }
            return result;
        }
    }

    @Reviser
    public static class IfEx extends IfStmnt {
        public IfEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env) {
            int numCondition = numChildren() / 2;
            for (int i = 0; i < numCondition; i++) {
                Object c = ((AstNodeEx) condition(i)).eval(env);
                if (c instanceof Boolean && Boolean.TRUE.equals(c)) {
                    return ((AstNodeEx) thenBlock(i)).eval(env);
                }
            }
            AstNode b = elseBlock();
            if (b == null) {
                return 0;
            } else {
                return ((AstNodeEx) b).eval(env);
            }
        }
    }

    @Reviser
    public static class WhileEx extends WhileStmnt {
        public WhileEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object result = 0;
            try {
                for (;;) {
                    Object c = ((AstNodeEx) condition()).eval(env);
                    if (c instanceof Boolean && Boolean.FALSE.equals(c)) {
                        return result;
                    } else {
                        result = ((AstNodeEx) body()).eval(env);
                    }
                }
            } catch (BreakException e) {
                return e.getValue();
            }
        }
    }

    @Reviser
    public static class ReturnStmntEx extends ReturnStmnt {
        public ReturnStmntEx(List<AstNode> c) {
            super(c);
        }

        public Object eval(Environment env) {
            AstNodeEx node = (AstNodeEx) value();
            if (node == null) {
                throw new ReturnException(0);
            }
            throw new ReturnException(node.eval(env));
        }
    }
}
