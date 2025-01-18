package top.auspice.data.database.sql.statements;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public class SQLUpsert extends SQLStatement {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    public SQLUpsert(@NotNull String var1, @NotNull String var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        this.a = var1;
        this.b = var2;
    }

    @NotNull
    public final String getParameters() {
        return this.a;
    }

    @NotNull
    public final String getPreparedValues() {
        return this.b;
    }
}
