package net.aurika.ecliptor.database.sql.connection;

import com.zaxxer.hikari.HikariDataSource;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.sql.schema.SQLSchemaProcessor;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.main.Auspice;
import net.aurika.auspice.utils.logging.AuspiceLogger;
import net.aurika.auspice.utils.unsafe.AutoCloseableUtils;

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

    private final @NotNull DatabaseType databaseType;
    public static final @NotNull String TABLE_PREFIX;

    public SQLConnectionProvider(@NotNull DatabaseType databaseType) {
        Validate.Arg.notNull(databaseType, "databaseType");
        this.databaseType = databaseType;
    }

    public final @NotNull DatabaseType getDatabaseType() {
        return this.databaseType;
    }

    public abstract @NotNull Connection getConnection();

    public abstract void connect();

    public abstract void close();

    public final void runSchema() {
        this.printMeta();
        InputStream var1 = Auspice.get().getResource("schema.sql");  // TODO
        SQLSchemaProcessor.runSchema(this.databaseType, var1, SQLConnectionProvider::a);
    }

    public final @NotNull String getMetaString() {
        try {
            DatabaseMetaData var11 = this.getConnection().getMetaData();

            return "Running " + this.databaseType.name() + " SQL Database:" +
                    "\n   | Driver: " + var11.getDriverName() + " (" + var11.getCatalogTerm() + ") / " + var11.getDriverVersion() +
                    "\n   | Product: " + var11.getDatabaseProductName() + " / " + var11.getDatabaseProductVersion() +
                    "\n   | JDBC: " + var11.getJDBCMajorVersion() + '.' + var11.getJDBCMinorVersion();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to retrieve meta information for SQL: " + this.databaseType, ex);
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

    public final void testTemporaryLibCreation$core(@NotNull DatabaseType databaseType) {
        Validate.Arg.notNull(databaseType, "databaseType");
        DatabaseType[] var2 = new DatabaseType[2];
        var2[0] = DatabaseType.SQLite;
        var2[1] = DatabaseType.H2;
        File var3 = new File(System.getProperty("java.io.tmpdir"));
        if (ArraysKt.contains(var2, databaseType) && (!var3.exists() || !var3.isDirectory() || !var3.canRead() || !var3.canWrite())) {
            AuspiceLogger.error("A problem has occurred for with java.io.tmpdir " + var3.exists() + " | " + var3.isDirectory() + " | " + var3.canRead() + " | " + var3.canWrite());
        }
    }

    private static Connection a(SQLConnectionProvider var0) {
        Validate.Arg.notNull(var0, "");
        return var0.getConnection();
    }

    private static void a(Function1 var0, Object var1) {
        Validate.Arg.notNull(var0, "");
        var0.invoke(var1);
    }

    @NotNull
    public static SQLConnectionProvider getProvider(@NotNull Path var1, @NotNull DatabaseType var2) {
        Validate.Arg.notNull(var1, "");
        Validate.Arg.notNull(var2, "");
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
