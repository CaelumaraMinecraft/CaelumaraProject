package net.aurika.wands.configs;

import net.aurika.auspice.config.accessor.UndefinedPathConfigAccessor;
import net.aurika.auspice.config.accessor.YamlUndefinedPathConfigAccessor;
import net.aurika.auspice.config.adapters.YamlResource;
import net.aurika.auspice.config.path.ConfigPath;
import net.aurika.auspice.config.yaml.importers.YamlGlobalImporter;
import net.aurika.auspice.configs.globalconfig.accessor.EnumGlobalConfig;
import net.aurika.wands.main.WandsCraftsmanship;

import java.io.File;

public enum WandsGlobalConfig implements EnumGlobalConfig {

    SPELL_SHOOT_COUNTS(1),

    ;

    private final ConfigPath option;

    WandsGlobalConfig() {
        this.option = ConfigPath.fromEnum(this);
    }

    WandsGlobalConfig(String var3) {
        this.option = new ConfigPath(var3);
    }

    WandsGlobalConfig(int... group) {
        this.option = new ConfigPath(this.name(), group);
    }


    public static final YamlResource MAIN = a("wand-craftsmanship");

    private static YamlResource a(String profileName) {
        File folder = WandsCraftsmanship.get().getDataFolder();
        return (new YamlResource(new File(folder, profileName + ".yml"))).setImporter(YamlGlobalImporter.INSTANCE).load().importDeclarations();
    }

    public UndefinedPathConfigAccessor getManager() {
        return new YamlUndefinedPathConfigAccessor(MAIN, this.option);
    }


}
