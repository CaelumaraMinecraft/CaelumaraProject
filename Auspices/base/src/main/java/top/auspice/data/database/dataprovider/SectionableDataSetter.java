package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public interface SectionableDataSetter extends DataSetter, SectionableDataProvider {
    @NotNull
    SectionableDataSetter get(@NotNull String var1);

    default void setString(@NotNull String var1, @Nullable String var2) {
        Objects.requireNonNull(var1, "");
        this.get(var1).setString(var2);
    }

    default void setInt(@NotNull String var1, int var2) {
        Objects.requireNonNull(var1, "");
        this.get(var1).setInt(var2);
    }

    default void setLong(@NotNull String var1, long var2) {
        Objects.requireNonNull(var1, "");
        this.get(var1).setLong(var2);
    }

    default void setFloat(@NotNull String var1, float var2) {
        Objects.requireNonNull(var1, "");
        this.get(var1).setFloat(var2);
    }

    default void setDouble(@NotNull String var1, double var2) {
        Objects.requireNonNull(var1, "");
        this.get(var1).setDouble(var2);
    }

    default void setBoolean(@NotNull String var1, boolean var2) {
        Objects.requireNonNull(var1, "");
        this.get(var1).setBoolean(var2);
    }

    default void setUUID(@NotNull String var1, @Nullable UUID var2) {
        Objects.requireNonNull(var1, "");
        this.get(var1).setUUID(var2);
    }

    @NotNull
    SectionableDataSetter createSection(@NotNull String var1);
}
