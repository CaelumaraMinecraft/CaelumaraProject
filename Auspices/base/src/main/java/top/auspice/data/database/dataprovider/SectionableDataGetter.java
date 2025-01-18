package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface SectionableDataGetter extends DataGetter, SectionableDataProvider {
    @NotNull
    SectionableDataGetter get(@NotNull String var1);

    @NotNull SectionableDataGetter asSection();

    default @Nullable String getString(@NotNull String key) {
        Objects.requireNonNull(key);
        return this.get(key).asString();
    }

    default int getInt(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return this.get(var1).asInt();
    }

    default float getFloat(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return this.get(var1).asFloat();
    }

    default long getLong(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return this.get(var1).asLong();
    }

    default double getDouble(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return this.get(var1).asDouble();
    }

    default boolean getBoolean(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return this.get(var1).asBoolean();
    }
}