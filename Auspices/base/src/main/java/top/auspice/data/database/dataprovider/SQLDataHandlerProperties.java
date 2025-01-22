package top.auspice.data.database.dataprovider;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.IndexedValue;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class SQLDataHandlerProperties {
    private final @NotNull Map<String, Integer> a;

    public SQLDataHandlerProperties(@NotNull String @NotNull [] array) {
        Objects.requireNonNull(array, "");
        Iterable<IndexedValue<String>> withIndex = ArraysKt.withIndex(array);
        Map<String, Integer> a = new LinkedHashMap<>();
        for (IndexedValue<String> next : withIndex) {
            Pair<String, Integer> to = TuplesKt.to(next.getValue(), next.getIndex() + 1);
            a.put(to.getFirst(), to.getSecond());
        }
        this.a = a;
    }

    @NotNull
    public Map<String, Integer> getAssociateNamedData() {
        return this.a;
    }

    @NotNull
    public static String[] ofLocation(@NotNull String s) {
        Objects.requireNonNull(s, "");
        String[] ofSimpleLocation = ofSimpleLocation(s);
        String[] array;
        (array = new String[2])[0] = s + "_yaw";
        array[1] = s + "_pitch";
        return (String[]) ArraysKt.plus(ofSimpleLocation, (Object[]) array);
    }

    @NotNull
    public static String[] ofSimpleLocation(@NotNull String s) {
        Objects.requireNonNull(s, "");
        String[] array = new String[4];
        array[0] = s + "_world";
        array[1] = s + "_x";
        array[2] = s + "_y";
        array[3] = s + "_z";
        return array;
    }
}