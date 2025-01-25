package top.auspice.utils.compiler.math;

import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import net.aurika.utils.Checker;
import top.auspice.utils.string.Strings;
import top.auspice.utils.time.TimeUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("ExpressionComparedToItself")
public final class MathCompiler {
    private final @NotNull String string;
    private int index;
    private final int length;
    private final boolean d;
    private final @NotNull LinkedList<LexicalEnvironment> environments;
    private final @NotNull LinkedList<Object> mathObjects;

    private static final @NotNull Map<String, Double> constants;
    private static final @NotNull Map<String, MathFunction> functions;
    private static final Operator[] operators;

    public static final Map<Character, Operator> OPERATORS;

    private static final @NotNull MathVariableTranslator wrong = s -> {
        throw new IllegalAccessError();
    };

    @NotNull
    public static final Expression DEFAULT_VALUE = new ConstantExpr(0.0, ConstantExprType.NUMBER);

    private MathCompiler(@NotNull String str, int index, int length, boolean var4, @NotNull LinkedList<LexicalEnvironment> var5) {
        this.string = str;
        this.index = index;
        this.length = length;
        this.d = var4;
        this.environments = var5;
        this.mathObjects = new LinkedList<>();
    }

    @NotNull
    public static Expression compile(@Nullable String str) throws NumberFormatException, ArithmeticException {
        return str == null || str.isEmpty() ? MathCompiler.DEFAULT_VALUE : compile_expr((new MathCompiler(str, 0, str.length(), false, new LinkedList<>())).a()).withOriginalString(str);
    }

    private int a(int index, boolean var2) {
        int pointer_2 = index;
        char indCh = this.string.charAt(index);
        if (!var2) {
            while (('a' <= indCh && indCh < '{') || ('A' <= indCh && indCh < '[') || ('0' <= indCh && indCh < ':') || indCh == '_') {
                ++pointer_2;
                if (pointer_2 == this.length) {
                    return pointer_2;
                }

                indCh = this.string.charAt(pointer_2);
            }
        } else {
            while (true) {
                if (indCh == '}') {
                    ++pointer_2;
                    break;
                }

                ++pointer_2;
                if (pointer_2 == this.length) {
                    throw this.e(index - 1, "Unclosed variable interpolation", new ArrayList<>());
                }

                indCh = this.string.charAt(pointer_2);
            }
        }

        return pointer_2;
    }

