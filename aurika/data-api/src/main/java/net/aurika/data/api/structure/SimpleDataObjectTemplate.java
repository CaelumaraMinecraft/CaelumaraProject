package net.aurika.data.api.structure;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class SimpleDataObjectTemplate<T> {
    protected final @NotNull Class<T> type;
    protected final @NotNull Map<String, DataMetaType> template;

    public static <T> SimpleDataObjectTemplate<T> of(@NotNull Class<T> type, @NotNull Function<SimpleData, T> entriesToObject, Object... map) {
        return new FunctionMapDataObjectTemplate<>(type, arrayToMap(map), entriesToObject);
    }

    protected static Map<String, DataMetaType> arrayToMap(@NotNull Object @NotNull [] keyTypeArray) {
        int length = keyTypeArray.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("keyTypeArray length must be even");
        }
        Map<String, DataMetaType> keyTypeMap = new LinkedHashMap<>();

        for (int i = 0; i < length; i += 2) {
            String key = ((CharSequence) keyTypeArray[i]).toString();
            DataMetaType type = (DataMetaType) keyTypeArray[i + 1];
            Objects.requireNonNull(key, "Key at index " + i + " is null");
            Objects.requireNonNull(type, "Type at index " + i + " is null");
            keyTypeMap.put(key, type);
        }
        return keyTypeMap;
    }

    protected SimpleDataObjectTemplate(@NotNull Class<T> type, @NotNull Map<String, DataMetaType> template) {
        Checker.Arg.notNull(type, "type");
        Checker.Arg.notNull(template, "template");
        this.type = type;
        this.template = Collections.unmodifiableMap(template);
    }

    public @NotNull Class<T> type() {
        return type;
    }

    public @Unmodifiable @NotNull Map<String, DataMetaType> templateMap() {
        return template;
    }

    public int size() {
        return template.size();
    }

    public @NotNull String @NotNull [] getKeys() {
        return template.keySet().toArray(new String[0]);
    }

    public abstract T toObject(@NotNull SimpleData data);

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SimpleDataObjectTemplate<?> that)) return false;
        return Objects.equals(type, that.type) && Objects.equals(template, that.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, template);
    }
}
