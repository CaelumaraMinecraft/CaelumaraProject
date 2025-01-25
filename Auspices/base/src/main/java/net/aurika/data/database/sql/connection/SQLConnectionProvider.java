package net.aurika.data.database.sql.connection;

import com.zaxxer.hikari.HikariDataSource;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.data.database.DatabaseType;
import net.aurika.data.database.sql.schema.SQLSchemaProcessor;
import top.auspice.main.Auspice;
import top.auspice.utils.logging.AuspiceLogger;
import top.auspice.utils.unsafe.AutoCloseableUtils;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public abstract class SQLConnectionProvider implements Closeable {
    @NotNull
    private final DatabaseType a;
    @NotNull
    public static final String TABLE_PREFIX;

    public SQLConnectionProvider(@NotNull DatabaseType var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.a = var1;
    }

    @NotNull
    public final DatabaseType getDatabaseType() {
        return this.a;
    }

    @NotNull
    public abstract Connection getConnection();

    public abstract void connect();

    public abstract void close();

    public final void runSchema() {
        this.printMeta();
        InputStream var1 = Auspice.get().getResource("schema.sql");  // TODO
        SQLSchemaProcessor.runSchema(this.a, var1, SQLConnectionProvider::a);
    }

    public final @NotNull String getMetaString() {
        try {
            Connection var1 = this.getConnection();
            Throwable var2 = null;
            boolean var7 = false;

            String var12;
            try {
                var7 = true;
                DatabaseMetaData var11 = var1.getMetaData();
                var12 = "Running " + this.a.name() + " SQL Database:\n   | Driver: " + var11.getDriverName() + " (" + var11.getCatalogTerm() + ") / " + var11.getDriverVersion() + "\n   | Product: " + var11.getDatabaseProductName() + " / " + var11.getDatabaseProductVersion() + "\n   | JDBC: " + var11.getJDBCMajorVersion() + '.' + var11.getJDBCMinorVersion();
                var7 = false;
            } catch (Throwable var8) {
                var2 = var8;
                throw var8;
            } finally {
                if (var7) {
                    AutoCloseableUtils.closeFinally(var1, var2);
                }
            }

            AutoCloseableUtils.closeFinally(var1, (Throwable) null);
            return var12;
        } catch (SQLException var10) {
            throw new RuntimeException("Failed to retrieve meta information for SQL: " + this.a, var10);
        }
    }

    public final void printMeta() {
        List var10000;
        label24:
        {
            CharSequence var1 = this.getMetaString();
            List var4;
            if (!(var4 = (new Regex("\n")).split(var1, 0)).isEmpty()) {
                ListIterator var2 = var4.listIterator(var4.size());

                while (var2.hasPrevious()) {
                    if (((CharSequence) var2.previous()).length() != 0) {
                        var10000 = CollectionsKt.take(var4, var2.nextIndex() + 1);
                        break label24;
                    }
                }
            }

            var10000 = CollectionsKt.emptyList();
        }

        final class NamelessClass_1 extends Lambda implements Function1<String, Unit> {
            public static final NamelessClass_1 a = new NamelessClass_1();

            NamelessClass_1() {
                super(1);
            }

            public void a(String var1) {
                AuspiceLogger.info(var1);
            }
        }

        Arrays.stream(((Collection) var10000).toArray(new String[0])).forEach(SQLConnectionProvider::a);
    }

    public final void testTemporaryLibCreation$core(@NotNull DatabaseType var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        DatabaseType[] var2;
        (var2 = new DatabaseType[2])[0] = DatabaseType.SQLite;
        var2[1] = DatabaseType.H2;
        File var3;
        if (ArraysKt.contains(var2, var1) && (!(var3 = new File(System.getProperty("java.io.tmpdir"))).exists() || !var3.isDirectory() || !var3.canRead() || !var3.canWrite())) {
            AuspiceLogger.error("A problem has occurred for with java.io.tmpdir " + var3.exists() + " | " + var3.isDirectory() + " | " + var3.canRead() + " | " + var3.canWrite());
        }
    }

    private static Connection a(SQLConnectionProvider var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        return var0.getConnection();
    }

    private static void a(Function1 var0, Object var1) {
        Intrinsics.checkNotNullParameter(var0, "");
        var0.invoke(var1);
    }

    @NotNull
    public static SQLConnectionProvider getProvider(@NotNull Path var1, @NotNull DatabaseType var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        if (var2 != DatabaseType.SQLite && var2 != DatabaseType.H2) {
            return new SQLHikariConnectionProvider(var2, new HikariDataSource());
        } else {
            String var10003 = var1.resolve("data").toAbsolutePath() + (var2 == DatabaseType.H2 ? "" : ".db");
            Path var10004 = var1.resolve("data" + (var2 == DatabaseType.H2 ? ".mv.db" : ".db"));
            Intrinsics.checkNotNullExpressionValue(var10004, "");
            return new SQLFlatFileConnectionProvider(var2, var10003, var10004);
        }
    }

    static {
        TABLE_PREFIX = AuspiceGlobalConfig.DATABASE_TABLE_PREFIX.getString() + '_';
    }
}
