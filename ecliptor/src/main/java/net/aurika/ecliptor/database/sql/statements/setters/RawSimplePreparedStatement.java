package net.aurika.ecliptor.database.sql.statements.setters;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import net.aurika.ecliptor.database.DatabaseType;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.util.UUID;

public  class RawSimplePreparedStatement implements SimplePreparedStatement {

    private final@NotNull DatabaseType databaseType;
    private final@NotNull PreparedStatement b;
    private int index;

    public RawSimplePreparedStatement(int var1, @NotNull DatabaseType var2, @NotNull PreparedStatement var3) {
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var3, "");
        this.databaseType = var2;
        this.b = var3;
        this.index = var1;
    }

    private int __next_index() {
        return this.index++;
    }

    public RawSimplePreparedStatement(@NotNull DatabaseType var1, @NotNull PreparedStatement var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        this(1, var1, var2);
    }

    public final void setString(@NotNull String key, @Nullable String value) {
        Intrinsics.checkNotNullParameter(key, "");
        this.b.setString(this.__next_index(), value);
    }

    public final void setInt(@NotNull String key, int value) {
        Intrinsics.checkNotNullParameter(key, "");
        this.b.setInt(this.__next_index(), value);
    }

    public final void setFloat(@NotNull String key, float value) {
        Intrinsics.checkNotNullParameter(key, "");
        this.b.setFloat(this.__next_index(), value);
    }

    public final void setLong(@NotNull String key, long value) {
        Intrinsics.checkNotNullParameter(key, "");
        this.b.setLong(this.__next_index(), value);
    }

    public final void setBoolean(@NotNull String key, boolean value) {
        Intrinsics.checkNotNullParameter(key, "");
        this.b.setBoolean(this.__next_index(), value);
    }

    public final void setDouble(@NotNull String key, double value) {
        Intrinsics.checkNotNullParameter(key, "");
        this.b.setDouble(this.__next_index(), value);
    }

    public final void setUUID(@NotNull String key, @Nullable UUID value) {
        Intrinsics.checkNotNullParameter(key, "");
        if (this.databaseType == DatabaseType.PostgreSQL) {
            this.b.setObject(this.__next_index(), value);
        } else {
            this.b.setBytes(this.__next_index(), SQLDatabase.Companion.asBytes(value));
        }
    }
}
