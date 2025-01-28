package top.auspice.data;

import net.aurika.data.centers.DataCenter;
import net.aurika.data.database.DatabaseType;
import net.aurika.data.database.base.Database;
import net.aurika.data.database.flatfile.json.KeyedJsonDatabase;
import net.aurika.data.database.flatfile.json.SingularJsonDatabase;
import net.aurika.data.database.flatfile.yaml.KeyedYamlDatabase;
import net.aurika.data.database.flatfile.yaml.SingularYamlDatabase;
import net.aurika.data.database.mongo.KeyedMongoDBDatabase;
import net.aurika.data.database.mongo.SingularMongoDBDatabase;
import net.aurika.data.database.sql.base.KeyedSQLDatabase;
import net.aurika.data.database.sql.base.SingularSQLDatabase;
import net.aurika.data.database.sql.connection.SQLConnectionProvider;
import net.aurika.data.database.sql.connection.SQLFlatFileConnectionProvider;
import net.aurika.data.handlers.abstraction.DataHandler;
import net.aurika.data.handlers.abstraction.KeyedDataHandler;
import net.aurika.data.handlers.abstraction.SingularDataHandler;
import net.aurika.data.managers.AuspicePlayerManager;
import net.aurika.data.object.DataObject;
import net.aurika.namespace.NSedKey;
import org.jetbrains.annotations.ApiStatus.Internal;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.dependencies.Dependency;
import top.auspice.main.Auspice;
import top.auspice.utils.ZeroArrays;
import top.auspice.utils.logging.AuspiceLogger;
import top.auspice.utils.string.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AuspiceDataCenter extends DataCenter {

    protected final AuspicePlayerManager auspicePlayerManager;
    protected final Path dataDirectory;
    protected SQLConnectionProvider sqlConnectionProvider;

    @Internal
    public static AuspiceDataCenter createDefault() {
        NSedKey id = Auspice.namespacedKey("MAIN");
        DatabaseType var10003 = getDefaultDatabaseType();
        Path var0 = Auspice.get().getDataFolder().toPath();
        if (Boolean.TRUE.equals(AuspiceGlobalConfig.DATABASE_USE_DATA_FOLDER.getManager().noDefault().getBoolean(ZeroArrays.STRING))) {
            var0 = Auspice.get().getPath("data");
        }

        return new AuspiceDataCenter(id, var10003, var0, AuspiceGlobalConfig.DATABASE_AUTO_SAVE_INTERVAL.getTime(), AuspiceGlobalConfig.DATABASE_LOAD_ALL_DATA_ON_STARTUP.getBoolean(), false, AuspiceGlobalConfig.DATABASE_SMART_SAVE.getBoolean());
    }

    @Internal
    public AuspiceDataCenter(NSedKey id,
                             DatabaseType databaseType,
                             Path dataDirectory,
                             Duration autoSaveInterval,
                             boolean isCacheStatic,
                             boolean isTemporary,
                             boolean isSmartSaving
    ) {
        super(id, databaseType, autoSaveInterval, isCacheStatic, isTemporary, isSmartSaving);
        this.dataDirectory = dataDirectory;
        this.auspicePlayerManager = this.register(new AuspicePlayerManager(this));
    }

    @Override
    protected <T extends DataObject> Database<T> constructDatabase0(String var1, String var2, DataHandler<T> var3) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        Objects.requireNonNull(var3);
        boolean singular = var3 instanceof SingularDataHandler;
        boolean var5 = super.hasLoadedInitials;
        super.hasLoadedInitials = true;
        switch (super.databaseType) {
            case JSON:
                if (singular) {
                    return new SingularJsonDatabase<>(this.dataDirectory.resolve(var1 + ".json"), (SingularDataHandler<T>) var3);
                }

                return new KeyedJsonDatabase<>(this.dataDirectory.resolve(var1), (KeyedDataHandler) var3);
            case YAML:
                if (singular) {
                    return new SingularYamlDatabase<>(this.dataDirectory.resolve(var1 + ".yml"), (SingularDataHandler<T>) var3);
                }

                return new KeyedYamlDatabase<>(this.dataDirectory.resolve(var1), (KeyedDataHandler) var3);
            case MongoDB:
                if (!var5) {
                    AuspiceLogger.info("Loading Mongo libraries for " + var1 + " database...");
                    loadDepends(Arrays.asList(DatabaseType.MongoDB.getDependencies()));
                    AuspiceLogger.info("Loaded all libraries.");
                }

                if (singular) {
                    return SingularMongoDBDatabase.withCollection(var2, (SingularDataHandler<T>) var3);
                }

                return KeyedMongoDBDatabase.withCollection(var2, (KeyedDataHandler) var3);
            default:
                if (!var5) {
                    AuspiceLogger.info("Loading SQL libraries for " + var1 + " database...");
                    loadDepends(Arrays.asList(Dependency.HIKARI, Dependency.SLF4J_SIMPLE));
                }

                if (this.sqlConnectionProvider == null) {
                    this.sqlConnectionProvider = SQLConnectionProvider.getProvider(this.dataDirectory, super.databaseType);
                }

                if (!var5) {
                    Path var11;
                    if (super.databaseType == DatabaseType.H2 && this.sqlConnectionProvider instanceof SQLFlatFileConnectionProvider && Files.exists(var11 = ((SQLFlatFileConnectionProvider) this.sqlConnectionProvider).getFile(), new LinkOption[0])) {
                        try {
                            BufferedReader var6 = Files.newBufferedReader(var11);

                            try {
                                if (var6.readLine().replace(" ", "").contains("format:2")) {
                                    Dependency.H2_DRIVER.setDefaultVersion("2.1.214");
                                }
                            } catch (Throwable var9) {
                                if (var6 != null) {
                                    try {
                                        var6.close();
                                    } catch (Throwable var8) {
                                        var9.addSuppressed(var8);
                                    }
                                }

                                throw var9;
                            }

                            if (var6 != null) {
                                var6.close();
                            }
                        } catch (IOException var10) {
                            throw new RuntimeException("Failed to read H2 database file: " + var11, var10);
                        }
                    }

                    loadDepends(Arrays.asList(super.databaseType.getDependencies()));
                    AuspiceLogger.info("Loaded all libraries.");
                }

                if (!var5) {
                    this.sqlConnectionProvider.connect();
                    this.sqlConnectionProvider.runSchema();
                }

                var2 = SQLConnectionProvider.TABLE_PREFIX + var2;
                return singular ? new SingularSQLDatabase<>(super.databaseType, var2, (SingularDataHandler<T>) var3, this.sqlConnectionProvider) : new KeyedSQLDatabase<>(super.databaseType, var2, (KeyedDataHandler) var3, this.sqlConnectionProvider);
        }
    }

    public static DatabaseType getDefaultDatabaseType() {
        String var0 = Strings.deleteWhitespace(AuspiceGlobalConfig.DATABASE_METHOD.getString()).toLowerCase(Locale.ENGLISH);
        DatabaseType configType = Arrays.stream(DatabaseType.values()).filter((var1x) -> var1x.name().toLowerCase().equals(var0)).findFirst().orElse(null);
        if (configType == null) {
            throw new IllegalArgumentException("Unknown database type: " + var0);
        } else {
            return configType;
        }
    }

    public static AuspiceDataCenter get() {
        return Auspice.get().getDataCenter();
    }

    public AuspicePlayerManager getAuspicePlayerManager() {
        return this.auspicePlayerManager;
    }

    private static void loadDepends(List<Dependency> dependencies) {
        Auspice.get().getDependencyManager().loadDependencies(dependencies);
    }
}