    private Expression a() throws NumberFormatException, MathEvaluateException {
        if (this.index == this.length) {
            return this.b();
        } else {
            do {
                int var3 = this.index;
                char var1 = this.string.charAt(var3);
                int var2;
                if (var1 != ' ') {
                    int var4;
                    int var5;
                    int var6;
                    MathCompiler compiler;
                    char indexCh;
                    Expression var27;
                    String var32;
                    Expression var33;
                    if ('0' <= var1 && var1 < ':') {
                        var2 = this.index;
                        compiler = this;
                        var4 = this.index;

                        for (indexCh = this.string.charAt(var4); ('0' <= indexCh && indexCh < ':') || indexCh == 'x' || indexCh == 'e' || indexCh == 'E' || indexCh == '-' || indexCh == '.'; indexCh = compiler.string.charAt(var4)) {
                            if (indexCh == '-') {
                                var6 = compiler.index - 1;
                                if ((var5 = compiler.string.charAt(var6)) != 'e' && var5 != 'E') {
                                    break;
                                }
                            }

                            ++compiler.index;
                            int var10000 = compiler.index;
                            if (compiler.index == compiler.length) {
                                break;
                            }

                            var4 = compiler.index;
                        }

                        var5 = this.index;
                        this.index = var5 + -1;
                        var32 = this.string.substring(var2, var5);
                        Objects.requireNonNull(var32, "");
                        String var24 = var32;

                        ConstantExpr var25;
                        try {
                            var25 = new ConstantExpr(Double.parseDouble(var24), ConstantExprType.NUMBER);
                        } catch (NumberFormatException var16) {
                            throw this.e(var2, "Invalid numeric value \"" + var24 + '"', pointerToName(var2, var24));
                        }

                        var33 = var25;
                    } else if ('a' <= var1 && var1 < '{' || ('A' <= var1 && var1 < '[')) {
                        var33 = this.a(false);
                    } else if (var1 == '{') {
                        var2 = this.index++;
                        var33 = this.a(true);
                    } else {
                        if (var1 == '"') {
                            var2 = this.index++;
                            StringBuilder var23 = new StringBuilder();

                            do {
                                var6 = this.index++;
                                indexCh = this.string.charAt(var6);
                                var23.append(indexCh);
                            } while (indexCh != '"');

                            String var10002 = var23.toString();
                            Objects.requireNonNull(var10002, "");
                            return new StringConstant(var10002);
                        }

                        LexicalEnvironment lastEnv;
                        if (var1 == ',' || var1 == ';') {
                            lastEnv = this.environments.peekLast();
                            if (lastEnv == null) {
                                throw e(this.index, "Function argument separator outside of functions", null);
                            }

                            LexicalEnvironment var22 = lastEnv;
                            if (lastEnv.getFunction() == null) {

                                if (this.environments.stream().anyMatch((env) -> env.getFunction() != null)) {
                                    throw e(var22.getIndex(), "Unclosed parentheses", null);
                                }

                                throw e(this.index, "Function argument separator outside of functions", null);
                            }

                            return this.b();
                        }

                        if (var1 == '[') {
                            var4 = this.index;
                            if ((var5 = this.string.indexOf(']', var4 + 1)) == -1) {
                                throw e(var4, "Cannot find time literal closing bracket.", null);
                            }

                            ++var4;
                            byte var34;
                            if (this.string.charAt(var4) == '-') {
                                ++var4;
                                var34 = -1;
                            } else {
                                var34 = 1;
                            }

                            byte var28 = var34;
                            var32 = this.string.substring(var4, var5);
                            Objects.requireNonNull(var32, "");
                            String var17 = var32;
                            this.index = var5;
                            Long var35 = TimeUtils.parseTime(var17);
                            if (var35 == null) {
                                throw this.e(this.index, "Unknown time format", CollectionsKt.toMutableList(new IntRange(var4, var5)));
                            }

                            long var14 = var35;
                            var33 = new ConstantExpr((double) var28 * (double) var14, ConstantExprType.TIME);
                        } else if (var1 == '(') {
                            var2 = this.index++;
                            this.environments.add(new LexicalEnvironment(var2, null));
                            MathCompiler var19;
                            var27 = (var19 = new MathCompiler(this.string, this.index, this.length, true, this.environments)).a();
                            this.index = var19.index;
                            var33 = var27;
                        } else {
                            if (var1 == ')') {
                                lastEnv = this.environments.pollLast();
                                if (lastEnv == null) {
                                    throw e(this.index, "No opening parentheses found for closing parenthes", null);
                                }

                                if (lastEnv.getFunction() == null && this.mathObjects.isEmpty()) {
                                    throw e(this.index, "Empty subexpression", null);
                                }

                                return this.b();
                            }

                            var33 = null;
                        }
                    }

                    Expression var20 = var33;
                    Object var26;
                    if (var33 == null) {
                        Operator var37 = operators[var1];
                        if (var37 == null) {
                            throw e(this.index, "Unrecognized character '" + var1 + "' (" + var1 + ") outside of variable/placeholder interpolation", null);
                        }

                        var26 = this.mathObjects.peekLast();
                        if (!var37.getArity().isUnary$core() && var26 instanceof Operator) {
                            throw e(this.index, "Blank operand on the left hand side of binary operator", null);
                        }

                        this.mathObjects.addLast(var37);
                    } else {
                        compiler = this;
                        if (this.mathObjects.isEmpty()) {
                            this.mathObjects.add(var20);
                        } else {
                            var27 = var20;
                            if (!((var26 = this.mathObjects.getLast()) instanceof Operator)) {
                                throw e(this.index, "Expected an operator before operand", null);
                            }

//                            label196:
//                            for(; var26 instanceof Operator; var26 = var18.f.peekLast()) {
//                                switch (OldMathCompiler.WhenMappings.$EnumSwitchMapping$0[((Operator)var26).getArity$base().ordinal()]) {
//                                    case 1:
//                                        break label196;
//                                    case 2:
//                                        var27 = new BiOperation(DEFAULT_VALUE, (Operator)var26, var27);
//                                        var18.f.removeLast();
//                                        break;
//                                    case 3:
//                                        var18.f.removeLast();
//                                        Object var29;
//                                        if ((var29 = var18.f.peekLast()) != null && !(var29 instanceof Operator)) {
//                                            var18.f.add(var26);
//                                            break label196;
//                                        }
//
//                                        var27 = new BiOperation(DEFAULT_VALUE, (Operator)var26, var27);
//                                }
//                            }

                            label196:
                            for (; var26 instanceof Operator; var26 = compiler.mathObjects.peekLast()) {
                                switch (((Operator) var26).getArity()) {
                                    case BINARY:
                                        break label196;
                                    case UNARY:
                                        var27 = new BiOperation(DEFAULT_VALUE, (Operator) var26, var27);
                                        compiler.mathObjects.removeLast();
                                        break;
                                    case UNARY_AND_BINARY:
                                        compiler.mathObjects.removeLast();
                                        Object var29 = compiler.mathObjects.peekLast();
                                        if (var29 != null && !(var29 instanceof Operator)) {
                                            compiler.mathObjects.add(var26);
                                            break label196;
                                        }

                                        var27 = new BiOperation(DEFAULT_VALUE, (Operator) var26, var27);
                                }
                            }

                            compiler.mathObjects.addLast(var27);
                        }
                    }
                }

                var2 = this.index++;
            } while (this.index < this.length);

            return this.b();
        }
    }

