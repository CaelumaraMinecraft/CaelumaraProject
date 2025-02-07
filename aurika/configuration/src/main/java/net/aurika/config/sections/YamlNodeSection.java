package net.aurika.config.sections;

import net.aurika.config.path.ConfigEntry;
import net.aurika.config.path.ConfigEntryMap;
import net.aurika.config.sections.format.YamlConfigSectionFormat;
import net.aurika.config.sections.label.Label;
import net.aurika.snakeyaml.extension.nodes.NodeUtils;
import net.aurika.snakeyaml.extension.nodes.NodesKt;
import net.aurika.utils.Checker;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.comments.CommentLine;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.exceptions.Mark;
import org.snakeyaml.engine.v2.nodes.*;
import top.auspice.utils.Generics;
import top.auspice.utils.Pair;
import top.auspice.utils.Validate;

import java.util.*;
import java.util.function.Function;

/**
 * 以 SnakeYaml 的 {@link Node} 作为主结构, 操作都基于 {@linkplain YamlNodeSection#root} 进行, 包括获取子配置节也依靠 {@linkplain YamlNodeSection#root} 中的数据进行
 */
public class YamlNodeSection extends AbstractConfigSection implements ConfigSection, YamlConfigSection {
    protected @NotNull ScalarNode key;
    protected @NotNull Node root;

    private static final ScalarNode ROOT = new ScalarNode(Tag.STR, ConfigSection.ROOT, ScalarStyle.PLAIN);
    private static final ScalarNode UNKNOWN_PARENT = new ScalarNode(Tag.STR, ConfigSection.UNKNOWN_PARENT, ScalarStyle.PLAIN);

    public static YamlNodeSection root(@NotNull Node root) {
        return new YamlNodeSection(ConfigEntry.empty(), ROOT, root);
    }

    @Deprecated
    public static YamlNodeSection unknownParent(@NotNull Node root) {
        return new YamlNodeSection(UNKNOWN_PARENT, root);
    }

    /**
     * 创建一个配置节, 它将不支持父节特性
     * 不建议使用
     */
    public YamlNodeSection(@Nullable ScalarNode key, @NotNull Node root) {
        super(b(key));
        this.key = c(key);
        this.root = Objects.requireNonNull(root);
        this.label = standardTagToLabel(root.getTag());
    }

    /**
     * 创建一个路径已知, 父节已知的 {@linkplain YamlNodeSection}.
     * 由于路径由父节的路径与 keyNode 拼接而来, 所以父节必须被提供
     *
     * @param parent 父节
     */
    public YamlNodeSection(@NotNull ConfigSection parent, @NotNull ScalarNode key, @NotNull Node root) {
        super(parent, Objects.requireNonNull(key, "Key node must be provided.").getValue(), standardTagToLabel(root.getTag()));
        Checker.Arg.notNull(key, "key", "Key node must be provided.");
        Checker.Arg.notNull(root, "root", "Root node must be provided.");
        this.key = key;
        this.root = root;
    }

    private static @NotNull String a(@Nullable ScalarNode key) {
        return key == null ? ConfigSection.UNKNOWN_PARENT : key.getValue();
    }

    private static @NotNull ConfigEntry b(@Nullable ScalarNode key) {
        return ConfigEntry.fromString(a(key));
    }

    private static @NotNull ScalarNode c(@Nullable ScalarNode key) {
        return key == null ? UNKNOWN_PARENT : key;
    }

    /**
     * 创建一个有路径, 但父节未知的 {@linkplain YamlNodeSection}.
     */
    public YamlNodeSection(@NotNull ConfigEntry path, @NotNull ScalarNode key, @NotNull Node root) {
        super(Objects.requireNonNull(path), null);
        this.key = key;
        this.root = root;
        this.label = standardTagToLabel(root.getTag());
    }

