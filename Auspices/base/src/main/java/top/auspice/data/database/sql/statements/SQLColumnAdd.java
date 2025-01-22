package top.auspice.data.database.sql.statements;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SQLColumnAdd extends SQLColumnChange {

    private final @NotNull SQLColumn column;
    private final @Nullable Object defaultValue;

    public SQLColumnAdd(@NotNull SQLColumn column, @Nullable Object object2) {
        super(column.getName());
        this.column = column;
        this.defaultValue = object2;
        if (!(this.column.getNullable() || this.defaultValue != null)) {
            throw new IllegalArgumentException("A NOT NULL column must define a default value: " + this);
        }
    }

    public @NotNull SQLColumn getColumn() {
        return this.column;
    }

    public @Nullable Object getDefaultValue() {
        return this.defaultValue;
    }
}