    private static final Function<LexicalEnvironment, Boolean> isInsideFunction = (env) -> env.getFunction() != null;

    private Expression b() {
        if ((this.index >= this.length || !this.d) && !this.environments.isEmpty()) {
            final ArrayList<Integer> var11 = new ArrayList<>();
            final Ref.BooleanRef var9 = new Ref.BooleanRef();

            for (LexicalEnvironment var1 : this.environments) {
                var11.add(var1.getIndex());
                if (!var9.element) {
                    var9.element = var1.getFunction() != null;
                }
            }

            throw this.e(this.environments.getLast().getIndex(), "Unclosed parentheses" + (var9.element ? " and functions" : ""), var11);
        } else if (this.mathObjects.isEmpty()) {
            return DEFAULT_VALUE;
        } else {
            Object var12;
            if (this.mathObjects.size() == 1) {
                var12 = this.mathObjects.getLast();
                Objects.requireNonNull(var12);
                return (Expression) var12;
            } else {
                Object var2;
                if ((var2 = this.mathObjects.getLast()) instanceof Operator) {
                    String var10 = ((Operator) var2).getSymbol() == '%' ? " (Hint: Write placeholder without % around them." : "";
                    throw e(this.length - 1, "Blank operand on right hand side of " + ((Operator) var2).getSymbol() + var10, null);
                } else {
                    BiOperation var1 = null;
                    ListIterator<Object> var10000 = this.mathObjects.listIterator();
                    Objects.requireNonNull(var10000, "");

                    BiOperation var13;
                    for (; var10000.hasNext(); var1 = var13) {
                        var12 = var10000.next();
                        Objects.requireNonNull(var12);
                        Expression var7 = (Expression) var12;
                        var12 = var10000.next();
                        Objects.requireNonNull(var12);
                        Operator var3 = (Operator) var12;
                        var12 = var10000.next();
                        Objects.requireNonNull(var12);
                        Expression var4 = (Expression) var12;
                        if (!var10000.hasNext()) {
                            var13 = new BiOperation(var7, var3, var4);
                        } else {
                            var12 = var10000.next();
                            Objects.requireNonNull(var12);
                            Operator var5 = (Operator) var12;
                            var12 = var10000.next();
                            Objects.requireNonNull(var12);
                            Expression var6 = (Expression) var12;
                            if (var3.hasPrecedenceOver$core(var5)) {
                                var1 = new BiOperation(var7, var3, var4);
                                var10000.previous();
                                var10000.previous();
                                var10000.previous();
                                var10000.remove();
                                var10000.previous();
                                var10000.remove();
                                var10000.previous();
                                var10000.set(var1);
                                var13 = var1;
                            } else {
                                var1 = new BiOperation(var4, var5, var6);
                                var10000.remove();
                                var10000.previous();
                                var10000.remove();
                                var10000.previous();
                                var10000.set(var1);
                                var10000.previous();
                                var10000.previous();
                                var13 = var1;
                            }
                        }
                    }

                    Objects.requireNonNull(var1);
                    return var1;
                }
            }
        }
    }

    private Expression a(boolean bl) {
        int n;
        MathCompiler mathCompiler = this;
        int n2 = mathCompiler.a(mathCompiler.index, bl);
        String string = this.string.substring(this.index, bl ? n2 - 1 : n2);
        if ("_".equals(string)) {
            MathCompiler mathCompiler2 = this;
            throw this.e(mathCompiler2.index, "Reserved single underscore identifier", null);
        }
        this.index = n2;
        MathCompiler mathCompiler3 = this;
        int n3 = this.index;
        while (n3 < this.length && mathCompiler3.string.charAt(n3) == ' ') {
            ++n3;
        }
        mathCompiler3.index = n3;
        if (this.index < this.length) {
            MathCompiler mathCompiler5 = this;
            if (mathCompiler5.string.charAt(n3) == '(') {
                return this.a(string);
            }
        }
        this.index = n2 - 1;
        Double d = constants.get(string);
        if (d == null) {
            return new MathVariable(string);
        }
        return new ConstantExpr(d, ConstantExprType.CONSTANT_VARIABLE);
    }

