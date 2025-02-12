package top.auspice.utils.compiler.condition;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.utils.compiler.math.MathCompiler;
import net.aurika.utils.unsafe.fn.Fn;
import top.auspice.utils.math.MathUtils;

import java.util.*;
import java.util.function.Supplier;

public final class ConditionCompiler {
    private final String string;
    private final int length;
    private int index;
    private final LinkedList<LogicalOperand> d = new LinkedList<>();
    private final LinkedList<LogicalOperator> e = new LinkedList<>();
    private int f = -1;
    private int g = -1;
    private int h = 0;
    private static final ConstantLogicalOperand i = new ConstantLogicalOperand(true);

    public ConditionCompiler(String str, int index, int length) {
        this.string = index != 0 ? str : str.replace('\n', ' ').replace('\r', ' ');
        this.index = index;
        this.length = length;
    }

    public static ConditionCompiler compile(String str) {
        return new ConditionCompiler(str, 0, str.length());
    }

    private void a(boolean var1) {
        if (this.f != -1) {
            String var2 = this.string.substring(this.f, this.index);
            boolean var3 = var2.endsWith("}");
            if (var3) {
                var2 = var2.substring(1, var2.length() - 1);
            }

            OperandData var10000;
            if (var3) {
                var10000 = new OperandData(OperandType.VARIABLE, 0);
            } else {
                int var4 = var2.length();
                boolean var5 = true;
                int var6 = 0;
                int var7;
                if (var2.charAt(0) == '\'') {
                    for (var7 = 1; var7 < var4; ++var7) {
                        if (var2.charAt(var7) == '\'') {
                            var6 = var7 + 1;
                            break;
                        }
                    }

                    var10000 = new OperandData(OperandType.STRING, var6);
                } else {
                    for (var7 = 0; var7 < var4; ++var7) {
                        char var8;
                        if (((var8 = var2.charAt(var7)) < 'A' || var8 > 'Z') && (var8 < 'a' || var8 > 'z') && var8 != '_') {
                            if (var8 == ' ') {
                                if (var6 == 0) {
                                    var6 = var7;
                                }
                            } else {
                                var5 = false;
                                var6 = 0;
                            }
                        } else if (var6 != 0) {
                            var5 = false;
                        }
                    }

                    var10000 = new OperandData(var5 ? OperandType.VARIABLE : OperandType.ARITHMETIC, var6);
                }
            }

            OperandData var10 = var10000;
            if (var10000.b != 0 && var10.type != OperandType.ARITHMETIC) {
                var2 = var2.substring(0, Math.abs(var10.b));
            }

            LogicalOperand result;
            label69:
            switch (var10.type) {
                case VARIABLE:
                    switch (var2) {
                        case "true":
                        case "else":
                            result = ConstantLogicalOperand.TRUE;
                            break label69;
                        case "false":
                            result = ConstantLogicalOperand.FALSE;
                            break label69;
                        case "null":
                        case "nil":
                            throw this.a(this.f, "Cannot use reserved logical keyword '" + var2 + '\'', var2);
                        default:
                            result = new LogicalVariableOperand(var2);
                            break label69;
                    }
                case ARITHMETIC:
                    MathCompiler.Expression mathExpr = MathCompiler.compile(var2);
                    result = new ArithmeticOperand(mathExpr);
                    break;
                case STRING:
                    result = new StringOperand(var2.substring(1, var2.length() - 1));
                    break;
                default:
                    throw new AssertionError();
            }

            String finalVar = var2;
            this.a(
                    result, var1, () -> finalVar);
        }
    }

    private void a(LogicalOperand var1, boolean var2, Supplier<String> var3) {
        LogicalOperator var4;
        if ((var4 = this.e.peekLast()) != null && var4.unary) {
            if (!var4.acceptsOperandOfType(var1)) {
                throw this.a(this.f, "Right hand side of '" + var4.symbol + "' unary operator must be " + var4.acceptedOperands[0] + " expression", var3.get());
            }

            var1 = new UnaryLogicalOperator(var4, var1);
            this.e.removeLast();
        }

        this.d.addLast(var1);
        if (var2 || this.e.size() == 2 && this.d.size() == 3) {
            this.handleOperations(var2, var3);
        }

    }

