package net.aurika.data.api.dataprovider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface SectionableDataGetter extends DataGetter, SectionableDataProvider {
    @NotNull SectionableDataGetter get(@NotNull String key);

    @NotNull SectionableDataGetter asSection();

    default int getInt(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return this.get(key).asInt();
    }

    default long getLong(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return this.get(key).asLong();
    }

    default float getFloat(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return this.get(key).asFloat();
    }

    default double getDouble(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return this.get(key).asDouble();
    }

    default boolean getBoolean(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return this.get(key).asBoolean();
    }

    default @Nullable String getString(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        return this.get(key).asString();
    }
}