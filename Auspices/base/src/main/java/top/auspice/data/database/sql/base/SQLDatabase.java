package top.auspice.data.database.sql.base;

import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.object.DataObject;
import top.auspice.data.object.KeyedDataObject;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.base.Database;
import top.auspice.data.database.dataprovider.IdDataTypeHandler;
import top.auspice.data.database.sql.SQLDataSetterProvider;
import top.auspice.data.database.sql.connection.SQLConnectionProvider;
import top.auspice.data.database.sql.statements.SQLStatement;
import top.auspice.data.database.sql.statements.setters.PreparedNamedSetterStatement;
import top.auspice.data.database.sql.statements.setters.SimplePreparedStatement;
import top.auspice.data.handlers.abstraction.DataHandler;
import top.auspice.data.handlers.abstraction.KeyedDataHandler;
import top.auspice.utils.unsafe.AutoCloseableUtils;
import top.auspice.utils.unsafe.Fn;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

public abstract class SQLDatabase<T extends DataObject.Impl> implements Database<T> {

    private final @NotNull DatabaseType databaseType;
    private final @NotNull String table;
    private final @NotNull SQLConnectionProvider connectionProvider;

    public SQLDatabase(@NotNull DatabaseType databaseType, @NotNull String table, @NotNull SQLConnectionProvider connectionProvider) {
        Objects.requireNonNull(databaseType, "databaseType");
        Objects.requireNonNull(table, "table");
        Objects.requireNonNull(connectionProvider, "connectionProvider");
        this.databaseType = databaseType;
        this.table = table;
        this.connectionProvider = connectionProvider;
    }

    protected final @NotNull String getTable() {
        return this.table;
    }

    protected final @NotNull SQLConnectionProvider getConnectionProvider() {
        return this.connectionProvider;
    }

    protected final @NotNull String handleQuery(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        char var2;
        return (var2 = this.databaseType.getSystemIdentifierEscapeChar()) != '`' ? StringsKt.replace(var1, '`', var2, false) : var1;
    }

    public @NotNull DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    protected final Connection getConnection() {
        return this.connectionProvider.getConnection();
    }

    protected abstract DataHandler<T> getDataHandler();

    public void save(@NotNull T data) {
        Objects.requireNonNull(data, "obj");
        DataHandler<T> dataHandler = this.getDataHandler();
        PreparedNamedSetterStatement var3 = new PreparedNamedSetterStatement(this.databaseType, dataHandler.getSqlProperties().getAssociateNamedData());

        try {
            Connection connection = this.getConnection();
            Throwable var5 = null;
            boolean var11 = false;

            try {
                var11 = true;
                SQLDataSetterProvider var7 = new SQLDataSetterProvider(this.databaseType, this.table, null, false, false, var3);
                if (dataHandler instanceof KeyedDataHandler) {
                    IdDataTypeHandler var10000 = ((KeyedDataHandler) dataHandler).getIdHandler();
                    SimplePreparedStatement var10001 = var3;
                    Object var10002 = Fn.cast(((KeyedDataObject.Impl<?>) data).getKey());
                    Intrinsics.checkNotNullExpressionValue(var10002, "");
                    var10000.setSQL(var10001, var10002);
                } else {
                    var7.setBoolean("id", true);
                }

                dataHandler.save(var7, data);
                var3.buildStatement(this.table, connection);
                var3.execute();
                var11 = false;
            } catch (Throwable var12) {
                var5 = var12;
                throw var12;
            } finally {
                if (var11) {
                    AutoCloseableUtils.closeFinally(connection, var5);
                }
            }

            AutoCloseableUtils.closeFinally(connection, null);
        } catch (Throwable var14) {
            throw new RuntimeException("Error while saving data " + data + " (" + data.getClass() + ')', var14);
        }
    }

    public void deleteAllData() {
        this.executeStatement("DROP TABLE `" + this.table + '`');
    }

