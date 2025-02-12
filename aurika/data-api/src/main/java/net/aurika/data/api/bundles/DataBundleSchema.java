package net.aurika.data.api.bundles;

import net.aurika.checker.Checker;
import net.aurika.data.api.bundles.scalars.DataScalarType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class DataBundleSchema<T> {  // MappingDataObject

    protected final @NotNull Class<T> type;
    protected final @NotNull Map<String, DataScalarType> template;
    protected final @NotNull Function<BundledData, T> toObject;

    public static <T> DataBundleSchema<T> of(@NotNull Class<T> type, @NotNull Function<BundledData, T> entriesToObject, Object... map) {
        return new DataBundleSchema<>(type, arrayToMap(map), entriesToObject);
    }

    protected static Map<String, DataScalarType> arrayToMap(@NotNull Object @NotNull [] keyTypeArray) {
        int length = keyTypeArray.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("keyTypeArray length must be even");
        }
        Map<String, DataScalarType> keyTypeMap = new LinkedHashMap<>();

        for (int i = 0; i < length; i += 2) {
            String key = ((CharSequence) keyTypeArray[i]).toString();
            DataScalarType type = (DataScalarType) keyTypeArray[i + 1];
            Objects.requireNonNull(key, "Key at index " + i + " is null");
            Objects.requireNonNull(type, "Type at index " + i + " is null");
            keyTypeMap.put(key, type);
        }
        return keyTypeMap;
    }

    public DataBundleSchema(@NotNull Class<T> type, @NotNull Map<String, DataScalarType> template, @NotNull Function<BundledData, T> toObject) {
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

    public @Unmodifiable @NotNull Map<String, DataScalarType> templateMap() {
        return template;
    }

    public int size() {
        return template.size();
    }

    public @NotNull String @NotNull [] getKeys() {
        return template.keySet().toArray(new String[0]);
    }

    public T toObject(@NotNull BundledData data) {
        return toObject.apply(data);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DataBundleSchema<?> that = (DataBundleSchema<?>) o;
        return Objects.equals(type, that.type) && Objects.equals(template, that.template) && Objects.equals(toObject, that.toObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, template, toObject);
    }
}