    private FunctionExpr a(String funcStr) {
        String var2 = (var2 = findFunction(funcStr)) == null ? "" : "; Did you mean '" + var2 + "' function?";
        MathFunction function = functions.get(funcStr);
        if (function == null) {
            throw e(this.index, "Unknown function: " + funcStr + var2, null);
        } else {
            int var3 = this.index++;
            ArrayList<Expression> expressions = new ArrayList<>();
            LexicalEnvironment var4 = new LexicalEnvironment(this.index, function);
            this.environments.add(var4);
            int var5 = this.index;

            do {
                MathCompiler otherCompiler = new MathCompiler(this.string, this.index, this.length, true, this.environments);
                Expression otherExpr = otherCompiler.a();
                if (!DEFAULT_VALUE.equals(otherExpr)) {
                    expressions.add(otherExpr);
                }

                this.index = otherCompiler.index + 1;
            } while (var4.equals(this.environments.peekLast()));

            int var10 = this.index;
            this.index = var10 + -1;
            if (function.getArgCount() < 0) {
                var10 = Math.abs(function.getArgCount()) - 1;
                if (expressions.size() < var10) {
                    throw this.e(var5, "Too few arguments for function '" + funcStr + "', expected at least: " + var10 + ", got: " + expressions.size(), pointerToName(var5, funcStr));
                }
            } else {
                if (expressions.size() < function.getArgCount()) {
                    throw this.e(var5, "Too few arguments for function '" + funcStr + "', expected: " + function.getArgCount() + ", got: " + expressions.size(), pointerToName(var5, funcStr));
                }

                if (expressions.size() > function.getArgCount()) {
                    throw this.e(var5, "Too many arguments for function '" + funcStr + "', expected: " + function.getArgCount() + ", got: " + expressions.size(), pointerToName(var5, funcStr));
                }
            }

            return new FunctionExpr(funcStr, function, expressions.toArray(new Expression[0]));
        }
    }

    private MathEvaluateException e(int offset, String string, Collection<Integer> collection) {
        if (collection == null) {
            collection = new ArrayList<>(4);
        }
        string = "\n" + string + " at offset " + offset + " in expression: \n\"" + this.string + '\"';
        int n2 = 0;
        collection.add(offset);
        for (Integer integer : collection) {
            int n3 = integer.intValue();
            if (n3 <= n2) continue;
            n2 = n3;
        }
        int n4 = n2 + 2;
        char[] object = new char[n4];
        Arrays.fill(object, ' ');
        StringBuilder builder = new StringBuilder(new String(object));
        collection.forEach(arg_0 -> MathCompiler.a(builder, arg_0));
        return new MathEvaluateException(string + '\n' + builder);
    }

    private static MathEvaluateException e(MathCompiler mathCompiler, int index, String string, Collection<Integer> collection, int n2) {
        collection = new ArrayList<>(n2);
        return mathCompiler.e(index, string, collection);
    }

    public static Collection<Integer> pointerToName(int n, String name) {
        int length = name.length();
        List<Integer> object = new ArrayList<>(length);
        for (int i = 1; i < length; ++i) {
            object.add(n + i);
        }
        return object;
    }

    public static String findFunction(String name) {
        Objects.requireNonNull(name, "The string used for the search function must be provided");
        String lowerCaseName = name.toLowerCase(Locale.ENGLISH);
        Optional<String> object2 = MathCompiler.getFunctions().keySet().stream().filter(funcName -> funcName.equals(lowerCaseName)).findFirst();  //TODO class 1 ? 2
        return object2.orElseGet(() -> findFunction2nd(lowerCaseName));
    }

    private static String findFunction2nd(final String name) {
        Objects.requireNonNull(name, "The string used for the search function must be provided");
        return MathCompiler.functions.keySet().stream().filter((funcName) -> {
            funcName = funcName.toLowerCase(Locale.ENGLISH);
            return (name.contains(funcName) || funcName.contains(name) && Math.abs(funcName.length() - name.length()) < 2);
        }).findFirst().orElse(null);
    }

    private static void a(Function<Object, Unit> var0, Object var1) {
        Objects.requireNonNull(var0, "");
        var0.apply(var1);
    }

    private static void a(StringBuilder stringBuilder, int index) {
        Objects.requireNonNull(stringBuilder, "");
        stringBuilder.setCharAt(index + 1, '^');
    }

    private static void default$registerConstants() {
        constants.put("E", Math.E);
        constants.put("PI", Math.PI);
        constants.put("Euler", 0.5772156649015329);
        constants.put("LN2", 0.693147180559945);
        constants.put("LN10", 2.302585092994046);
        constants.put("LOG2E", 1.442695040888963);
        constants.put("LOG10E", 0.434294481903252);
        constants.put("PHI", 1.618033988749895);
    }

