package net.aurika.config.profile.managers;

import com.github.benmanes.caffeine.cache.Cache;
import net.aurika.config.adapters.YamlContainer;
import net.aurika.config.adapters.YamlResource;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.configs.texts.MessageHandler;
import top.auspice.main.Auspice;
import top.auspice.scheduler.DelayedTask;
import top.auspice.utils.AuspiceLogger;
import top.auspice.utils.cache.caffeine.CacheHandler;
import top.auspice.utils.cache.caffeine.ExpirableSet;
import top.auspice.utils.cache.caffeine.ExpirationStrategy;

import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import java.util.function.BiConsumer;

public class ConfigWatcher {
    protected static final WatchService WATCH_SERVICE;
    private static final ExpirableSet<String> a = new ExpirableSet<>(ExpirationStrategy.expireAfterCreate(Duration.ofSeconds(3L)));
    private static final Cache<String, DelayedTask> b = CacheHandler.newBuilder().expireAfterWrite(Duration.ofSeconds(5L)).build();
    private static final Map<WatchKey, BiConsumer<Path, WatchEvent.Kind<?>>> c = new IdentityHashMap<>();
    private static final Auspice d;
    private static final Path e;
    private static boolean f;
    protected static final Map<String, FileWatcher> NORMAL_WATCHERS;

    public ConfigWatcher() {
    }

    public static void setAccepting(boolean var0) {
        f = var0;
    }

    public static void reload(YamlResource var0, String var1) {
        MessageHandler.sendConsolePluginMessage("&2Detected changes for&6 " + var1 + "&2, reloading...");
        var0.reload();
        if (AuspiceGlobalConfig.UPDATES_CONFIGS.getBoolean()) {
            var0.update();
        }

        ConfigManager.a(var0);
    }

