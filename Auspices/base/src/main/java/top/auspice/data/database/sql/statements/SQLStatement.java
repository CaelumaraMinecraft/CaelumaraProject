package top.auspice.data.database.sql.statements;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.history.SQLAssociatedStatement;
import top.auspice.data.managers.base.DataManager;

import java.util.Objects;

public abstract class SQLStatement {
    public SQLStatement() {
    }

    public final @NotNull SQLAssociatedStatement associateTo(@NotNull DataManager<?> dataManager) {
        Objects.requireNonNull(dataManager, "dataManager");
        return new SQLAssociatedStatement(dataManager, this);
    }

    public @NotNull String toString() {
        return this.getClass().getSimpleName() + "({" + DatabaseType.H2.createStatement(this, "%table%") + ')';
    }
}
