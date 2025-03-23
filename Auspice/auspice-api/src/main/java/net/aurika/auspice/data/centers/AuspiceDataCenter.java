package net.aurika.auspice.data.centers;

import net.aurika.ecliptor.centers.AurikaDataCenter;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.base.Database;
import net.aurika.ecliptor.database.flatfile.json.KeyedJsonDatabase;
import net.aurika.ecliptor.database.flatfile.json.SingularJsonDatabase;
import net.aurika.ecliptor.database.flatfile.yaml.KeyedYamlDatabase;
import net.aurika.ecliptor.database.flatfile.yaml.SingularYamlDatabase;
import net.aurika.ecliptor.database.mongo.KeyedMongoDBDatabase;
import net.aurika.ecliptor.database.mongo.SingularMongoDBDatabase;
import net.aurika.ecliptor.database.sql.base.KeyedSQLDatabase;
import net.aurika.ecliptor.database.sql.base.SingularSQLDatabase;
import net.aurika.ecliptor.database.sql.connection.SQLConnectionProvider;
import net.aurika.ecliptor.database.sql.connection.SQLFlatFileConnectionProvider;
import net.aurika.ecliptor.handler.DataHandler;
import net.aurika.ecliptor.handler.KeyedDataHandler;
import net.aurika.ecliptor.handler.SingularDataHandler;
import net.aurika.auspice.data.managers.AuspicePlayerManager;
import net.aurika.ecliptor.api.DataObject;
import net.aurika.common.key.namespace.NSedKey;
import org.jetbrains.annotations.ApiStatus.Internal;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.dependencies.Dependency;
import net.aurika.auspice.user.Auspice;
import net.aurika.auspice.utils.ZeroArrays;
import net.aurika.auspice.utils.logging.AuspiceLogger;
import net.aurika.auspice.utils.string.Strings;

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

public class AuspiceDataCenter extends AurikaDataCenter {

    protected final AuspicePlayerManager auspicePlayerManager;
    protected final Path dataDirectory;
    protected SQLConnectionProvider sqlConnectionProvider;

    @Internal
    public static AuspiceDataCenter createDefault() {
        NSedKey id = Auspice.createKey("MAIN");
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
    protected <T extends DataObject> Database<T> constructDatabase0(String var1, String var2, DataHandler<T> dataHandler) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        Objects.requireNonNull(dataHandler);
        boolean singular = dataHandler instanceof SingularDataHandler;
        boolean var5 = super.hasLoadedInitials;
        super.hasLoadedInitials = true;
        switch (super.databaseType) {
            case JSON:
                if (singular) {
                    return new SingularJsonDatabase<>(this.dataDirectory.resolve(var1 + ".json"), (SingularDataHandler<T>) dataHandler);
                }

                return new KeyedJsonDatabase<>(this.dataDirectory.resolve(var1), (KeyedDataHandler) dataHandler);
            case YAML:
                if (singular) {
                    return new SingularYamlDatabase<>(this.dataDirectory.resolve(var1 + ".yml"), (SingularDataHandler<T>) dataHandler);
                }

                return new KeyedYamlDatabase<>(this.dataDirectory.resolve(var1), (KeyedDataHandler) dataHandler);
            case MongoDB:
                if (!var5) {
                    AuspiceLogger.info("Loading Mongo libraries for " + var1 + " database...");
                    loadDepends(Arrays.asList(DatabaseType.MongoDB.getDependencies()));
                    AuspiceLogger.info("Loaded all libraries.");
                }

                if (singular) {
                    return SingularMongoDBDatabase.withCollection(var2, (SingularDataHandler<T>) dataHandler);
                }

                return KeyedMongoDBDatabase.withCollection(var2, (KeyedDataHandler) dataHandler);
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
                return singular ? new SingularSQLDatabase<>(super.databaseType, var2, (SingularDataHandler<T>) dataHandler, this.sqlConnectionProvider) : new KeyedSQLDatabase<>(super.databaseType, var2, (KeyedDataHandler) dataHandler, this.sqlConnectionProvider);
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
