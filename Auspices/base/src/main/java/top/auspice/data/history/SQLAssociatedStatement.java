package top.auspice.data.history;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.sql.statements.SQLStatement;
import top.auspice.data.managers.base.DataManager;

import java.util.Objects;

public class SQLAssociatedStatement {
    @NotNull
    private final DataManager<?> dataManager;
    @NotNull
    private final SQLStatement statement;

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
