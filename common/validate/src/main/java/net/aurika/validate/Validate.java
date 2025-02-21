package net.aurika.validate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class Validate {

    //===== Null =====

    private static final Predicate NOT_NULL = Objects::nonNull;

    static <T> Predicate<T> notNullChecker() {
        return NOT_NULL;
    }

    //===== Contains null Array

    private static final Predicate NON_NULL_ARRAY = arr -> {
        if (arr == null) return false;
        for (Object x : (Object[]) arr) {
            if (x == null) return false;
        }
        return true;
    };

    static <T> Predicate<T[]> nonNullArrayChecker() {
        return NON_NULL_ARRAY;
    }

    //===== Empty String =====

    private static final Predicate<String> NOT_EMPTY_STRING = s -> s != null && !s.isEmpty();

    static Predicate<String> notEmptyChecker() {
        return NOT_EMPTY_STRING;
    }

    static <T> T check(T t, Predicate<T> checker, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (checker.test(t)) return t;   // 如果检查通过
        else throw exceptionSupplier.get();
    }

    public static final class Arg {
        public static void require(boolean expression, @NotNull Supplier<String> errorMessageSupplier) {
            if (!expression)
                throw sanitizeStackTrace(new IllegalArgumentException(errorMessageSupplier.get()), Arg.class.getName());
        }

        public static void require(boolean expression, @NotNull String errorMessage) {
            if (!expression)
                throw sanitizeStackTrace(new IllegalArgumentException(errorMessage), Arg.class.getName());
        }

        @Contract("null, _ -> fail")
        public static <T> @NotNull T notNull(T obj, @NotNull String paramName) {
            return check(obj, notNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Null argument '" + paramName + '\''), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static <T> @NotNull T notNull(T obj, @NotNull String paramName, @NotNull String message) {
            return check(obj, notNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Null argument '" + paramName + "': " + message), Arg.class.getName())
            );
        }

        @Contract("null, _ -> fail")
        public static @NotNull String notEmpty(String str, @NotNull String paramName) {
            return check(str, notEmptyChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Empty argument string '" + paramName + '\''), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static @NotNull String notEmpty(String str, @NotNull String paramName, @NotNull String message) {
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
        public static <T> @NotNull T @NotNull [] nonNullArray(T[] arr, @NotNull String paramName, @NotNull String message) {
            notNull(arr, paramName, message);
            return check(arr, nonNullArrayChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Argument array '" + paramName + "' contains null value: " + message), Arg.class.getName())
            );
        }
    }

    public static final class Expr {

        @Contract("null, _ -> fail")
        public static <T> @NotNull T notNull(T obj, @NotNull String exprName) {
            return check(obj, notNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned null value"), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static <T> @NotNull T notNull(T obj, @NotNull String exprName, @NotNull String message) {
            return check(obj, notNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned null value: " + message), Arg.class.getName())
            );
        }

        @Contract("null, _ -> fail")
        public static @NotNull String notEmpty(String str, @NotNull String exprName) {
            return check(str, notEmptyChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned empty string value"), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static @NotNull String notEmpty(String str, @NotNull String exprName, @NotNull String message) {
            return check(str, notEmptyChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned empty string value: " + message), Arg.class.getName())
            );
        }

        @Contract("null, _ -> fail")
        public static <T> @NotNull T @NotNull [] nonNullArray(T[] arr, @NotNull String exprName) {
            return check(arr, nonNullArrayChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Expression '" + exprName + "' returned array contains null value"), Arg.class.getName())
            );
        }

        @Contract("null, _, _ -> fail")
        public static <T> @NotNull T @NotNull [] nonNullArray(T[] arr, @NotNull String exprName, @NotNull String message) {
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
