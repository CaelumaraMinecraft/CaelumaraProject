package top.auspice.config.profile.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.snakeyaml.engine.v2.exceptions.Mark;
import top.auspice.config.adapters.YamlContainer;
import top.auspice.config.adapters.YamlFile;
import top.auspice.config.adapters.YamlResource;
import top.auspice.config.sections.ConfigSection;
import top.auspice.config.yaml.snakeyaml.validation.ValidationFailure;
import top.auspice.config.yaml.snakeyaml.validation.ValidationFailure.Severity;
import top.auspice.config.yaml.snakeyaml.validation.Validator;
import top.auspice.configs.texts.AuspiceLang;
import top.auspice.main.Auspice;
import top.auspice.utils.AuspiceLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ConfigManager {
    private final Auspice a;
    private static final YamlFile GLOBAL_CONFIG = (new YamlFile(new File(Auspice.get().getDataFolder(), "globals.yml"))).load();
    private static final List<YamlResource> RESOURCES = new ArrayList<>(20);

    public static void beforeWrite(YamlContainer var0) {
        ConfigWatcher.beforeWrite(var0);
    }

    public static List<YamlResource> getConfigs() {
        return RESOURCES;
    }

    public static void registerAsMainConfig(YamlResource var0) {
        RESOURCES.add(var0);
    }

    public void setupWatchService() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Auspice.get(), ConfigWatcher::setupWatchService, 20L);
    }

//    public static void addAllConfigs() {
//        RESOURCES.addAll(Arrays.asList(KingdomsConfig.MAIN, KingdomsConfig.RANKS, KingdomsConfig.CLAIMS, KingdomsConfig.RELATIONS, KingdomsConfig.STRUCTURES, KingdomsConfig.TURRETS, KingdomsConfig.POWERS, KingdomsConfig.RESOURCE_POINTS, KingdomsConfig.PROTECTION_SIGNS, KingdomsConfig.INVASIONS, KingdomsConfig.CHAMPION_UPGRADES, KingdomsConfig.MAP, KingdomsConfig.MISC_UPGRADE, KingdomsConfig.CHAT));
//    }

    public static void addConfig(YamlResource var0) {
        RESOURCES.add(var0);
    }

    public static void registerNormalWatcher(String var0, FileWatcher var1) {
        ConfigWatcher.NORMAL_WATCHERS.put(var0, var1);
    }

    public void generateSchema() {
        Path var1;
        Path var2 = (var1 = this.a.getDataFolder().toPath()).resolve("schema");

        try {
            Files.createDirectory(var2);
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        YamlResource var4;
        Path var5;
        for (Iterator<YamlResource> var3 = RESOURCES.iterator(); var3.hasNext(); Validator.implicitSchemaGenerator(var4.getDefaults().getRoot(), var5)) {
            var4 = var3.next();
            var5 = var1.relativize(var4.getFile().toPath());
            var5 = var2.resolve(var5);

            try {
                Files.createDirectories(var5.getParent());
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        }

    }

    public static void onDisable() {
        if (ConfigWatcher.WATCH_SERVICE != null) {
            try {
                ConfigWatcher.WATCH_SERVICE.close();
            } catch (IOException var1) {
                AuspiceLogger.error("Failed to close config watchers:");
                var1.printStackTrace();
            }
        }

    }

    static void a(YamlResource yamlResource) {
        warnAboutValidation(yamlResource.getFile().getName(), yamlResource.validate());
    }

    public static void warnAboutValidation(String var0, List<ValidationFailure> errs) {
        if (!errs.isEmpty()) {
            StringBuilder builder = new StringBuilder(errs.size() * 100);
            builder.append(AuspiceLang.PREFIX.parse(new Object[0])).append(ChatColor.RED).append("Error while validating ").append(ChatColor.GOLD).append(var0).append(ChatColor.RED).append(" config:\n");
            int var7 = 0;

            for (ValidationFailure validationFailure : errs) {
                Mark var5 = (validationFailure).getMarker();
                boolean isSeverityErr = validationFailure.getSeverity() == Severity.ERROR;
                builder.append(ChatColor.GRAY).append('[').append(isSeverityErr ? ChatColor.RED : ChatColor.YELLOW).append(isSeverityErr ? "Error" : "Warning").append(ChatColor.GRAY).append("] ").append(isSeverityErr ? ChatColor.RED : ChatColor.YELLOW);
                builder.append(validationFailure.getMessage()).append(" at line ").append(ChatColor.GOLD).append(var5.getLine()).append(ChatColor.DARK_GRAY).append(':').append('\n').append(ChatColor.YELLOW).append(var5.createSnippet(ChatColor.DARK_RED.toString())).append('\n');
                ++var7;
                if (var7 != errs.size()) {
                    builder.append(ChatColor.DARK_GRAY).append("-------------------------------------------------------\n");
                }
            }

            builder.append(ChatColor.GRAY).append("============================================================");
            Bukkit.getConsoleSender().sendMessage(builder.toString());
        }
    }

    public void validateConfigs() {

        for (YamlResource yamlResource : RESOURCES) {
            a(yamlResource);
        }

    }

    public static ConfigSection getGlobals() {
        return GLOBAL_CONFIG.getConfig();
    }

    public static YamlFile getGlobalsAdapter() {
        return GLOBAL_CONFIG;
    }

    public ConfigManager(Auspice var1) {
        this.a = var1;
    }

    public static void updateConfigs() {

        for (YamlResource yamlResource : RESOURCES) {
            yamlResource.update();
        }

    }

    public void createDataFolderIfMissing() {
        try {
            Files.createDirectories(this.a.getDataFolder().toPath());
        } catch (IOException var2) {
            AuspiceLogger.error("Failed to create the plugin's folder:");
            var2.printStackTrace();
        }
    }

    static {
        String[] path = new String[]{"config-migration", "last-fresh-version"};
        if (GLOBAL_CONFIG.getConfig().getString(path) == null) {
            GLOBAL_CONFIG.getConfig().set(path, Auspice.get().getDescription().getVersion());
            GLOBAL_CONFIG.saveConfig();
        }

    }
}

