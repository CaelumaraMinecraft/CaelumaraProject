package net.aurika.config.yaml.part;

import net.aurika.config.part.annotation.NotNamed;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class DefaultYamlConfigSequence extends DefaultYamlConfigPart<SequenceNode> implements YamlConfigSequence {

  protected final Map<Node, @NotNamed YamlConfigPart> elementPartsStorage = new IdentityHashMap<>();

  protected DefaultYamlConfigSequence(@NotNull SequenceNode node) {
    super(node);
  }

  protected DefaultYamlConfigSequence(@NotNull YamlConfigSequence parentSequence, @NotNull SequenceNode node) {
    super(parentSequence, node);
  }

  protected DefaultYamlConfigSequence(@NotNull YamlConfigSection parentSection, @NotNull String name, @NotNull SequenceNode node) {
    super(parentSection, name, node);
  }

  @Override
  public @NotNull List<@NotNamed ? extends YamlConfigPart> elements() {
  }

}