    private BiLogicalOperator a(LogicalOperand var1, LogicalOperator var2, LogicalOperand var3) {
        if (!var2.acceptsOperandOfType(var1)) {
            throw this.a(this.h, "Left hand side of '" + var2.symbol + "' operator must be " + var2.acceptedOperands[0] + " expression", var2.symbol);
        } else if (!var2.acceptsOperandOfType(var3)) {
            throw this.a(this.f, "Right hand side of '" + var2.symbol + "' operator must be " + var2.acceptedOperands[0] + " expression", var2.symbol);
        } else {
            return new BiLogicalOperator(var1, var2, var3);
        }
    }

    public void handleOperations(boolean var1, Supplier<String> var2) {
        if (this.d.size() >= 2) {
            if (this.e.size() == 2) {
                LogicalOperand var8 = this.d.pollFirst();
                LogicalOperand var3 = this.d.pollFirst();
                LogicalOperand var4 = this.d.pollFirst();
                LogicalOperator var5 = this.e.getFirst();
                LogicalOperator var6 = this.e.peekLast();
                if (var4 == null) {
                    throw this.b(this.f, "Right hand side empty");
                }

                BiLogicalOperator var9;
                if (var5.isComparator() && var6.isComparator()) {
                    var9 = this.a(var8, var5, var3);
                    BiLogicalOperator var7 = this.a(var3, var5, var4);
                    var7 = this.a(var9, LogicalOperator.AND, var7);
                    this.d.add(var7);
                    this.e.clear();
                    return;
                }

                if (var5.hasPrecedenceOver(var6)) {
                    var9 = this.a(var8, var5, var3);
                    this.d.add(var9);
                    this.d.add(var4);
                    this.e.removeFirst();
                } else {
                    var9 = this.a(var3, var6, var4);
                    this.d.add(var8);
                    this.d.add(var9);
                    this.e.removeLast();
                }

                if (!var1) {
                    return;
                }
            }

            this.d.add(this.a(this.d.pollFirst(), this.e.pollLast(), this.d.pollLast()));
        }
    }

    public void handleOp() {
        if (this.g != -1) {
            int var2 = this.g;
            String var3;
            int var4 = (var3 = this.string.substring(var2, this.index)).length();
            LogicalOperator[] var5;
            int var6 = (var5 = LogicalOperator.OPERATORS).length;

            for (int var7 = 0; var7 < var6; ++var7) {
                LogicalOperator var8;
                String var9 = (var8 = var5[var7]).symbol;
                if (var4 == var8.symbolSize() && var9.equals(var3)) {
                    LogicalOperator var10 = this.e.peekLast();
                    if (this.d.isEmpty() && !var8.unary) {
                        throw this.a(this.h, "Blank operand on left hand side of '" + var8.symbol + "' operator", var8.symbol);
                    }

                    if (!this.e.isEmpty()) {
                        if (!var8.unary && this.d.size() < 2) {
                            throw this.a(this.h - var10.symbolSize(), "Blank operand on right side of '" + var10.symbol + "' binary operator.", var10.symbol);
                        }

                        if (var10.unary) {
                            throw this.a(this.f, "Unary operator '" + var10.symbol + "' was followed by another operator " + var8.symbol, var8.symbol);
                        }
                    }

                    this.e.addLast(var8);
                    return;
                }
            }

            String var11;
            if (var3.startsWith("!!")) {
                var11 = " (hint: Redundant multiple negation operators are not allowed)";
            } else if (var3.startsWith("=>")) {
                var11 = " (hint: Did you mean '>=' operator?)";
            } else if (var3.startsWith("=<")) {
                var11 = " (hint: Did you mean '<=' operator?)";
            } else {
                var11 = Arrays.stream(LogicalOperator.OPERATORS).filter((var1) -> var3.contains(var1.symbol)).findFirst().map((var0) -> " (hint: You have to write '" + var0.symbol + "' operator separated with a space from other operators)").orElse("");
            }

            throw this.a(var2, "Unrecognized logical operator '" + var3 + '\'' + var11, var3);
        }
    }

