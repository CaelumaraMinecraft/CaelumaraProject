
package net.aurika.data.database.sql.connection;

import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.data.database.DatabaseType;
import net.aurika.data.database.sql.H2Tools;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import top.auspice.dependencies.classpath.IsolatedClassLoader;
import top.auspice.main.Auspice;
import top.auspice.utils.logging.AuspiceLogger;
import top.auspice.utils.unsafe.AutoCloseableUtils;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumSet;
import java.util.Properties;

public class SQLFlatFileConnectionProvider extends SQLConnectionProvider {

    private final @NotNull String jdbcUrl;
    private final @NotNull Path file;
    private NonClosableConnection c;  // lazy

    public SQLFlatFileConnectionProvider(@NotNull DatabaseType databaseType, @NotNull String jdbcUrl, @NotNull Path file) {
        super(databaseType);
        Checker.Arg.notNull(jdbcUrl, "jdbcUrl");
        Checker.Arg.notNull(file, "file");
        this.jdbcUrl = jdbcUrl;
        this.file = file;
        DatabaseType[] var5 = new DatabaseType[2];
        var5[0] = DatabaseType.SQLite;
        var5[1] = DatabaseType.H2;
        if (!ArraysKt.contains(var5, databaseType)) {
            String var4 = "Invalid SQL type for flat file: " + databaseType;
            throw new IllegalArgumentException(var4);
        } else {
            this.testTemporaryLibCreation$core(databaseType);
        }
    }

    public @NotNull String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public @NotNull Path getFile() {
        return this.file;
    }

    public void connect() {
        String var2;
        if (this.c != null) {
            var2 = "Already connected";
            throw new IllegalArgumentException(var2);
        } else {
            IsolatedClassLoader var10000 = Auspice.get().getDependencyManager().obtainClassLoaderWith(EnumSet.of((Enum) ArraysKt.first(this.getDatabaseType().getDependencies())));
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            IsolatedClassLoader var1 = var10000;

            try {
                var2 = this.getDatabaseType() == DatabaseType.SQLite ? "org.sqlite.jdbc4.JDBC4Connection" : "org.h2.jdbc.JdbcConnection";
                Class<?> var14 = var1.loadClass(var2);
                Intrinsics.checkNotNullExpressionValue(var14, "");
                SQLFlatFileConnectionProvider var15 = this;
                Class<?>[] params;
                Object var17;
                Connection var18;
                if (this.getDatabaseType() == DatabaseType.SQLite) {
                    params = new Class[3];
                    params[0] = String.class;
                    params[1] = String.class;
                    params[2] = Properties.class;
                    Constructor<?> var10001 = var14.getConstructor(params);
                    Object[] args = new Object[3];
                    args[0] = "jdbc:sqlite:" + this.jdbcUrl;
                    args[1] = this.jdbcUrl;
                    args[2] = new Properties();
                    var17 = var10001.newInstance(args);
                    Intrinsics.checkNotNull(var17);
                    var18 = (Connection) var17;
                } else {
                    params = new Class[5];
                    params[0] = String.class;
                    params[1] = Properties.class;
                    params[2] = String.class;
                    params[3] = Object.class;
                    params[4] = boolean.class;
                    Constructor<?> var9 = var14.getConstructor(params);

                    Connection connection;
                    try {
                        var2 = AuspiceLogger.isDebugging() ? ";TRACE_LEVEL_FILE=3" : "";
                        Object[] var3;
                        (var3 = new Object[5])[0] = "jdbc:h2:" + this.jdbcUrl + var2;
                        var3[1] = new Properties();
                        var3[2] = null;
                        var3[3] = null;
                        var3[4] = Boolean.FALSE;
                        var17 = var9.newInstance(var3);
                        Intrinsics.checkNotNull(var17);
                        connection = (Connection) var17;
                    } catch (Throwable var5) {
                        String var16 = var5.getMessage();
                        if ((var16 != null && "corrupt".contains(var16)) && this.getDatabaseType() == DatabaseType.H2) {
                            AuspiceLogger.error("Detected corrupted H2 database, attempting to run the recovery script...");

                            try {
                                H2Tools.recover(this.file);
                                AuspiceLogger.info("Recovery done. Please check your 'data' folder.");
                            } catch (Throwable var4) {
                                var4.printStackTrace();
                            }
                        }

                        throw var5;
                    }

                    var18 = connection;
                }

                Connection var11 = var18;
                var15.c = new NonClosableConnection(var11);
            } catch (ReflectiveOperationException var6) {
                throw new RuntimeException(var6);
            }
        }
    }

    public @NotNull Connection getConnection() {
        NonClosableConnection var10000 = this.c;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("");
            var10000 = null;
        }

        return var10000;
    }

    public void close() {
        try {
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

                    NonClosableConnection var1 = var10000;
                    Throwable var2 = null;
                    boolean var10 = false;

                    try {
                        var10 = true;
                        Statement var20 = var1.createStatement();
                        Throwable var4 = null;
                        boolean var15 = false;

                        try {
                            var15 = true;
                            var20.execute("SHUTDOWN");
                            var15 = false;
                        } catch (Throwable var16) {
                            var4 = var16;
                            throw var16;
                        } finally {
                            if (var15) {
                                AutoCloseableUtils.closeFinally(var20, var4);
                            }
                        }

                        AutoCloseableUtils.closeFinally(var20, (Throwable) null);
                        var10 = false;
                    } catch (Throwable var18) {
                        var2 = var18;
                        throw var18;
                    } finally {
                        if (var10) {
                            AutoCloseableUtils.closeFinally(var1, var2);
                        }
                    }

                    AutoCloseableUtils.closeFinally(var1, (Throwable) null);
                }
            }

            var10000 = this.c;
            if (var10000 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("");
                var10000 = null;
            }

            var10000.shutdown();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
