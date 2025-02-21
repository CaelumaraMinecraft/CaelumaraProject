package net.aurika.ecliptor.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SQLColumnRename extends SQLColumnChange {

    private final @NotNull String newColumnName;

    public SQLColumnRename(@NotNull String oldColumnName, @NotNull String newColumnName) {
        super(oldColumnName);
        Objects.requireNonNull(newColumnName, "newColumnName");
        this.newColumnName = newColumnName;
    }

    public @NotNull String getNewColumnName() {
        return this.newColumnName;
    }
}
