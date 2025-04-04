package net.aurika.configuration.yaml.part;

import net.aurika.configuration.part.ConfigScalar;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.ScalarNode;

public interface YamlConfigScalar extends YamlConfigPart, ConfigScalar {

  @Override
  @NotNull ScalarNode yamlNode();

}
