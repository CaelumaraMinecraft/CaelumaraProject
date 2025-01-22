package top.auspice.data.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SQLColumnModify extends SQLColumnChange {

    private final @NotNull SQLColumn column;

    public SQLColumnModify(@NotNull SQLColumn column) {
        super(Objects.requireNonNull(column, "column").getName());
        this.column = column;
    }

    public @NotNull SQLColumn getColumn() {
        return this.column;
    }
}
