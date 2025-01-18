package top.auspice.utils;

import org.jetbrains.annotations.Contract;
import top.auspice.utils.function.ToBooleanFunction;

import java.util.Arrays;
import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class Checker {

    //===== Null =====

    private static final ToBooleanFunction NOT_NULL_CHECKER = x -> x != null;

    public static <T> ToBooleanFunction<T> getNotNullChecker() {
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

    public static <T> ToBooleanFunction<T[]> getNonNullArrayChecker() {
        return NON_NULL_ARRAY_CHECKER;
    }

    //===== Empty String =====

    private static final ToBooleanFunction<String> NOT_EMPTY_STR_CHECKER = s -> s != null && !s.isEmpty();

    public static ToBooleanFunction<String> getNotEmptyStringChecker() {
        return NOT_EMPTY_STR_CHECKER;
    }

    public static <T> T check(T t, ToBooleanFunction<T> checker, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (checker.applyAsBoolean(t)) return t;   // 如果检查通过
        throw exceptionSupplier.get();
    }

    public static final class Argument {

        @Contract("null, _ -> fail")
        public static <T> T checkNotNull(T obj, String paramName) {
            return check(obj, getNotNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Null argument '" + paramName + '\''), Argument.class.getName())
            );
        }

        public static <T> T checkNotNull(T obj, String paramName, String message) {
            return check(obj, getNotNullChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Null argument '" + paramName + "': " + message), Argument.class.getName())
            );
        }

        public static String checkNotEmptyString(String str, String paramName) {
            return check(str, getNotEmptyStringChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Empty argument string '" + paramName + '\''), Argument.class.getName())
            );
        }

        public static String checkNotEmptyString(String str, String paramName, String message) {
            return check(str, getNotEmptyStringChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Empty argument string '" + paramName + "': " + message), Argument.class.getName())
            );
        }

        @Contract("null, _ -> fail")
        public static <T> T[] checkNotNullArray(T[] arr, String paramName) {
            return check(arr, getNonNullArrayChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Argument '" + paramName + "' array contains null value"), Argument.class.getName())
            );
        }

        @Contract("null, _ -> fail")
        public static <T> T[] checkNotNullArray(T[] arr, String paramName, String message) {
            checkNotNull(arr, paramName, message);
            return check(arr, getNonNullArrayChecker(), () ->
                    sanitizeStackTrace(new IllegalArgumentException("Argument '" + paramName + "' array contains null value: " + message), Argument.class.getName())
            );
        }
    }

    public static final class Expression {

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
