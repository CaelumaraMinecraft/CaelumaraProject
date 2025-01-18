package top.auspice.data.database.sql.statements.setters;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.DatabaseType;

import java.sql.PreparedStatement;

public  class RawSimplePreparedStatement implements SimplePreparedStatement {
    @NotNull
    private final DatabaseType a;
    @NotNull
    private final PreparedStatement b;
    private int c;

    public RawSimplePreparedStatement(int var1, @NotNull DatabaseType var2, @NotNull PreparedStatement var3) {
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var3, "");
        this.a = var2;
        this.b = var3;
        this.c = var1;
    }

    private final int a() {
        return this.c++;
    }

    public RawSimplePreparedStatement(@NotNull DatabaseType var1, @NotNull PreparedStatement var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        this(1, var1, var2);
    }

    public final void setString(@NotNull String var1, @Nullable String var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.b.setString(this.a(), var2);
    }

    public final void setInt(@NotNull String var1, int var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.b.setInt(this.a(), var2);
    }

    public final void setFloat(@NotNull String var1, float var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.b.setFloat(this.a(), var2);
    }

    public final void setLong(@NotNull String var1, long var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.b.setLong(this.a(), var2);
    }

    public final void setBoolean(@NotNull String var1, boolean var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.b.setBoolean(this.a(), var2);
    }

    public final void setDouble(@NotNull String var1, double var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.b.setDouble(this.a(), var2);
    }

    public final void setUUID(@NotNull String var1, @Nullable UUID var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        if (this.a == DatabaseType.PostgreSQL) {
            this.b.setObject(this.a(), var2);
        } else {
            this.b.setBytes(this.a(), SQLDatabase.Companion.asBytes(var2));
        }
    }
}
