package net.aurika.config.yaml.part.adapter;

import net.aurika.config.part.adapter.ConfigSectionAdapter;
import net.aurika.config.yaml.part.YamlConfigSection;

public interface YamlConfigSectionAdapter<S extends YamlConfigSection> extends YamlConfigPartAdapter<S>, ConfigSectionAdapter<S> {
}
