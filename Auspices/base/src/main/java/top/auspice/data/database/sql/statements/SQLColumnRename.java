package top.auspice.data.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SQLColumnRename extends SQLColumnChange {
    @NotNull
    private final String a;

    public SQLColumnRename(@NotNull String var1, @NotNull String var2) {
        super(var1);
        Objects.requireNonNull(var2);
        this.a = var2;
    }

    @NotNull
    public final String getNewColumnName() {
        return this.a;
    }
}
