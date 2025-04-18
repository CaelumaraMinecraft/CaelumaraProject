package net.aurika.ecliptor.database.sql.connection;

import com.zaxxer.hikari.HikariDataSource;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.utils.logging.AuspiceLogger;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.sql.DatabaseProperties;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

public class SQLHikariConnectionProvider extends SQLConnectionProvider {

  private final @NotNull HikariDataSource a;

  public SQLHikariConnectionProvider(@NotNull DatabaseType databaseType, @NotNull HikariDataSource hikariDataSource) {
    super(databaseType);
    Checker.Arg.notNull(hikariDataSource, "");
    this.a = hikariDataSource;
    this.a.setDataSourceClassName(databaseType.dataSourceClassName());
    databaseType.applyProperties(this.a, DatabaseProperties.defaults(databaseType));
    HikariDataSource var3 = this.a;
    if (AuspiceLogger.isDebugging()) {
      var3.setLeakDetectionThreshold(Duration.ofSeconds(30L).toMillis());
    }

    var3.setPoolName("aurika-hikari");
    var3.setMaximumPoolSize(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_SIZE_MAX.getInt());
    var3.setMinimumIdle(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_MINIMUM_IDLE.getInt());
    var3.setMaxLifetime(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_MAXIMUM_LIFETIME.getInt());
    var3.setKeepaliveTime(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_KEEPALIVE_TIME.getInt());
    var3.setConnectionTimeout(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_CONNECTION_TIMEOUT.getInt());
  }

  public @NotNull Connection getConnection() {
    Connection var1;
    try {
      var1 = this.a.getConnection();
    } catch (SQLException var2) {
      throw new IllegalStateException(
          "Error while attempting to get connection from: " + this.getDatabaseType() + "::" + this.a, var2);
    }

    if (var1 == null) {
      String var3 = "Unable to get a connection from the pool from " + this.getDatabaseType() + "::" + this.a;
      throw new IllegalStateException(var3);
    } else {
      return var1;
    }
  }

  public void connect() {
    try {
      this.getConnection().close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void close() {
    this.a.close();
  }

}