    private static Collection<Integer> a(int var0, String var1) {
        ArrayList<Integer> var2 = new ArrayList<>(var1.length());

        for (int var3 = 1; var3 < var1.length(); ++var3) {
            var2.add(var0 + var3);
        }

        return var2;
    }

    public LogicalOperand evaluate() {
        for (; this.index < this.length; ++this.index) {
            int var1 = this.string.charAt(this.index);
            char var2 = (char) var1;
            if (var2 == '<' || var2 == '>' || var2 == '!' || var2 == '=' || var2 == '&' || var2 == '|') {
                if (this.f != -1) {
                    this.a(false);
                    this.f = -1;
                }

                if (this.g == -1) {
                    this.g = this.h = this.index;
                }
            } else {
                this.handleOp();
                if (var1 != 32 && this.f == -1) {
                    if (var1 == 40) {
                        var1 = this.index + 1;
                        ConditionCompiler var8 = this;
                        int var4 = 1;
                        int var5 = this.string.length();
                        int var6 = var1;

                        while (true) {
                            if (var6 >= var5) {
                                throw var8.b(var1 - 1, "Unclosed subexpression");
                            }

                            char var7;
                            if ((var7 = var8.string.charAt(var6)) == '(') {
                                ++var4;
                            } else if (var7 == ')') {
                                --var4;
                                if (var4 == 0) {
                                    this.f = this.index++;
                                    LogicalOperand var3 = (new ConditionCompiler(this.string, var1, var6)).evaluate();
                                    int finalVar = var1;
                                    int finalVar1 = var6;
                                    this.a(var3, false, () -> this.string.substring(finalVar - 1, finalVar1 + 1));
                                    this.index = var6;
                                    this.f = -1;
                                    break;
                                }
                            }

                            ++var6;
                        }
                    } else {
                        this.f = this.index;
                        if (var1 == 123) {
                            while (this.string.charAt(++this.index) != '}') {
                            }
                        }
                    }
                }

                this.g = -1;
            }
        }

        if (this.f != -1) {
            this.a(true);
        } else {
            this.handleOperations(true, () -> this.string);
        }

        this.handleOp();
        if (this.d.isEmpty()) {
            throw this.b(0, "Blank expression");
        } else if (!this.e.isEmpty()) {
            LogicalOperator var9 = this.e.getLast();
            throw this.a(this.h, "Blank operand on right hand side of '" + var9.symbol + "' binary operator", var9.symbol);
        } else {
            return this.d.getLast();
        }
    }

    private static String a(int var0) {
        char[] var1;
        Arrays.fill(var1 = new char[var0], ' ');
        return new String(var1);
    }

    private LogicalException b(int var1, String var2) {
        return this.a(var1, var2, new ArrayList<>());
    }

    private LogicalException a(int var1, String var2, String var3) {
        return this.a(var1, var2, a(var1, var3));
    }

    private LogicalException a(int var1, String var2, Collection<Integer> var3) {
        int var4 = 0;
        var3.add(var1);

        for (Integer var6 : var3) {
            if (var6 > var4) {
                var4 = var6;
            }
        }

        StringBuilder var7 = new StringBuilder(a(var4 + 2));
        var3.forEach((var1x) -> var7.setCharAt(var1x + 1, '^'));
        return new LogicalException(var2 + " at offset " + var1 + " in expression:\n\"" + this.string + "\"\n" + var7);
    }