    private static void default$registerFunctions() {
        registerFunction("abs", args -> Math.abs(args.next()));
        registerFunction("acos", args -> Math.acos(args.next()));
        registerFunction("asin", args -> Math.asin(args.next()));
        registerFunction("atan", args -> Math.atan(args.next()));
        registerFunction("cbrt", args -> Math.cbrt(args.next()));
        registerFunction("ceil", args -> Math.ceil(args.next()));
        registerFunction("cos", args -> Math.cos(args.next()));
        registerFunction("cosh", args -> Math.cosh(args.next()));
        registerFunction("exp", args -> Math.exp(args.next()));
        registerFunction("expm1", args -> Math.expm1(args.next()));
        registerFunction("floor", args -> Math.floor(args.next()));
        registerFunction("getExponent", args -> Math.getExponent(args.next()));
        registerFunction("logging", args -> Math.log(args.next()));
        registerFunction("log10", args -> Math.log10(args.next()));
        registerFunction("log1p", args -> Math.log1p(args.next()));
        registerFunction("max", true, args -> CollectionsKt.maxOrThrow(args.allArgs()), -2);
        registerFunction("min", true, args -> CollectionsKt.minOrThrow(args.allArgs()), -2);
        registerFunction("nextUp", args -> Math.nextUp(args.next()));
        registerFunction("nextDown", args -> Math.nextDown(args.next()));
        registerFunction("nextAfter", true, args -> Math.nextAfter(args.next(), args.next()), 2);
        registerFunction("random", false, args -> ThreadLocalRandom.current().nextDouble(args.next(), args.next() + 1.0), 2);
        registerFunction("randInt", false, args -> ThreadLocalRandom.current().nextInt((int) args.next(), (int) args.next() + 1), 2);
        registerFunction("round", args -> (double) Math.round(args.next()));
        registerFunction("rint", args -> Math.rint(args.next()));
        registerFunction("signum", args -> Math.signum(args.next()));
        registerFunction("whatPercentOf", true, args -> args.next() / args.next() * 100.0, 2);
        registerFunction("percentOf", true, args -> args.next() / 100.0 * args.next(), 2);
        registerFunction("sin", args -> Math.sin(args.next()));
        registerFunction("sinh", args -> Math.sinh(args.next()));
        registerFunction("bits", args -> (double) Double.doubleToRawLongBits(args.next()));
        registerFunction("hash", args -> Double.hashCode(args.next()));
        registerFunction("identityHash", args -> System.identityHashCode(args.next()));
        registerFunction("time", false, __ -> (double) System.currentTimeMillis(), 0);
        registerFunction("sqrt", args -> Math.sqrt(args.next()));
        registerFunction("tan", args -> Math.tan(args.next()));
        registerFunction("tanh", args -> Math.tanh(args.next()));
        registerFunction("toDegrees", args -> Math.toDegrees(args.next()));
        registerFunction("toRadians", args -> Math.toRadians(args.next()));
        registerFunction("ulp", args -> Math.ulp(args.next()));
        registerFunction("scalb", true, args -> Math.scalb(args.next(), (int) args.next()), 2);
        registerFunction("hypot", true, args -> Math.hypot(args.next(), args.next()), 2);
        registerFunction("copySign", true, args -> Math.copySign(args.next(), args.next()), 2);
        registerFunction("IEEEremainder", true, args -> Math.IEEEremainder(args.next(), args.next()), 2);
        registerFunction("naturalSum", args -> {
            int arg1 = (int) args.next();
            return (double) (arg1 * (arg1 + 1)) / 2.0;
        });
        registerFunction("reverse", args -> (double) Long.reverse((long) args.next()));
        registerFunction("reverseBytes", args -> (double) Long.reverseBytes((long) args.next()));
        registerFunction("eq", true, args -> args.next() == args.next() ? args.next() : args.next(3), 4);
        registerFunction("ne", true, args -> args.next() != args.next() ? args.next() : args.next(3), 4);
        registerFunction("gt", true, args -> args.next() > args.next() ? args.next() : args.next(3), 4);
        registerFunction("lt", true, args -> args.next() < args.next() ? args.next() : args.next(3), 4);
        registerFunction("ge", true, args -> args.next() >= args.next() ? args.next() : args.next(3), 4);
        registerFunction("le", true, args -> args.next() <= args.next() ? args.next() : args.next(3), 4);
    }

    private static void default$registerOperators() {
        registerOperator(new Operator('^', 12, 13, Side.NONE, ((left, right) -> Math.pow(left, right))));
        registerOperator(new Operator('*', 10, (left, right) -> left * right));
        registerOperator(new Operator('(', 10, (left, right) -> left * right));
        registerOperator(new Operator('/', 10, (left, right) -> left / right));
        registerOperator(new Operator('%', 10, (left, right) -> left % right));
        registerOperator(new Operator('+', 9, (left, right) -> left + right));
        registerOperator(new Operator('-', 9, (left, right) -> left - right));
        registerOperator(new Operator('~', 10, (left, right) -> (double) (~((long) right))));                         //按位取反
        registerOperator(new Operator('@', 8, (left, right) -> (double) Long.rotateLeft((long) left, (int) right)));  //循环左移
        registerOperator(new Operator('#', 8, (left, right) -> (double) Long.rotateRight((long) left, (int) right))); //循环右移
        registerOperator(new Operator('>', 8, (left, right) -> (double) ((long) left >> (int) ((long) right))));      //左移
        registerOperator(new Operator('<', 8, (left, right) -> (double) ((long) left << (int) ((long) right))));      //左移
        registerOperator(new Operator('$', 8, (left, right) -> (double) ((long) left >>> (int) ((long) right))));     //无符号右移
        registerOperator(new Operator('&', 7, (left, right) -> (double) ((long) left & (long) right)));               //按位与
        registerOperator(new Operator('!', 6, (left, right) -> (double) ((long) left ^ (long) right)));               //按位异或
        registerOperator(new Operator('|', 5, (left, right) -> (double) ((long) left | (long) right)));               //按位或
    }

