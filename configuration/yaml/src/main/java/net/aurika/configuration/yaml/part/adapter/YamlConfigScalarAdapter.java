package net.aurika.configuration.yaml.part.adapter;

import net.aurika.configuration.part.adapter.ConfigScalarAdapter;
import net.aurika.configuration.yaml.part.YamlConfigScalar;

public interface YamlConfigScalarAdapter<S extends YamlConfigScalar> extends YamlConfigPartAdapter<S>, ConfigScalarAdapter<S> {
}
