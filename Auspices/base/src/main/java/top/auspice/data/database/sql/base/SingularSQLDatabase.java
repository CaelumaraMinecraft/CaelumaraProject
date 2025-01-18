package top.auspice.data.database.sql.base;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.base.AuspiceObject;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.base.SingularKingdomsDatabase;
import top.auspice.data.database.sql.connection.SQLConnectionProvider;
import top.auspice.data.handlers.abstraction.SingularDataHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public final class SingularSQLDatabase<T extends AuspiceObject> extends SQLDatabase<T> implements SingularKingdomsDatabase<T> {
    @NotNull
    private final SingularDataHandler<T> a;

    public SingularSQLDatabase(@NotNull DatabaseType var1, @NotNull String var2, @NotNull SingularDataHandler<T> var3, @NotNull SQLConnectionProvider var4) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var3, "");
        Intrinsics.checkNotNullParameter(var4, "");
        super(var1, var2, var4);
        this.a = var3;
    }

    @NotNull
    protected final SingularDataHandler<T> getDataHandler() {
        return this.a;
    }

    @Nullable
    public final T load() {
        String var1 = "SELECT * FROM `" + this.getTable() + '`';
        SQLDatabase var2;
        var1 = SQLDatabase.access$handleQuery(var2 = this, var1);

        try {
            AutoCloseable var3 = (AutoCloseable)SQLDatabase.access$getConnection(var2);
            Throwable var4 = null;
            boolean var17 = false;

            AuspiceObject var42;
            try {
                var17 = true;
                Connection var5;
                AutoCloseable var41 = (AutoCloseable)(var5 = (Connection)var3).prepareStatement(var1);
                Throwable var6 = null;
                boolean var25 = false;

                AuspiceObject var44;
                try {
                    var25 = true;
                    PreparedStatement var7;
                    AutoCloseable var43 = (AutoCloseable)(var7 = (PreparedStatement)var41).executeQuery();
                    Throwable var8 = null;
                    boolean var33 = false;

                    KingdomsObject var10000;
                    label182: {
                        label181: {
                            KingdomsObject var46;
                            try {
                                var33 = true;
                                ResultSet var9;
                                if (!(var9 = (ResultSet)var43).next()) {
                                    var33 = false;
                                    break label181;
                                }

                                DatabaseType var10002 = ((SQLDatabase)this).getDatabaseType();
                                String var10003 = this.getTable();
                                DatabaseType var10009 = ((SQLDatabase)this).getDatabaseType();
                                Intrinsics.checkNotNull(var9);
                                SQLDataGetterProvider var45 = new SQLDataGetterProvider(var10002, var10003, (String)null, false, false, new SimpleResultSetQuery(var10009, var9));
                                var46 = (KingdomsObject)this.getDataHandler().load((SectionableDataGetter)var45);
                                var33 = false;
                            } catch (Throwable var34) {
                                var8 = var34;
                                throw var34;
                            } finally {
                                if (var33) {
                                    AutoCloseableKt.closeFinally(var43, var8);
                                }
                            }

                            AutoCloseableKt.closeFinally(var43, (Throwable)null);
                            var10000 = var46;
                            break label182;
                        }

                        AutoCloseableKt.closeFinally(var43, (Throwable)null);
                        var10000 = null;
                    }

                    var44 = var10000;
                    var25 = false;
                } catch (Throwable var36) {
                    var6 = var36;
                    throw var36;
                } finally {
                    if (var25) {
                        AutoCloseableKt.closeFinally(var41, var6);
                    }
                }

                AutoCloseableKt.closeFinally(var41, (Throwable)null);
                var42 = var44;
                var17 = false;
            } catch (Throwable var38) {
                var4 = var38;
                throw var38;
            } finally {
                if (var17) {
                    AutoCloseableKt.closeFinally(var3, var4);
                }
            }

            AutoCloseableKt.closeFinally(var3, (Throwable)null);
            return var42;
        } catch (Throwable var40) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + SQLDatabase.access$getConnectionProvider(var2).getMetaString(), var40);
        }
    }

    public final boolean hasData() {
        String var1 = "SELECT 1 FROM `" + this.getTable() + '`';
        SQLDatabase var2;
        var1 = SQLDatabase.access$handleQuery(var2 = (SQLDatabase)this, var1);

        try {
            AutoCloseable var3 = (AutoCloseable)SQLDatabase.access$getConnection(var2);
            Throwable var4 = null;
            boolean var13 = false;

            boolean var26;
            try {
                var13 = true;
                Connection var5;
                AutoCloseable var25 = (AutoCloseable)(var5 = (Connection)var3).prepareStatement(var1);
                Throwable var6 = null;
                boolean var19 = false;

                try {
                    var19 = true;
                    PreparedStatement var7;
                    var26 = (var7 = (PreparedStatement)var25).executeQuery().next();
                    var19 = false;
                } catch (Throwable var20) {
                    var6 = var20;
                    throw var20;
                } finally {
                    if (var19) {
                        AutoCloseableKt.closeFinally(var25, var6);
                    }
                }

                AutoCloseableKt.closeFinally(var25, (Throwable)null);
                var13 = false;
            } catch (Throwable var22) {
                var4 = var22;
                throw var22;
            } finally {
                if (var13) {
                    AutoCloseableKt.closeFinally(var3, var4);
                }
            }

            AutoCloseableKt.closeFinally(var3, (Throwable)null);
            return var26;
        } catch (Throwable var24) {
            throw new RuntimeException("Error while handling data with query: " + var1 + " with " + SQLDatabase.access$getConnectionProvider(var2).getMetaString(), var24);
        }
    }
}
