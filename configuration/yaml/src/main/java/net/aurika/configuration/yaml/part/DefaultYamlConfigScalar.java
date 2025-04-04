package net.aurika.configuration.yaml.part;

import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.nodes.ScalarNode;
import org.snakeyaml.engine.v2.representer.BaseRepresenter;
import org.snakeyaml.engine.v2.representer.StandardRepresenter;

public class DefaultYamlConfigScalar extends DefaultYamlConfigPart<ScalarNode> implements YamlConfigScalar {

  public static final BaseRepresenter REPRESENTER = new StandardRepresenter(DumpSettings.builder().build());

  protected DefaultYamlConfigScalar(@NotNull ScalarNode node) {
    super(node);
  }

  protected DefaultYamlConfigScalar(@NotNull YamlConfigSequence sequenceParent, @NotNull ScalarNode node) {
    super(sequenceParent, node);
  }

  protected DefaultYamlConfigScalar(@NotNull YamlConfigSection sectionParent, @NotNull String name, @NotNull ScalarNode node) {
    super(sectionParent, name, node);
  }

  @Override
  public @NotNull String scalarValue() {
    return node.getValue();
  }

  @Override
  public void scalarValue(@NotNull String scalarValue) {
    ScalarNode scalarNode = (ScalarNode) REPRESENTER.represent(scalarValue);
    changeThisInParent(node, scalarNode);
    this.node = scalarNode;
  }

}
