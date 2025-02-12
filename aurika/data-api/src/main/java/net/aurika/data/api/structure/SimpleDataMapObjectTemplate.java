package net.aurika.data.api.structure;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class SimpleDataMapObjectTemplate<T> {  // MappingDataObject

    protected final @NotNull Class<T> type;
    protected final @NotNull Map<String, DataUnitType> template;
    protected final @NotNull Function<DataUnits, T> toObject;

    public static <T> SimpleDataMapObjectTemplate<T> of(@NotNull Class<T> type, @NotNull Function<DataUnits, T> entriesToObject, Object... map) {
        return new SimpleDataMapObjectTemplate<>(type, arrayToMap(map), entriesToObject);
    }

    protected static Map<String, DataUnitType> arrayToMap(@NotNull Object @NotNull [] keyTypeArray) {
        int length = keyTypeArray.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("keyTypeArray length must be even");
        }
        Map<String, DataUnitType> keyTypeMap = new LinkedHashMap<>();

        for (int i = 0; i < length; i += 2) {
            String key = ((CharSequence) keyTypeArray[i]).toString();
            DataUnitType type = (DataUnitType) keyTypeArray[i + 1];
            Objects.requireNonNull(key, "Key at index " + i + " is null");
            Objects.requireNonNull(type, "Type at index " + i + " is null");
            keyTypeMap.put(key, type);
        }
        return keyTypeMap;
    }

    public SimpleDataMapObjectTemplate(@NotNull Class<T> type, @NotNull Map<String, DataUnitType> template, @NotNull Function<DataUnits, T> toObject) {
        Checker.Arg.notNull(type, "type");
        Checker.Arg.notNull(template, "template");
        Checker.Arg.notNull(toObject, "toObject");
        this.type = type;
        this.template = Collections.unmodifiableMap(template);
        this.toObject = toObject;
    }

    public @NotNull Class<T> type() {
        return type;
    }

    public @Unmodifiable @NotNull Map<String, DataUnitType> templateMap() {
        return template;
    }

    public int size() {
        return template.size();
    }

    public @NotNull String @NotNull [] getKeys() {
        return template.keySet().toArray(new String[0]);
    }

    public T toObject(@NotNull DataUnits data) {
        return toObject.apply(data);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SimpleDataMapObjectTemplate<?> that = (SimpleDataMapObjectTemplate<?>) o;
        return Objects.equals(type, that.type) && Objects.equals(template, that.template) && Objects.equals(toObject, that.toObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, template, toObject);
    }
}
