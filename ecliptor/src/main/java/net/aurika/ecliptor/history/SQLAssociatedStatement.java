package net.aurika.ecliptor.history;

import net.aurika.ecliptor.database.sql.statements.SQLStatement;
import net.aurika.ecliptor.managers.base.DataManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SQLAssociatedStatement {

  private final @NotNull DataManager<?> dataManager;
  private final @NotNull SQLStatement statement;

  public SQLAssociatedStatement(@NotNull DataManager<?> dataManager, @NotNull SQLStatement statement) {
    Objects.requireNonNull(dataManager, "dataManager");
    Objects.requireNonNull(statement, "statement");
    this.dataManager = dataManager;
    this.statement = statement;
  }

  public @NotNull DataManager<?> getDataManager() {
    return this.dataManager;
  }

  public @NotNull SQLStatement getStatement() {
    return this.statement;
  }

  public @NotNull String toString() {
    return "SQLAssociatedStatement(" + this.dataManager + " <- " + this.statement + ')';
  }

}
