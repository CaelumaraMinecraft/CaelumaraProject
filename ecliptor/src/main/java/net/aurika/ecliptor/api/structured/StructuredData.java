package net.aurika.ecliptor.api.structured;

import net.aurika.ecliptor.api.structured.scalars.*;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.Objects;

public interface StructuredData {
//    @NotNull DataStructSchema<?> schema();

    @Unmodifiable
    @NotNull Map<String, DataScalar> data();

    default int size() {
        return data().size();
    }

    default int getInt(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        DataScalar value = data().get(key);
        if (value != null) return ((IntDataScalar) value).value();
        else throw new IllegalArgumentException("Data component not found: " + key);
    }

    default long getLong(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        DataScalar value = data().get(key);
        if (value != null) return ((LongDataScalar) value).value();
        else throw new IllegalArgumentException("Data component not found: " + key);
    }

    default float getFloat(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        DataScalar value = data().get(key);
        if (value != null) return ((FloatDataScalar) value).value();
        else throw new IllegalArgumentException("Data component not found: " + key);
    }

    default double getDouble(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        DataScalar value = data().get(key);
        if (value != null) return ((DoubleDataScalar) value).value();
        else throw new IllegalArgumentException("Data component not found: " + key);
    }

    default boolean getBoolean(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        DataScalar value = data().get(key);
        if (value != null) return ((BooleanDataScalar) value).value();
        else throw new IllegalArgumentException("Data component not found: " + key);
    }

    default @NotNull String getString(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        DataScalar value = data().get(key);
        if (value != null) return ((StringDataScalar) value).value();
        else throw new IllegalArgumentException("Data component not found: " + key);
    }

    static @NotNull StructuredData structuredData(/*@NotNull DataStructSchema<?> schema, */@NotNull Map<String, DataScalar> data) {
//        Validate.Arg.notNull(schema, "schema");
        Validate.Arg.notNull(data, "data");
        for (Map.Entry<String, DataScalar> entry : data.entrySet()) {
            Objects.requireNonNull(entry.getKey(), "entryKey");
            Objects.requireNonNull(entry.getValue(), "entryValue");
        }
        return new StructuredDataImpl(/*schema, */data);
    }
}

class StructuredDataImpl implements StructuredData {
    //    private final @NotNull DataStructSchema<?> schema;
    private final @Unmodifiable
    @NotNull Map<String, DataScalar> data;

    StructuredDataImpl(/*@NotNull DataStructSchema<?> schema,*/ @Unmodifiable @NotNull Map<String, DataScalar> data) {
//        this.schema = schema;
        this.data = data;
    }

//    @Override
//    public @NotNull DataStructSchema<?> schema() {
//        return schema;
//    }

    @Override
    public @Unmodifiable @NotNull Map<String, DataScalar> data() {
        return data;
    }
}
