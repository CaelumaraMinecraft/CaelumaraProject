package net.aurika.config.yaml.part;

import net.aurika.common.snakeyaml.nodes.NodeUtils;
import net.aurika.config.part.AbstractConfigPart;
import net.aurika.config.part.exception.ConfigPartIsRootException;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.ScalarNode;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

/**
 * {@linkplain YamlConfigPart} 的默认实现.
 * <p>{@link DefaultYamlConfigScalar} 的数据修改方式为替换一个新的 {@link ScalarNode}, 并在父配置段中换掉自己的引用 (如果有父节的话).</p>
 * <p>{@link DefaultYamlConfigSection} 的数据修改方式为直接修改当前其拥有的 {@link YamlConfigSection#yamlNode() mappingNode}
 * 属性{@linkplain MappingNode#getValue() 值}.</p>
 * <p>{@link DefaultYamlConfigSequence}</p>
 *
 * @param <N> the YAML node type
 */
public class DefaultYamlConfigPart<N extends Node> extends AbstractConfigPart implements YamlConfigPart {

  protected @NotNull N node;

  /**
   * Creates a root YAML config part vy a YAML node.
   *
   * @param rootNode the node to create the config part
   * @return the created YAML config part
   */
  @SuppressWarnings("unchecked")
  public static <N extends Node> @NotNull DefaultYamlConfigPart<N> createRoot(@NotNull N rootNode) {
    Validate.Arg.notNull(rootNode, "rootNode");
    rootNode = (N) NodeUtils.unpackAnchor(rootNode);
    if (rootNode instanceof ScalarNode scalarNode) {
      return (DefaultYamlConfigPart<N>) new DefaultYamlConfigScalar(scalarNode);
    }
    if (rootNode instanceof SequenceNode sequenceNode) {
      return (DefaultYamlConfigPart<N>) new DefaultYamlConfigSequence(sequenceNode);
    }
    if (rootNode instanceof MappingNode mappingNode) {
      return (DefaultYamlConfigPart<N>) new DefaultYamlConfigSection(mappingNode);
    }
    throw new UnsupportedOperationException("Unsupported node type: " + rootNode.getClass());
  }

  @SuppressWarnings("unchecked")
  public static <N extends Node> DefaultYamlConfigPart<N> createSequenceElement(
      @NotNull YamlConfigSequence sequenceParent,
      @NotNull N elementNode
  ) {
    Validate.Arg.notNull(sequenceParent, "sequenceParent");
    Validate.Arg.notNull(elementNode, "elementNode");
    elementNode = (N) NodeUtils.unpackAnchor(elementNode);
    if (elementNode instanceof ScalarNode scalarNode) {
      return (DefaultYamlConfigPart<N>) new DefaultYamlConfigScalar(sequenceParent, scalarNode);
    }
    if (elementNode instanceof SequenceNode sequenceNode) {
      return (DefaultYamlConfigPart<N>) new DefaultYamlConfigSequence(sequenceParent, sequenceNode);
    }
    if (elementNode instanceof MappingNode mappingNode) {
      return (DefaultYamlConfigPart<N>) new DefaultYamlConfigSection(sequenceParent, mappingNode);
    }
    throw new UnsupportedOperationException("Unsupported node type: " + elementNode.getClass());
  }

  @SuppressWarnings("unchecked")
  public static <N extends Node> DefaultYamlConfigPart<N> createSectionSub(
      @NotNull YamlConfigSection parentSection,
      @NotNull String name,
      @NotNull N subNode
  ) {
    Validate.Arg.notNull(parentSection, "parentSection");
    Validate.Arg.notNull(name, "name");
    Validate.Arg.notNull(subNode, "subNode");
    subNode = (N) NodeUtils.unpackAnchor(subNode);
    if (subNode instanceof ScalarNode scalarNode) {
      return (DefaultYamlConfigPart<N>) new DefaultYamlConfigScalar(parentSection, name, scalarNode);
    }
    if (subNode instanceof SequenceNode sequenceNode) {
      return (DefaultYamlConfigPart<N>) new DefaultYamlConfigSequence(parentSection, name, sequenceNode);
    }
    if (subNode instanceof MappingNode mappingNode) {
      return (DefaultYamlConfigPart<N>) new DefaultYamlConfigSection(parentSection, name, mappingNode);
    }
    throw new UnsupportedOperationException("Unsupported node type: " + subNode.getClass());
  }

  protected DefaultYamlConfigPart(@NotNull N node) {
    super();
    init(node);
  }

  protected DefaultYamlConfigPart(@NotNull YamlConfigSequence parentSequence, @NotNull N node) {
    super(parentSequence);
    init(node);
  }

  protected DefaultYamlConfigPart(@NotNull YamlConfigSection parentSection, @NotNull String name, @NotNull N node) {
    super(parentSection, name);
    init(node);
  }

  private void init(@NotNull N node) {
    Validate.Arg.notNull(node, "node");
    this.node = node;
    addToParent();
  }

  /**
   * Adda this config part into the present parent.
   */
  protected void addToParent() {
    YamlConfigPart.addSubToParent(this);
  }

  /**
   * Removes this config part into the present parent.
   */
  protected void removeThisFromParent() {
    if (!hasNoParent()) {
      YamlConfigPart parent = parent();
      if (parent instanceof YamlConfigSection sectionParent) {
        sectionParent.removeSubPart(name());  // this should have name when parent is a section
      }
      if (parent instanceof YamlConfigSequence sequenceParent) {
        sequenceParent.yamlNode().getValue().remove(node);
      }
      if (parent instanceof YamlConfigScalar) {
        throw new IllegalStateException("Parent is scalar");
      }
    }
  }

  protected void changeThisInParent(@NotNull Node fromNode, @NotNull Node toNode) {
    if (!hasNoParent()) {
      YamlConfigPart parent = parent();
      if (parent instanceof YamlConfigSection sectionParent) {
        MappingNode mapNode = sectionParent.yamlNode();
        if (NodeUtils.hasNode(mapNode, name())) {
          NodeUtils.putNode(mapNode, name(), toNode);
        } else {  // not root, but also has no parent
          throw new IllegalStateException();
        }
      }
      if (parent instanceof YamlConfigSequence sequenceParent) {
        SequenceNode seqNode = sequenceParent.yamlNode();
        if (seqNode.getValue().contains(fromNode)) {
          seqNode.getValue().remove(fromNode);
          seqNode.getValue().add(toNode);
        } else {
          throw new IllegalStateException();
        }
      }
      if (parent instanceof YamlConfigScalar) {
        throw new IllegalStateException("Parent is scalar");
      }
    }
  }

  @Override
  public @NotNull YamlConfigPart parent() throws ConfigPartIsRootException {
    return (YamlConfigPart) super.parent();
  }

  @Override
  public @NotNull N yamlNode() {
    return node;
  }

  protected void yamlNode(@NotNull N node) {
    Validate.Arg.notNull(node, "node");
    changeThisInParent(this.node, node);
    this.node = node;
  }

}