    private static void registerOperator(Operator operator) {
        if (operator.getSymbol() >= MathCompiler.operators.length) {
            String excMsg = "Operator handler cannot handle char '" + operator.getSymbol() + "' with char code: " + operator.getSymbol();
            throw new IllegalArgumentException(excMsg);
        } else {
            MathCompiler.operators[operator.getSymbol()] = operator;
        }
    }

    private static void registerFunction(String fnName, boolean optimizable, QuantumFunction quantumFunction, int argCount) {
        MathCompiler.functions.put(fnName, new MathFunction(quantumFunction, optimizable, argCount));
    }

    private static void registerFunction(String string, QuantumFunction quantumFunction) {
        registerFunction(string, true, quantumFunction, 1);
    }

    static {
        constants = new HashMap<>(8);
        functions = new HashMap<>(44);
        operators = new Operator[127];

        default$registerConstants();
        default$registerFunctions();
        default$registerOperators();

        Map<Character, Operator> ops = new HashMap<>();

        for (char i = 0; i < operators.length; i++) {
            Operator op = operators[i];
            if (op != null) {
                ops.put(i, op);
            }
        }

        OPERATORS = Collections.unmodifiableMap(ops);
    }

    private static Expression compile_expr(Expression expr) {
        if (expr instanceof BiOperation biOp) {
            Expression leftExpr = compile_expr(biOp.getLeft());
            Expression rightExpr = compile_expr(biOp.getRight());
            if (leftExpr instanceof ConstantExpr && rightExpr instanceof ConstantExpr) {
                return new ConstantExpr(biOp.getOperator().getFunction().apply(((ConstantExpr) leftExpr).getValue$core(), ((ConstantExpr) rightExpr).getValue$core()), ConstantExprType.OPTIMIZED);
            }
        } else if (expr instanceof FunctionExpr funExpr) {
            if (!funExpr.getHandler().isOptimizable()) {
                return expr;
            }

            boolean isConstantExpr = true;
            ArrayList<Expression> expressions = new ArrayList<>(funExpr.getArgs().length);
            Expression[] var4 = funExpr.getArgs();
            int var5 = 0;

            for (int var6 = var4.length; var5 < var6; ++var5) {
                Expression var7 = var4[var5];
                var7 = compile_expr(var7);
                expressions.add(var7);
                if (isConstantExpr) {
                    isConstantExpr = var7 instanceof ConstantExpr;
                }
            }

            if (isConstantExpr) {
                return new ConstantExpr(funExpr.getHandler().getFunction().apply(new FnArgs(funExpr, MathCompiler.wrong)), ConstantExprType.OPTIMIZED);
            }

            Collection<Expression> var10;
            return new FunctionExpr(funExpr.getName(), funExpr.getHandler(), expressions.toArray(new Expression[0]));
        }

        return expr;
    }

    public static @Unmodifiable Map<String, MathFunction> getFunctions() {
        return Collections.unmodifiableMap(functions);
    }

    public static @Unmodifiable Map<String, Double> getConstants() {
        return Collections.unmodifiableMap(constants);
    }

    public static class ConstantExpr extends Expression {

        private final double value;
        @NotNull
        private final ConstantExprType type;

        public ConstantExpr(double value, @NotNull ConstantExprType exprType) {
            Objects.requireNonNull(exprType, "");

            this.value = value;
            this.type = exprType;
        }

        public final double getValue$core() {
            return this.value;
        }

        public double getValue() {
            return this.value;
        }

        @NotNull
        public final ConstantExprType getType() {
            return this.type;
        }

        public ConstantExpr(double value) {
            this(value, ConstantExprType.NUMBER);
        }

        @NotNull
        public Double eval(@NotNull MathVariableTranslator variableTranslator) {
            Objects.requireNonNull(variableTranslator, "");
            return this.value;
        }

        @NotNull
        public String asString(boolean b) {
            return String.valueOf(this.value);
        }

        @NotNull
        public String toString() {
            return "ConstantExpr(" + this.value + ')';
        }
    }

    public enum Arity {
        UNARY,               //一元运算符
        BINARY,              //二元运算符
        UNARY_AND_BINARY;    //

        public final boolean isUnary$core() {
            return this == UNARY || this == UNARY_AND_BINARY;
        }
    }

    public static final class BiOperation extends Expression {
        @NotNull
        private final Expression left;
        @NotNull
        private final Operator operator;
        @NotNull
        private final Expression right;

