package net.aurika.ecliptor.database.sql;

import com.zaxxer.hikari.HikariConfig;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.common.annotations.data.LateInit;
import net.aurika.configuration.accessor.YamlClearlyConfigAccessor;
import net.aurika.configuration.sections.ConfigSection;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.property.BooleanProperty;
import net.aurika.property.IntProperty;
import net.aurika.property.Property;
import net.aurika.util.string.Strings;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;

import java.time.Duration;
import java.util.*;

public class DatabaseProperties {

    private final Property<String> user = Property.property("user");
    private final Property<String> password = Property.property("password");
    private final Property<String> address = Property.property("address");
    private final IntProperty port = Property.intProperty("port");
    private final Property<String> databaseName = Property.property("databaseName");
    private final BooleanProperty useSSL = Property.booleanProperty("useSSL", true);
    private final BooleanProperty verifyServerCertificate = Property.booleanProperty("verifyServerCertificate", true);
    private final BooleanProperty allowPublicKeyRetrieval = Property.booleanProperty("allowPublicKeyRetrieval", false);
    private final long socketTimeout = Duration.ofSeconds(30L).toMillis();
    private final Map<String, Object> others = new HashMap<>();
    private final Set<String> ignoredProperties = new HashSet<>();

    private static final @NotNull DatabaseProperties h = new DatabaseProperties();
    private static boolean i;

    public DatabaseProperties() {
    }

    public @NotNull String user() {
        return user.get();
    }

    public void user(@NotNull String user) {
        Validate.Arg.notNull(user, "user");
        this.user.set(user);
    }

    public @NotNull String password() {
        return this.password.get();
    }

    public void password(@NotNull String password) {
        Validate.Arg.notNull(password, "password");
        this.password.set(password);
    }

    public @NotNull String address() {
        return this.address.get();
    }

    public void address(@NotNull String address) {
        Validate.Arg.notNull(address, "address");
        this.address.set(address);
    }

    public int port() {
        return this.port.get();
    }

    public void port(int port) {
        this.port.set(port);
    }

    public @NotNull String databaseName() {
        return this.databaseName.get();
    }

    public void databaseName(@NotNull String databaseName) {
        Validate.Arg.notNull(databaseName, "databaseName");
        this.databaseName.set(databaseName);
    }

    public boolean useSSL() {
        return this.useSSL.get();
    }

    public void useSSL(boolean useSSL) {
        this.useSSL.set(useSSL);
    }

    public boolean verifyServerCertificate() {
        return this.verifyServerCertificate.get();
    }

    public void verifyServerCertificate(boolean verifyServerCertificate) {
        this.verifyServerCertificate.set(verifyServerCertificate);
    }

    public boolean allowPublicKeyRetrieval() {
        return this.allowPublicKeyRetrieval.get();
    }

    public void allowPublicKeyRetrieval(boolean allowPublicKeyRetrieval) {
        this.allowPublicKeyRetrieval.set(allowPublicKeyRetrieval);
    }

    public long socketTimeout() {
        return this.socketTimeout;
    }

    public @NotNull Map<String, Object> others() {
        return this.others;
    }

    public @NotNull Set<String> ignoredProperties() {
        return this.ignoredProperties;
    }

    public boolean ignore(@NotNull String @NotNull [] ignores) {
        Validate.Arg.notNull(ignores, "ignores");
        return this.ignoredProperties.addAll(List.of(ignores));
    }

    public void add(@NotNull String property, @NotNull Object value) {
        Validate.Arg.notNull(property, "property");
        Validate.Arg.notNull(value, "value");
        this.others.put(property, value);
    }

    public void useStandardDataSourcePropertyAppendar(@NotNull HikariConfig hikariConfig) {
        Validate.Arg.notNull(hikariConfig, "hikariConfig");
        a(this, hikariConfig, "user", this.user());
        a(this, hikariConfig, "password", this.password());
        a(this, hikariConfig, "serverName", this.address());
        a(this, hikariConfig, "port", this.port());
        a(this, hikariConfig, "portNumber", this.port());
        a(this, hikariConfig, "databaseName", this.databaseName());
        a(this, hikariConfig, "useSSL", this.useSSL());
        a(this, hikariConfig, "verifyServerCertificate", this.verifyServerCertificate());
        a(this, hikariConfig, "allowPublicKeyRetrieval", this.allowPublicKeyRetrieval());
        a(this, hikariConfig, "socketTimeout", this.socketTimeout());

        for (Map.Entry<String, Object> other : this.others.entrySet()) {
            a(this, hikariConfig, other.getKey(), other.getValue());
        }
    }

    public @NotNull Properties asJavaProperties() {
        Properties props = new Properties();
        for (Map.Entry<String, Object> entry : this.others.entrySet()) {
            props.setProperty(entry.getKey(), entry.getValue().toString());
        }
        return props;
    }

    private static void a(@NotNull DatabaseProperties var0, @NotNull HikariConfig hikariConfig, @NotNull String propertyName, Object value) {
        if (!var0.ignoredProperties.contains(propertyName)) {
            hikariConfig.addDataSourceProperty(propertyName, value);
        }
    }

    public static @NotNull DatabaseProperties defaults(@NotNull DatabaseType databaseType) {  // TODO 迁移
        Validate.Arg.notNull(databaseType, "databaseType");
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
                var2.address(var3);
                var2.port(var9);
                var11 = var2;
                String var10001 = AuspiceGlobalConfig.DATABASE_USERNAME.getManager().getString(new String[0]);
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                var2.user(var10001);
                var10001 = AuspiceGlobalConfig.DATABASE_PASSWORD.getManager().getString(new String[0]);
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                var2.password(var10001);
                var10001 = AuspiceGlobalConfig.DATABASE_DATABASE.getManager().getString(new String[0]);
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                var2.databaseName(var10001);
                var2.useSSL(AuspiceGlobalConfig.DATABASE_SSL_ENABLED.getManager().getBoolean(new String[0]));
                var2.verifyServerCertificate(AuspiceGlobalConfig.DATABASE_SSL_VERIFY_SERVER_CERTIFICATE.getManager().getBoolean(new String[0]));
                var2.allowPublicKeyRetrieval(AuspiceGlobalConfig.DATABASE_SSL_ALLOW_PUBLIC_KEY_RETRIEVAL.getManager().getBoolean(new String[0]));
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
                    Map<String, Object> var6 = var11.others();
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

    public static class Builder {
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

        public Builder() {

        }
    }
}
