package net.aurika.data.database.sql.statements;

import org.jetbrains.annotations.NotNull;

public class SQLColumnRemove extends SQLColumnChange {
    public SQLColumnRemove(@NotNull String columnName) {
        super(columnName);
    }
}
