package top.auspice.data.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class SQLColumnChange extends SQLStatement {
    @NotNull
    private final String a;

    public SQLColumnChange(@NotNull String var1) {
        Objects.requireNonNull(var1);
        this.a = var1;
    }

    @NotNull
    public final String getColumnName() {
        return this.a;
    }
}
