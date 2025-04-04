package net.aurika.configuration.yaml.part.adapter;

import net.aurika.configuration.part.adapter.ConfigSectionAdapter;
import net.aurika.configuration.yaml.part.YamlConfigSection;

public interface YamlConfigSectionAdapter<S extends YamlConfigSection> extends YamlConfigPartAdapter<S>, ConfigSectionAdapter<S> {
}
