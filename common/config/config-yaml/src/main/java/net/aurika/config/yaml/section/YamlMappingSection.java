package net.aurika.config.yaml.section;

import net.aurika.common.annotations.data.LateInit;
import net.aurika.common.snakeyaml.nodes.NodesKt;
import net.aurika.config.path.ConfigEntryMap;
import net.aurika.config.sections.label.Label;
import net.aurika.common.snakeyaml.nodes.NodeUtils;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.*;

import java.util.*;

public class YamlMappingSection extends AbstractConfigSection implements ConfigSection, YamlConfigSection {

    protected final MappingNode parentRoot;
    private @LateInit(by = "") Node root;

    public YamlMappingSection(@NotNull YamlConfigSection parent, @NotNull String key, @NotNull Label label) {
        super(parent, key, label);
        Node rootNode = parent.getRootNode();
        if (!(rootNode instanceof MappingNode)) {
            throw new IllegalArgumentException("Root node is not a mapping node");
        } else {
            this.parentRoot = (MappingNode) rootNode;
        }
    }

    public @Nullable Node getRootNode() {
        if (root == null) {
            Node newRoot = NodeUtils.getNode(parentRoot, key);
            if (newRoot == null) {
                // TODO
            }
            root = newRoot;
        }
        return root;
    }

    public void setRootNode(Node newRoot) {
        if (newRoot == null) {
            NodeUtils.removeNode(parentRoot, key);
        } else {
            NodeUtils.putNode(parentRoot, key, newRoot);
        }
        root = newRoot;
    }

    public boolean isPresent() {
        return getRootNode() != null;
    }

    @Override
    public @Nullable Map<String, ? extends YamlMappingSection> getSubSections() {
        Node root = getRootNode();
        if (root instanceof MappingNode mappingRoot) {
            Map<String, YamlMappingSection> subSections = new HashMap<>();
            for (NodeTuple tuple : mappingRoot.getValue()) {
                String key = ((ScalarNode) tuple.getKeyNode()).getValue();
                subSections.put(key, new YamlMappingSection(this, key, YamlNodeSection.standardTagToLabel(tuple.getValueNode().getTag())));
            }
            return subSections;
        }
        return null;
    }

    @Override
    public @Nullable YamlMappingSection getSubSection(@NotNull String key) {
        Node root = getRootNode();
        if (root instanceof MappingNode mappingRoot) {
            if (NodeUtils.hasNode(mappingRoot, key)) {
                return new YamlMappingSection(this, key, Label.AUTO);
            }
        }
        return null;
    }

    @Override
    public @NotNull YamlMappingSection set(@NotNull String key, @Nullable Object parsed) {
        Node root = getRootNode();
        Node subNode = NodeUtils.nodeOfObject(parsed);
        if (root instanceof MappingNode mappingRoot) {
            NodeUtils.putNode(mappingRoot, key, subNode);
            return new YamlMappingSection(this, key, Label.AUTO);
        }
        if (root == null) {
            MappingNode newMapRoot = NodeUtils.emptyMapping();
            NodeUtils.putNode(newMapRoot, key, subNode);
            setRootNode(newMapRoot);
            return new YamlMappingSection(this, key, Label.AUTO);
        }
        throw new IllegalStateException("Excepted a mapping, but new is " + root.getClass().getName());
    }

    @Override
    public @Nullable YamlMappingSection removeSubSection(@NotNull String key) {
        Node root = getRootNode();
        if (root instanceof MappingNode mappingRoot) {
            Node removed = NodeUtils.removeNode(mappingRoot, key);
            return new YamlMappingSection(this, key, Label.AUTO);
        }
        return null;
    }

    @Override
    public boolean hasSubSections() {
        Node root = getRootNode();
        if (root instanceof MappingNode mappingRoot) {
            return !mappingRoot.getValue().isEmpty();
        }
        return false;
    }

    @Override
    public @Nullable YamlMappingSection getSection(@NotNull String @NotNull [] path) {

    }

    @Override
    public @NotNull YamlMappingSection createSection(@NotNull String @NotNull [] path) {

    }

    @Override
    public @Nullable Set<String> getKeys() {
        Node root = getRootNode();
        if (root instanceof MappingNode mappingRoot) {
            Set<String> keys = new HashSet<>();
            for (NodeTuple tuple : mappingRoot.getValue()) {
                keys.add(((ScalarNode) tuple.getKeyNode()).getValue());
            }
            return keys;
        }
        return null;
    }

    @Override
    public @NotNull Collection<String> getKeys(@IntRange(from = 1) int depth) {

    }

    @Override
    public @NotNull Map<String, Object> getSets() {

    }

    @Override
    public @NotNull Map<String, Object> getSets(@IntRange(from = 1) int depth) {

    }

    @Override
    public @Nullable Object getSet(@NotNull String @NotNull [] path) {
        Node found = findNode(path);
        return found != null ? NodesKt.parsed(found) : null;
    }

    @Override
    public @NotNull YamlMappingSection set(@NotNull String @NotNull [] path, Object value) {

    }

    @Override
    public ConfigEntryMap<Object> getValues(@IntRange(from = 1) int depth) {

    }

    @Override
    public void set(Object value) {
        setRootNode(NodeUtils.nodeOfObject(value));
    }

    @Override
    public Object getParsedValue() {
        Node root = getRootNode();
        return root != null ? NodesKt.parsed(root) : null;
    }

    @Override
    public void setParsedValue(Object parsed) {
        Node root = getRootNode();
        if (root != null) NodesKt.cacheConstructed(root, parsed);
    }

    @Override
    public @Nullable String getConfigureString() {
        Node root = getRootNode();
        return root instanceof ScalarNode ? ((ScalarNode) root).getValue() : null;
    }

    @Override
    public void setConfigureString(String configure) {
        Node root = getRootNode();
        if (root instanceof ScalarNode)
            setRootNode(new ScalarNode(Tag.STR, true, configure, ScalarStyle.DOUBLE_QUOTED, Optional.empty(), Optional.empty()));
    }

    @Override
    public Object castToObject() {
        return NodeUtils.objectOfNode(getRootNode());
    }

    @Override
    public @Nullable Object getParsed(@NotNull String @NotNull [] path) {
        Node found = findNode(path);
        return found != null ? NodesKt.parsed(found) : null;
    }

    @Override
    public <T> @Nullable T getObject(@NotNull String @NotNull [] path, @NotNull Class<T> type) {
        Node found = findNode(path);
        Object parsed;
        if (found != null) {
            parsed = NodesKt.parsed(found);
            if (type.isInstance(parsed)) {
                return type.cast(parsed);
            }
        }
        return null;
    }



    public MappingNode getParentRoot() {
        return parentRoot;
    }
}
