package net.aurika.configuration.part.visitor;

import net.aurika.configuration.part.ConfigPart;
import net.aurika.configuration.part.ConfigSection;
import net.aurika.configuration.path.ConfigEntry;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConfigVisitor {

  static @Nullable ConfigPart find(@NotNull ConfigPart config, @NotNull ConfigEntry path) {
    Validate.Arg.notNull(config, "config");
    Validate.Arg.notNull(path, "path");
    if (ConfigVisitor.hasConfig(config, path)) {
      return get(config, path);
    } else {
      return null;
    }
  }

  @SuppressWarnings("DataFlowIssue")
  static @NotNull ConfigPart get(@NotNull ConfigPart config, @NotNull ConfigEntry path) {
    Validate.Arg.notNull(config, "config");
    Validate.Arg.notNull(path, "path");
    if (path.isEmpty()) return config;
    for (int i = 0; i < path.length(); i++) {
      String name = path.getSection(i);
      config = ((ConfigSection) config).getSubPart(name);
    }
    return config;
  }

  /**
   * Returns a config part has the low-level part that contains the {@code path}.
   *
   * @param config the config part
   * @param path   the path
   */
  // TODO validate
  static boolean hasConfig(@NotNull ConfigPart config, @NotNull ConfigEntry path) {
    if (path.isEmpty()) return true;
    String name;
    for (int i = 0; i < path.length(); i++) {
      name = path.getSection(i);
      if (config instanceof ConfigSection configSection) {
        if (configSection.hasSubPart(name)) {
          config = configSection.getSubPart(name);
          continue;
        }
      }
      return false;
    }
    return true;
  }

}