        public BiOperation(@NotNull Expression left, @NotNull Operator operator, @NotNull Expression right) {
            Objects.requireNonNull(left, "");
            Objects.requireNonNull(operator, "");
            Objects.requireNonNull(right, "");

            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @NotNull
        public Expression getLeft() {
            return this.left;
        }

        @NotNull
        public Operator getOperator() {
            return this.operator;
        }

        @NotNull
        public Expression getRight() {
            return this.right;
        }

        @NotNull
        public Double eval(@NotNull MathVariableTranslator variableTranslator) {
            Objects.requireNonNull(variableTranslator, "");
            return this.operator.getFunction().apply(this.left.eval(variableTranslator), this.right.eval(variableTranslator));
        }

        @NotNull
        public String asString(boolean b) {
            return this.left.asString(b) + ' ' + this.operator.getSymbol() + ' ' + this.right.asString(b);
        }

        @NotNull
        public String toString() {
            return "(" + this.left + ' ' + this.operator.getSymbol() + (this.operator.getSymbol() == '(' ? "" : ' ') + this.right + ')';
        }
    }

    public abstract static class Expression implements MathExpression {

        private @Nullable String originalString;

        protected Expression() {
        }

        public final @Nullable String getOriginalString$core() {
            return this.originalString;
        }

        public final void setOriginalString$core(@Nullable String originalString) {
            this.originalString = originalString;
        }

        public abstract @NotNull Double eval(@NotNull MathVariableTranslator variableTranslator);

        public boolean isDefault() {
            return this == MathCompiler.DEFAULT_VALUE;
        }

        public @Nullable String getOriginalString() {
            return this.originalString;
        }

        public @NotNull Expression withOriginalString(@NotNull String originalString) {
            Objects.requireNonNull(originalString, "");
            this.originalString = originalString;
            return this;
        }

        public final <T extends Expression> boolean contains(@NotNull Class<T> type, @NotNull Predicate<T> predicate) {
            Checker.Argument.checkNotNull(type, "type");
            Checker.Argument.checkNotNull(predicate, "predicate");

            if (type.isInstance(this)) {
                return predicate.test((T) this);
            }

            if (this instanceof BiOperation) {
                return ((BiOperation) this).getLeft().contains(type, predicate) || ((BiOperation) this).getRight().contains(type, predicate);
            } else if (this instanceof FunctionExpr) {
                Expression[] var3 = ((FunctionExpr) this).getArgs();
                int var4 = 0;

                for (int var5 = var3.length; var4 < var5; ++var4) {
                    if (var3[var4].contains(type, predicate)) {
                        return true;
                    }
                }

                return false;
            } else {
                return false;
            }
        }
    }

    public static final class FnArgs {
        private final @NotNull FunctionExpr func;
        private final @NotNull MathVariableTranslator variableTranslator;
        private int index;

        public FnArgs(@NotNull FunctionExpr function, @NotNull MathVariableTranslator variableTranslator) {
            Objects.requireNonNull(function, "");
            Objects.requireNonNull(variableTranslator, "");

            this.func = function;
            this.variableTranslator = variableTranslator;
        }

        public double next() {
            Expression[] args = this.func.getArgs();
            int var1 = this.index++;
            return args[var1].eval(this.variableTranslator);
        }

        @NotNull
        public List<Double> allArgs() {
            Expression[] var1 = this.func.getArgs();
            List<Double> var7 = new ArrayList<>(var1.length);
            int var3 = 0;

            for (int var4 = var1.length; var3 < var4; ++var3) {
                Expression var5 = var1[var3];
                var7.add(var5.eval(this.variableTranslator));
            }

            return var7;
        }

        public double next(int d) {
            Expression[] expressions = this.func.getArgs();
            this.index = d;
            return expressions[d].eval(this.variableTranslator);
        }
    }

    public enum ConstantExprType {
        OPTIMIZED,
        NUMBER,
        STRING,
        CONSTANT_VARIABLE,
        TIME;

        ConstantExprType() {
        }
    }

    public static final class MathFunction {
        @NotNull
        private final QuantumFunction function;
        private final boolean isOptimizable;
        private final int argCount;

        public MathFunction(@NotNull QuantumFunction function, boolean optimizable, int argCount) {
            Objects.requireNonNull(function, "");
            this.function = function;
            this.isOptimizable = optimizable;
            this.argCount = argCount;
        }

        @NotNull
        public QuantumFunction getFunction() {
            return this.function;
        }

        public boolean isOptimizable() {
            return this.isOptimizable;
        }

        public int getArgCount() {
            return this.argCount;
        }
    }

    public static final class FunctionExpr extends Expression {
        private final @NotNull String name;
        private final @NotNull MathFunction handler;
        private final @NotNull Expression[] args;

        public FunctionExpr(@NotNull String name, @NotNull MathFunction handler, @NotNull Expression[] args) {
            Checker.Argument.checkNotNull(name, "name");
            Checker.Argument.checkNotNull(handler, "handler");
            Checker.Argument.checkNotNull(args, "args");
            this.name = name;
            this.handler = handler;
            this.args = args;
        }

        public @NotNull String getName() {
            return this.name;
        }

        public @NotNull MathFunction getHandler() {
            return this.handler;
        }

