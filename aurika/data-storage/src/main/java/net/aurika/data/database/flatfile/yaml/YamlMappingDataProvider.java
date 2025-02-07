package net.aurika.data.database.flatfile.yaml;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.data.database.dataprovider.*;
import net.aurika.snakeyaml.extension.nodes.MapNode;
import net.aurika.snakeyaml.extension.nodes.interpret.NodeInterpreter;
import net.aurika.snakeyaml.extension.nodes.NodeUtils;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.*;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.io.Serializable;
import java.util.*;
import java.util.function.*;

/**
 * 通过一个 {@link MappingNode} 和一个字符串键来提供数据.
 * <p>
 * 上级节点已知, 因此可以读和写数据.
 * <p>
 * 当 {@linkplain #name} 不为 {@code null} 时数据从 {@link #obj} 中获取 {@linkplain #name} 对应的下级节点中读取.
 * <p>
 * 当 {@linkplain #name} 为 {@code null} 时数据直接从 {@link #obj} 中获取, 但无法写数据, 相当于退化成了 {@link YamlNodeDataProvider}.
 */
public class YamlMappingDataProvider implements DataProvider, SectionCreatableDataSetter, YamlDataProvider {

    private @Nullable String name;
    private @NotNull MapNode obj;

    public YamlMappingDataProvider(@Nullable String name, @NotNull MapNode obj) {
        Checker.Arg.notNull(obj, "obj");
        this.name = name;
        this.obj = obj;
    }

    @Nullable
    public final String getName$core() {
        return this.name;
    }

    public final void setName$core(@Nullable String string) {
        this.name = string;
    }

    public final @NotNull MappingNode getObj$core() {
        return this.obj.getNode();
    }

    public final void setObj$core(@NotNull MappingNode MappingNode) {
        Checker.Arg.notNull(MappingNode, "mappingNode");
        this.obj = new MapNode(MappingNode);
    }

    private @NotNull MapNode a() {
        String setKey = this.name;
        if (setKey == null) {
            return this.obj;
        }
        Node node = this.obj.getNode(setKey);
        if (!(node instanceof MappingNode)) {
            node = NodeUtils.emptyMapping();
            this.obj.putNode(setKey, node);
        }
        return new MapNode((MappingNode) node);
    }

    @Override
    public @NotNull Node getNode() {
        return this.obj.getNode();
    }

    private @NotNull String getKey() {
        String string = this.name;
        if (string == null) {
            throw new IllegalStateException("No key name set");
        }
        return string;
    }

    @Override
    public @NotNull YamlMappingDataProvider get(@NotNull String key) {
        Checker.Arg.notNull(key, "key");
        return new YamlMappingDataProvider(key, this.a());
    }

    @Override
    public @NotNull YamlMappingDataProvider asSection() {
        return new YamlMappingDataProvider(null, this.a());
    }

    @Override
    public @Nullable String asString(@NotNull Supplier<String> def) {
        Checker.Arg.notNull(def, "def");
        String s = NodeInterpreter.STRING.parse(this.obj.getNode(this.getKey()));
        return s == null ? def.get() : s;
    }

    @Override
    public @Nullable UUID asUUID() {
        String string = this.asString();
        if (string != null) {
            return FastUUID.fromString(string);
        }
        return null;
    }

    @Override
    public @Nullable SimpleBlockLocation asSimpleLocation() {
        String string = this.asString();
        if (string != null) {
            return SimpleBlockLocation.fromDataString(string);
        }
        return null;
    }

