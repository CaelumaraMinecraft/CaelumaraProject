package net.aurika.config.yaml.part.adapter;

import net.aurika.config.part.adapter.ConfigScalarAdapter;
import net.aurika.config.yaml.part.YamlConfigScalar;

public interface YamlConfigScalarAdapter<S extends YamlConfigScalar> extends YamlConfigPartAdapter<S>, ConfigScalarAdapter<S> {
}
