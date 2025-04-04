package net.aurika.config.yaml.section.format;

import net.aurika.common.snakeyaml.nodes.NodeUtils;
import net.aurika.config.sections.format.ConfigSectionFormat;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.*;

public enum YamlConfigSectionFormat implements ConfigSectionFormat {
  /**
   * <blockquote><pre>
   *   { key: value, key2: value2 }
   * </pre></blockquote>
   */
  FLOW_MAPPING(NodeType.MAPPING, FlowStyle.FLOW),
  /**
   * <blockquote><pre>
   *   key1:
   *     value1
   *   key2:
   *     value2
   * </pre></blockquote>
   */
  BLOCK_MAPPING(NodeType.MAPPING, FlowStyle.BLOCK),
  /**
   * <blockquote><pre>
   *   [1, 2, 3]
   * </pre></blockquote>
   */
  FLOW_SEQUENCE(NodeType.SEQUENCE, FlowStyle.FLOW),
  /**
   * <blockquote><pre>
   *   elements:
   *     - e1
   *     - e2
   *     - e2
   * </pre></blockquote>
   */
  BLOCK_SEQUENCE(NodeType.SEQUENCE, FlowStyle.BLOCK),
  /**
   * Plain scalar format
   * <blockquote><pre>
   *   string
   * </pre></blockquote>
   */
  PLAIN_SCALAR(NodeType.SCALAR, FlowStyle.FLOW),
  /**
   * <blockquote><pre>
   *   "double quoted scalar"
   * </pre></blockquote>
   */
  DOUBLE_QUOTED_SCALAR(NodeType.SCALAR, FlowStyle.FLOW),
  /**
   * <blockquote><pre>
   *   '25'
   * </pre></blockquote>
   */
  SINGLE_QUOTED_SCALAR(NodeType.SCALAR, FlowStyle.FLOW),
  LITERAL_SCALAR(NodeType.SCALAR, FlowStyle.BLOCK),
  FOLDED_SCALAR(NodeType.SCALAR, FlowStyle.BLOCK),
  /**
   * Mixture of scalar styles to dump JSON format.
   * <p>
   * <blockquote><pre>
   *   Double-quoted styles for:
   *       !!str "a string"
   *       !!binary  ""
   *       !!timestamp "timestamp"
   *   Plain styles for:
   *       !!bool true
   *       !!float 1.23
   *       !!int 45
   *       !!null null
   * </pre></blockquote>
   * These are never dumped - !!merge, !!value, !!yaml
   */
  JSON_SCALAR(NodeType.SCALAR, FlowStyle.FLOW),
  ;

  private final @NotNull NodeType nodeType;
  private final @NotNull FlowStyle flowStyle;

  YamlConfigSectionFormat(@NotNull NodeType nodeType, @NotNull FlowStyle flowStyle) {
    this.nodeType = nodeType;
    this.flowStyle = flowStyle;
  }

  public @NotNull NodeType getNodeType() {
    return this.nodeType;
  }

  public @NotNull FlowStyle getFlowStyle() {
    return flowStyle;
  }

  @Override
  public boolean isMap() {
    return this.nodeType == NodeType.MAPPING;
  }

  @Override
  public boolean equals(ConfigSectionFormat other) {
    return this == other;
  }

  public static @NotNull YamlConfigSectionFormat fromNode(@NotNull Node node) {
    Validate.Arg.notNull(node, "node");
    node = NodeUtils.unpackAnchor(node);
    if (node instanceof ScalarNode sca) {
      ScalarStyle style = sca.getScalarStyle();
      if (style == ScalarStyle.PLAIN) return PLAIN_SCALAR;
      if (style == ScalarStyle.SINGLE_QUOTED) return SINGLE_QUOTED_SCALAR;
      if (style == ScalarStyle.DOUBLE_QUOTED) return DOUBLE_QUOTED_SCALAR;
      if (style == ScalarStyle.LITERAL) return LITERAL_SCALAR;
      if (style == ScalarStyle.FOLDED) return FOLDED_SCALAR;
      if (style == ScalarStyle.JSON_SCALAR_STYLE) return JSON_SCALAR;
    }
    if (node instanceof MappingNode mapping) {
      FlowStyle style = mapping.getFlowStyle();
      if (style == FlowStyle.FLOW) return FLOW_MAPPING;
      if (style == FlowStyle.BLOCK) return BLOCK_MAPPING;
    }
    if (node instanceof SequenceNode seq) {
      FlowStyle style = seq.getFlowStyle();
      if (style == FlowStyle.FLOW) return FLOW_SEQUENCE;
      if (style == FlowStyle.BLOCK) return BLOCK_SEQUENCE;
    }
    throw new IllegalArgumentException("Unsupported node type: " + node);
  }
}
