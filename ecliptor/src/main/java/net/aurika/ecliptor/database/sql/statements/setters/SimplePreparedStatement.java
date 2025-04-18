package net.aurika.ecliptor.database.sql.statements.setters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface SimplePreparedStatement {

  void setString(@NotNull String key, @Nullable String value);

  void setInt(@NotNull String key, int value);

  void setFloat(@NotNull String key, float value);

  void setLong(@NotNull String key, long value);

  void setBoolean(@NotNull String key, boolean value);

  void setDouble(@NotNull String key, double value);

  void setUUID(@NotNull String key, @Nullable UUID value);

}
