package net.aurika.config.yaml.part.adapter;

import net.aurika.config.part.adapter.AbstractConfigPartAdapter;
import net.aurika.config.yaml.part.YamlConfigPart;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class DefaultYamlConfigPartAdapter<P extends YamlConfigPart> extends AbstractConfigPartAdapter<P> implements YamlConfigPartAdapter<P> {

  protected final @NotNull YamlConfigAdapterSettings settings;

  protected DefaultYamlConfigPartAdapter(@NotNull Class<P> targetType, @NotNull YamlConfigAdapterSettings settings) {
    super(targetType);
    Validate.Arg.notNull(settings, "settings");
    this.settings = settings;
  }

}
