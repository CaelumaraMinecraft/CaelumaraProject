package net.aurika.ecliptor.database.sql.statements.setters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface SimplePreparedStatement {
    void setString(@NotNull String key, @Nullable String var2);

    void setInt(@NotNull String key, int var2);

    void setFloat(@NotNull String key, float var2);

    void setLong(@NotNull String var1, long var2);

    void setBoolean(@NotNull String var1, boolean var2);

    void setDouble(@NotNull String var1, double var2);

    void setUUID(@NotNull String var1, @Nullable UUID var2);
}
