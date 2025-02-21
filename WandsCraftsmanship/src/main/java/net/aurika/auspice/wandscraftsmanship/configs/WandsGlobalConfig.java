package net.aurika.auspice.wandscraftsmanship.configs;

import top.auspice.config.accessor.UndefinedPathConfigAccessor;
import top.auspice.config.accessor.YamlUndefinedPathConfigAccessor;
import top.auspice.config.adapters.YamlResource;
import top.auspice.config.path.ConfigPath;
import top.auspice.config.yaml.importers.YamlGlobalImporter;
import net.aurika.auspice.configs.globalconfig.accessor.EnumGlobalConfig;
import net.aurika.auspice.wandscraftsmanship.main.WandsCraftsmanship;

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
