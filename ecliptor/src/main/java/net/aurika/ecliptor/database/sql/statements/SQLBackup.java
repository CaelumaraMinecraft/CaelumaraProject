package net.aurika.ecliptor.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Objects;

public class SQLBackup extends SQLStatement {

  private final @NotNull Path to;
  private final @NotNull String named;

  public SQLBackup(@NotNull Path to, @NotNull String named) {
    Objects.requireNonNull(to, "to");
    Objects.requireNonNull(named, "named");
    this.to = to;
    this.named = named;
  }

  public @NotNull Path getTo() {
    return this.to;
  }

  public @NotNull String getNamed() {
    return this.named;
  }

}
