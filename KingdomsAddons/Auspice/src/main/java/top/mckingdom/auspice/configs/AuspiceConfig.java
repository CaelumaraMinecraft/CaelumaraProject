package top.mckingdom.auspice.configs;

import org.kingdoms.config.accessor.EnumConfig;
import org.kingdoms.config.accessor.KeyedConfigAccessor;
import org.kingdoms.config.implementation.KeyedYamlConfigAccessor;
import org.kingdoms.config.managers.ConfigManager;
import org.kingdoms.config.managers.ConfigWatcher;
import org.kingdoms.main.Kingdoms;
import org.kingdoms.utils.config.ConfigPath;
import org.kingdoms.utils.config.adapters.YamlResource;
import org.kingdoms.utils.string.Strings;
import top.mckingdom.auspice.AuspiceAddon;

public enum AuspiceConfig implements EnumConfig {

    MEMBER_TRANSFER_ENABLED(2),

    CURRENCY_TEST,


    ;

    public static final YamlResource AUSPICE_MAIN =
            new YamlResource(AuspiceAddon.get(),
                    Kingdoms.getPath("auspice-addon.yml").toFile(),
                    "auspice-addon.yml").load();

    static {
        ConfigWatcher.register(AUSPICE_MAIN.getFile().toPath().getParent(), ConfigWatcher::handleNormalConfigs);
        ConfigManager.registerNormalWatcher("auspice-addon.yml", (event) -> {
            ConfigWatcher.reload(AUSPICE_MAIN, "auspice-addon.yml");
        });
    }

    private final ConfigPath option;

    AuspiceConfig() {
        this.option = new ConfigPath(Strings.configOption(this));
    }

    AuspiceConfig(int... grouped) {
        this.option = new ConfigPath(this.name(), grouped);
    }

    @Override
    public KeyedConfigAccessor getManager() {
        return new KeyedYamlConfigAccessor(AUSPICE_MAIN, this.option);
    }

    public static YamlResource getConfig() {
        return AUSPICE_MAIN;
    }
}