    /**
     * true or false
     */
    public static final class ConstantLogicalOperand extends LogicalOperand {
        private final boolean value;
        public static final ConstantLogicalOperand TRUE = new ConstantLogicalOperand(true);
        public static final ConstantLogicalOperand FALSE = new ConstantLogicalOperand(false);

        private ConstantLogicalOperand(boolean value) {
            this.value = value;
        }

        public Boolean eval0(ConditionVariableTranslator variableTranslator) {
            return this.value;
        }

        public boolean getValue() {
            return this.value;
        }

        public String toString() {
            return Boolean.toString(this.value);
        }

        public @NotNull String asString(boolean var1) {
            return Boolean.toString(this.value);
        }
    }

    private static final class OperandData {
        private final OperandType type;
        private final int b;

        private OperandData(OperandType var1, int var2) {
            this.type = var1;
            this.b = var2;
        }
    }

    private enum OperandType {
        VARIABLE,   //
        ARITHMETIC, //
        STRING      //
    }

    public static final class LogicalVariableOperand extends LogicalOperand {
        private final String a;

        public LogicalVariableOperand(String var1) {
            this.a = var1;
        }

        public Object eval0(ConditionVariableTranslator variableTranslator) {
            return this.parseVariable(this.a, variableTranslator);
        }

        public String toString() {
            return "{" + this.a + '}';
        }

        public @NotNull String asString(boolean var1) {
            return this.a;
        }
    }

    public static final class ArithmeticOperand extends LogicalOperand {
        private final MathCompiler.Expression mathExpression;

        public ArithmeticOperand(MathCompiler.Expression var1) {
            this.mathExpression = var1;
        }

        public Double eval0(ConditionVariableTranslator variableTranslator) {
            return this.mathExpression.eval((var2) -> {
                Object var3 = this.parseVariable(var2, variableTranslator);
                return MathUtils.expectDouble(var2, var3);
            });
        }

        public String toString() {
            return this.mathExpression.toString();
        }

        public @NotNull String asString(boolean var1) {
            return this.mathExpression.asString(var1);
        }
    }

    public static final class StringOperand extends LogicalOperand {
        private final String a;

        public StringOperand(String var1) {
            this.a = var1;
        }

        public String eval0(ConditionVariableTranslator variableTranslator) {
            return this.a;
        }

        public String toString() {
            return "StringOperand[" + this.a + ']';
        }

        public @NotNull String asString(boolean var1) {
            return this.a;
        }
    }

    public abstract static class LogicalOperand implements ConditionExpression {
        protected String originalString;

        public LogicalOperand() {
        }

        protected abstract Object eval0(ConditionVariableTranslator variableTranslator);

        protected LogicalOperand withOriginalString(String originalString) {
            this.originalString = originalString;
            return this;
        }

        public @Nullable String getOriginalString() {
            return this.originalString;
        }

        @NotNull
        public Boolean eval(@NotNull ConditionVariableTranslator variableTranslator) {
            Objects.requireNonNull(variableTranslator);
            Object evaled = this.eval0(variableTranslator);
            if (!(evaled instanceof Boolean)) {
                if (evaled instanceof Number) {
                    double var3;
                    if ((var3 = ((Number) evaled).doubleValue()) == 1.0) {
                        return Boolean.TRUE;
                    }

                    if (var3 == 0.0) {
                        return Boolean.FALSE;
                    }
                } else if (evaled instanceof String) {
                    if (evaled.equals("true")) {
                        return Boolean.TRUE;
                    }

                    if (evaled.equals("false")) {
                        return Boolean.FALSE;
                    }
                }

                throw new LogicalException("Not a boolean expression: " + evaled + " (" + (evaled == null ? null : evaled.getClass()) + ')');
            } else {
                return (Boolean) evaled;
            }
        }

        public boolean isDefault() {
            return this == ConditionCompiler.i;
        }

