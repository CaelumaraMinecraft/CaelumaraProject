package net.aurika.utils.checker;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.auspice.utils.function.ToBooleanFunction;

import java.util.Arrays;
import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class Checker {

    //===== Null =====

    private static final ToBooleanFunction NOT_NULL_CHECKER = x -> x != null;

    public static <T> ToBooleanFunction<T> notNullChecker() {
        return NOT_NULL_CHECKER;
    }

    //===== Contains null Array

    private static final ToBooleanFunction NON_NULL_ARRAY_CHECKER = arr -> {
        if (arr == null) return false;
        for (Object x : (Object[]) arr) {
            if (x == null) return false;
        }
        return true;
    };

    public static <T> ToBooleanFunction<T[]> nonNullArrayChecker() {
        return NON_NULL_ARRAY_CHECKER;
    }

    //===== Empty String =====

    private static final ToBooleanFunction<String> NOT_EMPTY_STR_CHECKER = s -> s != null && !s.isEmpty();

    public static ToBooleanFunction<String> notEmptyChecker() {
        return NOT_EMPTY_STR_CHECKER;
    }

    public static <T> T check(T t, ToBooleanFunction<T> checker, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (checker.applyAsBoolean(t)) return t;   // 如果检查通过
        throw exceptionSupplier.get();
    }

    public static final class Arg {

        @Contract("null, _ -> fail")
        public static <T> @NotNull T notNull(T obj, String paramName) {
            return check(obj, notNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Null argument '" + paramName + '\''), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static <T> @NotNull T notNull(T obj, String paramName, String message) {
            return check(obj, notNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Null argument '" + paramName + "': " + message), Arg.class.getName())
            );
        }

        @Contract("null, _ -> fail")
        public static @NotNull String notEmpty(String str, String paramName) {
            return check(str, notEmptyChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Empty argument string '" + paramName + '\''), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static @NotNull String notEmpty(String str, String paramName, String message) {
            return check(str, notEmptyChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Empty argument string '" + paramName + "': " + message), Arg.class.getName())
            );
        }

        @Contract("null, _ -> fail")
        public static <T> @NotNull T @NotNull [] nonNullArray(T[] arr, String paramName) {
            return check(arr, nonNullArrayChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Argument array '" + paramName + "' contains null value"), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static <T> @NotNull T @NotNull [] nonNullArray(T[] arr, String paramName, String message) {
            notNull(arr, paramName, message);
            return check(arr, nonNullArrayChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Argument array '" + paramName + "' contains null value: " + message), Arg.class.getName())
            );
        }
    }

    public static final class Expr {

        @Contract("null, _ -> fail")
        public static <T> @NotNull T notNull(T obj, String exprName) {
            return check(obj, notNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned null value"), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static <T> @NotNull T notNull(T obj, String exprName, String message) {
            return check(obj, notNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned null value: " + message), Arg.class.getName())
            );
        }

        @Contract("null, _ -> fail")
        public static @NotNull String notEmpty(String str, String exprName) {
            return check(str, notEmptyChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned empty string value"), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static @NotNull String notEmpty(String str, String exprName, String message) {
            return check(str, notEmptyChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned empty string value: " + message), Arg.class.getName())
            );
        }

        @Contract("null, _ -> fail")
        public static <T> @NotNull T @NotNull [] nonNullArray(T[] arr, String exprName) {
            return check(arr, nonNullArrayChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned array contains null value"), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static <T> @NotNull T @NotNull [] nonNullArray(T[] arr, String exprName, String message) {
            notNull(arr, exprName, message);
            return check(arr, nonNullArrayChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned array contains null value: " + message), Arg.class.getName())
            );
        }
    }

    public static final class State {

    }

    static <T extends Throwable> T sanitizeStackTrace(T throwable, String classNameToDrop) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        int lastIntrinsic = -1;

        for (int i = 0; i < size; ++i) {
            if (classNameToDrop.equals(stackTrace[i].getClassName())) {
                lastIntrinsic = i;
            }
        }

        StackTraceElement[] newStackTrace = Arrays.copyOfRange(stackTrace, lastIntrinsic + 1, size);
        throwable.setStackTrace(newStackTrace);
        return throwable;
    }
}
