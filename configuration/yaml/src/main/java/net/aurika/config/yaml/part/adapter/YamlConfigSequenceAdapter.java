package net.aurika.config.yaml.part.adapter;

import net.aurika.config.part.adapter.ConfigSequenceAdapter;
import net.aurika.config.yaml.part.YamlConfigSequence;

public interface YamlConfigSequenceAdapter<S extends YamlConfigSequence> extends YamlConfigPartAdapter<S>, ConfigSequenceAdapter<S> {
}
