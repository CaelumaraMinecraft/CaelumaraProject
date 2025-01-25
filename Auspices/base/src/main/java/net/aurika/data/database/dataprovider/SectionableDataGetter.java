package net.aurika.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface SectionableDataGetter extends DataGetter, SectionableDataProvider {
    @NotNull SectionableDataGetter get(@NotNull String key);

    @NotNull SectionableDataGetter asSection();

    default @Nullable String getString(@NotNull String key) {
        Objects.requireNonNull(key);
        return this.get(key).asString();
    }

    default int getInt(@NotNull String key) {
        Objects.requireNonNull(key);
        return this.get(key).asInt();
    }

    default float getFloat(@NotNull String key) {
        Objects.requireNonNull(key);
        return this.get(key).asFloat();
    }

    default long getLong(@NotNull String key) {
        Objects.requireNonNull(key);
        return this.get(key).asLong();
    }

    default double getDouble(@NotNull String key) {
        Objects.requireNonNull(key);
        return this.get(key).asDouble();
    }

    default boolean getBoolean(@NotNull String key) {
        Objects.requireNonNull(key);
        return this.get(key).asBoolean();
    }
}