package net.aurika.ecliptor.database;

import com.zaxxer.hikari.HikariConfig;
import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;
import net.aurika.common.dependency.Dependency;
import net.aurika.ecliptor.database.sql.DatabaseProperties;
import net.aurika.ecliptor.database.sql.statements.*;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public enum DatabaseType {
  SQLite(
      3306, new Dependency[]{Dependency.MYSQL_DRIVER}, "net.aurika.ecliptor.libs.mysql.cj.jdbc.MysqlDataSource", '`') {
    @Override
    public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
      Validate.Arg.notNull(hikariConfig, "hikariConfig");
      Validate.Arg.notNull(props, "props");
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

    @Override
    public @NotNull String processCommands(@NotNull String commands) {
      Validate.Arg.notNull(commands, "");
      return StringsKt.replace(
          StringsKt.replace(StringsKt.replace(commands, "UUID", "BINARY(16)", false), "LONG", "BIGINT", false),
          "STRICT", "", false
      );
    }

    @Override
    public @NotNull String createStatement(@NotNull SQLStatement statement, @NotNull String value) {
      Validate.Arg.notNull(statement, "");
      Validate.Arg.notNull(value, "");
      if (statement instanceof SQLUpsert) {
        return "REPLACE INTO `" + value + "` (" + ((SQLUpsert) statement).getParameters() + ") VALUES(" + ((SQLUpsert) statement).getPreparedValues() + ')';
      }
      if (statement instanceof SQLColumnRemove) {
        return "ALTER TABLE `" + value + "` DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
      }
      if (statement instanceof SQLColumnAdd) {
        return "ALTER TABLE `" + value + "` ADD " + this.processCommands(
            ((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + (((SQLColumnAdd) statement).getColumn().getNullable() ? "NULL" : "NOT NULL");
      }
      if (statement instanceof SQLColumnRename) {
        return "ALTER TABLE `" + value + "` ALTER COLUMN " + ((SQLColumnRename) statement).getColumnName() + " RENAME TO " + ((SQLColumnRename) statement).getNewColumnName();
      }
      throw new UnsupportedOperationException(statement.toString());
    }
  },
  H2(0, new Dependency[]{Dependency.H2_DRIVER}, "org.h2.jdbcx.JdbcDataSource", '`') {
    @Override
    public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
      Validate.Arg.notNull(hikariConfig, "");
      Validate.Arg.notNull(props, "");
      throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull String createStatement(@NotNull SQLStatement statement, @NotNull String value) {
      Validate.Arg.notNull(statement, "");
      Validate.Arg.notNull(value, "");
      if (statement instanceof SQLUpsert) {
        return "MERGE INTO `" + value + "` (" + ((SQLUpsert) statement).getParameters() + ") VALUES(" + ((SQLUpsert) statement).getPreparedValues() + ')';
      } else if (statement instanceof SQLColumnRemove) {
        return "ALTER TABLE `" + value + "` DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
      } else if (statement instanceof SQLColumnAdd) {
        String var3 = ((SQLColumnAdd) statement).getDefaultValue() == null ? "" : " DEFAULT '" + ((SQLColumnAdd) statement).getDefaultValue() + '\'';
        return "ALTER TABLE `" + value + "` ADD " + this.processCommands(
            ((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + ((SQLColumnAdd) statement).getColumn().getNullability() + var3;
      } else if (statement instanceof SQLColumnRename) {
        return "ALTER TABLE `" + value + "` ALTER COLUMN " + ((SQLColumnRename) statement).getColumnName() + " RENAME TO " + ((SQLColumnRename) statement).getNewColumnName();
      } else if (statement instanceof SQLColumnModify) {
        return "ALTER TABLE `" + value + "` ALTER COLUMN " + ((SQLColumnModify) statement).getColumnName() + ' ' + ((SQLColumnModify) statement).getColumn().getType() + ' ' + ((SQLColumnModify) statement).getColumn().getNullability();
      } else if (statement instanceof SQLBackup) {
        return "SCRIPT TO '" + ((SQLBackup) statement).getTo().resolve(
            ((SQLBackup) statement).getNamed() + ".sql").toAbsolutePath() + '\'';
      } else if (statement instanceof SQLRestore) {
        return "RUNSCRIPT FROM '" + ((SQLRestore) statement).getFrom().toAbsolutePath() + '\'';
      } else {
        throw new UnsupportedOperationException(statement.toString());
      }
    }

    @Override
    public @NotNull String processCommands(@NotNull String commands) {
      Validate.Arg.notNull(commands, "");
      return StringsKt.replace(
          StringsKt.replace(
              StringsKt.replace(
                  StringsKt.replace(StringsKt.replace(commands, "LONG", "BIGINT", false), "FLOAT", "REAL", false), "DOUBLE",
                  "DOUBLE PRECISION", false
              ), "NVARCHAR", "VARCHAR", false
          ), "STRICT", "", false
      );
    }
  },
  MariaDB(
      3306, new Dependency[]{Dependency.MARIADB_DRIVER}, "net.aurika.ecliptor.libs.mariadb.MariaDbDataSource", '`') {
    @Override
    public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
      Validate.Arg.notNull(hikariConfig, "");
      Validate.Arg.notNull(props, "");
      String string;
      if (props.others().isEmpty()) {
        Map<String, Object> others = props.others();
        Collection<String> collection = new ArrayList<>((others).size());
        for (Map.Entry<String, Object> entry : others.entrySet()) {
          collection.add(entry.getKey() + '=' + entry.getValue());
        }
        string = "?" + CollectionsKt.joinToString(collection, "&", "", "", -1, "...", null);
      } else {
        string = "";
      }
      hikariConfig.addDataSourceProperty(
          "url", "jdbc:mariadb://" + props.address() + ':' + props.port() + '/' + props.databaseName() + string);
      hikariConfig.addDataSourceProperty("user", props.user());
      hikariConfig.addDataSourceProperty("password", props.password());
    }

    @Override
    public @NotNull String createStatement(@NotNull SQLStatement statement, @NotNull String value) {
      Validate.Arg.notNull(statement, "");
      Validate.Arg.notNull(value, "");
      if (statement instanceof SQLUpsert) {
        return "REPLACE INTO " + value + " (" + ((SQLUpsert) statement).getParameters() + ") VALUES(" + ((SQLUpsert) statement).getPreparedValues() + ')';
      }
      if (statement instanceof SQLColumnRemove) {
        return "ALTER TABLE " + value + " DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
      }
      if (statement instanceof SQLColumnAdd) {
        return "ALTER TABLE " + value + " ADD " + this.processCommands(
            ((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + (((SQLColumnAdd) statement).getColumn().getNullable() ? "NULL" : "NOT NULL");
      }
      if (statement instanceof SQLColumnRename) {
        return "ALTER TABLE " + value + " ALTER COLUMN " + ((SQLColumnRename) statement).getColumnName() + " RENAME TO " + ((SQLColumnRename) statement).getNewColumnName();
      }
      throw new UnsupportedOperationException(statement.toString());
    }

    @Override
    public @NotNull String processCommands(@NotNull String commands) {
      Validate.Arg.notNull(commands, "");
      return StringsKt.replace(
          StringsKt.replace(StringsKt.replace(commands, "UUID", "BINARY(16)", false), "LONG", "BIGINT", false),
          "STRICT", "", false
      );
    }
  },
  MySQL(
      3306, new Dependency[]{Dependency.MYSQL_DRIVER}, "net.aurika.ecliptor.libs.mysql.cj.jdbc.MysqlDataSource", '`') {
    @Override
    public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
      Validate.Arg.notNull(hikariConfig, "");
      Validate.Arg.notNull(props, "");
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
      String[] array = new String[2];
      array[0] = "useUnicode";
      array[1] = "characterEncoding";
      props.ignore(array);
      props.useStandardDataSourcePropertyAppendar(hikariConfig);
    }

    @Override
    public @NotNull String processCommands(@NotNull String commands) {
      Validate.Arg.notNull(commands, "");
      return StringsKt.replace(
          StringsKt.replace(StringsKt.replace(commands, "UUID", "BINARY(16)", false), "LONG", "BIGINT", false),
          "STRICT", "", false
      );
    }

    @Override
    public @NotNull String createStatement(@NotNull SQLStatement statement, @NotNull String value) {
      Validate.Arg.notNull(statement, "statement");
      Validate.Arg.notNull(value, "");
      if (statement instanceof SQLUpsert) {
        return "REPLACE INTO `" + value + "` (" + ((SQLUpsert) statement).getParameters() + ") VALUES(" + ((SQLUpsert) statement).getPreparedValues() + ')';
      }
      if (statement instanceof SQLColumnRemove) {
        return "ALTER TABLE `" + value + "` DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
      }
      if (statement instanceof SQLColumnAdd) {
        return "ALTER TABLE `" + value + "` ADD " + this.processCommands(
            ((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + (((SQLColumnAdd) statement).getColumn().getNullable() ? "NULL" : "NOT NULL");
      }
      if (statement instanceof SQLColumnRename) {
        return "ALTER TABLE `" + value + "` ALTER COLUMN " + ((SQLColumnRename) statement).getColumnName() + " RENAME TO " + ((SQLColumnRename) statement).getNewColumnName();
      }
      throw new UnsupportedOperationException(statement.toString());
    }
  },
  PostgreSQL(
      5432, new Dependency[]{Dependency.POSTGRESQL_DRIVER}, "net.aurika.ecliptor.libs.postgresql.ds.PGSimpleDataSource",
      '\"'
  ) {
    @Override
    public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
      Validate.Arg.notNull(hikariConfig, "hikariConfig");
      Validate.Arg.notNull(props, "props");
      String[] array = new String[]{"verifyServerCertificate", "allowPublicKeyRetrieval", "useSSL", "useUnicode", "characterEncoding", "port"};
      props.ignore(array);
      props.useStandardDataSourcePropertyAppendar(hikariConfig);
    }

    @Override
    public @NotNull String processCommands(@NotNull String commands) {
      Validate.Arg.notNull(commands, "");
      return StringsKt.replace(
          StringsKt.replace(
              StringsKt.replace(
                  StringsKt.replace(StringsKt.replace(commands, "NVARCHAR", "VARCHAR", false), "LONG", "BIGINT", false),
                  "DOUBLE", "DOUBLE PRECISION", false
              ), "STRICT", "", false
          ), '`', '\"', false
      );
    }

    @Override
    public @NotNull String createStatement(@NotNull SQLStatement statement, @NotNull String value) {
      Validate.Arg.notNull(statement, "");
      Validate.Arg.notNull(value, "");
      if (statement instanceof SQLUpsert) {
        String replace$default = StringsKt.replace(
            ((SQLUpsert) statement).getParameters(), '`', this.systemIdentifierEscapeChar(), false);
        StringBuilder append = new StringBuilder("INSERT INTO \"").append(value).append("\" (").append(
            replace$default).append(") VALUES(").append(((SQLUpsert) statement).getPreparedValues()).append(
            ") ON CONFLICT ON CONSTRAINT \"").append(value).append("_pkey\" DO UPDATE SET (").append(
            replace$default).append(") = (");
        List<String> iterable = StringsKt.split(replace$default, new String[]{","}, false, 0);
        Collection<Object> collection = new ArrayList<>(10);
        for (CharSequence o : iterable) {
          collection.add(StringsKt.trim(o).toString());
        }
        return append.append(
            CollectionsKt.joinToString(collection, ", ", "", "", -1, "...", (S) -> "excluded." + S)).append(
            ')').toString();
      }
      if (statement instanceof SQLColumnRemove) {
        return "ALTER TABLE `" + value + "` DROP COLUMN " + ((SQLColumnRemove) statement).getColumnName();
      }
      if (statement instanceof SQLColumnAdd) {
        return "ALTER TABLE `" + value + "` ADD COLUMN " + this.processCommands(
            ((SQLColumnAdd) statement).getColumn().getName()) + ' ' + ((SQLColumnAdd) statement).getColumn().getType() + ' ' + (((SQLColumnAdd) statement).getColumn().getNullable() ? "NULL" : "NOT NULL") + ((((SQLColumnAdd) statement).getDefaultValue() == null) ? "" : (" DEFAULT '" + ((SQLColumnAdd) statement).getDefaultValue() + '\''));
      }
      if (statement instanceof SQLColumnRename) {
        return "ALTER TABLE `" + value + "` RENAME COLUMN " + ((SQLColumnRename) statement).getColumnName() + " TO " + ((SQLColumnRename) statement).getNewColumnName();
      }
      throw new UnsupportedOperationException(statement.toString());
    }
  },
  JSON(0, new Dependency[0], "", '`') {
    @Override
    public @NotNull String processCommands(@NotNull String commands) {
      throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull String createStatement(@NotNull SQLStatement statement, @NotNull String value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
      throw new UnsupportedOperationException();
    }
  },
  YAML(0, new Dependency[0], "", '`') {
    @Override
    public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
      throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull String processCommands(@NotNull String commands) {
      throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull String createStatement(@NotNull SQLStatement statement, @NotNull String value) {
      throw new UnsupportedOperationException();
    }
  },
  MongoDB(
      27017,
      new Dependency[]{Dependency.SLF4J_SIMPLE, Dependency.MONGODB_DRIVER_SYNC, Dependency.MONGODB_DRIVER_BSON, Dependency.MONGODB_DRIVER_CORE},
      "", '`'
  ) {
    @Override
    public void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props) {
      throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull String processCommands(@NotNull String commands) {
      throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull String createStatement(@NotNull SQLStatement statement, @NotNull String value) {
      throw new UnsupportedOperationException();
    }
  };

  private final int defaultPort;
  private final @NotNull Dependency @NotNull [] dependencies;
  private final @NotNull String dataSourceClassName;
  private final char systemIdentifierEscapeChar;

  public static final @NotNull Pattern SQLITE_TEXT_AFFINITY = Pattern.compile("(N)?(VAR)?CHAR\\(\\d+\\)");
  public static final @NotNull Pattern SQLITE_BLOB_AFFINITY = Pattern.compile("BINARY\\(\\d+\\)");

  DatabaseType(
      int defaultPort,
      @NotNull Dependency @NotNull [] dependencies,
      @NotNull String dataSourceClassName,
      char systemIdentifierEscapeChar
  ) {
    this.defaultPort = defaultPort;
    this.dependencies = dependencies;
    this.dataSourceClassName = dataSourceClassName;
    this.systemIdentifierEscapeChar = systemIdentifierEscapeChar;
  }

  public int getDefaultPort() {
    return this.defaultPort;
  }

  public @NotNull Dependency @NotNull [] dependencies() {
    return this.dependencies.clone();
  }

  public @NotNull String dataSourceClassName() {
    return this.dataSourceClassName;
  }

  public char systemIdentifierEscapeChar() {
    return this.systemIdentifierEscapeChar;
  }

  public abstract void applyProperties(@NotNull HikariConfig hikariConfig, @NotNull DatabaseProperties props);

  public abstract @NotNull String processCommands(@NotNull String commands);

  public abstract @NotNull String createStatement(@NotNull SQLStatement statement, @NotNull String value);
}
