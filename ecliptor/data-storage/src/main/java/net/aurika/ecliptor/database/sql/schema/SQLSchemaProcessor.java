package net.aurika.ecliptor.database.sql.schema;

import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.Regex;
import net.aurika.ecliptor.database.DatabaseType;
import org.intellij.lang.annotations.Language;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.utils.string.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class SQLSchemaProcessor {
    private static final Regex LOCATION = a("Location\\((\\w+)\\)");
    private static final Regex BLOCK_LOCATION = a("SimpleLocation\\((\\w+)\\)");
    private static final Regex CHUNK_LOCATION = a("SimpleChunkLocation\\((\\w+)\\)");

    public SQLSchemaProcessor() {
    }

    public static void runSchema(DatabaseType databaseType, InputStream schemaInput, Supplier<Connection> var2) {
        try {
            String var3 = databaseType == DatabaseType.SQLite ? "PRAGMA strict=ON" : null;
            List<String> var11 = (new SQLSchemaReader()).getStatements(schemaInput);
            Connection var13 = var2.get();

            try {
                Statement var4 = var13.createStatement();

                try {

                    for (String var5 : var11) {
                        if (var3 != null) {
                            var4.addBatch(var3);
                            var3 = null;
                        }

                        var5 = Strings.replace(var5, "{PREFIX}", AuspiceGlobalConfig.DATABASE_TABLE_PREFIX.getString() + '_');
                        var5 = a(LOCATION, var5, "world WORLD", "x DOUBLE", "y DOUBLE", "z DOUBLE", "yaw FLOAT", "pitch FLOAT");
                        var5 = a(BLOCK_LOCATION, var5, "world WORLD", "x INT", "y INT", "z INT");
                        var5 = Strings.replace(Strings.replace(Strings.replace(Strings.replace(a(CHUNK_LOCATION, var5, "world WORLD", "x INT", "z INT"), "WORLD", "VARCHAR(64)"), "RANK_NODE", "VARCHAR(50)"), "RANK_NAME", "NVARCHAR(100)"), "COLOR", "VARCHAR(30)");
                        var5 = databaseType.processCommands(var5);
                        var4.addBatch(var5);
                    }

                    var4.executeBatch();
                } catch (Throwable var8) {
                    if (var4 != null) {
                        try {
                            var4.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }
                    }

                    throw var8;
                }

                if (var4 != null) {
                    var4.close();
                }
            } catch (Throwable var9) {
                if (var13 != null) {
                    try {
                        var13.close();
                    } catch (Throwable var6) {
                        var9.addSuppressed(var6);
                    }
                }

                throw var9;
            }

            if (var13 != null) {
                var13.close();
            }
        } catch (SQLException | IOException var10) {
            throw new RuntimeException(var10);
        }
    }

    private static Regex a(@Language("RegExp") String var0) {
        return new Regex(Pattern.compile("(?<!\\w)" + var0 + "( +(?:NOT )?NULL)?"));
    }

    private static String a(Regex var0, String var1, String... var2) {
        return var0.replace(var1, (var1x) -> {
            MatchGroupCollection var7 = var1x.getGroups();
            String var2x = var7.get(1).getValue();
            MatchGroup var8 = var7.get(2);
            String var9 = var8 == null ? "" : var8.getValue();
            StringJoiner var3 = new StringJoiner(", ");

            for (String var6 : var2) {
                var3.add(var2x + '_' + var6 + var9);
            }

            return var3.toString();
        });
    }
}
