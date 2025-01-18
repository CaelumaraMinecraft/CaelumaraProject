package top.auspice.data.database.sql.statements;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SQLColumn {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    private final boolean c;

    public SQLColumn(@NotNull String var1, @NotNull String var2, boolean var3) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        this.a = var1;
        this.b = var2;
        this.c = var3;
    }

    @NotNull
    public final String getName() {
        return this.a;
    }

    @NotNull
    public final String getType() {
        return this.b;
    }

    public final boolean getNullable() {
        return this.c;
    }

    @NotNull
    public final String getNullability() {
        return this.c ? "NULL" : "NOT NULL";
    }
}
