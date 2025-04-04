package net.aurika.configuration.yaml.part.adapter;

import net.aurika.configuration.yaml.part.YamlConfigSection;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DefaultYamlConfigSectionAdapter extends DefaultYamlConfigPartAdapter<YamlConfigSection> implements YamlConfigSectionAdapter<YamlConfigSection> {

  protected DefaultYamlConfigSectionAdapter(@NotNull YamlConfigAdapterSettings settings) {
    super(YamlConfigSection.class, settings);
  }

  @Override
  public @NotNull Map<? extends CharSequence, ?> asMap(@NotNull YamlConfigSection configSection) {
  }

}
