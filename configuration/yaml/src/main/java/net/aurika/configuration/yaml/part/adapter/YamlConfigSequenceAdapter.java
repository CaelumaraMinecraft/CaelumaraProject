package net.aurika.configuration.yaml.part.adapter;

import net.aurika.configuration.part.adapter.ConfigSequenceAdapter;
import net.aurika.configuration.yaml.part.YamlConfigSequence;

public interface YamlConfigSequenceAdapter<S extends YamlConfigSequence> extends YamlConfigPartAdapter<S>, ConfigSequenceAdapter<S> {
}
