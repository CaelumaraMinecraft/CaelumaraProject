package top.auspice.data.database;

import com.zaxxer.hikari.HikariConfig;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.sql.DatabaseProperties;
import top.auspice.data.database.sql.statements.*;
import top.auspice.dependencies.Dependency;

import java.util.*;
import java.util.regex.Pattern;

public enum DatabaseType {
    SQLite("SQLite", 3, 3306, new Dependency[]{Dependency.MYSQL_DRIVER}, "org.kingdoms.libs.mysql.cj.jdbc.MysqlDataSource", '\0', 8, null) {
        public final void applyProperties(@NotNull final HikariConfig hikariConfig, @NotNull final DatabaseProperties props) {
            Objects.requireNonNull(hikariConfig);
            Objects.requireNonNull(props);
            props.add("cachePrepStmts", Boolean.TRUE);
            props.add("prepStmtCacheSize", 250);
            props.add("prepStmtCacheSqlLimit", 2048);
            props.add("useServerPrepStmts", Boolean.TRUE);
            props.add("useLocalSessionState", Boolean.TRUE);
            props.add("rewriteBatchedStatements", Boolean.TRUE);
            props.add("cacheResultSetMetadata", Boolean.TRUE);
            props.add("cacheServerConfiguration", Boolean.TRUE);
            props.add("elideSetAutoCommits", Boolean.TRUE);
            props.add("maintainTimeStats", Boolean.FALSE);
            props.ignore(new String[]{"useUnicode", "characterEncoding"});
            props.useStandardDataSourcePropertyAppendar(hikariConfig);
        }

        @NotNull
        public final String processCommands(@NotNull final String cmd) {
            Objects.requireNonNull(cmd, "");
            return StringsKt.replace(StringsKt.replace(StringsKt.replace(cmd, "UUID", "BINARY(16)", false), "LONG", "BIGINT", false), "STRICT", "", false);
        }

        @NotNull
        public final String createStatement(@NotNull final SQLStatement statement, @NotNull final String s) {
            Objects.requireNonNull(statement, "");
            Objects.requireNonNull(s, "");
            if (statement instanceof SQLUpsert) {
                return "REPLACE INTO `" + s + "` (" + ((SQLUpsert) statement).getParameters() + ") VALUES(" + ((SQLUpsert) statement).getPreparedValues() + ')';
            }
            if (statement instanceof SQLColumnRemove) {
                return "ALTER TABLE `" + s + "` DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
            }
            if (statement instanceof SQLColumnAdd) {
                return "ALTER TABLE `" + s + "` ADD " + this.processCommands(((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + (((SQLColumnAdd) statement).getColumn().getNullable() ? "NULL" : "NOT NULL");
            }
            if (statement instanceof SQLColumnRename) {
                return "ALTER TABLE `" + s + "` ALTER COLUMN " + ((SQLColumnRename) statement).getColumnName() + " RENAME TO " + ((SQLColumnRename) statement).getNewColumnName();
            }
            throw new UnsupportedOperationException(statement.toString());
        }
    },
    H2(null, 1, 0, new Dependency[]{Dependency.H2_DRIVER}, "org.h2.jdbcx.JdbcDataSource", '\u0000', 8, null) {
        public final void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
            Objects.requireNonNull(hikariConfig, "");
            Objects.requireNonNull(props, "");
            throw new UnsupportedOperationException();
        }

        @NotNull
        public final String createStatement(@NotNull SQLStatement statement, @NotNull String var2) {
            Objects.requireNonNull(statement, "");
            Objects.requireNonNull(var2, "");
            if (statement instanceof SQLUpsert) {
                return "MERGE INTO `" + var2 + "` (" + ((SQLUpsert) statement).getParameters() + ") VALUES(" + ((SQLUpsert) statement).getPreparedValues() + ')';
            } else if (statement instanceof SQLColumnRemove) {
                return "ALTER TABLE `" + var2 + "` DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
            } else if (statement instanceof SQLColumnAdd) {
                String var3 = ((SQLColumnAdd) statement).getDefaultValue() == null ? "" : " DEFAULT '" + ((SQLColumnAdd) statement).getDefaultValue() + '\'';
                return "ALTER TABLE `" + var2 + "` ADD " + this.processCommands(((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + ((SQLColumnAdd) statement).getColumn().getNullability() + var3;
            } else if (statement instanceof SQLColumnRename) {
                return "ALTER TABLE `" + var2 + "` ALTER COLUMN " + ((SQLColumnRename) statement).getColumnName() + " RENAME TO " + ((SQLColumnRename) statement).getNewColumnName();
            } else if (statement instanceof SQLColumnModify) {
                return "ALTER TABLE `" + var2 + "` ALTER COLUMN " + ((SQLColumnModify) statement).getColumnName() + ' ' + ((SQLColumnModify) statement).getColumn().getType() + ' ' + ((SQLColumnModify) statement).getColumn().getNullability();
            } else if (statement instanceof SQLBackup) {
                return "SCRIPT TO '" + ((SQLBackup) statement).getTo().resolve(((SQLBackup) statement).getNamed() + ".sql").toAbsolutePath() + '\'';
            } else if (statement instanceof SQLRestore) {
                return "RUNSCRIPT FROM '" + ((SQLRestore) statement).getFrom().toAbsolutePath() + '\'';
            } else {
                throw new UnsupportedOperationException(statement.toString());
            }
        }

        @NotNull
        public final String processCommands(@NotNull String cmd) {
            Objects.requireNonNull(cmd, "");
            return StringsKt.replace(StringsKt.replace(StringsKt.replace(StringsKt.replace(StringsKt.replace(cmd, "LONG", "BIGINT", false), "FLOAT", "REAL", false), "DOUBLE", "DOUBLE PRECISION", false), "NVARCHAR", "VARCHAR", false), "STRICT", "", false);
        }
    },
    MariaDB(null, 2, 3306, new Dependency[]{Dependency.MARIADB_DRIVER}, "org.kingdoms.libs.mariadb.MariaDbDataSource", '\0', 8, null) {
        public final void applyProperties(@NotNull final HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
            Objects.requireNonNull(hikariConfig, "");
            Objects.requireNonNull(props, "");
            String string;
            if ((props).getOthers().isEmpty()) {
                final Map<String, Object> others = props.getOthers();
                final Collection<String> collection = new ArrayList<>((others).size());
                for (final Map.Entry<String, Object> entry : others.entrySet()) {
                    collection.add(entry.getKey() + '=' + entry.getValue());
                }
                string = "?" + CollectionsKt.joinToString(collection, "&", "", "", -1, "...", null);
            } else {
                string = "";
            }
            hikariConfig.addDataSourceProperty("url", "jdbc:mariadb://" + props.getAddress() + ':' + props.getPort() + '/' + props.getDatabaseName() + string);
            hikariConfig.addDataSourceProperty("user", props.getUser());
            hikariConfig.addDataSourceProperty("password", props.getPassword());
        }

        @NotNull
        public final String createStatement(@NotNull final SQLStatement statement, @NotNull final String s) {
            Objects.requireNonNull(statement, "");
            Objects.requireNonNull(s, "");
            if (statement instanceof SQLUpsert) {
                return "REPLACE INTO " + s + " (" + ((SQLUpsert) statement).getParameters() + ") VALUES(" + ((SQLUpsert) statement).getPreparedValues() + ')';
            }
            if (statement instanceof SQLColumnRemove) {
                return "ALTER TABLE " + s + " DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
            }
            if (statement instanceof SQLColumnAdd) {
                return "ALTER TABLE " + s + " ADD " + this.processCommands(((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + (((SQLColumnAdd) statement).getColumn().getNullable() ? "NULL" : "NOT NULL");
            }
            if (statement instanceof SQLColumnRename) {
                return "ALTER TABLE " + s + " ALTER COLUMN " + ((SQLColumnRename) statement).getColumnName() + " RENAME TO " + ((SQLColumnRename) statement).getNewColumnName();
            }
            throw new UnsupportedOperationException(statement.toString());
        }

        @NotNull
        public final String processCommands(@NotNull final String cmd) {
            Objects.requireNonNull(cmd, "");
            return StringsKt.replace(StringsKt.replace(StringsKt.replace(cmd, "UUID", "BINARY(16)", false), "LONG", "BIGINT", false), "STRICT", "", false);
        }
    },
    MySQL("MySQL", 3, 3306, new Dependency[]{Dependency.MYSQL_DRIVER}, "org.kingdoms.libs.mysql.cj.jdbc.MysqlDataSource", '\0', 8, null) {
        public final void applyProperties(@NotNull final HikariConfig hikariConfig, @NotNull final DatabaseProperties props) {
            Objects.requireNonNull(hikariConfig, "");
            Objects.requireNonNull(props, "");
            props.add("cachePrepStmts", Boolean.TRUE);
            props.add("prepStmtCacheSize", 250);
            props.add("prepStmtCacheSqlLimit", 2048);
            props.add("useServerPrepStmts", Boolean.TRUE);
            props.add("useLocalSessionState", Boolean.TRUE);
            props.add("rewriteBatchedStatements", Boolean.TRUE);
            props.add("cacheResultSetMetadata", Boolean.TRUE);
            props.add("cacheServerConfiguration", Boolean.TRUE);
            props.add("elideSetAutoCommits", Boolean.TRUE);
            props.add("maintainTimeStats", Boolean.FALSE);
            final String[] array;
            (array = new String[2])[0] = "useUnicode";
            array[1] = "characterEncoding";
            props.ignore(array);
            props.useStandardDataSourcePropertyAppendar(hikariConfig);
        }

        @NotNull
        public final String processCommands(@NotNull final String cmd) {
            Objects.requireNonNull(cmd, "");
            return StringsKt.replace(StringsKt.replace(StringsKt.replace(cmd, "UUID", "BINARY(16)", false), "LONG", "BIGINT", false), "STRICT", "", false);
        }

        @NotNull
        public final String createStatement(@NotNull final SQLStatement statement, @NotNull final String s) {
            Objects.requireNonNull(statement, "");
            Objects.requireNonNull(s, "");
            if (statement instanceof SQLUpsert) {
                return "REPLACE INTO `" + s + "` (" + ((SQLUpsert) statement).getParameters() + ") VALUES(" + ((SQLUpsert) statement).getPreparedValues() + ')';
            }
            if (statement instanceof SQLColumnRemove) {
                return "ALTER TABLE `" + s + "` DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
            }
            if (statement instanceof SQLColumnAdd) {
                return "ALTER TABLE `" + s + "` ADD " + this.processCommands(((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + (((SQLColumnAdd) statement).getColumn().getNullable() ? "NULL" : "NOT NULL");
            }
            if (statement instanceof SQLColumnRename) {
                return "ALTER TABLE `" + s + "` ALTER COLUMN " + ((SQLColumnRename) statement).getColumnName() + " RENAME TO " + ((SQLColumnRename) statement).getNewColumnName();
            }
            throw new UnsupportedOperationException(statement.toString());
        }
    },
    PostgreSQL("PostgreSQL", 4, 5432, new Dependency[]{Dependency.POSTGRESQL_DRIVER}, "org.kingdoms.libs.postgresql.ds.PGSimpleDataSource", '\"', null) {
        public final void applyProperties(@NotNull final HikariConfig hikariConfig, @NotNull final DatabaseProperties props) {
            Objects.requireNonNull(hikariConfig);
            Objects.requireNonNull(props);
            final String[] array = new String[]{"verifyServerCertificate", "allowPublicKeyRetrieval", "useSSL", "useUnicode", "characterEncoding", "port"};
            props.ignore(array);
            props.useStandardDataSourcePropertyAppendar(hikariConfig);
        }

        @NotNull
        public final String processCommands(@NotNull final String cmd) {
            Objects.requireNonNull(cmd, "");
            return StringsKt.replace(StringsKt.replace(StringsKt.replace(StringsKt.replace(StringsKt.replace(cmd, "NVARCHAR", "VARCHAR", false), "LONG", "BIGINT", false), "DOUBLE", "DOUBLE PRECISION", false), "STRICT", "", false), '`', '\"', false);
        }

        @NotNull
        public final String createStatement(@NotNull final SQLStatement statement, @NotNull final String str) {
            Objects.requireNonNull(statement, "");
            Objects.requireNonNull(str, "");
            if (statement instanceof SQLUpsert) {
                final String replace$default = StringsKt.replace(((SQLUpsert) statement).getParameters(), '`', this.getSystemIdentifierEscapeChar(), false);
                final StringBuilder append = new StringBuilder("INSERT INTO \"").append(str).append("\" (").append(replace$default).append(") VALUES(").append(((SQLUpsert) statement).getPreparedValues()).append(") ON CONFLICT ON CONSTRAINT \"").append(str).append("_pkey\" DO UPDATE SET (").append(replace$default).append(") = (");
                final List<String> iterable = StringsKt.split(replace$default, new String[]{","}, false, 0);
                final Collection<Object> collection = new ArrayList<>(10);
                for (CharSequence o : iterable) {
                    collection.add(StringsKt.trim(o).toString());
                }
                return append.append(CollectionsKt.joinToString(collection, ", ", "", "", -1, "...", (S) -> "excluded." + S)).append(')').toString();
            }
            if (statement instanceof SQLColumnRemove) {
                return "ALTER TABLE `" + str + "` DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
            }
            if (statement instanceof SQLColumnAdd) {
                return "ALTER TABLE `" + str + "` ADD COLUMN " + this.processCommands(((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + (((SQLColumnAdd) statement).getColumn().getNullable() ? "NULL" : "NOT NULL") + ((((SQLColumnAdd) statement).getDefaultValue() == null) ? "" : (" DEFAULT '" + ((SQLColumnAdd) statement).getDefaultValue() + '\''));
            }
            if (statement instanceof SQLColumnRename) {
                return "ALTER TABLE `" + str + "` RENAME COLUMN " + ((SQLColumnRename) statement).getColumnName() + " TO " + ((SQLColumnRename) statement).getNewColumnName();
            }
            throw new UnsupportedOperationException(statement.toString());
        }
    },
    JSON(null, 5, 0, new Dependency[0], "", '\u0000', 8, null) {
        @NotNull
        public final String processCommands(@NotNull String cmd) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        public final String createStatement(@NotNull SQLStatement statement, @NotNull String string) {
            throw new UnsupportedOperationException();
        }

        public final void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
            throw new UnsupportedOperationException();
        }
    },
    YAML("YAML", 6, 0, new Dependency[0], "", '\0', 8, null) {
        @Override
        public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        public final String processCommands(@NotNull final String cmd) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        public final String createStatement(@NotNull final SQLStatement statement, @NotNull final String s) {
            throw new UnsupportedOperationException();
        }
    },
    MongoDB("MongoDB", 7, 27017, new Dependency[]{Dependency.SLF4J_SIMPLE, Dependency.MONGODB_DRIVER_SYNC, Dependency.MONGODB_DRIVER_BSON, Dependency.MONGODB_DRIVER_CORE}, "", '\0', 8, null) {
        @Override
        public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        public final String processCommands(@NotNull final String cmd) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        public final String createStatement(@NotNull final SQLStatement statement, @NotNull final String s) {
            throw new UnsupportedOperationException();
        }
    };
    private final int defaultPort;
    @NotNull
    private final Dependency[] dependencies;
    @NotNull
    private final String dataSourceClassName;
    private final char systemIdentifierEscapeChar;
    @NotNull
    private static final Regex SQLITE_TEXT_AFFINITY;
    @NotNull
    private static final Regex SQLITE_BLOB_AFFINITY;

    DatabaseType(int n2, Dependency[] dependencyArray, String string2, char c) {
        this.defaultPort = n2;
        this.dependencies = dependencyArray;
        this.dataSourceClassName = string2;
        this.systemIdentifierEscapeChar = c;
    }

    DatabaseType(String string, int n, int n2, Dependency[] dependencyArray, String string2, char c, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 8) != 0) {
            c = '`';
        }
        this(n2, dependencyArray, string2, c);
    }

    public final int getDefaultPort() {
        return this.defaultPort;
    }

    @NotNull
    public final Dependency[] getDependencies() {
        return this.dependencies;
    }

    @NotNull
    public final String getDataSourceClassName() {
        return this.dataSourceClassName;
    }

    public final char getSystemIdentifierEscapeChar() {
        return this.systemIdentifierEscapeChar;
    }

    public abstract void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props);

    @NotNull
    public abstract String processCommands(@NotNull String cmd);

    @NotNull
    public abstract String createStatement(@NotNull SQLStatement statement, @NotNull String var2);

    public static Regex access$getSQLITE_BLOB_AFFINITY$cp() {
        return SQLITE_BLOB_AFFINITY;
    }

    public static Regex access$getSQLITE_TEXT_AFFINITY$cp() {
        return SQLITE_TEXT_AFFINITY;
    }

    DatabaseType(String string, int n, int n2, Dependency[] dependencyArray, String string2, char c, DefaultConstructorMarker defaultConstructorMarker) {
        this(n2, dependencyArray, string2, c);
    }

    static {
        Pattern pattern = Pattern.compile("(N)?(VAR)?CHAR\\(\\d+\\)");
        SQLITE_TEXT_AFFINITY = new Regex(pattern);
        Pattern pattern2 = Pattern.compile("BINARY\\(\\d+\\)");
        SQLITE_BLOB_AFFINITY = new Regex(pattern2);
    }

}