        public @NotNull Expression[] getArgs() {
            return this.args;
        }

        public @NotNull Double eval(@NotNull MathVariableTranslator variableTranslator) {
            Objects.requireNonNull(variableTranslator, "");
            return this.handler.getFunction().apply(new FnArgs(this, variableTranslator));
        }

        public @NotNull String asString(boolean b) {
            return this.name + '(' + Strings.join(this.args, ", ") + ')';
        }

        public @NotNull String toString() {
            String[] var1 = Arrays.stream(this.args).map(Object::toString).toArray(FunctionExpr::a);
            Objects.requireNonNull(var1, "");
            return this.name + '(' + (this.args.length == 0 ? "" : Strings.join(var1, ", ")) + ')';
        }

        private static String a(Function<Object, String> var0, Object var1) {
            Objects.requireNonNull(var0, "");
            return var0.apply(var1);
        }

        private static String[] a(int var0) {
            return new String[var0];
        }
    }

    public static final class LexicalEnvironment {
        private final int index;
        @Nullable
        private final MathFunction function;

        public LexicalEnvironment(int index, @Nullable MathFunction mathFunction) {
            this.index = index;
            this.function = mathFunction;
        }

        public int getIndex() {
            return this.index;
        }

        @Nullable
        public MathFunction getFunction() {
            return this.function;
        }

        public boolean isFunction() {
            return this.getFunction() != null;
        }

        @NotNull
        public String toString() {
            return "LexicalEnvironment{index=" + this.index + ", function=" + (this.getFunction() != null) + '}';
        }
    }

    public static final class Operator {
        private final char symbol;
        private final byte priority;  //优先级?
        private final byte c;
        @NotNull
        private final Side side;
        @NotNull
        private final Arity arity;
        @NotNull
        private final TriDoubleFn function;

        public Operator(char symbol, int priority, int var3, @NotNull Side side, @NotNull TriDoubleFn fn) {
            Objects.requireNonNull(side, "");
            Objects.requireNonNull(fn, "");
            this.symbol = symbol;
            this.priority = (byte) priority;
            this.c = (byte) var3;
            this.side = side;
            this.function = fn;
            this.arity = symbol == '-' ? Arity.UNARY_AND_BINARY : (symbol == '~' ? Arity.UNARY : Arity.BINARY);
        }

        public Operator(char ch, int priority, @NotNull TriDoubleFn var3) {
            this(ch, priority, priority, Side.NONE, var3);
        }

        public char getSymbol() {
            return this.symbol;
        }

        @NotNull
        public Arity getArity() {
            return this.arity;
        }

        @NotNull
        public TriDoubleFn getFunction() {
            return this.function;
        }

        @NotNull
        public Side getSide() {
            return this.side;
        }

        @NotNull
        public String toString() {
            return "MathOperator['" + this.symbol + "']";
        }

        public boolean hasPrecedenceOver$core(@NotNull Operator operator) {
            Objects.requireNonNull(operator, "");
            return this.priority >= operator.priority;
        }
    }

    @FunctionalInterface
    public interface QuantumFunction {
        double apply(@NotNull FnArgs args);
    }

    public enum Side {
        RIGHT,
        LEFT,
        NONE,
    }

    public static class StringConstant extends ConstantExpr {
        @NotNull
        private final String string;

        public StringConstant(@NotNull String string) {
            super(string.hashCode(), ConstantExprType.STRING);
            this.string = string;
        }

        @NotNull
        public final String getString$core() {
            return this.string;
        }

        @NotNull
        public Double eval(@NotNull MathVariableTranslator variableTranslator) {
            Objects.requireNonNull(variableTranslator, "");
            return super.getValue$core();
        }

        @NotNull
        public String toString() {
            return "StringConstant(\"" + this.string + "\")";
        }

        @NotNull
        public String asString(boolean b) {
            return this.string;
        }
    }

    public interface TriDoubleFn {
        double apply(double left, double right);
    }

    private static final class MathVariable extends Expression {
        @NotNull
        private final String variableName;

        public MathVariable(@NotNull String name) {
            this.variableName = name;
        }

        @NotNull
        public Double eval(@NotNull MathVariableTranslator variableTranslator) {
            Objects.requireNonNull(variableTranslator, "");
            Double var3 = variableTranslator.apply(this.variableName);
            if (var3 == null) {
                String var4 = MathCompiler.findFunction(this.variableName);
                String var2 = "";
                if (var4 != null) {
                    var2 = "; Did you mean to invoke '" + var4 + "' function? If so, put parentheses after the name like '" + var4 + "(args)'";
                }

                throw new MathEvaluateException("Unknown variable: '" + this.variableName + '\'' + var2);
            } else {
                return var3;
            }
        }

        @NotNull
        public String asString(boolean b) {
            return this.variableName;
        }

        @NotNull
        public String toString() {
            return "MathVariable(" + this.variableName + ')';
        }
    }
}