    protected final <A> A prepareStatement(@NotNull String var1, @NotNull Function<? super PreparedStatement, ? extends A> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        var1 = this.handleQuery(var1);

        try {
            Connection connection = this.getConnection();
            Throwable var4 = null;
            boolean var12 = false;

            A var24;
            try {
                var12 = true;
                PreparedStatement var25 = connection.prepareStatement(var1);
                Throwable var6 = null;
                boolean var18 = false;

                try {
                    var18 = true;
                    var24 = var2.apply(var25);
                    var18 = false;
                } catch (Throwable var19) {
                    var6 = var19;
                    throw var19;
                } finally {
                    if (var18) {
                        InlineMarker.finallyStart(1);
                        AutoCloseableUtils.closeFinally(var25, var6);
                        InlineMarker.finallyEnd(1);
                    }
                }

                InlineMarker.finallyStart(1);
                AutoCloseableUtils.closeFinally(var25, null);
                InlineMarker.finallyEnd(1);
                var24 = var24;
                var12 = false;
            } catch (Throwable var21) {
                var4 = var21;
                throw var21;
            } finally {
                if (var12) {
                    InlineMarker.finallyStart(1);
                    AutoCloseableUtils.closeFinally(connection, var4);
                    InlineMarker.finallyEnd(1);
                }
            }

            InlineMarker.finallyStart(2);
            AutoCloseableUtils.closeFinally(connection, null);
            InlineMarker.finallyEnd(2);
            return var24;
        } catch (Throwable var23) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + this.getConnectionProvider().getMetaString(), var23);
        }
    }

    protected final <A> A executeStatement(@NotNull String var1, @NotNull Function<? super ResultSet, ? extends A> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        var1 = this.handleQuery(var1);

        try {
            Connection connection = this.getConnection();
            Throwable var4 = null;
            boolean var16 = false;

            A var40;
            try {
                var16 = true;
                Statement var41 = connection.createStatement();
                Throwable var6 = null;
                boolean var24 = false;

                try {
                    var24 = true;
                    ResultSet var42 = var41.executeQuery(var1);
                    Throwable var8 = null;
                    boolean var32 = false;

                    try {
                        var32 = true;
                        var40 = var2.apply(var42);
                        var32 = false;
                    } catch (Throwable var33) {
                        var8 = var33;
                        throw var33;
                    } finally {
                        if (var32) {
                            InlineMarker.finallyStart(1);
                            AutoCloseableUtils.closeFinally(var42, var8);
                            InlineMarker.finallyEnd(1);
                        }
                    }

                    InlineMarker.finallyStart(1);
                    AutoCloseableUtils.closeFinally(var42, null);
                    InlineMarker.finallyEnd(1);
                    var24 = false;
                } catch (Throwable var35) {
                    var6 = var35;
                    throw var35;
                } finally {
                    if (var24) {
                        InlineMarker.finallyStart(1);
                        AutoCloseableUtils.closeFinally(var41, var6);
                        InlineMarker.finallyEnd(1);
                    }
                }

                InlineMarker.finallyStart(2);
                AutoCloseableUtils.closeFinally(var41, null);
                InlineMarker.finallyEnd(2);
                var16 = false;
            } catch (Throwable var37) {
                var4 = var37;
                throw var37;
            } finally {
                if (var16) {
                    InlineMarker.finallyStart(1);
                    AutoCloseableUtils.closeFinally(connection, var4);
                    InlineMarker.finallyEnd(1);
                }
            }

            InlineMarker.finallyStart(2);
            AutoCloseableUtils.closeFinally(connection, null);
            InlineMarker.finallyEnd(2);
            return var40;
        } catch (Throwable var39) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + this.getConnectionProvider().getMetaString(), var39);
        }
    }

    public final void executeStatement(@NotNull SQLStatement var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.executeStatement(this.databaseType.createStatement(var1, this.table));
    }

    public final void executeStatement(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        var1 = this.handleQuery(var1);

        try {
            Connection connection = this.getConnection();
            Throwable var3 = null;
            boolean var12 = false;

            try {
                var12 = true;
                Statement var24 = connection.createStatement();
                Throwable var5 = null;
                boolean var18 = false;

                try {
                    var18 = true;
                    var24.execute(var1);
                    var18 = false;
                } catch (Throwable var19) {
                    var5 = var19;
                    throw var19;
                } finally {
                    if (var18) {
                        AutoCloseableUtils.closeFinally(var24, var5);
                    }
                }

                AutoCloseableUtils.closeFinally(var24, null);
                var12 = false;
            } catch (Throwable var21) {
                var3 = var21;
                throw var21;
            } finally {
                if (var12) {
                    AutoCloseableUtils.closeFinally(connection, var3);
                }
            }

            AutoCloseableUtils.closeFinally(connection, null);
        } catch (Throwable var23) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + this.connectionProvider.getMetaString(), var23);
        }
    }

    public void close() {
        this.connectionProvider.close();
    }

    public static byte @Nullable [] asBytes(@Nullable UUID uuid) {
        if (uuid == null) {
            return null;
        } else {
            ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
            buf.putLong(uuid.getMostSignificantBits());
            buf.putLong(uuid.getLeastSignificantBits());
            return buf.array();
        }
    }

    public static @Nullable UUID asUUID(byte @Nullable [] bytes) {
        if (bytes == null) {
            return null;
        } else {
            ByteBuffer buf = ByteBuffer.wrap(bytes);
            long var3 = buf.getLong();
            long var5 = buf.getLong();
            return new UUID(var3, var5);
        }
    }
}
