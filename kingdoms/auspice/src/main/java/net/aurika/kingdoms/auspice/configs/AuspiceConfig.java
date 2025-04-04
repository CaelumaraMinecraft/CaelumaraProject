package net.aurika.kingdoms.auspice.configs;

import net.aurika.kingdoms.auspice.AuspiceAddon;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.config.accessor.EnumConfig;
import org.kingdoms.config.accessor.KeyedConfigAccessor;
import org.kingdoms.config.implementation.KeyedYamlConfigAccessor;
import org.kingdoms.config.managers.ConfigManager;
import org.kingdoms.main.Kingdoms;
import org.kingdoms.utils.config.ConfigPath;
import org.kingdoms.utils.config.adapters.YamlResource;
import org.kingdoms.utils.string.Strings;

public enum AuspiceConfig implements EnumConfig {

  MEMBER_TRANSFER_ENABLED(2),

  CURRENCY_TEST,


  ;

  public static final YamlResource AUSPICE =
      new YamlResource(
          AuspiceAddon.get(),
          Kingdoms.getPath("auspice-addon.yml").toFile(),
          "auspice-addon.yml"
      ).load();

  static {
    ConfigManager.registerAsMainConfig(AUSPICE);
    AUSPICE.reloadHandle(() -> {
    });
    ConfigManager.watch(AUSPICE);
  }

  public static void init() { }

  private final @NotNull ConfigPath option;

  AuspiceConfig() {
    this.option = new ConfigPath(Strings.configOption(this));
  }

  AuspiceConfig(int... grouped) {
    this.option = new ConfigPath(this.name(), grouped);
  }

  @Override
  @Contract(" -> new")
  public @NotNull KeyedConfigAccessor getManager() {
    return new KeyedYamlConfigAccessor(AUSPICE, this.option);
  }

  public static YamlResource getConfig() {
    return AUSPICE;
  }
}
