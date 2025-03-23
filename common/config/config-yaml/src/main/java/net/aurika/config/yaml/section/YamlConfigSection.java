package net.aurika.config.yaml.section;

import net.aurika.validate.Validate;
import net.aurika.config.sections.format.ConfigSectionFormat;
import net.aurika.common.snakeyaml.nodes.NodeUtils;
import net.aurika.common.snakeyaml.nodes.interpret.NodeInterpretContext;
import net.aurika.common.snakeyaml.nodes.interpret.NodeInterpreter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.NodeTuple;

import java.util.List;
import java.util.Map;

public interface YamlConfigSection extends ConfigSection {
    Node getRootNode();

    @Override
    default @Nullable Boolean getBoolean(@NotNull String @NotNull [] path) {
        return get(path, NodeInterpreter.BOOLEAN);
    }

    @Override
    default @Nullable Byte getByte(@NotNull String @NotNull [] path) {
        return 0;
    }

    @Override
    default @Nullable Short getShort(@NotNull String @NotNull [] path) {
        return 0;
    }

    @Override
    default @Nullable Integer getInteger(@NotNull String @NotNull [] path) {
        return 0;
    }

    @Override
    default @Nullable Long getLong(@NotNull String @NotNull [] path) {
        return 0L;
    }

    @Override
    default @Nullable Float getFloat(@NotNull String @NotNull [] path) {
        return 0f;
    }

    @Override
    default @Nullable Double getDouble(@NotNull String @NotNull [] path) {
        return 0.0;
    }

    @Override
    default @Nullable String getString(@NotNull String @NotNull [] path) {
        return "";
    }

    @Override
    default @Nullable List<?> getList(@NotNull String @NotNull [] path) {
        return List.of();
    }

    @Override
    default @Nullable Map<?, ?> getMap(@NotNull String @NotNull [] path) {
        return Map.of();
    }

    @Override
    default @Nullable Boolean getBoolean() {
        return null;
    }

    @Override
    default @Nullable Character getCharacter() {
        return null;
    }

    @Override
    default @Nullable Number getNumber() {
        return null;
    }

    @Override
    default @Nullable Byte getByte() {
        return 0;
    }

    @Override
    default @Nullable Short getShort() {
        return 0;
    }

    @Override
    default @Nullable Integer getInteger() {
        return 0;
    }

    @Override
    default @Nullable Long getLong() {
        return 0L;
    }

    @Override
    default @Nullable Float getFloat() {
        return 0f;
    }

    @Override
    default @Nullable Double getDouble() {
        return 0.0;
    }

    @Override
    default @Nullable String getString() {
        return "";
    }

    @Override
    default @Nullable List<?> getList() {
        return List.of();
    }

    @Override
    default @Nullable Map<?, ?> getMap() {
        return Map.of();
    }

    @Override
    default @NotNull ConfigSectionFormat getSectionFormat() {
        return null;
    }

    default <T> @Nullable T get(@NotNull String @NotNull [] path, NodeInterpreter<T> interpreter) {
        return interpreter.parse(this.findNode(path));
    }

    default <T> @Nullable T get(@NotNull String @NotNull [] path, NodeInterpreter<T> interpreter, NodeInterpretContext<T> context) {
        return interpreter.parse(context.withNode(this.findNode(path)));
    }

    default <T> @Nullable T get(@NotNull String key, NodeInterpreter<T> interpreter, NodeInterpretContext<T> context) {
        return interpreter.parse(context.withNode(this.getNode(key)));
    }

    /**
     * @param key 子节点的键
     */
    default @Nullable Node getNode(@NotNull String key) {
        Node root = getRootNode();
        return root instanceof MappingNode ? NodeUtils.getNode((MappingNode) root, key) : null;
    }

    default @Nullable Node findNode(@NotNull String @NotNull [] path) {
        NodeTuple found = findTuple(path);
        return found != null ? found.getValueNode() : null;
    }

    default @Nullable NodeTuple getTuple(@NotNull String key) {
        Node root = getRootNode();
        return root instanceof MappingNode ? NodeUtils.getTuple((MappingNode) root, key) : null;
    }

    default @Nullable NodeTuple findTuple(@NotNull String @NotNull [] path) {
        Node root = getRootNode();
        return root != null ? YamlConfigSection.findTuple(root, path) : null;
    }

    static @Nullable NodeTuple findTuple(@NotNull Node node, @NotNull String @NotNull [] path) {
        Validate.Arg.notNull(node, "node");
        Validate.Arg.nonNullArray(path, "path", ConfigSection.PATH_CONTAINS_NULL);
        int length = path.length;
        int index = 0;

        while (node instanceof MappingNode) {
            String sec = path[index];
            NodeTuple subTuple = NodeUtils.getTuple((MappingNode) node, sec);
            if (subTuple == null) {
                return null;
            }
            node = subTuple.getValueNode();

            if (index + 1 == length) {
                return subTuple;
            }

            index++;
        }

        return null;
    }
}
