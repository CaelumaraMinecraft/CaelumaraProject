package net.aurika.ecliptor.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class SQLColumnChange extends SQLStatement {

  private final @NotNull String columnName;

  public SQLColumnChange(@NotNull String columnName) {
    Objects.requireNonNull(columnName, "columnName");
    this.columnName = columnName;
  }

  public final @NotNull String getColumnName() {
    return this.columnName;
  }

}
