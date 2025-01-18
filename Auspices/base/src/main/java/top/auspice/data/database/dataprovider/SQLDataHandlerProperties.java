package top.auspice.data.database.dataprovider;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class SQLDataHandlerProperties {
    @NotNull
    private final Map<String, Integer> a;

    public SQLDataHandlerProperties(@NotNull final String[] array) {
        Objects.requireNonNull(array, "");
        final Iterable<IndexedValue<String>> withIndex = ArraysKt.withIndex(array);
        final Map<String, Integer> a = new LinkedHashMap<>();
        for (final IndexedValue<String> next : withIndex) {
            final Pair<String, Integer> to = TuplesKt.to(next.getValue(), next.getIndex() + 1);
            a.put(to.getFirst(), to.getSecond());
        }
        this.a = a;
    }

    @NotNull
    public final Map<String, Integer> getAssociateNamedData() {
        return this.a;
    }

    @NotNull
    public static String[] ofLocation(@NotNull final String s) {
        Objects.requireNonNull(s, "");
        final String[] ofSimpleLocation = ofSimpleLocation(s);
        final String[] array;
        (array = new String[2])[0] = s + "_yaw";
        array[1] = s + "_pitch";
        return (String[]) ArraysKt.plus(ofSimpleLocation, (Object[]) array);
    }

    @NotNull
    public static String[] ofSimpleLocation(@NotNull final String s) {
        Objects.requireNonNull(s, "");
        final String[] array;
        (array = new String[4])[0] = s + "_world";
        array[1] = s + "_x";
        array[2] = s + "_y";
        array[3] = s + "_z";
        return array;
    }

}