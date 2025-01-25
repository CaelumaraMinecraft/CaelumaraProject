package net.aurika.data.database.sql;

import com.zaxxer.hikari.HikariConfig;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import net.aurika.annotations.data.LateInit;
import net.aurika.config.accessor.YamlClearlyConfigAccessor;
import net.aurika.config.sections.ConfigSection;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.data.database.DatabaseType;
import top.auspice.utils.string.Strings;

import java.time.Duration;
import java.util.*;

public class DatabaseProperties {

    public @LateInit String user;
    public @LateInit String password;
    public @LateInit String address;
    private int port;
    public @LateInit String databaseName;
    private boolean useSSL = true;
    private boolean verifyServerCertificate = true;
    private boolean allowPublicKeyRetrieval;
    private final long socketTimeout = Duration.ofSeconds(30L).toMillis();
    private final @NotNull Map<String, Object> others = new HashMap<>();
    private final @NotNull Set<String> ignoredProperties = new HashSet<>();

    private static final @NotNull DatabaseProperties h = new DatabaseProperties();
    private static boolean i;

    public DatabaseProperties() {
    }

    public @NotNull String getUser() {
        String var10000 = this.user;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("");
            return null;
        }
    }

    public void setUser(@NotNull String user) {
        Objects.requireNonNull(user, "user");
        this.user = user;
    }

    public @NotNull String getPassword() {
        String var10000 = this.password;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            return null;
        }
    }

    public void setPassword(@NotNull String password) {
        Objects.requireNonNull(password, "password");
        this.password = password;
    }

    public @NotNull String getAddress() {
        String var10000 = this.address;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("");
            return null;
        }
    }

    public void setAddress(@NotNull String address) {
        Objects.requireNonNull(address, "address");
        this.address = address;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public @NotNull String getDatabaseName() {
        String var10000 = this.databaseName;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("databaseName");
            return null;
        }
    }

    public void setDatabaseName(@NotNull String databaseName) {
        Objects.requireNonNull(databaseName, "databaseName");
        this.databaseName = databaseName;
    }

    public boolean getUseSSL() {
        return this.useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public boolean getVerifyServerCertificate() {
        return this.verifyServerCertificate;
    }

    public void setVerifyServerCertificate(boolean verifyServerCertificate) {
        this.verifyServerCertificate = verifyServerCertificate;
    }

    public boolean getAllowPublicKeyRetrieval() {
        return this.allowPublicKeyRetrieval;
    }

    public void setAllowPublicKeyRetrieval(boolean allowPublicKeyRetrieval) {
        this.allowPublicKeyRetrieval = allowPublicKeyRetrieval;
    }

    public long getSocketTimeout() {
        return this.socketTimeout;
    }

    public @NotNull Map<String, Object> getOthers() {
        return this.others;
    }

    public @NotNull Set<String> getIgnoredProperties() {
        return this.ignoredProperties;
    }

    public boolean ignore(@NotNull String @NotNull [] ignoredProperties) {
        Objects.requireNonNull(ignoredProperties, "ignoredProperties");
        return this.ignoredProperties.addAll(List.of(ignoredProperties));
    }

    public void add(@NotNull String property, @NotNull Object value) {
        Objects.requireNonNull(property, "property");
        Objects.requireNonNull(value, "value");
        this.others.put(property, value);
    }

    public void useStandardDataSourcePropertyAppendar(@NotNull HikariConfig var1) {
        Objects.requireNonNull(var1, "");
        a(this, var1, "user", this.getUser());
        a(this, var1, "password", this.getPassword());
        a(this, var1, "serverName", this.getAddress());
        a(this, var1, "port", this.port);
        a(this, var1, "portNumber", this.port);
        a(this, var1, "databaseName", this.getDatabaseName());
        a(this, var1, "useSSL", this.useSSL);
        a(this, var1, "verifyServerCertificate", this.verifyServerCertificate);
        a(this, var1, "allowPublicKeyRetrieval", this.allowPublicKeyRetrieval);
        a(this, var1, "socketTimeout", this.socketTimeout);

        for (Map.Entry<String, Object> var3 : this.others.entrySet()) {
            a(this, var1, var3.getKey(), var3.getValue());
        }
    }

    @NotNull
    public Properties asJavaProperties() {
        Properties properties = new Properties();
        for (Map.Entry<String, Object> entry : this.others.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getValue().toString());
        }
        return properties;
    }

    private static void a(DatabaseProperties var0, HikariConfig var1, String var2, Object var3) {
        if (!var0.ignoredProperties.contains(var2)) {
            var1.addDataSourceProperty(var2, var3);
        }
    }

    @NotNull
    public static DatabaseProperties defaults(@NotNull DatabaseType databaseType) {  // TODO 迁移
        Objects.requireNonNull(databaseType, "databaseType");
        if (DatabaseProperties.i) {
            return DatabaseProperties.h;
        } else {
            DatabaseProperties var2 = DatabaseProperties.h;
            String var3 = AuspiceGlobalConfig.DATABASE_ADDRESS.getManager().getString(new String[0]);
            int var9 = databaseType.getDefaultPort();
            List<String> var10000 = Strings.split(var3, ':', false);
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            Object var15;
            if (var10000.size() == 2) {
                var3 = var10000.get(0);
                var15 = var10000.get(1);
                Intrinsics.checkNotNullExpressionValue(var15, "");
                var9 = Integer.parseInt((String) var15);
            }

            DatabaseProperties var11;
            ConfigSection var17;
            label28:
            {
                Pair<String, Integer> var10 = TuplesKt.to(var3, var9);
                var3 = var10.component1();
                var9 = var10.component2();
                var2.setAddress(var3);
                var2.setPort(var9);
                var11 = var2;
                String var10001 = AuspiceGlobalConfig.DATABASE_USERNAME.getManager().getString(new String[0]);
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                var2.setUser(var10001);
                var10001 = AuspiceGlobalConfig.DATABASE_PASSWORD.getManager().getString(new String[0]);
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                var2.setPassword(var10001);
                var10001 = AuspiceGlobalConfig.DATABASE_DATABASE.getManager().getString(new String[0]);
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                var2.setDatabaseName(var10001);
                var2.setUseSSL(AuspiceGlobalConfig.DATABASE_SSL_ENABLED.getManager().getBoolean(new String[0]));
                var2.setVerifyServerCertificate(AuspiceGlobalConfig.DATABASE_SSL_VERIFY_SERVER_CERTIFICATE.getManager().getBoolean(new String[0]));
                var2.setAllowPublicKeyRetrieval(AuspiceGlobalConfig.DATABASE_SSL_ALLOW_PUBLIC_KEY_RETRIEVAL.getManager().getBoolean(new String[0]));
                YamlClearlyConfigAccessor var16 = AuspiceGlobalConfig.DATABASE_PROPERTIES.getManager().getSection();
                if (var16 != null) {
                    var16 = var16.noDefault();
                    if (var16 != null) {
                        var17 = var16.getSection();
                        break label28;
                    }
                }

                var17 = null;
            }

            ConfigSection var12 = var17;
            if (var17 != null) {

                for (String var5 : var12.getKeys()) {
                    Map<String, Object> var6 = var11.getOthers();
                    Intrinsics.checkNotNull(var5);
                    String[] var8 = new String[1];
                    var8[0] = var5;
                    var15 = var12.getParsed(var8);  //TODO
                    Intrinsics.checkNotNull(var15);
                    Object var14 = var15;
                    var6.put(var5, var14);
                }
            }

            DatabaseProperties.i = true;
            return var2;
        }
    }
}
