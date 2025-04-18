package net.aurika.ecliptor.database.sql;

import org.jetbrains.annotations.NotNull;

public interface SQLStatementSetter {

  void setString(@NotNull String var1, @NotNull String var2);

  void setInt(@NotNull String var1, int var2);

  void setFloat(@NotNull String var1, float var2);

  void setLong(@NotNull String var1, long var2);

  void setBoolean(@NotNull String var1, boolean var2);

  void setDouble(@NotNull String var1, double var2);

  void setBytes(@NotNull String var1, byte @NotNull [] var2);

}