    public static WatchKey register(Path var0, BiConsumer<Path, WatchEvent.Kind<?>> var1) {
        try {
            WatchKey var2 = var0.register(WATCH_SERVICE, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            c.put(var2, var1);
            return var2;
        } catch (IOException var3) {
            AuspiceLogger.warn("Failed to register config watchers for: " + var0);
            throw new RuntimeException(var3);
        }
    }

    public static void unregister(WatchKey var0) {
        Objects.requireNonNull(var0);
        var0.cancel();
        c.remove(var0);
    }

    public static void registerGUIWatchers(SupportedLanguage var0) {
        Path var1;
        if (Files.exists(var1 = var0.getGUIFolder(), new LinkOption[0])) {
            final BiConsumer<Path, WatchEvent.Kind<?>> var3 = generateGUIHandlerForLang(var0);

            try {
                Files.walkFileTree(var1, new SimpleFileVisitor<Path>() {
                });
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }
    }

    protected static void setupWatchService() {
        if (WATCH_SERVICE != null) {
            Path var0;
            register(var0 = Kingdoms.getFolder(), ConfigWatcher::handleNormalConfigs);
            register(var0.resolve("Turrets"), ConfigWatcher::b);
            register(var0.resolve("Structures"), ConfigWatcher::a);
            register(LanguageManager.LANG_FOLDER, ConfigWatcher::c);
            SupportedLanguage[] var4;
            int var1 = (var4 = SupportedLanguage.VALUES).length;

            for(int var2 = 0; var2 < var1; ++var2) {
                SupportedLanguage var3;
                if ((var3 = var4[var2]).isInstalled()) {
                    registerGUIWatchers(var3);
                }
            }

            Kingdoms.taskScheduler().async().execute(ConfigWatcher::a);
        }
    }

    protected static void beforeWrite(YamlContainer var0) {
        String var1 = a(Auspice.get().getDataFolder().toPath().relativize(var0.getFile().toPath()).toString());
        a.add(var1);
    }

    private static String a(String var0) {
        return var0.substring(0, var0.length() - 4);
    }

    static void a() {
        while(true) {
            WatchKey var0;
            try {
                var0 = WATCH_SERVICE.take();
            } catch (InterruptedException | ClosedWatchServiceException var14) {
                AuspiceLogger.info("Config watcher service has stopped.");
                return;
            }

            List<WatchEvent<?>> var1 = var0.pollEvents();
            if (f) {
                Iterator<WatchEvent<?>> var17 = var1.iterator();

                label90:
                while(true) {
                    while(true) {
                        WatchEvent<?> var2;
                        Path var3;
                        Path var4;
                        Path var5;
                        String var6;
                        boolean var7;
                        while(true) {
                            do {
                                do {
                                    if (!var17.hasNext()) {
                                        break label90;
                                    }
                                } while((var3 = (Path)(var2 = var17.next()).context()).toString().endsWith(".filepart"));

                                var4 = ((Path)var0.watchable()).resolve(var3);
                                var6 = a((var5 = e.relativize(var4)).toString());
                            } while(a.contains(var6));

                            var7 = var2.kind() == StandardWatchEventKinds.ENTRY_DELETE || !Files.exists(var4, new LinkOption[0]);

                            try {
                                if (!Files.isDirectory(var4) && !Files.isHidden(var4)) {
                                    break;
                                }
                            } catch (NoSuchFileException var15) {
                                var7 = true;
                                break;
                            } catch (IOException var16) {
                                break;
                            }
                        }

                        long var9;
                        try {
                            if (var7) {
                                var9 = -100L;
                            } else {
                                var9 = Files.size(var4);
                            }
                        } catch (NoSuchFileException var12) {
                            var9 = -100L;
                            var7 = true;
                        } catch (Throwable var13) {
                            var13.printStackTrace();
                            var9 = -10000000L;
                        }

                        DelayedTask var8;
                        if ((var8 = (DelayedTask)b.getIfPresent(var6)) != null) {
                            var8.cancel();
                        }

                        if (var8 == null && (var7 || var9 > 0L)) {
                            a.add(var6);

                            try {
                                BiConsumer<Path, WatchEvent.Kind<?>> var18 = c.get(var0);
                                Objects.requireNonNull(var18, "Handler for file " + var4 + " (" + var3 + ") is null");
                                var18.accept(var5, var2.kind());
                            } catch (Throwable var11) {
                                AuspiceLogger.error("Failed to handle automatic reload for file: " + var5);
                                var11.printStackTrace();
                            }
                        } else {
                            b.put(var6, Auspice.taskScheduler().async().delayed(Duration.ofSeconds(5L), () -> {
                                try {
                                    b.invalidate(var6);
                                    BiConsumer var7;
                                    Objects.requireNonNull(var7 = c.get(var0), () -> {
                                        return "Handler for file " + var4 + " (" + var3 + ") is null";
                                    });
                                    var7.accept(var5, var2.kind());
                                } catch (Throwable var6x) {
                                    AuspiceLogger.error("Failed to handle FTP automatic reload for file: " + var5);
                                    var6x.printStackTrace();
                                }
                            }));
                        }
                    }
                }
            }

            var0.reset();
        }
    }

    static void a(Path var0, WatchEvent.Kind<?> var1) {
        Path var3;
        String var2 = (var2 = (var3 = d.getDataFolder().toPath()).relativize(var3.resolve("Structures")).relativize(var0).toString()).substring(0, var2.length() - 4);
        StructureStyle var4 = StructureRegistry.get().getStyle(var2);
        MessageHandler.sendConsolePluginMessage("&2Detected changes for structure&8: &9" + var2 + (var4 == null ? " &4which is not a registered structure format, ignoring." : ""));
        if (var4 != null) {
            var4.getConfig().reload();
            StructureRegistry.validate(var2, var4.getConfig());
            var4.loadSettings();
        }

    }

    static void b(Path var0, WatchEvent.Kind<?> var1) {
        Path var3;
        String var2 = (var2 = (var3 = d.getDataFolder().toPath()).relativize(var3.resolve("Turrets")).relativize(var0).toString()).substring(0, var2.length() - 4);
        TurretStyle var4 = TurretRegistry.get().getStyle(var2);
        MessageHandler.sendConsolePluginMessage("&2Detected changes for turret&8: &9" + var2 + (var4 == null ? " &4which is not a registered turret format, ignoring." : ""));
        if (var4 != null) {
            var4.getAdapter().reload();
            TurretRegistry.get();
            TurretRegistry.validate(var2, var4.getAdapter());
            var4.loadSettings();
        }

    }

    static void c(Path var0, WatchEvent.Kind<?> var1) {
        String var2;
        SupportedLanguage var3;
        if ((var3 = LanguageManager.getLanguageOrWarn(var2 = (var2 = var0.getFileName().toString()).substring(0, var2.length() - 4))) != null) {
            if (var3.isInstalled()) {
                MessageHandler.sendConsolePluginMessage("&2Detected changes for language file&8: &9" + var2);
                LanguageManager.load(var3);
            }
        }
    }

    public static BiConsumer<Path, WatchEvent.Kind<?>> generateGUIHandlerForLang(final SupportedLanguage var0) {
        return new BiConsumer<Path, WatchEvent.Kind<?>>() {
            private final Path a = Kingdoms.getFolder().relativize(GUIConfig.getFolder().resolve(var0.getLowerCaseName()));
        };
    }

    public static void handleNormalConfigs(Path var0, WatchEvent.Kind<?> var1) {
        String var2;
        String var3 = (var2 = var0.toString()).toLowerCase(Locale.ENGLISH).replace('\\', '/').substring(0, var2.length() - 4);
        FileWatcher var4;
        if ((var4 = (FileWatcher)NORMAL_WATCHERS.get(var3)) != null) {
            var4.handle(new FileWatchEvent(var0, var1));
        } else {
            switch (var3) {
                case "config":
                    reload(KingdomsConfig.MAIN, var2);
                    StandardKingdomsPlaceholder.init();
                    return;
                case "claims":
                    reload(KingdomsConfig.CLAIMS, var2);
                    return;
                case "invasions":
                    reload(KingdomsConfig.INVASIONS, var2);
                    return;
                case "map":
                    reload(KingdomsConfig.MAP, var2);
                    return;
                case "ranks":
                    reload(KingdomsConfig.RANKS, var2);
                    Rank.init();
                    return;
                case "structures":
                    reload(KingdomsConfig.STRUCTURES, var2);
                    StructureRegistry.get().getStyles().values().forEach((var0x) -> {
                        var0x.getConfig().reload();
                        var0x.loadSettings();
                    });
                    return;
                case "turrets":
                    reload(KingdomsConfig.TURRETS, var2);
                    return;
                case "protection-signs":
                    reload(KingdomsConfig.PROTECTION_SIGNS, var2);
                    return;
                case "relations":
                    reload(KingdomsConfig.RELATIONS, var2);
                    KingdomRelation.init();
                    return;
                case "champion-upgrades":
                    reload(KingdomsConfig.CHAMPION_UPGRADES, var2);
                    return;
                case "misc-upgrades":
                    reload(KingdomsConfig.MISC_UPGRADE, var2);
                    MiscUpgrade.registerAll();
                    return;
                case "chat":
                    reload(KingdomsConfig.CHAT, var2);
                    KingdomsChatChannel.registerChannels();
                    return;
                case "powers":
                    reload(KingdomsConfig.POWERS, var2);
                    Powerup.init();
                    return;
                case "resource-points":
                    reload(KingdomsConfig.RESOURCE_POINTS, var2);
                    ResourcePointManager.loadSettings();
                    return;
                default:
            }
        }
    }

    static {
        e = (d = Auspice.get()).getDataFolder().toPath();
        f = true;
        Path var0 = Auspice.get().getDataFolder().toPath().toAbsolutePath();

        WatchService var2;
        try {
            var2 = var0.getFileSystem().newWatchService();
        } catch (IOException var1) {
            AuspiceLogger.error("Failed to register config file watchers:");
            var1.printStackTrace();
            var2 = null;
        }

        WATCH_SERVICE = var2;
        NORMAL_WATCHERS = new HashMap<>();
    }
}