        public Object parseVariable(String var1, ConditionVariableTranslator var2) {
            Object var7;
            try {
                var7 = var2.apply(var1);
            } catch (StackOverflowError var3) {
                throw new LogicalException("Circular variable: '" + var1 + '\'');
            } catch (LogicalException var4) {
                throw var4;
            } catch (Exception var5) {
                throw new LogicalException("Error while evaluating variable: '" + var1 + '\'', var5);
            }

            if (var7 == null) {
                throw new LogicalException("Unknown variable: '" + var1 + '\'');
            } else if (var7 instanceof Boolean) {
                return var7;
            } else if (var7 instanceof Number) {
                return ((Number) var7).doubleValue();
            } else {
                if (var7 instanceof String) {
                    if (var7.equals("true")) {
                        return Boolean.TRUE;
                    }

                    if (var7.equals("false")) {
                        return Boolean.FALSE;
                    }

                    try {
                        return Double.parseDouble((String) var7);
                    } catch (NumberFormatException ignored) {
                    }
                }

                return var7;
            }
        }
    }

    public enum LogicalOperator {
        NOT("!", 1, new AcceptedOperand[]{AcceptedOperand.LOGICAL}, true) {
            public final boolean eval(Object left, Object right) {
                return !this.assertBool(right);
            }
        },//
        AND("&&", 4, AcceptedOperand.LOGICAL) {
            public final boolean eval(Supplier<Object> leftSupplier, Supplier<Object> rightSupplier) {
                return this.assertBool(leftSupplier.get()) && this.assertBool(rightSupplier.get());
            }
        },//
        OR("||", 5, AcceptedOperand.LOGICAL) {
            public final boolean eval(Supplier<Object> leftSupplier, Supplier<Object> rightSupplier) {
                return this.assertBool(leftSupplier.get()) || this.assertBool(rightSupplier.get());
            }
        },//
        NOT_EQUALS("!=", 3, AcceptedOperand.ARITHMETIC, AcceptedOperand.LOGICAL) {
            public final boolean eval(Object left, Object right) {
                try {
                    return !left.equals(right);
                } catch (Exception var3) {
                    return left != right;
                }
            }
        },//
        EQUALS("==", 3, AcceptedOperand.ARITHMETIC, AcceptedOperand.LOGICAL, AcceptedOperand.STRING) {
            public final boolean eval(Object left, Object right) {
                try {
                    return left.equals(right);
                } catch (Exception var3) {
                    return left == right;
                }
            }
        },//
        LESS_THAN_OR_EQUAL("<=", 2, AcceptedOperand.ARITHMETIC, AcceptedOperand.COMPARATOR, AcceptedOperand.STRING) {
            public final boolean eval(Object left, Object right) {
                return this.assertNumber(left) <= this.assertNumber(right);
            }
        },//
        LESS_THAN("<", 2, AcceptedOperand.ARITHMETIC, AcceptedOperand.COMPARATOR) {
            public final boolean eval(Object left, Object right) {
                return this.assertNumber(left) < this.assertNumber(right);
            }
        },//
        GREATER_THAN_OR_EQUAL(">=", 2, AcceptedOperand.ARITHMETIC, AcceptedOperand.COMPARATOR) {
            public final boolean eval(Object left, Object right) {
                return this.assertNumber(left) >= this.assertNumber(right);
            }
        },//
        GREATER_THAN(">", 2, AcceptedOperand.ARITHMETIC, AcceptedOperand.COMPARATOR) {
            public final boolean eval(Object left, Object right) {
                return this.assertNumber(left) > this.assertNumber(right);
            }
        };//

        public static final LogicalOperator[] OPERATORS = values();
        private final String symbol;
        private final boolean unary;
        private final byte priority;
        private final AcceptedOperand[] acceptedOperands;

        LogicalOperator(String symbol, int priority, AcceptedOperand... var5) {
            this(symbol, priority, var5, false);
        }

