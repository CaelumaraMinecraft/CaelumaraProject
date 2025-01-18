package top.auspice.data.database.dataprovider;

import kotlin.collections.ArraysKt;
import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.sql.statements.getters.SimpleResultSetQuery;
import top.auspice.data.database.sql.statements.setters.SimplePreparedStatement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class IdDataTypeHandler<T> {
    @NotNull
    private final String prefix;
    @NotNull
    private final Class<T> klass;
    @NotNull
    private final String[] columns;
    @NotNull
    private final String columnTuple;
    @NotNull
    private final String whereClause;
    @NotNull
    private final String inClause;

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

    @NotNull
    public final String getPrefix$core() {
        return this.prefix;
    }

    @NotNull
    public final Class<T> getKlass() {
        return this.klass;
    }

    @NotNull
    public final String[] getColumns() {
        return this.columns;
    }

    @NotNull
    public final String getColumnsTuple() {
        return this.columnTuple;
    }

    @NotNull
    public final String getWhereClause() {
        return this.whereClause;
    }

    @NotNull
    public final String getInClause() {
        return this.inClause;
    }

    public abstract void setSQL(@NotNull SimplePreparedStatement var1, T var2);

    public abstract T fromSQL(@NotNull SimpleResultSetQuery var1) throws SQLException;

    public abstract T fromString(@NotNull String var1);

    @NotNull
    public abstract String toString(T var1);
}
