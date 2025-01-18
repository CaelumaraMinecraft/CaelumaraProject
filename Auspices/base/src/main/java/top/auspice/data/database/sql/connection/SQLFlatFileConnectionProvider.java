
package top.auspice.data.database.sql.connection;

import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.DatabaseType;
import top.auspice.dependencies.classpath.IsolatedClassLoader;
import top.auspice.main.Auspice;
import top.auspice.utils.AuspiceLogger;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.EnumSet;
import java.util.Properties;

public final class SQLFlatFileConnectionProvider extends SQLConnectionProvider {
    @NotNull
    private final String a;
    @NotNull
    private final Path b;
    private NonClosableConnection c;

    public SQLFlatFileConnectionProvider(@NotNull DatabaseType var1, @NotNull String var2, @NotNull Path var3) {
        super(var1);
        Intrinsics.checkNotNullParameter(var2, "");
        Intrinsics.checkNotNullParameter(var3, "");
        this.a = var2;
        this.b = var3;
        DatabaseType[] var5;
        (var5 = new DatabaseType[2])[0] = DatabaseType.SQLite;
        var5[1] = DatabaseType.H2;
        if (!ArraysKt.contains(var5, var1)) {
            String var4 = "Invalid SQL type for flat file: " + var1;
            throw new IllegalArgumentException(var4);
        } else {
            this.testTemporaryLibCreation$core(var1);
        }
    }

    @NotNull
    public String getJdbcUrl() {
        return this.a;
    }

    @NotNull
    public Path getFile() {
        return this.b;
    }

    public void connect() {
        String var2;
        if (this.c != null) {
            var2 = "Already connected";
            throw new IllegalArgumentException(var2);
        } else {
            IsolatedClassLoader var10000 = Auspice.get().getDependencyManager().obtainClassLoaderWith(EnumSet.of((Enum)ArraysKt.first(this.getDatabaseType().getDependencies())));
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            IsolatedClassLoader var1 = var10000;

            try {
                var2 = this.getDatabaseType() == DatabaseType.SQLite ? "org.sqlite.jdbc4.JDBC4Connection" : "org.h2.jdbc.JdbcConnection";
                Class var14 = var1.loadClass(var2);
                Intrinsics.checkNotNullExpressionValue(var14, "");
                Class var7 = var14;
                SQLFlatFileConnectionProvider var15 = this;
                Class[] var10;
                Object var17;
                Connection var18;
                if (this.getDatabaseType() == DatabaseType.SQLite) {
                    (var10 = new Class[3])[0] = String.class;
                    var10[1] = String.class;
                    var10[2] = Properties.class;
                    Constructor var10001 = var7.getConstructor(var10);
                    Object[] var8;
                    (var8 = new Object[3])[0] = "jdbc:sqlite:" + this.a;
                    var8[1] = this.a;
                    var8[2] = new Properties();
                    var17 = var10001.newInstance(var8);
                    Intrinsics.checkNotNull(var17);
                    var18 = (Connection)var17;
                } else {
                    (var10 = new Class[5])[0] = String.class;
                    var10[1] = Properties.class;
                    var10[2] = String.class;
                    var10[3] = Object.class;
                    var10[4] = Boolean.TYPE;
                    Constructor var9 = var7.getConstructor(var10);
                    SQLFlatFileConnectionProvider var12 = this;

                    Connection var13;
                    try {
                        var15 = var12;
                        var2 = AuspiceLogger.isDebugging() ? ";TRACE_LEVEL_FILE=3" : "";
                        Object[] var3;
                        (var3 = new Object[5])[0] = "jdbc:h2:" + this.a + var2;
                        var3[1] = new Properties();
                        var3[2] = null;
                        var3[3] = null;
                        var3[4] = Boolean.FALSE;
                        var17 = var9.newInstance(var3);
                        Intrinsics.checkNotNull(var17);
                        var13 = (Connection)var17;
                    } catch (Throwable var5) {
                        String var16 = var5.getMessage();
                        if ((var16 != null && "corrupt".contains(var16)) && this.getDatabaseType() == DatabaseType.H2) {
                            AuspiceLogger.error("Detected corrupted H2 database, attempting to run the recovery script...");

                            try {
                                H2Tools.recover(this.b);
                                AuspiceLogger.info("Recovery done. Please check your 'data' folder.");
                            } catch (Throwable var4) {
                                var4.printStackTrace();
                            }
                        }

                        throw var5;
                    }

                    var18 = var13;
                }

                Connection var11 = var18;
                var15.c = new NonClosableConnection(var11);
            } catch (ReflectiveOperationException var6) {
                throw new RuntimeException(var6);
            }
        }
    }

    @NotNull
    public Connection getConnection() {
        NonClosableConnection var10000 = this.c;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("");
            var10000 = null;
        }

        return var10000;
    }

    public void close() {
        NonClosableConnection var10000;
        if (this.getDatabaseType() == DatabaseType.H2) {
            var10000 = this.c;
            if (var10000 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("");
                var10000 = null;
            }

            if (!var10000.isClosed()) {
                var10000 = this.c;
                if (var10000 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("");
                    var10000 = null;
                }

                AutoCloseable var1 = var10000;
                Throwable var2 = null;
                boolean var10 = false;

                try {
                    var10 = true;
                    NonClosableConnection var3;
                    AutoCloseable var20 = (var3 = (NonClosableConnection)var1).createStatement();
                    Throwable var4 = null;
                    boolean var15 = false;

                    try {
                        var15 = true;
                        Statement var5;
                        (var5 = (Statement)var20).execute("SHUTDOWN");
                        var15 = false;
                    } catch (Throwable var16) {
                        var4 = var16;
                        throw var16;
                    } finally {
                        if (var15) {
                            AutoCloseableKt.closeFinally(var20, var4);
                        }
                    }

                    AutoCloseableKt.closeFinally(var20, (Throwable)null);
                    var10 = false;
                } catch (Throwable var18) {
                    var2 = var18;
                    throw var18;
                } finally {
                    if (var10) {
                        AutoCloseableKt.closeFinally(var1, var2);
                    }
                }

                AutoCloseableKt.closeFinally(var1, (Throwable)null);
            }
        }

        var10000 = this.c;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("");
            var10000 = null;
        }

        var10000.shutdown();
    }
}
