package net.aurika.snakeyaml.extension.nodes;

import net.aurika.annotations.bookmark.Bookmark;
import net.aurika.annotations.bookmark.BookmarkType;
import net.aurika.config.annotations.ImplicitOperateCached;
import net.aurika.config.yaml.snakeyaml.common.NodeReplacer;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.constructor.BaseConstructor;
import org.snakeyaml.engine.v2.constructor.StandardConstructor;
import org.snakeyaml.engine.v2.nodes.*;
import org.snakeyaml.engine.v2.representer.BaseRepresenter;
import org.snakeyaml.engine.v2.representer.StandardRepresenter;
import top.auspice.utils.Generics;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.unsafe.Fn;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;

public final class NodeUtils {
    static final @NotNull BaseRepresenter CORE_REPRESENTER = new StandardRepresenter(
            DumpSettings.builder()
                    .setDumpComments(true)
                    .build()
    ) {

    };

    @ImplicitOperateCached
    public static Node nodeOfObject(@Nullable Object obj) {
        Node node = CORE_REPRESENTER.represent(obj);
        NodesKt.cacheConstructed(node, obj);
        return node;
    }

//    public static Node nodeOfObject(@Nullable Object obj) {
//        if (obj == null) {
//            return new ScalarNode(Tag.NULL, "~", ScalarStyle.PLAIN);
//        }
//        if (obj instanceof Node) {
//            return (Node) obj;
//        }
//        if (obj instanceof Collection) {
//            List<Node> var2 = new ArrayList<>(((Collection<?>) obj).size());
//
//            for (Object e : (Collection<?>) obj) {
//                var2.add(nodeOfObject(e));
//            }
//
//            return new SequenceNode(Tag.SEQ, var2, FlowStyle.AUTO);
//        } else {
//            if (obj instanceof Map) {
//                throw new UnsupportedOperationException("Mapping from objects");
//            } else {
//                ScalarNode newScaNode = new ScalarNode(NodeInterpreter.DEFAULT_RESOLVER.resolve(obj.toString(), true), obj.toString(), ScalarStyle.DOUBLE_QUOTED);
//                NodesKt.cacheConstructed(newScaNode, NodeInterpreter.DEFAULT_CTOR.constructSingleDocument(Optional.of(newScaNode)));
//                return newScaNode;
//            }
//        }
//    }

    static final @NotNull BaseConstructor CORE_CONSTRUCTOR = new StandardConstructor(LoadSettings.builder().build());

    @Contract("null -> null")
    public static Object objectOfNode(Node node) {
        if (node == null) return null;
        return CORE_CONSTRUCTOR.constructSingleDocument(Optional.of(node));
    }

    private static final @NotNull ScalarNode NULL_SCALAR = new ScalarNode(Tag.NULL, true, "~", ScalarStyle.PLAIN, Optional.empty(), Optional.empty());
    private static final @NotNull MappingNode EMPTY_MAPPING = new MappingNode(Tag.MAP, true, new ArrayList<>(), FlowStyle.AUTO, Optional.empty(), Optional.empty());

    @Contract(value = "-> new", pure = true)
    public static @NotNull ScalarNode nullScalar() {
        return copyScalarNode(NULL_SCALAR);
    }

    @Contract(value = "-> new", pure = true)
    public static @NotNull MappingNode emptyMapping() {
        return deepCopyMappingNode(EMPTY_MAPPING);
    }

    public static boolean isEmptyNode(Node node) {
        if (node == null) return true;
        if (node instanceof ScalarNode && (node.getTag().equals(Tag.NULL) || ((ScalarNode) node).getValue().isEmpty())) return true;
        if (node instanceof CollectionNode<?> && ((CollectionNode<?>) node).getValue().isEmpty()) return true;
        return false;
    }

