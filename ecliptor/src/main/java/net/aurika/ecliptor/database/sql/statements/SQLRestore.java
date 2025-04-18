package net.aurika.ecliptor.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Objects;

public class SQLRestore extends SQLStatement {

  private final @NotNull Path from;

  public SQLRestore(@NotNull Path from) {
    Objects.requireNonNull(from);
    this.from = from;
  }

  public @NotNull Path getFrom() {
    return this.from;
  }

}
