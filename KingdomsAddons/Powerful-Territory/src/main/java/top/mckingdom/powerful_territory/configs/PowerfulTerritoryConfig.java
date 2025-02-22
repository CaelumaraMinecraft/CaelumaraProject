package top.mckingdom.powerful_territory.configs;

import org.kingdoms.config.accessor.EnumConfig;
import org.kingdoms.config.accessor.KeyedConfigAccessor;
import org.kingdoms.config.implementation.KeyedYamlConfigAccessor;
import org.kingdoms.config.managers.ConfigManager;
import org.kingdoms.config.managers.ConfigWatcher;
import org.kingdoms.main.Kingdoms;
import org.kingdoms.utils.config.ConfigPath;
import org.kingdoms.utils.config.adapters.YamlResource;
import org.kingdoms.utils.string.Strings;
import top.mckingdom.powerful_territory.PowerfulTerritoryAddon;

public enum PowerfulTerritoryConfig implements EnumConfig {

    METICULOUS_LAND_PROTECTION_RELATION_ATTRIBUTE_ELYTRA(3, 5),
    METICULOUS_LAND_PROTECTION_RELATION_ATTRIBUTE_ENDER_PEARL(3, 5),
    METICULOUS_LAND_PROTECTION_RELATION_ATTRIBUTE_BEACON_EFFECTS(3, 5),

    METICULOUS_LAND_PROTECTION_KINGDOM_PERMISSION_BOAT(3, 5),


    LAND_CONTRACTION_ENABLED(2),
    LAND_CONTRACTION_CLAIMER_HAS_ALL_PERMISSIONS(2),
    LAND_CONTRACTION_ALLOCATE_OTHER_KINGDOMS(2, 3),
    LAND_CONTRACTION_ALLOCATE_DEFAULT_DURATION(2, 3),

    LAND_CONTRACTION_UNCLAIM_KEEP_DATA_DEFAULT(2, 5)

    ;

    public static final YamlResource POWERFUL_TERRITORY =
            new YamlResource(PowerfulTerritoryAddon.get(),
                    Kingdoms.getPath("powerful-territory.yml").toFile(),
                    "powerful-territory.yml").load();

    static {
        ConfigWatcher.register(POWERFUL_TERRITORY.getFile().toPath().getParent(), ConfigWatcher::handleNormalConfigs);
        ConfigManager.registerNormalWatcher("powerful-territory.yml", (event) -> {
            ConfigWatcher.reload(POWERFUL_TERRITORY, "powerful-territory.yml");
        });
    }
    private final ConfigPath option;

    PowerfulTerritoryConfig() {
        this.option = new ConfigPath(Strings.configOption(this));
    }
    PowerfulTerritoryConfig(int... grouped) {
        this.option = new ConfigPath(this.name(), grouped);
    }

    @Override
    public KeyedConfigAccessor getManager() {
        return new KeyedYamlConfigAccessor(POWERFUL_TERRITORY, option);
    }

    public static YamlResource getConfig() {
        return POWERFUL_TERRITORY;
    }

}
