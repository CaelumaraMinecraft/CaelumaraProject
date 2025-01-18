package top.auspice.data.database.sql.statements;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.DatabaseType;

public abstract class SQLStatement {
    public SQLStatement() {
    }

    @NotNull
    public final SQLAssociatedStatement associateTo(@NotNull DataManager<?> var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        return new SQLAssociatedStatement(var1, this);
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + "({" + DatabaseType.H2.createStatement(this, "%table%") + ')';
    }
}
