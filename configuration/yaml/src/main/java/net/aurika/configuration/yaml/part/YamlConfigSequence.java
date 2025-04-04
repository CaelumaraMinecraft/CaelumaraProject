package net.aurika.configuration.yaml.part;

import net.aurika.configuration.part.ConfigSequence;
import net.aurika.configuration.part.annotation.NotNamed;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

import java.util.List;

public interface YamlConfigSequence extends YamlConfigPart, ConfigSequence {

  @Override
  @NotNull List<@NotNamed ? extends YamlConfigPart> elements();

  @Override
  @NotNull SequenceNode yamlNode();

}
