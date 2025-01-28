package net.aurika.data.database.dataprovider;

import kotlin.collections.ArraysKt;
import net.aurika.data.database.sql.statements.getters.SimpleResultSetQuery;
import net.aurika.data.database.sql.statements.setters.SimplePreparedStatement;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class IdDataTypeHandler<T> {

    private final @NotNull String prefix;
    private final @NotNull Class<T> klass;
    private final @NotNull String @NotNull [] columns;
    private final @NotNull String columnTuple;
    private final @NotNull String whereClause;
    private final @NotNull String inClause;

    public IdDataTypeHandler(@NotNull String prefix, @NotNull Class<T> klass, @NotNull String[] columns) {
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(klass);
        Objects.requireNonNull(columns);
        this.prefix = prefix;
        this.klass = klass;
        String[] var10001;
        if (columns.length == 0) {
            var10001 = new String[]{this.prefix};
        } else {
            List<String> var9 = new ArrayList<>(columns.length);
            int var11 = 0;

            for (int var4 = columns.length; var11 < var4; ++var11) {
                String var5 = columns[var11];
                var9.add(this.prefix + '_' + var5);
            }

            var10001 = var9.toArray(new String[0]);
        }

        this.columns = var10001;
        this.columnTuple = ArraysKt.joinToString(this.columns, ", ", "", "", -1, "...", (str) -> "`" + str + '`');
        this.whereClause = ArraysKt.joinToString(this.columns, " AND ", "", "", -1, "...", (str) -> "`" + str + "`=?");
        this.inClause = ArraysKt.joinToString(this.columns, ", ", "", "", -1, "", (str) -> "?");
    }

    public final @NotNull String getPrefix$core() {
        return this.prefix;
    }

    public final @NotNull Class<T> getKlass() {
        return this.klass;
    }

    public final @NotNull String @NotNull [] getColumns() {
        return this.columns;
    }

    public final @NotNull String getColumnsTuple() {
        return this.columnTuple;
    }

    public final @NotNull String getWhereClause() {
        return this.whereClause;
    }

    public final @NotNull String getInClause() {
        return this.inClause;
    }

    public abstract void setSQL(@NotNull SimplePreparedStatement var1, T var2);

    public abstract T fromSQL(@NotNull SimpleResultSetQuery var1) throws SQLException;

    public abstract T fromString(@NotNull String var1);

    public abstract @NotNull String toString(T var1);
}