    public static void copyIfDoesntExist(MappingNode $this$copyIfDoesntExist, MappingNode copyFrom, String rootPath, Predicate<NodeReplacer.ReplacementDetails> ignore) {

        for (NodeTuple copyFromPair : copyFrom.getValue()) {
            String path;
            if (rootPath.isEmpty()) {
                path = ((ScalarNode) copyFromPair.getKeyNode()).getValue();
            } else {
                path = rootPath + '.' + ((ScalarNode) copyFromPair.getKeyNode()).getValue();
            }

            if (!ignore.test(new NodeReplacer.ReplacementDetails(path, copyFromPair.getValueNode(), true, null))) {
                Node copyNode = copyFromPair.getValueNode();
                NodeTuple currentPair = null;
                for (NodeTuple sT : $this$copyIfDoesntExist.getValue()) {
                    if (((ScalarNode) sT.getKeyNode()).getValue().equals(((ScalarNode) copyFromPair.getKeyNode()).getValue())) {
                        currentPair = sT;
                        break;
                    }
                }
                if (currentPair == null) {
                    $this$copyIfDoesntExist.getValue().add(new NodeTuple(NodeUtils.deepCopyNode(copyFromPair.getKeyNode()), NodeUtils.deepCopyNode(copyFromPair.getValueNode())));
                } else {
                    Node currentNode = currentPair.getValueNode();
                    if (currentNode instanceof MappingNode && copyNode instanceof MappingNode) {
                        copyIfDoesntExist((MappingNode) currentNode, (MappingNode) copyNode, path, ignore);
                    }
                }
            }
        }
    }

    public static Node deepCopyNode(Node node) {
        if (node instanceof ScalarNode scalarNode) {
            return copyScalarNode(scalarNode);
        }
        if (node instanceof SequenceNode sequenceNode) {
            return deepCopySequenceNode(sequenceNode);
        }
        if (node instanceof MappingNode mappingNode) {
            return deepCopyMappingNode(mappingNode);
        }
        if (node instanceof AnchorNode anchorNode) {
            return deepCopyAnchorNode(anchorNode);
        }
        throw new UnsupportedOperationException("Unsupported node type");
    }

    @Contract("_ -> new")
    public static SequenceNode deepCopySequenceNode(SequenceNode node) {
        List<Node> value = new ArrayList<>();
        for (Node valueNode : node.getValue()) {
            value.add(deepCopyNode(valueNode));
        }
        return new SequenceNode(node.getTag(), true, value, node.getFlowStyle(), node.getStartMark(), node.getEndMark());
    }

    @Contract("_ -> new")
    public static MappingNode deepCopyMappingNode(MappingNode node) {
        List<NodeTuple> value = new ArrayList<>();
        for (NodeTuple valueTuple : node.getValue()) {
            value.add(new NodeTuple(deepCopyNode(valueTuple.getKeyNode()), deepCopyNode(valueTuple.getValueNode())));
        }
        return new MappingNode(node.getTag(), true, value, node.getFlowStyle(), node.getStartMark(), node.getEndMark());
    }

    @Contract("_ -> new")
    public static AnchorNode deepCopyAnchorNode(AnchorNode node) {
        return new AnchorNode(deepCopyNode(node.getRealNode()));
    }

    @Contract("_ -> new")
    public static SequenceNode shallowCopySequenceNode(SequenceNode node) {
        return new SequenceNode(node.getTag(), true, node.getValue(), node.getFlowStyle(), node.getStartMark(), node.getEndMark());
    }

    @Contract("_ -> new")
    public static MappingNode shallowCopyMappingNode(MappingNode node) {
        return new MappingNode(node.getTag(), true, node.getValue(), node.getFlowStyle(), node.getStartMark(), node.getEndMark());
    }

    @Bookmark(value = {BookmarkType.EXPERIMENTAL}, comment = "我不知道浅拷贝一个 AnchorNode 有啥用")
    @Contract("_ -> new")
    public static AnchorNode shallowCopyAnchorNode(AnchorNode node) {
        return new AnchorNode(node.getRealNode());
    }

    @Contract("_ -> new")
    public static ScalarNode copyScalarNode(ScalarNode node) {
        return new ScalarNode(node.getTag(), true, node.getValue(), node.getScalarStyle(), node.getStartMark(), node.getEndMark());
    }

    public static boolean hasNode(@NotNull MappingNode mappingNode, @NotNull String key) {
        Checker.Arg.notNull(mappingNode, "mappingNode");
        return hasNode(mappingNode.getValue(), key);
    }

