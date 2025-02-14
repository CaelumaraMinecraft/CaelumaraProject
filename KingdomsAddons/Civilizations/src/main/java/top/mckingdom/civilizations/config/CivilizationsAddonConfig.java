package top.mckingdom.civilizations.config;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.config.accessor.EnumConfig;
import org.kingdoms.config.accessor.KeyedConfigAccessor;
import org.kingdoms.config.implementation.KeyedYamlConfigAccessor;
import org.kingdoms.config.managers.ConfigManager;
import org.kingdoms.config.managers.ConfigWatcher;
import org.kingdoms.libs.snakeyaml.nodes.Node;
import org.kingdoms.libs.xseries.XSound;
import org.kingdoms.main.Kingdoms;
import org.kingdoms.utils.config.ConfigPath;
import org.kingdoms.utils.config.NodeInterpreter;
import org.kingdoms.utils.config.adapters.YamlResource;
import org.kingdoms.utils.string.Strings;
import top.mckingdom.civilizations.CivilizationsAddon;


public enum CivilizationsAddonConfig implements EnumConfig {


    CIVILIZATION_NAME_CASE_SENSITIVE(2),


    POSITION_MAX_LAYERS(1),
    POSITION_MAX_SUBORDINATES(1),

    POSITION_MANAGE_RELATIONSHIP_KINGDOM_NATION(1, 3),
    POSITION_MANAGE_RELATIONSHIP_KINGDOM_KINGDOM(1, 3),
    POSITION_MANAGE_RELATIONSHIP_NATION_NATION(1, 3),
    POSITION_MANAGE_RELATIONSHIP_NATION_KINGDOM(1, 3),



    ;

    public static void init() {

    }

    public final boolean getBoolean() {
        return NodeInterpreter.BOOLEAN.parse(this.getNode());
    }

    public static class Companion{
        public static final YamlResource CIVILIZATION_MAIN =
                new YamlResource(
                        CivilizationsAddon.get(),
                        Kingdoms.getPath("civilizations.yml").toFile(), "civilizations.yml").load();

    }



    static {
        ConfigWatcher.register(Companion. CIVILIZATION_MAIN.getFile().toPath().getParent(), ConfigWatcher::handleNormalConfigs);
        ConfigManager.registerNormalWatcher("Civilizations-Addon", (event) -> {
            ConfigWatcher.reload(Companion. CIVILIZATION_MAIN, "civilizations.yml");
        });
    }

    private final ConfigPath option;

    public final String getString() {
        return NodeInterpreter.STRING.parse(this.getNode());
    }

    public final Node getNode() {
        String[] strings = this.option.build(null, null);
        Node node;
        if ((node = Companion.CIVILIZATION_MAIN.getConfig().findNode(strings)) == null) {
            node = Companion.CIVILIZATION_MAIN.getDefaults().findNode(strings);
        }

        return node;
    }

    CivilizationsAddonConfig() {
        this.option = new ConfigPath(Strings.configOption(this));
    }

    CivilizationsAddonConfig(int... grouped) {
        this.option = new ConfigPath(this.name(), grouped);
    }

    @Contract(pure = true)
    @Override
    public @NotNull KeyedConfigAccessor getManager() {
        return new KeyedYamlConfigAccessor(Companion.CIVILIZATION_MAIN, option);
    }


    public static YamlResource getConfig() {
        return Companion. CIVILIZATION_MAIN;
    }


    public static void errorSound(Player player) {
        XSound.play(KingdomsConfig.ERROR_SOUND.getString(), (var1) -> {
            var1.forPlayers(player);
        });
    }


}
