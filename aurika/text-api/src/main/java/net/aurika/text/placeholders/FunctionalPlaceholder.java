package net.aurika.text.placeholders;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.text.compiler.placeholders.types.KingdomsPlaceholder;
import net.aurika.text.compiler.placeholders.functions.PlaceholderFunctionData;
import net.aurika.text.compiler.placeholders.functions.PlaceholderFunctionInvoker;
import top.auspice.constants.player.AuspicePlayer;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public abstract class FunctionalPlaceholder implements Function1<KingdomsPlaceholderTranslationContext, Object> {
    @NotNull
    private final Map<String, CompiledFunction> a;
    @NotNull
    private static final Map<Class<?>, String> b;

    public FunctionalPlaceholder() {
        Map<String, FunctionalPlaceholder.CompiledFunction> var1 = new LinkedHashMap<>();
        Method[] var10000 = this.getClass().getMethods();
        Objects.requireNonNull(var10000);
        int var3 = 0;

        for (int var4 = var10000.length; var3 < var4; ++var3) {
            Method var5;
            if ((var5 = var10000[var3]).getAnnotation(PlaceholderFunction.class) != null) {
                String var6 = var5.getName();
                List<Parameter> var7 = new ArrayList<>();
                java.lang.reflect.Parameter[] var25 = var5.getParameters();
                Objects.requireNonNull(var25);
                int var9 = 0;

                for (int var10 = var25.length; var9 < var10; ++var9) {
                    java.lang.reflect.Parameter var11;
                    PlaceholderParameter var12;
                    String var26;
                    if ((var12 = (var11 = var25[var9]).getAnnotation(PlaceholderParameter.class)) != null) {
                        var26 = var12.name();
                    } else if (var11.isNamePresent()) {
                        String var13;
                        Objects.requireNonNull(var13 = var11.getName());
                        var26 = var13;
                    } else {
                        if (!b.containsKey(var11.getType())) {
                            throw new IllegalArgumentException("Missing parameter name from function placeholder: " + var6);
                        }

                        String var27 = b.get(var11.getType());
                        Objects.requireNonNull(var27);
                        var26 = var27;
                    }

                    String var20 = var26;
                    Class<?> paramType = var11.getType();
                    ParameterType var28;
                    if (Intrinsics.areEqual(paramType, Integer.TYPE)) {
                        var28 = FunctionalPlaceholder.ParameterType.INT;
                    } else if (Intrinsics.areEqual(paramType, String.class)) {
                        var28 = FunctionalPlaceholder.ParameterType.STRING;
                    } else if (Intrinsics.areEqual(paramType, Boolean.TYPE)) {
                        var28 = FunctionalPlaceholder.ParameterType.BOOL;
                    } else if (Intrinsics.areEqual(paramType, KingdomsPlaceholderTranslationContext.class)) {
                        var28 = FunctionalPlaceholder.ParameterType.CONTEXT;
                    } else if (Intrinsics.areEqual(paramType, PlaceholderFunctionInvoker.class)) {
                        var28 = FunctionalPlaceholder.ParameterType.FN;
                    } else {
                        if (!Intrinsics.areEqual(paramType, AuspicePlayer.class)) {
                            throw new IllegalArgumentException("Unsupported argument type " + var11.getType() + " for parameter " + var20 + " in function " + var6);
                        }

                        var28 = FunctionalPlaceholder.ParameterType.AUSPICE_PLAYER;
                    }

                    ParameterType var23 = var28;
                    Annotation[] var10003 = var11.getDeclaredAnnotations();
                    Objects.requireNonNull(var10003);
                    int var18 = 0;
                    int var21 = var10003.length;

                    boolean var29;
                    while (true) {
                        if (var18 >= var21) {
                            var29 = false;
                            break;
                        }

                        if (Intrinsics.areEqual(var10003[var18].getClass().getName(), "Nullable")) {
                            var29 = true;
                            break;
                        }

                        ++var18;
                    }

                    boolean var19 = var29;
                    var7.add(new Parameter(var20, var23, var19));
                }

                Objects.requireNonNull(var6);
                Objects.requireNonNull(var5);
                var1.put(var6, new CompiledFunction(var6, var5, var7));
            }
        }

        this.a = var1;
    }

    @NotNull
    public final Map<String, CompiledFunction> getFunctions() {
        return this.a;
    }

    @Nullable
    public Object invoke(@NotNull KingdomsPlaceholderTranslationContext var1) {
        Objects.requireNonNull(var1, "");
        String[] var2;
        var1.getPlaceholder().requireFunction(Arrays.copyOf(var2 = (String[]) ((Collection) this.a.keySet()).toArray(new String[0]), var2.length));
        PlaceholderFunctionData var10000 = var1.getPlaceholder().getFunction();
        Objects.requireNonNull(var10000);
        String var4 = var10000.getFunctionName();
        CompiledFunction var3 = this.a.get(var4);
        Objects.requireNonNull(var3);
        return var3.invoke(var1);
    }

    static {
        b = new HashMap<>();
        b.put(KingdomsPlaceholderTranslationContext.class, "context");
        b.put(PlaceholderFunctionInvoker.class, "fn");
        b.put(KingdomPlayer.class, "kp");
    }

    public static final class Companion {
        private Companion() {
        }
    }

    public final class CompiledFunction {
        @NotNull
        private final String a;
        @NotNull
        private final Method b;
        @NotNull
        private final List<Parameter> c;

        public CompiledFunction(@NotNull String var2, @NotNull Method var3, @NotNull List<Parameter> var4) {
            Objects.requireNonNull(var2);
            Objects.requireNonNull(var3);
            Objects.requireNonNull(var4);
            this.a = var2;
            this.b = var3;
            this.c = var4;
        }

        @NotNull
        public String getFnName() {
            return this.a;
        }

        @NotNull
        public Method getMethod() {
            return this.b;
        }

        @NotNull
        public List<Parameter> getParameters() {
            return this.c;
        }

        @Nullable
        public Object invoke(@NotNull KingdomsPlaceholderTranslationContext var1) {
            Objects.requireNonNull(var1);
            KingdomsPlaceholder var10000 = var1.getPlaceholder();
            String[] var4;
            (var4 = new String[1])[0] = this.a;
            PlaceholderFunctionInvoker var10 = var10000.requireFunction(var4);
            Objects.requireNonNull(var10);
            List<Object> var3 = new ArrayList<>();

            for (Parameter parameter : this.c) {
                Parameter var5;
                String var6 = (var5 = parameter).getName();
                Object var12;
                if (var5.getOptional() && !var5.getType().isInternal() && !var10.has(var6)) {
                    var12 = null;
                } else {
                    switch (var5.getType()) {
                        case STRING:
                            var12 = var10.getString(var6);
                            break;
                        case INT:
                            var12 = var10.getInt(var6);
                            break;
                        case BOOL:
                            var12 = var10.getBool(var6);
                            break;
                        case CONTEXT:
                            var12 = var1;
                            break;
                        case FN:
                            var12 = var10;
                            break;
                        case AUSPICE_PLAYER:
                            var12 = var1.getPlayer();
                            if (var12 == null) {
                                if (!var5.getOptional()) {
                                    return null;
                                }
                            }
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                }

                Object var9 = var12;
                var3.add(var9);
            }

            Object[] var8 = var3.toArray(new Object[0]);
            try {
                return this.b.invoke(FunctionalPlaceholder.this, Arrays.copyOf(var8, var8.length));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final class Parameter {
        @NotNull
        private final String a;
        @NotNull
        private final ParameterType b;
        private final boolean c;

        public Parameter(@NotNull String var1, @NotNull ParameterType var2, boolean var3) {
            Objects.requireNonNull(var1);
            Objects.requireNonNull(var2);
            this.a = var1;
            this.b = var2;
            this.c = var3;
        }

        @NotNull
        public String getName() {
            return this.a;
        }

        @NotNull
        public ParameterType getType() {
            return this.b;
        }

        public boolean getOptional() {
            return this.c;
        }
    }

    public enum ParameterType {
        CONTEXT(true),       //when 4
        FN(true),            //when 5
        STRING(false),       //when 1
        INT(false),          //when 2
        BOOL(false),         //when 3
        AUSPICE_PLAYER(true);//when 6
        private final boolean isInternal;

        ParameterType(boolean isInternal) {
            this.isInternal = isInternal;
        }

        public final boolean isInternal() {
            return this.isInternal;
        }

    }
}