package top.auspice.data.database.sql.statements;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SQLColumnAdd extends SQLColumnChange {
    @NotNull
    private final SQLColumn a;
    @Nullable
    private final Object b;

    public SQLColumnAdd(@NotNull SQLColumn object, @Nullable Object object2) {
        super(object.getName());
        this.a = object;
        this.b = object2;
        if (!(this.a.getNullable() || this.b != null)) {
            throw new IllegalArgumentException("A NOT NULL column must define a default value: " + this);
        }
    }

    public SQLColumnAdd(SQLColumn sQLColumn, Object object, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            object = null;
        }
        this(sQLColumn, object);
    }

    @NotNull
    public final SQLColumn getColumn() {
        return this.a;
    }

    @Nullable
    public final Object getDefaultValue() {
        return this.b;
    }
}