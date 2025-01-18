package top.auspice.data.database.sql.connection;

import com.zaxxer.hikari.HikariDataSource;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.sql.DatabaseProperties;
import top.auspice.utils.AuspiceLogger;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

public class SQLHikariConnectionProvider extends SQLConnectionProvider {
    @NotNull
    private final HikariDataSource a;

    public SQLHikariConnectionProvider(@NotNull DatabaseType var1, @NotNull HikariDataSource var2) {
        super(var1);
        Intrinsics.checkNotNullParameter(var2, "");
        this.a = var2;
        this.a.setDataSourceClassName(var1.getDataSourceClassName());
        var1.applyProperties(this.a, DatabaseProperties.defaults(var1));
        HikariDataSource var3 = this.a;
        if (AuspiceLogger.isDebugging()) {
            var3.setLeakDetectionThreshold(Duration.ofSeconds(30L).toMillis());
        }

        var3.setPoolName("auspice-hikari");
        var3.setMaximumPoolSize(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_SIZE_MAX.getInt());
        var3.setMinimumIdle(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_MINIMUM_IDLE.getInt());
        var3.setMaxLifetime(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_MAXIMUM_LIFETIME.getInt());
        var3.setKeepaliveTime(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_KEEPALIVE_TIME.getInt());
        var3.setConnectionTimeout(AuspiceGlobalConfig.DATABASE_POOL_SETTINGS_CONNECTION_TIMEOUT.getInt());
    }

    @NotNull
    public Connection getConnection() {
        Connection var1;
        try {
            var1 = this.a.getConnection();
        } catch (SQLException var2) {
            throw new IllegalStateException("Error while attempting to get connection from: " + this.getDatabaseType() + "::" + this.a, var2);
        }

        if (var1 == null) {
            String var3 = "Unable to get a connection from the pool from " + this.getDatabaseType() + "::" + this.a;
            throw new IllegalStateException(var3);
        } else {
            return var1;
        }
    }

    public void connect() {
        ((SQLConnectionProvider) this).getConnection().close();
    }

    public void close() {
        this.a.close();
    }
}
