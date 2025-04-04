package net.aurika.configuration.yaml.part.adapter;

import net.aurika.configuration.yaml.part.YamlConfigSequence;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

import java.util.Collection;

public class DefaultYamlConfigSequenceAdapter extends DefaultYamlConfigPartAdapter<YamlConfigSequence> implements YamlConfigSequenceAdapter<YamlConfigSequence> {

  protected DefaultYamlConfigSequenceAdapter(@NotNull YamlConfigAdapterSettings settings) {
    super(YamlConfigSequence.class, settings);
  }

  @Override
  public @NotNull Collection<?> asCollection(@NotNull YamlConfigSequence configSequence) {
    @NotNull SequenceNode sequenceNode = configSequence.yamlNode();
  }

}
