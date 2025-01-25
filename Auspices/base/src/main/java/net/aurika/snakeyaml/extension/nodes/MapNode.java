package net.aurika.snakeyaml.extension.nodes;

import net.aurika.snakeyaml.extension.interpret.NodeInterpretContext;
import net.aurika.snakeyaml.extension.interpret.NodeInterpreter;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.NodeTuple;

public class MapNode {

    private final @NotNull MappingNode node;

    public MapNode(@NotNull MappingNode node) {
        Checker.Argument.checkNotNull(node, "node");
        this.node = node;
    }

    public <T> @Nullable T getAndInterpret(String key, @NotNull NodeInterpreter<T> interpreter, @Nullable NodeInterpretContext<T> context) {
        Checker.Argument.checkNotNull(interpreter, "interpreter");
        Node found = getNode(key);
        if (context == null) context = new NodeInterpretContext<>(found);
        return interpreter.parse(context.withNode(found));
    }

    public @Nullable Node getNode(String key) {
        return NodeUtils.getNode(node, key);
    }

    public @Nullable Node removeNode(String key) {
        return NodeUtils.removeNode(node, key);
    }

    public @Nullable NodeTuple getTuple(String key) {
        return NodeUtils.getTuple(node, key);
    }

    public @Nullable Node putNode(@NotNull String key, @NotNull Node node) {
        Checker.Argument.checkNotNull(key, "key");
        Checker.Argument.checkNotNull(node, "node");
        return NodeUtils.putNode(this.node, key, node);
    }

    public @NotNull MappingNode getNode() {
        return node;
    }
}
