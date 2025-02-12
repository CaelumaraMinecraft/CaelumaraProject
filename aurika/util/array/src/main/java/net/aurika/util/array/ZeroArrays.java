package net.aurika.util.array;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public final class ZeroArrays {
    public static final boolean[] P_BOOLEAN = new boolean[0];
    public static final char[] P_CHAR = new char[0];
    public static final byte[] P_BYTE = new byte[0];
    public static final short[] P_SHORT = new short[0];
    public static final int[] P_INT = new int[0];
    public static final long[] P_LONG = new long[0];
    public static final float[] P_FLOAT = new float[0];
    public static final double[] P_DOUBLE = new double[0];
    public static final Boolean[] BOOLEAN = new Boolean[0];
    public static final Character[] CHARACTER = new Character[0];
    public static final Byte[] BYTE = new Byte[0];
    public static final Short[] SHORT = new Short[0];
    public static final Integer[] INTEGER = new Integer[0];
    public static final Long[] LONG = new Long[0];
    public static final Float[] FLOAT = new Float[0];
    public static final Double[] DOUBLE = new Double[0];
    public static final String[] STRING = new String[0];

    private static final Map<Class<?>, Object[]> ARR_CONSTANTS = new HashMap<>();

    private ZeroArrays() {
    }

    public static <T> @NotNull T @NotNull [] getZeroArray(@NotNull Class<T> type) {
        Checker.Arg.notNull(type, "type");
        return (T[]) Array.newInstance(type, 0);
    }
}
