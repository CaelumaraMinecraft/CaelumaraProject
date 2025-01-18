package top.auspice.data.database.sql.base;

import kotlin.Unit;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import top.auspice.constants.base.AuspiceObject;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.base.KingdomsDatabase;
import top.auspice.data.database.dataprovider.IdDataTypeHandler;
import top.auspice.data.database.dataprovider.SectionableDataSetter;
import top.auspice.data.database.sql.SQLDataSetterProvider;
import top.auspice.data.database.sql.connection.SQLConnectionProvider;
import top.auspice.data.database.sql.statements.SQLStatement;
import top.auspice.data.database.sql.statements.setters.PreparedNamedSetterStatement;
import top.auspice.data.database.sql.statements.setters.SimplePreparedStatement;
import top.auspice.data.handlers.abstraction.DataHandler;
import top.auspice.data.handlers.abstraction.KeyedDataHandler;
import top.auspice.utils.internal.Fn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public abstract class SQLDatabase<T extends AuspiceObject> implements KingdomsDatabase<T> {
    @NotNull
    public static final Companion Companion = new Companion();
    @NotNull
    private final DatabaseType a;
    @NotNull
    private final String b;
    @NotNull
    private final SQLConnectionProvider c;

    public SQLDatabase(@NotNull DatabaseType var1, @NotNull String var2, @NotNull SQLConnectionProvider var3) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var3, "");
        this.a = var1;
        this.b = var2;
        this.c = var3;
    }

    @NotNull
    protected final String getTable() {
        return this.b;
    }

    @NotNull
    protected final SQLConnectionProvider getConnectionProvider() {
        return this.c;
    }

    @NotNull
    protected final String handleQuery(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        char var2;
        return (var2 = this.a.getSystemIdentifierEscapeChar()) != '`' ? StringsKt.replace(var1, '`', var2, false) : var1;
    }

    @NotNull
    public DatabaseType getDatabaseType() {
        return this.a;
    }

    @NotNull
    protected final Connection getConnection() {
        return this.c.getConnection();
    }

    @NotNull
    protected abstract DataHandler<T> getDataHandler();

    public void save(@NotNull T var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        DataHandler var2 = this.getDataHandler();
        PreparedNamedSetterStatement var3 = new PreparedNamedSetterStatement(this.a, var2.getSqlProperties().getAssociateNamedData());

        try {
            AutoCloseable var4 = (AutoCloseable)this.getConnection();
            Throwable var5 = null;
            boolean var11 = false;

            try {
                var11 = true;
                Connection var6 = (Connection)var4;
                SQLDataSetterProvider var7 = new SQLDataSetterProvider(this.a, this.b, (String)null, false, false, var3);
                if (var2 instanceof KeyedDataHandler) {
                    IdDataTypeHandler var10000 = ((KeyedDataHandler)var2).getIdHandler();
                    SimplePreparedStatement var10001 = (SimplePreparedStatement)var3;
                    Object var10002 = Fn.cast(((KeyedAuspiceObject<?>) var1).getKey());
                    Intrinsics.checkNotNullExpressionValue(var10002, "");
                    var10000.setSQL(var10001, var10002);
                } else {
                    var7.setBoolean("id", true);
                }

                var2.save((SectionableDataSetter)var7, var1);
                var3.buildStatement(this.b, var6);
                var3.execute();
                var11 = false;
            } catch (Throwable var12) {
                var5 = var12;
                throw var12;
            } finally {
                if (var11) {
                    AutoCloseableKt.closeFinally(var4, var5);
                }
            }

            AutoCloseableKt.closeFinally(var4, (Throwable)null);
        } catch (Throwable var14) {
            throw new RuntimeException("Error while saving data " + var1 + " (" + var1.getClass() + ')', var14);
        }
    }

    public void deleteAllData() {
        this.executeStatement("DROP TABLE `" + this.b + '`');
    }

    protected final <A> A prepareStatement(@NotNull String var1, @NotNull Function1<? super PreparedStatement, ? extends A> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        var1 = access$handleQuery(this, var1);

        try {
            AutoCloseable var3 = (AutoCloseable)access$getConnection(this);
            Throwable var4 = null;
            boolean var12 = false;

            Object var24;
            try {
                var12 = true;
                Connection var5;
                AutoCloseable var25 = (AutoCloseable)(var5 = (Connection)var3).prepareStatement(var1);
                Throwable var6 = null;
                boolean var18 = false;

                try {
                    var18 = true;
                    var24 = var2.invoke(var25);
                    var18 = false;
                } catch (Throwable var19) {
                    var6 = var19;
                    throw var19;
                } finally {
                    if (var18) {
                        InlineMarker.finallyStart(1);
                        AutoCloseableKt.closeFinally(var25, var6);
                        InlineMarker.finallyEnd(1);
                    }
                }

                InlineMarker.finallyStart(1);
                AutoCloseableKt.closeFinally(var25, (Throwable)null);
                InlineMarker.finallyEnd(1);
                var24 = var24;
                var12 = false;
            } catch (Throwable var21) {
                var4 = var21;
                throw var21;
            } finally {
                if (var12) {
                    InlineMarker.finallyStart(1);
                    AutoCloseableKt.closeFinally(var3, var4);
                    InlineMarker.finallyEnd(1);
                }
            }

            InlineMarker.finallyStart(2);
            AutoCloseableKt.closeFinally(var3, (Throwable)null);
            InlineMarker.finallyEnd(2);
            return var24;
        } catch (Throwable var23) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + access$getConnectionProvider(this).getMetaString(), var23);
        }
    }

    protected final <A> A executeStatement(@NotNull String var1, @NotNull Function1<? super ResultSet, ? extends A> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        var1 = access$handleQuery(this, var1);

        try {
            AutoCloseable var3 = (AutoCloseable)access$getConnection(this);
            Throwable var4 = null;
            boolean var16 = false;

            Object var40;
            try {
                var16 = true;
                Connection var5;
                AutoCloseable var41 = (AutoCloseable)(var5 = (Connection)var3).createStatement();
                Throwable var6 = null;
                boolean var24 = false;

                try {
                    var24 = true;
                    Statement var7;
                    AutoCloseable var42 = (AutoCloseable)(var7 = (Statement)var41).executeQuery(var1);
                    Throwable var8 = null;
                    boolean var32 = false;

                    try {
                        var32 = true;
                        var40 = var2.invoke(var42);
                        var32 = false;
                    } catch (Throwable var33) {
                        var8 = var33;
                        throw var33;
                    } finally {
                        if (var32) {
                            InlineMarker.finallyStart(1);
                            AutoCloseableKt.closeFinally(var42, var8);
                            InlineMarker.finallyEnd(1);
                        }
                    }

                    InlineMarker.finallyStart(1);
                    AutoCloseableKt.closeFinally(var42, (Throwable)null);
                    InlineMarker.finallyEnd(1);
                    var40 = var40;
                    var24 = false;
                } catch (Throwable var35) {
                    var6 = var35;
                    throw var35;
                } finally {
                    if (var24) {
                        InlineMarker.finallyStart(1);
                        AutoCloseableKt.closeFinally(var41, var6);
                        InlineMarker.finallyEnd(1);
                    }
                }

                InlineMarker.finallyStart(2);
                AutoCloseableKt.closeFinally(var41, (Throwable)null);
                InlineMarker.finallyEnd(2);
                var40 = var40;
                var16 = false;
            } catch (Throwable var37) {
                var4 = var37;
                throw var37;
            } finally {
                if (var16) {
                    InlineMarker.finallyStart(1);
                    AutoCloseableKt.closeFinally(var3, var4);
                    InlineMarker.finallyEnd(1);
                }
            }

            InlineMarker.finallyStart(2);
            AutoCloseableKt.closeFinally(var3, (Throwable)null);
            InlineMarker.finallyEnd(2);
            return var40;
        } catch (Throwable var39) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + access$getConnectionProvider(this).getMetaString(), var39);
        }
    }

    public final void executeStatement(@NotNull SQLStatement var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.executeStatement(this.a.createStatement(var1, this.b));
    }

    public final void executeStatement(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        var1 = this.handleQuery(var1);

        try {
            AutoCloseable var2 = (AutoCloseable)this.getConnection();
            Throwable var3 = null;
            boolean var12 = false;

            try {
                var12 = true;
                Connection var4;
                AutoCloseable var24 = (AutoCloseable)(var4 = (Connection)var2).createStatement();
                Throwable var5 = null;
                boolean var18 = false;

                try {
                    var18 = true;
                    Statement var6;
                    (var6 = (Statement)var24).execute(var1);
                    var18 = false;
                } catch (Throwable var19) {
                    var5 = var19;
                    throw var19;
                } finally {
                    if (var18) {
                        AutoCloseableKt.closeFinally(var24, var5);
                    }
                }

                AutoCloseableKt.closeFinally(var24, (Throwable)null);
                var12 = false;
            } catch (Throwable var21) {
                var3 = var21;
                throw var21;
            } finally {
                if (var12) {
                    AutoCloseableKt.closeFinally(var2, var3);
                }
            }

            AutoCloseableKt.closeFinally(var2, (Throwable)null);
        } catch (Throwable var23) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + this.c.getMetaString(), var23);
        }
    }

    public void close() {
        this.c.close();
    }

    @Nullable
    public static final byte[] asBytes(@Nullable UUID var0) {
        return Companion.asBytes(var0);
    }

    @Nullable
    public static final UUID asUUID(@Nullable byte[] var0) {
        return Companion.asUUID(var0);
    }

    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @Nullable
        public final byte[] asBytes(@Nullable UUID var1) {
            if (var1 == null) {
                return null;
            } else {
                ByteBuffer var2;
                (var2 = ByteBuffer.wrap(new byte[16])).putLong(var1.getMostSignificantBits());
                var2.putLong(var1.getLeastSignificantBits());
                return var2.array();
            }
        }

        @JvmStatic
        @Nullable
        public final UUID asUUID(@Nullable byte[] var1) {
            if (var1 == null) {
                return null;
            } else {
                ByteBuffer var7;
                long var3 = (var7 = ByteBuffer.wrap(var1)).getLong();
                long var5 = var7.getLong();
                return new UUID(var3, var5);
            }
        }
    }
}
