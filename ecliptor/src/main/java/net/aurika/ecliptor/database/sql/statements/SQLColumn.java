package net.aurika.ecliptor.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SQLColumn {

    private final @NotNull String name;
    private final @NotNull String type;
    private final boolean nullable;

    public SQLColumn(@NotNull String name, @NotNull String type, boolean var3) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(type, "type");
        this.name = name;
        this.type = type;
        this.nullable = var3;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public @NotNull String getType() {
        return this.type;
    }

    public boolean getNullable() {
        return this.nullable;
    }

    public @NotNull String getNullability() {
        return this.nullable ? "NULL" : "NOT NULL";
    }
}
