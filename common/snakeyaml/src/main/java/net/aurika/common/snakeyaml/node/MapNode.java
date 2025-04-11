package net.aurika.common.snakeyaml.node;

import net.aurika.common.snakeyaml.node.interpret.NodeInterpretContext;
import net.aurika.common.snakeyaml.node.interpret.NodeInterpreter;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.NodeTuple;

public class MapNode {

  private final @NotNull MappingNode node;

  public MapNode(@NotNull MappingNode node) {
    Validate.Arg.notNull(node, "node");
    this.node = node;
  }

  public <T> @Nullable T getAndInterpret(String key, @NotNull NodeInterpreter<T> interpreter, @Nullable NodeInterpretContext<T> context) {
    Validate.Arg.notNull(interpreter, "interpreter");
    Node found = getNode(key);
    if (context == null) context = new NodeInterpretContext<>(found);
    return interpreter.parse(context.withNode(found));
  }

  public @Nullable Node getNode(String key) {
    return NodeUtil.getNode(node, key);
  }

  public @Nullable Node removeNode(String key) {
    return NodeUtil.removeNode(node, key);
  }

  public @Nullable NodeTuple getTuple(String key) {
    return NodeUtil.getTuple(node, key);
  }

  public @Nullable Node putNode(@NotNull String key, @NotNull Node node) {
    Validate.Arg.notNull(key, "key");
    Validate.Arg.notNull(node, "node");
    return NodeUtil.putNode(this.node, key, node);
  }

  public @NotNull MappingNode getNode() {
    return node;
  }

}
