package net.aurika.util.snakeyaml.nodes;

import net.aurika.util.snakeyaml.nodes.interpret.NodeInterpretContext;
import net.aurika.util.snakeyaml.nodes.interpret.NodeInterpreter;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.NodeTuple;

public class MapNode {

    private final @NotNull MappingNode node;

    public MapNode(@NotNull MappingNode node) {
        Checker.Arg.notNull(node, "node");
        this.node = node;
    }

    public <T> @Nullable T getAndInterpret(String key, @NotNull NodeInterpreter<T> interpreter, @Nullable NodeInterpretContext<T> context) {
        Checker.Arg.notNull(interpreter, "interpreter");
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
        Checker.Arg.notNull(key, "key");
        Checker.Arg.notNull(node, "node");
        return NodeUtils.putNode(this.node, key, node);
    }

    public @NotNull MappingNode getNode() {
        return node;
    }
}
