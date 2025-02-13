package net.aurika.ecliptor.database.dataprovider;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public interface SectionableDataSetter extends DataSetter, SectionableDataProvider {
    @NotNull SectionableDataSetter get(@NotNull String key);

    default void setInt(@NotNull String key, int value) {
        Objects.requireNonNull(key, "key");
        this.get(key).setInt(value);
    }

    default void setLong(@NotNull String key, long value) {
        Objects.requireNonNull(key, "key");
        this.get(key).setLong(value);
    }

    default void setFloat(@NotNull String key, float value) {
        Objects.requireNonNull(key, "key");
        this.get(key).setFloat(value);
    }

    default void setDouble(@NotNull String key, double value) {
        Objects.requireNonNull(key, "key");
        this.get(key).setDouble(value);
    }

    default void setBoolean(@NotNull String key, boolean value) {
        Objects.requireNonNull(key, "key");
        this.get(key).setBoolean(value);
    }

    default void setString(@NotNull String key, @NotNull String value) {
        Objects.requireNonNull(key, "key");
        this.get(key).setString(value);
    }

    default void setUUID(@NotNull String key, @NotNull UUID value) {
        Objects.requireNonNull(key, "key");
        this.get(key).setUUID(value);
    }

    @NotNull SectionableDataSetter createSection(@NotNull String key);
}
