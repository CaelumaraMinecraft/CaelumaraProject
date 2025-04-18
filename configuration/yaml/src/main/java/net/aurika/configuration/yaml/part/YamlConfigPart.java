package net.aurika.configuration.yaml.part;

import net.aurika.common.snakeyaml.node.NodeUtil;
import net.aurika.configuration.part.ConfigPart;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.Node;

public interface YamlConfigPart extends ConfigPart {

  /**
   * Adds the config part into the present parent. If the sub config part has no parent, the method will do nothing.
   *
   * @param subYamlConfig the sub config part, it must be initialized parent
   */
  static void addSubToParent(@NotNull YamlConfigPart subYamlConfig) {
    Validate.Arg.notNull(subYamlConfig, "subYamlConfig");
    Node yamlNode = subYamlConfig.yamlNode();
    if (!subYamlConfig.hasNoParent()) {
      ConfigPart parent = subYamlConfig.parent();
      if (parent instanceof YamlConfigPart yamlParent) {
        if (yamlParent instanceof YamlConfigSection sectionParent) {
          NodeUtil.putNode(sectionParent.yamlNode(), subYamlConfig.name(), yamlNode);
        }
        if (yamlParent instanceof YamlConfigSequence sequenceParent) {
          sequenceParent.yamlNode().getValue().add(yamlNode);
        }
        if (yamlParent instanceof YamlConfigScalar) {
          throw new IllegalStateException("Parent is scalar");
        }
      } else {
        throw new IllegalStateException("Can not sync data to parent config part");
      }
    }
  }

  static void changeSubInParent(
      @NotNull YamlConfigPart parentYamlConfig,
      @NotNull Node fromNode,
      @NotNull Node toNode
  ) {
    Validate.Arg.notNull(parentYamlConfig, "parentYamlConfig");
    Validate.Arg.notNull(fromNode, "fromNode");
    Validate.Arg.notNull(toNode, "toNode");
    if (parentYamlConfig instanceof YamlConfigSection sectionParent) {
      NodeUtil.replaceValue(sectionParent.yamlNode(), fromNode, toNode);
    }
    if (parentYamlConfig instanceof YamlConfigSequence sequenceParent) {
      NodeUtil.replaceElement(sequenceParent.yamlNode(), fromNode, toNode);
    }
    throw new IllegalArgumentException("Unsupported parent type: " + parentYamlConfig.getClass().toGenericString());
  }

  /**
   * Gets the YAML node of the config part.
   *
   * @return the YAML node
   */
  @NotNull Node yamlNode();

}
