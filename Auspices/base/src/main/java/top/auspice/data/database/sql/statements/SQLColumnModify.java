package top.auspice.data.database.sql.statements;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public class SQLColumnModify extends SQLColumnChange {
    @NotNull
    private final SQLColumn a;

    public SQLColumnModify(@NotNull SQLColumn var1) {
        super(var1.getName());
        Intrinsics.checkNotNullParameter(var1, "");
        this.a = var1;
    }

    @NotNull
    public final SQLColumn getColumn() {
        return this.a;
    }
}
