package top.auspice.config.yaml.snakeyaml.node;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.*;
import top.auspice.api.annotations.RecursiveMethod;
import top.auspice.config.annotations.ImplicitConstructed;
import top.auspice.config.yaml.snakeyaml.common.NodeReplacer;
import top.auspice.utils.Checker;
import top.auspice.utils.Generics;
import top.auspice.utils.internal.Fn;

import java.util.*;
import java.util.function.Predicate;

public final class NodeUtils {
    private static final Logger log = LoggerFactory.getLogger(NodeUtils.class);

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
                    $this$copyIfDoesntExist.getValue().add(new NodeTuple(NodeUtils.cloneNode(copyFromPair.getKeyNode()), NodeUtils.cloneNode(copyFromPair.getValueNode())));
                } else {
                    Node currentNode = currentPair.getValueNode();
                    if (currentNode instanceof MappingNode && copyNode instanceof MappingNode) {
                        copyIfDoesntExist((MappingNode) currentNode, (MappingNode) copyNode, path, ignore);
                    }
                }
            }
        }
    }

    public static Node cloneNode(Node node) {
        if (node instanceof ScalarNode scalarNode) {     //TODO isResolved?
            return cloneNode(scalarNode);
        }
        if (node instanceof SequenceNode sequenceNode) {
            return cloneNode(sequenceNode);
        }
        if (node instanceof MappingNode mappingNode) {
            return cloneNode(mappingNode);
        }
        if (node instanceof AnchorNode anchorNode) {
            return cloneNode(anchorNode);
        }
        throw new UnsupportedOperationException("Unsupported node type");
    }

    @Contract("_ -> new")
    public static ScalarNode cloneNode(ScalarNode node) {  //TODO isResolved?
        return new ScalarNode(node.getTag(), true, node.getValue(), node.getScalarStyle(), node.getStartMark(), node.getEndMark());
    }

    @Contract("_ -> new")
    public static SequenceNode cloneNode(SequenceNode node) {
        List<Node> value = new ArrayList<>();
        for (Node valueNode : node.getValue()) {
            value.add(cloneNode(valueNode));
        }
        return new SequenceNode(node.getTag(), true, value, node.getFlowStyle(), node.getStartMark(), node.getEndMark());
    }

    @Contract("_ -> new")
    public static MappingNode cloneNode(MappingNode node) {
        List<NodeTuple> value = new ArrayList<>();
        for (NodeTuple valueTuple : node.getValue()) {
            value.add(new NodeTuple(cloneNode(valueTuple.getKeyNode()), cloneNode(valueTuple.getValueNode())));
        }
        return new MappingNode(node.getTag(), true, value, node.getFlowStyle(), node.getStartMark(), node.getEndMark());
    }

    @Contract("_ -> new")
    public static AnchorNode cloneNode(AnchorNode node) {
        return new AnchorNode(cloneNode(node.getRealNode()));
    }

    public static @Nullable Node putNode(MappingNode mappingNode, @NotNull String key, @NotNull Node value) {
        return mappingNode == null ? null : putNode(mappingNode.getValue(), key, value);
    }

    public static @Nullable Node putNode(List<NodeTuple> tupleList, @NotNull String key, @NotNull Node value) {
        Checker.Argument.checkNotNull(key, "key");
        Checker.Argument.checkNotNull(value, "value");
        if (tupleList != null) {
            Node keyNode = null;
            for (NodeTuple tuple : tupleList) {  // Search for existent node
                keyNode = tuple.getKeyNode();
                keyNode = unpackAnchor(keyNode);
                if (keyNode instanceof ScalarNode scalarKey && scalarKey.getValue().equals(key)) {
                    tupleList.remove(tuple);
                    return tuple.getValueNode();
                }
            }
            if (keyNode == null) {
                keyNode = key(key);
            }
            tupleList.add(new NodeTuple(keyNode, value));
        }
        return null;
    }

    public static @Nullable Node getNode(@Nullable MappingNode mappingNode, @Nullable String key) {
        return mappingNode == null ? null : getNode(mappingNode.getValue(), key);
    }

    public static @Nullable Node getNode(@Nullable List<NodeTuple> tupleList, @Nullable String key) {
        if (tupleList != null) {
            for (NodeTuple tuple : tupleList) {
                Node keyNode = tuple.getKeyNode();
                keyNode = unpackAnchor(keyNode);
                if (keyNode instanceof ScalarNode scalarKey && scalarKey.getValue().equals(key)) {
                    return tuple.getValueNode();
                }
            }
        }
        return null;
    }

    public static @Nullable NodeTuple getPair(@Nullable MappingNode mappingNode, @Nullable String key) {
        return getPair(mappingNode == null ? null : mappingNode.getValue(), key);
    }

    public static @Nullable NodeTuple getPair(@Nullable List<NodeTuple> tupleList, @Nullable String key) {
        if (tupleList != null) {
            for (NodeTuple tuple : tupleList) {
                Node keyNode = tuple.getKeyNode();
                keyNode = unpackAnchor(keyNode);
                if (keyNode instanceof ScalarNode scalarKey && scalarKey.getValue().equals(key)) {
                    return tuple;
                }
            }
        }
        return null;
    }

    public static @Nullable Node remove(@Nullable MappingNode mappingNode, @Nullable String key) {
        return remove(mappingNode == null ? null : mappingNode.getValue(), key);
    }

    public static @Nullable Node remove(@Nullable List<NodeTuple> tupleList, @Nullable String key) {
        if (tupleList != null) {
            for (NodeTuple tuple : tupleList) {
                Node keyNode = tuple.getKeyNode();
                keyNode = unpackAnchor(keyNode);
                if (keyNode instanceof ScalarNode scalarKey && scalarKey.getValue().equals(key)) {
                    tupleList.remove(tuple);
                    return tuple.getValueNode();
                }
            }
        }
        return null;
    }

    public static boolean equalsScalarValue(Node node, String value) {
        node = unpackAnchor(node);
        if (node instanceof ScalarNode scalarNode) {
            return scalarNode.getValue().equals(value);
        }

        return false;
    }

    /**
     * 若为 {@link ScalarNode} ,返回这个 {@linkplain Node} 作为 {@linkplain ScalarNode} 的值
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

    @RecursiveMethod
    @ImplicitConstructed
    public static @Nullable Object deepGetParsed(Node node) {
        node = unpackAnchor(node);
        Object parsed = NodesKt.getParsed(node);
        if (parsed != null) {
            return parsed;
        }
        if (node instanceof MappingNode mappingNode) {
            parsed = new HashMap<String, Object>();
            for (NodeTuple tuple : mappingNode.getValue()) {
                ((Map<String, Object>) parsed).put(((ScalarNode) tuple.getKeyNode()).getValue(), deepGetParsed(tuple.getValueNode()));
            }
            NodesKt.cacheConstructed(node, parsed);
            return parsed;
        }
        if (node instanceof SequenceNode sequenceNode) {
            return deepGetParsed(sequenceNode);
        }

        return parsed;
    }

    @RecursiveMethod
    @ImplicitConstructed
    public static List<?> deepGetParsed(SequenceNode node) {
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

    @RecursiveMethod
    @ImplicitConstructed
    public static Map<String, ?> deepGetParsed(MappingNode node) {
        Object parsed = NodesKt.getParsed(node);
        if (parsed instanceof Map<?, ?>) {
            parsed = Generics.filterKeyType(((Map<?, ?>) parsed), String.class);
            if (parsed != null) {
                return Fn.cast(parsed);
            }
        }
        parsed = new HashMap<String, Object>();
        for (NodeTuple tuple : node.getValue()) {
            ((Map<String, Object>) parsed).put(((ScalarNode) tuple.getKeyNode()).getValue(), deepGetParsed(tuple.getValueNode()));
        }
        NodesKt.cacheConstructed(node, parsed);
        return Fn.cast(parsed);
    }

    @RecursiveMethod
    @Contract("null -> null; !null -> !null")
    public static Node unpackAnchor(Node node) {
        if (node instanceof AnchorNode anchorNode) {
            return unpackAnchor(anchorNode.getRealNode());
        }
        return node;
    }

    public static @NotNull ScalarNode key(@NotNull String key) {
        Checker.Argument.checkNotNull(key, "key");
        if (key.contains(" ") || key.contains(":")) {
            return new ScalarNode(Tag.STR, key, ScalarStyle.SINGLE_QUOTED);
        }
        return new ScalarNode(Tag.STR, key, ScalarStyle.PLAIN);
    }

    /**
     * @param requiredElementType 需要的列表项目类型, 为 null 时则是不验证
     * @param requiredElementTag  需要的列表项目标签, 为 null 时则不验证
     */
    public static @Nullable <T> List<@NotNull T> filterList(@Nullable SequenceNode listNode, @Nullable Class<T> requiredElementType, @Nullable Tag requiredElementTag) {
        if (listNode == null) return null;

        boolean allRight = true;
        List<T> list = new LinkedList<>();
        for (Node element : listNode.getValue()) {
            if (requiredElementTag != null && !requiredElementTag.equals(element.getTag())) {
                allRight = false;
                break;
            }

            @Nullable Object parsed = NodesKt.getParsed(element);
            if (requiredElementType != null && !requiredElementType.isInstance(parsed)) {
                allRight = false;
                break;
            }

            list.add(Fn.cast(parsed));
        }

        return allRight ? list : null;
    }

    public static @Nullable <T> T filterObject(@Nullable Node node, @Nullable Class<T> requiredType, @Nullable Tag requiredTag) {
        if (node == null) return null;

        Object parsed = NodesKt.getParsed(node);
        if (
                (requiredType != null && requiredType.isInstance(parsed))
                        && (requiredTag != null && requiredTag.equals(node.getTag()))
        ) {
            return Fn.cast(parsed);
        }

        return null;
    }
}
