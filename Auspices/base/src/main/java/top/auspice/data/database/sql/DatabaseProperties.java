package top.auspice.data.database.sql;

import com.zaxxer.hikari.HikariConfig;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.config.accessor.YamlClearlyConfigAccessor;
import top.auspice.config.sections.ConfigSection;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.data.database.DatabaseType;
import top.auspice.utils.string.Strings;

import java.time.Duration;
import java.util.*;

public class DatabaseProperties {
    public String user;
    public String password;
    public String address;
    private int a;
    public String databaseName;
    private boolean b = true;
    private boolean c = true;
    private boolean d;
    private final long e = Duration.ofSeconds(30L).toMillis();
    @NotNull
    private final Map<String, Object> f = new HashMap<>();
    @NotNull
    private final Set<String> g = new HashSet<>();
    @NotNull
    private static final DatabaseProperties h = new DatabaseProperties();
    private static boolean i;

    public DatabaseProperties() {
    }

    @NotNull
    public String getUser() {
        String var10000 = this.user;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("");
            return null;
        }
    }

    public void setUser(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        this.user = var1;
    }

    @NotNull
    public String getPassword() {
        String var10000 = this.password;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("");
            return null;
        }
    }

    public void setPassword(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        this.password = var1;
    }

    @NotNull
    public String getAddress() {
        String var10000 = this.address;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("");
            return null;
        }
    }

    public void setAddress(@NotNull String address) {
        Objects.requireNonNull(address, "");
        this.address = address;
    }

    public int getPort() {
        return this.a;
    }

    public void setPort(int var1) {
        this.a = var1;
    }

    @NotNull
    public String getDatabaseName() {
        String var10000 = this.databaseName;
        if (var10000 != null) {
            return var10000;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("");
            return null;
        }
    }

    public void setDatabaseName(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        this.databaseName = var1;
    }

    public boolean getUseSSL() {
        return this.b;
    }

    public void setUseSSL(boolean var1) {
        this.b = var1;
    }

    public boolean getVerifyServerCertificate() {
        return this.c;
    }

    public void setVerifyServerCertificate(boolean var1) {
        this.c = var1;
    }

    public boolean getAllowPublicKeyRetrieval() {
        return this.d;
    }

    public void setAllowPublicKeyRetrieval(boolean var1) {
        this.d = var1;
    }

    public long getSocketTimeout() {
        return this.e;
    }

    @NotNull
    public Map<String, Object> getOthers() {
        return this.f;
    }

    @NotNull
    public Set<String> getIgnoredProperties() {
        return this.g;
    }

    public boolean ignore(@NotNull String[] var1) {
        Objects.requireNonNull(var1);
        return CollectionsKt.addAll(this.g, var1);
    }

    public void add(@NotNull String var1, @NotNull Object var2) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        this.f.put(var1, var2);
    }

    public void useStandardDataSourcePropertyAppendar(@NotNull HikariConfig var1) {
        Objects.requireNonNull(var1, "");
        a(this, var1, "user", this.getUser());
        a(this, var1, "password", this.getPassword());
        a(this, var1, "serverName", this.getAddress());
        a(this, var1, "port", this.a);
        a(this, var1, "portNumber", this.a);
        a(this, var1, "databaseName", this.getDatabaseName());
        a(this, var1, "useSSL", this.b);
        a(this, var1, "verifyServerCertificate", this.c);
        a(this, var1, "allowPublicKeyRetrieval", this.d);
        a(this, var1, "socketTimeout", this.e);

        for (Map.Entry<String, Object> var3 : this.f.entrySet()) {
            a(this, var1, var3.getKey(), var3.getValue());
        }

    }

    @NotNull
    public Properties asJavaProperties() {
        Properties var1 = new Properties();
        Map var10000 = this.f;
        return var1;
    }

    private static void a(DatabaseProperties var0, HikariConfig var1, String var2, Object var3) {
        if (!var0.g.contains(var2)) {
            var1.addDataSourceProperty(var2, var3);
        }
    }

    @NotNull
    public static DatabaseProperties defaults(@NotNull DatabaseType var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        if (DatabaseProperties.i) {
            return DatabaseProperties.h;
        } else {
            DatabaseProperties var2 = DatabaseProperties.h;
            String var3 = AuspiceGlobalConfig.DATABASE_ADDRESS.getManager().getString();
            int var9 = var1.getDefaultPort();
            List var10000 = Strings.split(var3, ':', false);
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            List var4 = var10000;
            Object var15;
            if (var10000.size() == 2) {
                var3 = (String) var4.get(0);
                var15 = var4.get(1);
                Intrinsics.checkNotNullExpressionValue(var15, "");
                var9 = Integer.parseInt((String) var15);
            }

            DatabaseProperties var11;
            ConfigSection var17;
            label28:
            {
                Pair var10;
                var3 = (String) (var10 = TuplesKt.to(var3, var9)).component1();
                var9 = ((Number) var10.component2()).intValue();
                var2.setAddress(var3);
                var2.setPort(var9);
                var11 = var2;
                String var10001 = AuspiceGlobalConfig.DATABASE_USERNAME.getManager().getString();
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                var2.setUser(var10001);
                var10001 = AuspiceGlobalConfig.DATABASE_PASSWORD.getManager().getString();
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                var2.setPassword(var10001);
                var10001 = AuspiceGlobalConfig.DATABASE_DATABASE.getManager().getString();
                Intrinsics.checkNotNullExpressionValue(var10001, "");
                var2.setDatabaseName(var10001);
                var2.setUseSSL(AuspiceGlobalConfig.DATABASE_SSL_ENABLED.getManager().getBoolean());
                var2.setVerifyServerCertificate(AuspiceGlobalConfig.DATABASE_SSL_VERIFY_SERVER_CERTIFICATE.getManager().getBoolean());
                var2.setAllowPublicKeyRetrieval(AuspiceGlobalConfig.DATABASE_SSL_ALLOW_PUBLIC_KEY_RETRIEVAL.getManager().getBoolean());
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