    @Override
    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        String string = this.asString();
        if (string != null) {
            return SimpleChunkLocation.fromDataString(string);
        }
        return null;
    }

    @Override
    public @Nullable SimpleLocation asLocation() {
        String string = this.asString();
        if (string != null) {
            return SimpleLocation.fromDataString(string);
        }
        return null;
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Checker.Arg.notNull(def, "");
        Integer i = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.INT, null);
        if (i != null) {
            return i;
        }
        return def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Checker.Arg.notNull(def, "def");
        Long l = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.LONG, null);
        return l != null ? l : def.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Checker.Arg.notNull(def, "def");
        Float f = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.FLOAT, null);
        return f != null ? f : def.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Checker.Arg.notNull(def, "def");
        Double d = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.DOUBLE, null);
        return d != null ? d : def.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Checker.Arg.notNull(def, "def");
        Boolean b = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.BOOLEAN, null);
        return b != null ? b : def.getAsBoolean();
    }

    @Override
    public @NotNull <E, C extends Collection<E>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Checker.Arg.notNull(c, "c");
        Checker.Arg.notNull(dataProcessor, "dataProcessor");
        Node node = this.obj.getNode(this.getKey());
        SequenceNode sequenceNode = node instanceof SequenceNode ? (SequenceNode) node : null;
        if (sequenceNode == null) {
            return c;
        }
        for (Node e : sequenceNode.getValue()) {
            dataProcessor.accept(c, new YamlNodeDataProvider(e));
        }
        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Checker.Arg.notNull(m, "m");
        Checker.Arg.notNull(dataProcessor, "triConsumer");
        Node object2 = this.obj.getNode(this.getKey());
        MappingNode mappingNode = object2 instanceof MappingNode ? (MappingNode) object2 : null;
        if (mappingNode == null) {
            return m;
        }
        for (NodeTuple nodePair : mappingNode.getValue()) {
            ScalarNode keyNode = (ScalarNode) nodePair.getKeyNode();
            YamlNodeDataProvider keyData = new YamlNodeDataProvider(keyNode);
            Node valueNode = nodePair.getValueNode();
            YamlNodeDataProvider valueData = new YamlNodeDataProvider(valueNode);
            dataProcessor.accept(m, keyData, valueData);
        }
        return m;
    }

    private void setPlain(Tag tag, Serializable serializable) {
        this.obj.putNode(this.getKey(), YamlNodeDataProvider.plainNode(tag, serializable));
    }

    @Override
    public void setString(@Nullable String value) {
        if (value == null) return;
        this.obj.putNode(this.getKey(), new ScalarNode(Tag.STR, value, ScalarStyle.DOUBLE_QUOTED));
    }

    @Override
    public void setInt(int value) {
        Tag tag = Tag.INT;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.setPlain(tag, value);
    }

    @Override
    public void setLong(long value) {
        Tag tag = Tag.INT;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.setPlain(tag, value);
    }

    @Override
    public void setFloat(float value) {
        Tag tag = Tag.FLOAT;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.setPlain(tag, value);
    }

    @Override
    public void setDouble(double value) {
        Tag tag = Tag.FLOAT;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.setPlain(tag, value);
    }

    @Override
    public void setBoolean(boolean value) {
        Tag tag = Tag.BOOL;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.setPlain(tag, value);
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value == null) return;
        this.setPlain(Tag.STR, FastUUID.toString(value));
    }

    @Override
    public void setSimpleLocation(@Nullable SimpleBlockLocation simpleLocation) {
        if (simpleLocation == null) return;
        this.setString(simpleLocation.asDataString());
    }

    @Override
    public void setSimpleChunkLocation(@Nullable SimpleChunkLocation value) {
        if (value == null) return;
        this.setString(value.asDataString());
    }

    @Override
    public void setLocation(@Nullable SimpleLocation location) {
        if (location == null) return;
        this.setString(location.asDataString());
    }

    @Override
    public <E> void setCollection(@NotNull Collection<? extends E> value, @NotNull BiConsumer<SectionCreatableDataSetter, E> biConsumer) {
        Checker.Arg.notNull(value, "value");
        Checker.Arg.notNull(biConsumer, "");
        if (value.isEmpty()) return;
        SequenceNode sequenceNode = new SequenceNode(Tag.SEQ, new ArrayList<>(), FlowStyle.AUTO);
        for (E e : value) {
            biConsumer.accept(createProvider$core(sequenceNode), e);
        }
        this.obj.putNode(this.getKey(), sequenceNode);
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> mappingSetterHandler) {
        Checker.Arg.notNull(value, "value");
        Checker.Arg.notNull(mappingSetterHandler, "mappingSetterHandler");
        if (value.isEmpty()) return;
        MappingNode mappingNode = NodeUtils.emptyMapping();
        for (Map.Entry<K, ? extends V> entry : value.entrySet()) {
            K k = entry.getKey();
            V v = entry.getValue();
            mappingSetterHandler.map(k, new StringMappedIdSetter(name -> new YamlMappingDataProvider(name, new MapNode(mappingNode))), v);
        }
        this.obj.putNode(this.getKey(), mappingNode);
    }

    @Override
    public @NotNull YamlMappingDataProvider createSection() {
        MappingNode mappingNode = NodeUtils.emptyMapping();
        this.obj.putNode(this.getKey(), mappingNode);
        return new YamlMappingDataProvider(null, new MapNode(mappingNode));
    }

    @Override
    public @NotNull SectionableDataSetter createSection(@NotNull String key) {
        Checker.Arg.notNull(key, "key");
        if (this.name != null) {
            throw new IllegalStateException("Previous name not handled: " + this.name + " -> " + key);
        }
        MappingNode mappingNode = NodeUtils.emptyMapping();
        this.obj.putNode(key, mappingNode);
        return new YamlMappingDataProvider(null, new MapNode(mappingNode));
    }

    public static @NotNull DataProvider createProvider$core(@NotNull Node node) {
        Checker.Arg.notNull(node, "");
        if (node instanceof MappingNode) {
            List<NodeTuple> value = new ArrayList<>();
            value.addAll(((MappingNode) node).getValue());
            return new YamlMappingDataProvider(null, new MapNode(new MappingNode(Tag.MAP, value, FlowStyle.BLOCK)));
        }
        return new YamlNodeDataProvider(node);
    }
}