    public static boolean hasNode(@NotNull List<NodeTuple> tupleList, @NotNull String key) {
        Checker.Arg.notNull(tupleList, "tupleList");
        Checker.Arg.notNull(key, "key");
        for (NodeTuple tuple : tupleList) {
            Node keyNode = tuple.getKeyNode();
            if (equalsScalarValue(keyNode, key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return 之前存在的键相同的节点
     */
    public static @Nullable Node putNode(@NotNull MappingNode mappingNode, @NotNull String key, @NotNull Node value) {
        Checker.Arg.notNull(mappingNode, "mappingNode");
        return putNode(mappingNode.getValue(), key, value);
    }

    public static @Nullable Node putNode(@NotNull List<NodeTuple> tupleList, @NotNull String key, @NotNull Node value) {
        Checker.Arg.notNull(tupleList, "tupleList");
        Checker.Arg.notNull(key, "key");
        Checker.Arg.notNull(value, "value");
        Node keyNode = null;
        for (NodeTuple tuple : tupleList) {  // Search for existent node
            keyNode = tuple.getKeyNode();
            if (equalsScalarValue(keyNode, key)) {
                tupleList.remove(tuple);
                return tuple.getValueNode();
            }
        }
        if (keyNode == null) {
            keyNode = key(key);
        }
        tupleList.add(new NodeTuple(keyNode, value));
        return null;
    }

    public static @Nullable Node getNode(@NotNull MappingNode mappingNode, String key) {
        return getNode(mappingNode.getValue(), key);
    }

    public static @Nullable Node getNode(@NotNull List<NodeTuple> tupleList, String key) {
        Checker.Arg.notNull(tupleList, "tupleList");
        for (NodeTuple tuple : tupleList) {
            Node keyNode = tuple.getKeyNode();
            if (equalsScalarValue(keyNode, key)) {
                return tuple.getValueNode();
            }
        }
        return null;
    }

    public static @Nullable NodeTuple getTuple(@NotNull MappingNode mappingNode, String key) {
        Checker.Arg.notNull(mappingNode, "mappingNode");
        return getTuple(mappingNode.getValue(), key);
    }

    public static @Nullable NodeTuple getTuple(@NotNull List<NodeTuple> tupleList, String key) {
        Checker.Arg.notNull(tupleList, "tupleList");
        for (NodeTuple tuple : tupleList) {
            Node keyNode = tuple.getKeyNode();
            keyNode = unpackAnchor(keyNode);
            if (equalsScalarValue(keyNode, key)) {
                return tuple;
            }
        }
        return null;
    }

    public static @Nullable Node removeNode(@NotNull MappingNode mappingNode, String key) {
        Checker.Arg.notNull(mappingNode, "mappingNode");
        return removeNode(mappingNode.getValue(), key);
    }

    public static @Nullable Node removeNode(@NotNull List<NodeTuple> tupleList, String key) {
        Checker.Arg.notNull(tupleList, "tupleList");
        AtomicReference<Node> removed = new AtomicReference<>();
        consumeWhenFindTuple(tupleList, key, ((tuple, index) -> {
            tupleList.remove(index);
            removed.set(tuple.getValueNode());
        }));
        return removed.get();
    }

    /**
     * 通过 {@linkplain String} 键寻找一个 {@link NodeTuple} 并处理.
     *
     * @param tupleAndIndexConsumer 处理器, 将会接收寻找到的 {@link NodeTuple} 和其对应的在 tupleList 中的位置
     */
    public static void consumeWhenFindTuple(@NotNull List<NodeTuple> tupleList, String key, @NotNull ObjIntConsumer<NodeTuple> tupleAndIndexConsumer) {
        Checker.Arg.notNull(tupleList, "tupleList");
        Checker.Arg.notNull(tupleAndIndexConsumer, "tupleAndIndexConsumer");
        for (int i = 0, tupleListSize = tupleList.size(); i < tupleListSize; i++) {
            NodeTuple tuple = tupleList.get(i);
            Node keyNode = tuple.getKeyNode();
            if (equalsScalarValue(keyNode, key)) {
                tupleAndIndexConsumer.accept(tuple, i);
            }
        }
    }

    static boolean equalsScalarValue(Node node, String value) {
        node = unpackAnchor(node);
        if (node instanceof ScalarNode scalarNode) {
            return Objects.equals(scalarNode.getValue(), value);
        }

        return false;
    }

    /**
     * 若为 {@link ScalarNode} ,返回这个 {@linkplain Node} 作为 {@linkplain ScalarNode} 的值
     *
     * @return null 当 Node 不是 ScalarNode 时
     */
    public static @Nullable String getScalarValue(Node node) {
        node = unpackAnchor(node);
        if (node instanceof ScalarNode scalarNode) {
            return scalarNode.getValue();
        }
        return null;
    }

    public static boolean isScalar(Node node) {
        node = unpackAnchor(node);
        return node instanceof ScalarNode;
    }

    @ImplicitOperateCached
    public static @Nullable Object deepGetParsed(Node node) {
        node = unpackAnchor(node);
        Object parsed = NodesKt.getParsed(node);
        if (parsed != null) {
            return parsed;
        }
        if (node instanceof MappingNode mappingNode) {
            HashMap<String, Object> map = new HashMap<>();
            parsed = map;
            for (NodeTuple tuple : mappingNode.getValue()) {
                map.put(((ScalarNode) tuple.getKeyNode()).getValue(), deepGetParsed(tuple.getValueNode()));
            }
            NodesKt.cacheConstructed(node, parsed);
            return parsed;
        }
        if (node instanceof SequenceNode sequenceNode) {
            return deepGetParsed(sequenceNode);
        }

        return parsed;
    }

    @ImplicitOperateCached
    public static List<?> deepGetParsed(SequenceNode node) {
        if (node == null) return null;
        Object parsed = NodesKt.getParsed(node);
        if (parsed instanceof List) {
            return (List<?>) parsed;
        }
        parsed = new LinkedList<>();
        for (Node subNode : node.getValue()) {
            ((LinkedList<?>) parsed).add(Fn.cast(deepGetParsed(subNode)));
        }
        NodesKt.cacheConstructed(node, parsed);
        return (List<?>) parsed;
    }

    @ImplicitOperateCached
    public static Map<String, ?> deepGetParsed(MappingNode node) {
        Object parsed = NodesKt.getParsed(node);
        if (parsed instanceof Map<?, ?>) {
            Map<String, ?> filtedMap = Generics.filterKeyType(((Map<?, ?>) parsed), String.class);
            parsed = filtedMap;
            if (parsed != null) {
                return filtedMap;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        for (NodeTuple tuple : node.getValue()) {
            map.put(((ScalarNode) tuple.getKeyNode()).getValue(), deepGetParsed(tuple.getValueNode()));
        }
        NodesKt.cacheConstructed(node, map);
        return map;
    }

    @Contract("null -> null; !null -> !null")
    public static Map<String, ?> asMap(MappingNode node) {
        if (node == null) return null;
        return asMap(node, new LinkedHashMap<>(), (m, keyNode, valueNode) -> m.put(((ScalarNode) keyNode).getValue(), deepGetParsed(valueNode)));
    }

    public static <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull MappingNode mappingNode, @NotNull M map, TriConsumer<M, Node, Node> consumeKeyValueNode) {
        Checker.Arg.notNull(mappingNode, "mappingNode");
        Checker.Arg.notNull(map, "map");
        for (NodeTuple tuple : mappingNode.getValue()) {
            if (consumeKeyValueNode != null) consumeKeyValueNode.accept(map, tuple.getKeyNode(), tuple.getValueNode());
        }
        return map;
    }

    @Contract("null -> null; !null -> !null")
    public static Node unpackAnchor(Node node) {
        if (node instanceof AnchorNode anchorNode) {
            return unpackAnchor(anchorNode.getRealNode());
        }
        return node;
    }

    static @NotNull ScalarNode key(@NotNull String key) {
        Checker.Arg.notNull(key, "key");
        if (key.contains(" ") || key.contains(":")) {
            return new ScalarNode(Tag.STR, key, ScalarStyle.SINGLE_QUOTED);
        }
        return new ScalarNode(Tag.STR, key, ScalarStyle.PLAIN);
    }

    /**
     * @param requiredElementType 需要的列表项目类型, 为 null 时则是不验证
     * @param requiredElementTag  需要的列表项目标签, 为 null 时则不验证
     */
    public static <T> @Nullable List<@NotNull T> getParsedListWithElementFilter(SequenceNode seqNode, @Nullable Class<T> requiredElementType, @Nullable Tag requiredElementTag) {
        if (seqNode == null) return null;

        boolean allRight = true;
        List<T> list = new LinkedList<>();
        for (Node element : seqNode.getValue()) {
            if (requiredElementTag != null && !requiredElementTag.equals(element.getTag())) {
                allRight = false;
                break;
            }

            @Nullable Object eleParsed = NodesKt.getParsed(element);
            if (requiredElementType != null && !requiredElementType.isInstance(eleParsed)) {
                allRight = false;
                break;
            }

            list.add((T) eleParsed);
        }

        return allRight ? list : null;
    }

    public static <T> @Nullable T getParsedWithFilter(Node node, @Nullable Class<T> requiredType, @Nullable Tag requiredTag) {
        if (node == null) return null;

        Object parsed = NodesKt.getParsed(node);
        if (
                (requiredType != null && requiredType.isInstance(parsed))
                        && (requiredTag != null && requiredTag.equals(node.getTag()))
        ) {
            return (T) parsed;
        }

        return null;
    }
}