    /**
     * 验证配置路径的最后一节是否等于 key 的值
     */
    private static String validatePathEnd(ConfigEntry path, ScalarNode key) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(key);
        if (!Objects.equals(path.getEnd(), key.getValue())) {
            throw new IllegalArgumentException("Last section of path " + path.asString() + " is not contains the scalar node value: " + key.getValue());
        } else {
            return key.getValue();
        }
    }

    public YamlNodeSection(@NotNull Node root, @NotNull ConfigEntry path, @NotNull String name, @Nullable ConfigSection parent) {
        super(path, name, parent);
        Objects.requireNonNull(root);
        this.root = root;
        this.key = new ScalarNode(Tag.STR, name, ScalarStyle.PLAIN);
        this.label = standardTagToLabel(root.getTag());
    }

    @Override
    public @NotNull String getKey() {
        return this.key.getValue();
    }

    @Override
    public @Nullable ConfigSection getParent() {
        return super.getParent();
    }

    public @NotNull Node getRootNode() {
        return root;
    }

    public void setRoot(@NotNull Node root) {
        this.root = Objects.requireNonNull(root, "");
    }

    public @NotNull Node toNode() {
        // 所有数据操作都在 root 上进行
        return this.root;
    }

    public @Nullable MappingNode getMapRoot() {
        if (this.root instanceof MappingNode) {
            return (MappingNode) this.root;
        }
        if (this.root instanceof AnchorNode anchorNode && anchorNode.getRealNode() instanceof MappingNode) {
            return (MappingNode) anchorNode.getRealNode();
        }
        return null;
    }

    protected void syncParentNodeData(@NotNull Node data) {
        Checker.Arg.notNull(data, "data");
        ConfigSection parent = getParent();
        if (parent instanceof YamlNodeSection yamlParent) {
            Node prentNode = yamlParent.root;
            if (!(prentNode instanceof MappingNode mappingParentNode)) {
                throw new IllegalStateException("Parent node is not a mapping node");
            } else {
                NodeUtils.putNode(mappingParentNode, this.getKey(), data);
            }
        }
    }

    protected void changeRoot(@NotNull Node newRoot) {
        this.root = newRoot;
        syncParentNodeData(newRoot);
    }

    /**
     * @return 找到的节点, ScalarNode 的值为路径的最后一个值, Node 为找到的节点
     */
    public @Nullable Pair<ScalarNode, Node> findNodePairs(@NotNull String @NotNull [] path) {
        Checker.Arg.nonNullArray(path, "path", PATH_CONTAINS_NULL);

        NodeTuple founded = findTuple(path);
        if (founded != null) {
            return Pair.of((founded.getKeyNode() instanceof AnchorNode anchorNode ? ((ScalarNode) anchorNode.getRealNode()) : ((ScalarNode) founded.getKeyNode())), founded.getValueNode());
        }
        return null;
    }

    @Override
    public @Nullable YamlNodeSection getSubSection(@NotNull String key) {
        Checker.Arg.notNull(key, "key");
        MappingNode mappingNode = this.getMapRoot();
        if (mappingNode != null) {
            Node subNode = NodeUtils.getNode(mappingNode, key);
            if (subNode != null) {
                return of(this.path.append(key), this, subNode);
            }
        }
        return null;
    }

    @Override
    public @NotNull YamlNodeSection set(@NotNull String key, @Nullable Object parsed) {
        Checker.Arg.notNull(key, "key", "SubSection key must be provided");
        Node rootNode = this.root;
        if (rootNode instanceof MappingNode mappingRoot) {

            Node subNode = NodeUtils.getNode(mappingRoot, key);
            if (subNode == null) {
                subNode = NodeUtils.nodeOfObject(parsed);
                NodeUtils.putNode(mappingRoot, key, subNode);
            }

            Object this_parsed = NodesKt.getParsed(rootNode);
            if (this_parsed instanceof Map) {
                ((Map<String, Object>) this_parsed).put(key, parsed);
            }

            return of(this.path.append(key), this, subNode);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public @Nullable YamlNodeSection removeSubSection(@NotNull String key) {
        Objects.requireNonNull(key);
        MappingNode mappingNode = this.getMapRoot();
        if (mappingNode != null) {
            Node removed = NodeUtils.removeNode(mappingNode, key);
            if (removed != null) {
                return of(this.path.append(key), this, removed);
            }
        }
        return null;
    }

    @Override
    public boolean hasSubSections() {
        return this.getMapRoot() != null;
    }

    @Override
    public @Nullable LinkedHashMap<String, ? extends YamlNodeSection> getSubSections() {
        LinkedHashMap<String, YamlNodeSection> subSections = new LinkedHashMap<>();
        MappingNode mappingNode = this.getMapRoot();
        if (mappingNode != null) {
            for (NodeTuple tuple : mappingNode.getValue()) {
                Node keyNode = tuple.getKeyNode();
                Node valueNode = tuple.getValueNode();
                String key = NodeUtils.getScalarValue(keyNode);
                if (key != null && valueNode != null) {
                    subSections.put(key, of(this.path.append(key), this, valueNode));
                }
            }
            return subSections;
        }
        return null;
    }

    @Override
    public @Nullable YamlNodeSection getSection(@NonNull String @NotNull [] path) {
        Checker.Arg.nonNullArray(path, "path", "The path cannot contains null value.");
        return $$getSection(this, path, 0);
    }

    private static @Nullable YamlNodeSection $$getSection(@NotNull YamlNodeSection parent, String[] path, int index) {
        MappingNode parentNode = parent.getMapRoot();
        if (parentNode == null) {
            return null;
        }

        if (index < path.length) {
            String name = path[index];

            Node n = NodeUtils.getNode(parentNode, name);

            if (index + 1 == path.length && n != null) {
                return of(parent.path.append(name), parent, n);
            }

            YamlNodeSection sub = parent.getSubSection(name);
            if (sub != null) {
                return $$getSection(sub, path, index + 1);
            }
        }
        return null;
    }

    @Override
    public @NotNull YamlNodeSection createSection(@NotNull String @NotNull [] path) {
        Checker.Arg.nonNullArray(path, "path", PATH_CONTAINS_NULL);
        int length = path.length;
        YamlNodeSection section = this;

        for (String secName : path) {
            section = section.set(secName, null);
        }

        return section;
    }

    private static @NotNull YamlNodeSection $$createSection(@NotNull YamlNodeSection parent, String[] path, int index) {
        if (index < path.length) {  // index 1, path {"", ""}
            YamlNodeSection subSection = parent.set(path[index], null);
            return $$createSection(subSection, path, index + 1);
        } else {
            return parent;
        }
    }

    @Override
    public @NotNull Set<String> getKeys() {
        MappingNode mappingNode = this.getMapRoot();
        Set<String> keys = new HashSet<>();
        if (mappingNode != null) {
            for (NodeTuple tuple : mappingNode.getValue()) {
                if (tuple.getKeyNode() instanceof ScalarNode) {
                    keys.add(((ScalarNode) tuple.getKeyNode()).getValue());
                }
                if (tuple.getKeyNode() instanceof AnchorNode anchorNode && anchorNode.getRealNode() instanceof ScalarNode scalarNode) {
                    keys.add(scalarNode.getValue());
                }
            }
        }

        return keys;
    }

    @Override
    public @NotNull Collection<String> getKeys(@IntRange(from = 1) int depth) {
        return $$getKeys(new ArrayList<>(), this.getMapRoot(), depth);
    }

    private static Collection<String> $$getKeys(Collection<String> container, @Nullable MappingNode root, int depthCount) {
        if (depthCount > 0) {
            if (root != null) {
                for (NodeTuple tuple : root.getValue()) {
                    String key = NodeUtils.getScalarValue(tuple.getKeyNode());
                    if (key != null) {
                        container.add(key);
                    }

                    Node valueNode = tuple.getValueNode();
                    if (valueNode instanceof MappingNode) {
                        return $$getKeys(container, ((MappingNode) valueNode), depthCount - 1);
                    }
                }
            }
        }

        return container;
    }

    @Override
    public @NotNull Map<String, Object> getSets() {
        Map<String, Object> sets = new HashMap<>();
        MappingNode mappingNode = this.getMapRoot();
        if (mappingNode != null) {
            for (NodeTuple tuple : mappingNode.getValue()) {
                String key = NodeUtils.getScalarValue(tuple.getKeyNode());
                if (key != null) {
                    sets.put(key, NodesKt.getParsed(tuple.getValueNode()));
                }
            }
        }
        return sets;
    }

    @Override
    public @NotNull Map<String, Object> getSets(@IntRange(from = 1) int depth) {
        Map<String, Object> sets = new HashMap<>();
        return $$get_sets(sets, this.root, depth);
    }

    public static @NotNull Map<String, Object> $$get_sets(@NotNull Map<String, Object> container, @Nullable Node node, int depthCount) {
        if (depthCount > 0) {
            if (node != null) {
                if (node instanceof MappingNode) {
                    for (NodeTuple tuple : ((MappingNode) node).getValue()) {
                        String key = NodeUtils.getScalarValue(tuple.getKeyNode());
                        if (key != null) {
                            container.put(key, NodesKt.getParsed(tuple.getValueNode()));   // put(!null, nullable)
                            $$get_sets(container, tuple.getValueNode(), depthCount - 1);
                        }
                    }
                }
            }
        }

        return container;
    }

    @Override
    public @Nullable Object getSet(@NotNull String @NotNull [] path) {
        return $$get_set(this.root, path, 0);
    }

    static @Nullable Object $$get_set(@Nullable Node node, String[] path, int index) {
        if (index < path.length) {
            if (node != null) {
                if (index + 1 == path.length) {
                    return NodesKt.getParsed(node);
                }

                if (node instanceof MappingNode) {
                    Node subnode = NodeUtils.getNode((MappingNode) node, path[index]);
                    return $$get_set(subnode, path, index + 1);
                }
            }
        }

        return null;
    }

    @Override
    public @NotNull YamlNodeSection set(String @NotNull [] path, Object value) {
        YamlNodeSection section = this.createSection(path);  //支持父节特性
        section.set(value);
        return section;
    }

    @Override
    public void set(Object value) {
        Node newNode = NodeUtils.nodeOfObject(value);
        changeRoot(newNode);
        NodesKt.cacheConstructed(newNode, value);
    }

    @Override
    public @NotNull ConfigEntryMap<Object> getValues(@IntRange(from = 1) int depth) {
        ConfigEntryMap<Object> values = new ConfigEntryMap<>();

        ConfigEntry path = ConfigEntry.empty();
        Node node = this.root;

        if (node.getNodeType() == NodeType.MAPPING) {
            for (NodeTuple tuple : ((MappingNode) node).getValue()) {
                String key = NodeUtils.getScalarValue(tuple.getKeyNode());
                if (key != null) {
                    path = path.append(key);
                    node = tuple.getValueNode();
                    values.put(path, NodesKt.getParsed(node));
                }
            }
        }
        return values;
    }

    @Override
    public @Nullable Object castToObject() {
        return NodeUtils.deepGetParsed(this.root);
    }

    @Override
    public @Nullable Object getParsed(@NonNull String @NotNull [] path) {
        Node node = this.findNode(path);
        return node == null ? null : NodesKt.getParsed(node);
    }

    @Override
    public @Nullable String getString(@NonNull String @NotNull [] path) {
        return NodeUtils.getParsedWithFilter(this.findNode(path), String.class, Tag.STR);
    }

    @Override
    public @Nullable List<?> getList(@NonNull String @NotNull [] path) {
        Node node = this.findNode(path);
        Object deepParsed = NodeUtils.deepGetParsed(node);
        if (deepParsed instanceof List<?>) {
            return (List<?>) deepParsed;
        }
        return null;
    }

    @Override
    public @Nullable Map<?, ?> getMap(@NonNull String @NotNull [] path) {
        Node node = this.findNode(path);
        Object deepParsed = NodeUtils.deepGetParsed(node);
        if (deepParsed instanceof Map) {
            return (Map<?, ?>) deepParsed;
        }
        return null;
    }

    @Override
    public @Nullable List<Boolean> getBooleanList() {
        return getList(this.root, Boolean.class, Tag.BOOL);
    }

    @Override
    public @Nullable List<Character> getCharacterList() {
        return getList(this.root, Character.class, null);
    }

    @Override
    public @Nullable List<Byte> getByteList() {
        return getNumberList(this.root, Byte.class, Number::byteValue, Tag.INT);
    }

    @Override
    public @Nullable List<Short> getShortList() {
        return getNumberList(this.root, Short.class, Number::shortValue, Tag.INT);
    }

    @Override
    public @Nullable List<Integer> getIntegerList() {
        return getNumberList(this.root, Integer.class, Number::intValue, Tag.INT);
    }

    @Override
    public @Nullable List<Long> getLongList() {
        return getNumberList(this.root, Long.class, Number::longValue, Tag.INT);
    }

    @Override
    public @Nullable List<Float> getFloatList() {
        return getNumberList(this.root, Float.class, Number::floatValue, Tag.FLOAT);
    }

    @Override
    public @Nullable List<Double> getDoubleList() {
        return getNumberList(this.root, Double.class, Number::doubleValue, Tag.FLOAT);
    }

    @Override
    public @Nullable List<Number> getNumberList() {
        return getList(this.root, Number.class, null);
    }

    @Override
    public @Nullable List<String> getStringList() {
        return getList(this.root, String.class, Tag.STR);
    }

    @Override
    public @Nullable Boolean getBoolean(String @NotNull ... path) {
        Node node = this.findNode(path);
        return node == null ? null : NodesKt.getParsed(node) instanceof Boolean b ? b : null;
    }

    @Override
    public @Nullable Byte getByte(String @NotNull ... path) {
        Node node = this.findNode(path);
        return node == null ? null : NodesKt.getParsed(node) instanceof Number b ? b.byteValue() : null;
    }

    @Override
    public @Nullable Short getShort(String @NotNull ... path) {
        Node node = this.findNode(path);
        return node == null ? null : NodesKt.getParsed(node) instanceof Number b ? b.shortValue() : null;
    }

    @Override
    public @Nullable Integer getInteger(String @NotNull ... path) {
        Node node = this.findNode(path);
        return node == null ? null : NodesKt.getParsed(node) instanceof Number b ? b.intValue() : null;
    }

    @Override
    public @Nullable Long getLong(@NotNull String @NotNull [] path) {
        Validate.noNullElements(path, "Can not use path contains null String");
        Node node = this.findNode(path);
        return node == null ? null : NodesKt.getParsed(node) instanceof Number b ? b.longValue() : null;
    }

    @Override
    public @Nullable Float getFloat(@NotNull String @NotNull [] path) {
        Node node = this.findNode(path);
        return node == null ? null : NodesKt.getParsed(node) instanceof Number b ? b.floatValue() : null;
    }

    @Override
    public @Nullable Double getDouble(@NotNull String @NotNull [] path) {
        Node node = this.findNode(path);
        return node == null ? null : NodesKt.getParsed(node) instanceof Number b ? b.doubleValue() : null;
    }

    @Override
    public @Nullable Boolean getBoolean() {
        Object parsed = NodesKt.getParsed(this.root);
        return parsed instanceof Boolean ? (Boolean) parsed : null;
    }

    @Override
    public @Nullable Character getCharacter() {
        return NodeUtils.getParsedWithFilter(this.root, Character.class, null);  // TODO
    }

    @Override
    public @Nullable Number getNumber() {
        return NodeUtils.getParsedWithFilter(this.root, Number.class, null);
    }

    @Override
    public @Nullable Byte getByte() {
        Number number = NodeUtils.getParsedWithFilter(this.root, Number.class, Tag.INT);
        return number == null ? null : number.byteValue();
    }

    @Override
    public @Nullable Short getShort() {
        Object parsed = NodesKt.getParsed(this.root);
        return parsed instanceof Number ? ((Number) parsed).shortValue() : null;
    }

    @Override
    public @Nullable Integer getInteger() {
        Object parsed = NodesKt.getParsed(this.root);
        return parsed instanceof Number ? ((Number) parsed).intValue() : null;
    }

    @Override
    public @Nullable Long getLong() {
        Object parsed = NodesKt.getParsed(this.root);
        return parsed instanceof Number ? ((Number) parsed).longValue() : null;
    }

    @Override
    public @Nullable Float getFloat() {
        Object parsed = NodesKt.getParsed(this.root);
        return parsed instanceof Number ? ((Number) parsed).floatValue() : null;
    }

    @Override
    public @Nullable Double getDouble() {
        Object parsed = NodesKt.getParsed(this.root);
        return parsed instanceof Number ? ((Number) parsed).doubleValue() : null;
    }

    @Override
    public @Nullable String getString() {
        return this.root instanceof ScalarNode scalarRoot ? scalarRoot.getValue() : null;
    }

    @Override
    public @Nullable List<?> getList() {
        if (this.root instanceof SequenceNode seqRoot) {
            return NodeUtils.deepGetParsed(seqRoot);
        }
        Object parsed = NodesKt.getParsed(this.root);
        return parsed instanceof List ? (List<?>) parsed : null;
    }

    @Override
    public @Nullable Map<?, ?> getMap() {
        if (this.root instanceof MappingNode mapRoot) {
            return NodeUtils.deepGetParsed(mapRoot);
        }
        Object parsed = NodesKt.getParsed(this.root);
        return parsed instanceof Map ? (Map<?, ?>) parsed : null;
    }

    @Override
    public @Nullable String getConfigureString() {
        return NodeUtils.getScalarValue(this.root);
    }

    @Override
    public void setConfigureString(String configure) {
        if (this.root instanceof ScalarNode) {
            this.root = configure == null ? new ScalarNode(this.root.getTag(), "~", ScalarStyle.PLAIN) : new ScalarNode(this.root.getTag(), configure, ScalarStyle.DOUBLE_QUOTED);
        }
    }

    @Override
    public <T> @Nullable T getObject(@NotNull String @NotNull [] path, @NotNull Class<T> type) {
        Node node = this.findNode(path);
        Object obj = NodeUtils.objectOfNode(node);
        if (type.isInstance(obj)) {
            return type.cast(obj);
        }
        return null;
    }

    @Override
    public @Nullable Object getParsedValue() {
        return NodesKt.getParsed(this.root);
    }

    @Override
    public void setParsedValue(Object parsed) {
        NodesKt.cacheConstructed(this.root, parsed);
    }

    @Override
    public @NotNull ConfigSection.BranchState getBranchState() {
        if (this.parent == null) return BranchState.ROOT;
        if (this.getMapRoot() != null) return BranchState.MIDDLE;
        return BranchState.END;
    }

    @Override
    public @NotNull YamlConfigSectionFormat getSectionFormat() {
        return YamlConfigSectionFormat.fromNode(this.root);
    }

    public Optional<Mark> getStartMark() {
        return this.root.getStartMark();
    }

    public Optional<Mark> getEndMark() {
        return this.root.getEndMark();
    }

    public List<CommentLine> getBlockComments() {
        return this.root.getBlockComments();
    }

    public void setBlockComments(List<CommentLine> blockComments) {
        this.root.setBlockComments(blockComments);
    }

    public List<CommentLine> getInlineComments() {
        return this.root.getInLineComments();
    }

    public void setInlineComments(List<CommentLine> inlineComments) {
        this.root.setInLineComments(inlineComments);
    }

    public List<CommentLine> getEndComments() {
        return this.root.getEndComments();
    }

    public void setEndComments(List<CommentLine> endComments) {
        this.root.setEndComments(endComments);
    }

    public YamlConfigSectionFormat getSectionStyle() {
        return YamlConfigSectionFormat.fromNode(this.root);
    }

    public String toString() {
        return "YamlConfigSection{"
                + "path=" + path
                + ", name='" + this.key + '\''
                + ", configuredString='" + this.getConfigureString() + '\''
                + ", parsed=" + this.getParsedValue()
                + ", node=" + this.root
                + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof YamlNodeSection that)) return false;
        return Objects.equals(this.root, that.root);
    }

    public int hashCode() {
        return Objects.hash(this.root);
    }

    protected static @Nullable <T> List<T> getList(@Nullable Node node, @Nullable Class<T> type, @Nullable Tag tag) {
        if (node instanceof SequenceNode seqNode) {
            return NodeUtils.getParsedListWithElementFilter(seqNode, type, tag);
        }
        return null;
    }

    protected static @Nullable <N extends Number> List<N> getNumberList(@Nullable Node node, @NotNull Class<N> type, @NotNull Function<Number, N> toNumber, @Nullable Tag tag) {
        if (node == null) return null;
        Checker.Arg.notNull(type, "type");
        Checker.Arg.notNull(toNumber, "toNumber");
        if (node instanceof SequenceNode seqNode) {
            List<Number> numberList = NodeUtils.getParsedListWithElementFilter(seqNode, Number.class, tag);
            if (numberList == null) return null;
            return Generics.migrationType(numberList, new LinkedList<>(), type, toNumber);
        }
        return null;
    }

    public static YamlNodeSection empty() {
        return new YamlNodeSection(new MappingNode(Tag.MAP, new ArrayList<>(), FlowStyle.BLOCK), ConfigEntry.empty(), "", null);
    }

    /**
     * 比如提供一个 {@link MappingNode} 实例, 配置路径为 "abs.test.trp"
     * 将会返回一个名称为 "trp" 的 {@link YamlNodeSection} 实例
     *
     * @param root        yaml 引擎的 {@link Node}
     * @param sectionPath 当前配置路径, 若为 {@code null}, 则视为空路径
     */
    public static YamlNodeSection of(ConfigEntry sectionPath, @Nullable ConfigSection parent, @NotNull Node root) {
        Objects.requireNonNull(root);
        if (sectionPath == null) sectionPath = ConfigEntry.empty();

        return new YamlNodeSection(root, sectionPath, sectionPath.getEnd(ConfigSection.ROOT), parent);
    }

    /**
     * 比如提供一个 {@linkplain MappingNode} 实例, 配置路径为 {@code "abs.test.trp"}, 这个函数将会返回一个名称为 {@code "trp"}, 路径为 {@code "abs.test.trp"} 的 {@linkplain MemoryConfigSection} 实例
     *
     * @param node        用来创建 {@linkplain MemoryConfigSection} 的 {@linkplain Node}
     * @param currentPath 当前配置路径, 若为 {@code null}, 则视为空路径
     */
    public static MemoryConfigSection parseSection(@Nullable ConfigEntry currentPath, @Nullable ConfigSection parent, @NotNull Node node) {
        Objects.requireNonNull(node);
        if (currentPath == null) {
            currentPath = ConfigEntry.empty();
        }

        if (node instanceof MappingNode mapNode) {
            Map<String, ConfigSection> subSections = new HashMap<>();
            MemoryConfigSection section = new MemoryConfigSection(currentPath, parent, subSections);
            for (NodeTuple tuple : mapNode.getValue()) {

                if (tuple.getKeyNode() instanceof ScalarNode scaKey) {
                    String name = scaKey.getValue();
                    subSections.put(name, parseSection(currentPath.append(name), section, tuple.getValueNode()));
                }
            }

            return section;
        }

        if (node instanceof SequenceNode seqNode) {
            List<Object> parsed = new ArrayList<>();

            for (Node value : seqNode.getValue()) {
                parsed.add(NodesKt.getParsed(value));
            }

            return new MemoryConfigSection(currentPath, parent, parsed);
        }

        if (node instanceof ScalarNode scaNode) {
            return new MemoryConfigSection(currentPath, parent, NodesKt.getParsed(scaNode), scaNode.getValue());
        }

        if (node instanceof AnchorNode anchorNode) {
            Node real = anchorNode.getRealNode();
            return parseSection(currentPath, parent, real);
        }

        throw new UnsupportedOperationException("Unsupported node type: " + node.getClass().getName());
    }

    @Contract("null -> null")
    public static Label standardTagToLabel(Tag tag) {
        if (tag == null) {
            return null;
        }

        if (Tag.SET.equals(tag)) {
            return Label.SET;
        }
        if (Tag.BINARY.equals(tag)) {
            return Label.BINARY;
        }
        if (Tag.INT.equals(tag)) {
            return Label.INT;
        }
        if (Tag.FLOAT.equals(tag)) {
            return Label.FLOAT;
        }
        if (Tag.BOOL.equals(tag)) {
            return Label.BOOL;
        }
        if (Tag.NULL.equals(tag)) {
            return Label.NULL;
        }
        if (Tag.STR.equals(tag)) {
            return Label.STR;
        }
        if (Tag.SEQ.equals(tag)) {
            return Label.SEQ;
        }
        if (Tag.MAP.equals(tag)) {
            return Label.MAP;
        }

        return new Label(tag.getValue());
    }

    @Contract("null -> null")
    public static Tag standardLabelToTag(Label label) {
        if (label == null) {
            return null;
        }
        String labelValue = label.getValue();
        switch (labelValue) {
            case "set" -> {
                return Tag.SET;
            }
            case "binary" -> {
                return Tag.BINARY;
            }
            case "int" -> {
                return Tag.INT;
            }
            case "float" -> {
                return Tag.FLOAT;
            }
            case "bool" -> {
                return Tag.BOOL;
            }
            case "null" -> {
                return Tag.NULL;
            }
            case "str" -> {
                return Tag.STR;
            }
            case "seq" -> {
                return Tag.SEQ;
            }
            case "map" -> {
                return Tag.MAP;
            }
        }

        return new Tag(labelValue);
    }
}