        LogicalOperator(String symbol, int priority, AcceptedOperand[] var5, boolean isUnary) {
            if (var5.length != 0 && var5.length <= 3) {
                this.symbol = symbol;
                this.priority = (byte) priority;
                this.acceptedOperands = var5;
                this.unary = isUnary;
            } else {
                throw new AssertionError("Invalid list of accepted operands: " + Arrays.toString(var5) + " for operator " + symbol);
            }
        }

        public boolean isComparator() {
            return Arrays.stream(this.acceptedOperands).anyMatch((var0) -> var0 == AcceptedOperand.COMPARATOR);
        }

        public boolean hasPrecedenceOver(LogicalOperator var1) {
            return this.priority <= var1.priority;
        }

        public boolean acceptsOperandOfType(LogicalOperand var1) {
            if (var1 instanceof LogicalVariableOperand) {
                return true;
            } else {
                AcceptedOperand var2 = var1 instanceof ArithmeticOperand ? AcceptedOperand.ARITHMETIC : AcceptedOperand.LOGICAL;
                return Arrays.stream(this.acceptedOperands).anyMatch((var1x) -> var1x == var2);
            }
        }

        public boolean eval(Object left, Object right) {
            throw new UnsupportedOperationException();
        }

        public boolean eval(Supplier<Object> leftSupplier, Supplier<Object> rightSupplier) {
            return this.eval(leftSupplier.get(), rightSupplier.get());
        }

        public int symbolSize() {
            return this.symbol.length();
        }

        public Double assertNumber(Object obj) {
            if (obj instanceof Double) {
                return (Double) obj;
            } else {
                throw new LogicalException("Operands of '" + this.symbol + "' operator must be numbers instead got: '" + obj + "' (" + (obj == null ? null : obj.getClass().getName() + ')'));
            }
        }

        public boolean assertBool(Object obj) {
            if (obj instanceof Boolean) {
                return (Boolean) obj;
            } else {
                throw new LogicalException("Operands of '" + this.symbol + "' operator must be booleans instead got: '" + obj + "' (" + (obj == null ? null : obj.getClass().getName() + ')'));
            }
        }

        public enum AcceptedOperand {
            ARITHMETIC, //
            LOGICAL,    //
            COMPARATOR, //
            STRING      //
        }
    }

    public static final class UnaryLogicalOperator extends LogicalOperand {
        private final LogicalOperator logicalOperator;
        private final LogicalOperand b;

        public UnaryLogicalOperator(LogicalOperator var1, LogicalOperand var2) {
            this.logicalOperator = var1;
            this.b = var2;
        }

        public Boolean eval0(ConditionVariableTranslator variableTranslator) {
            return this.logicalOperator.eval(Fn.nullSupplier(), new a(this.b, variableTranslator));
        }

        public String toString() {
            return this.logicalOperator.symbol + this.b.toString();
        }

        public @NotNull String asString(boolean var1) {
            return this.logicalOperator.symbol + this.b.asString(var1);
        }
    }

    public static final class BiLogicalOperator extends LogicalOperand {
        private final LogicalOperator operator;
        private final LogicalOperand left;
        private final LogicalOperand right;

        public BiLogicalOperator(LogicalOperand left, LogicalOperator operator, LogicalOperand right) {
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        public Boolean eval0(ConditionVariableTranslator variableTranslator) {
            return this.operator.eval(new a(this.left, variableTranslator), new a(this.right, variableTranslator));
        }

        public String toString() {
            return "(" + this.left.toString() + ' ' + this.operator.symbol + ' ' + this.right.toString() + ')';
        }

        public @NotNull String asString(boolean var1) {
            return this.left.toString() + ' ' + this.operator.symbol + ' ' + this.right.toString();
        }
    }

    private static final class a implements Supplier<Object> {
        private final LogicalOperand a;
        private final ConditionVariableTranslator b;

        public a(LogicalOperand var1, ConditionVariableTranslator var2) {
            this.a = var1;
            this.b = var2;
        }

        public Object get() {
            return this.a.eval0(this.b);
        }
    }
}
