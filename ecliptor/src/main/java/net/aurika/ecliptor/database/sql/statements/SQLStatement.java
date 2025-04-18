package net.aurika.ecliptor.database.sql.statements;

import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.history.SQLAssociatedStatement;
import net.aurika.ecliptor.managers.base.DataManager;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class SQLStatement {

  public SQLStatement() {
  }

  public final @NotNull SQLAssociatedStatement associateTo(@NotNull DataManager<?> dataManager) {
    Validate.Arg.notNull(dataManager, "dataManager");
    return new SQLAssociatedStatement(dataManager, this);
  }

  public @NotNull String toString() {
    return this.getClass().getSimpleName() + "({" + DatabaseType.H2.createStatement(this, "%table%") + ')';
  }

}
