package net.aurika.data.database.flatfile.yaml;

import net.aurika.checker.Checker;
import net.aurika.data.database.dataprovider.*;
import net.aurika.utils.function.FloatSupplier;
import net.aurika.utils.snakeyaml.nodes.interpret.NodeInterpretContext;
import net.aurika.utils.snakeyaml.nodes.interpret.NodeInterpreter;
import net.aurika.utils.snakeyaml.nodes.MapNode;
import net.aurika.utils.snakeyaml.nodes.NodeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.*;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.*;

/**
 * 上级节点未知, 因此无法更改数据, 只能读取
 */
public class YamlNodeDataProvider implements DataProvider, SectionCreatableDataSetter, YamlDataProvider {

    private final @NotNull Node node;

    public YamlNodeDataProvider(@NotNull Node node) {
        Checker.Arg.notNull(node, "node");
        this.node = node;
    }

    public final @NotNull Node getNode$core() {
        return this.node;
    }

    @Override
    public @NotNull DataProvider createSection(@NotNull String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull SectionableDataSetter createSection() {
        MappingNode mappingNode = NodeUtils.emptyMapping();
        if (this.node instanceof SequenceNode) {
            ((SequenceNode) this.node).getValue().add(mappingNode);
            return new YamlMappingDataProvider(null, new MapNode(mappingNode));
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Node getNode() {
        return this.node;
    }

    private <T> T interpret(NodeInterpreter<T> nodeInterpreter) {
        return nodeInterpreter.parse(new NodeInterpretContext<>(this.node));
    }

    @Override
    public @NotNull DataProvider get(@NotNull String key) {
        Checker.Arg.notNull(key, "key");
        return new YamlMappingDataProvider(key, new MapNode((MappingNode) this.node));
    }

    @Override
    public @NotNull DataProvider asSection() {
        return this;
    }

    @Override
    public String asString(@NotNull Supplier<String> def) {
        Checker.Arg.notNull(def, "def");
        String s = this.interpret(NodeInterpreter.STRING);
        return s == null ? def.get() : s;
    }

    @Override
    public @Nullable UUID asUUID() {
        String s = this.asString();
        return s == null ? null : FastUUID.fromString(s);
    }

    @Override
    public @Nullable SimpleBlockLocation asSimpleLocation() {
        String s = this.asString();
        return s == null ? null : SimpleBlockLocation.fromDataString(s);
    }

    @Override
    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        String s = this.asString();
        return s == null ? null : SimpleChunkLocation.fromDataString(s);
    }

    @Override
    public @Nullable SimpleLocation asLocation() {
        String string = this.asString();
        return string == null ? null : SimpleLocation.fromDataString(string);
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Checker.Arg.notNull(def, "def");
        Integer i = this.interpret(NodeInterpreter.INT);
        if (i != null) {
            return i;
        }
        return def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier function0) {
        Checker.Arg.notNull(function0, "def");
        Long l = this.interpret(NodeInterpreter.LONG);
        if (l != null) {
            return l;
        }
        return function0.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier function0) {
        Checker.Arg.notNull(function0, "def");
        Float f = this.interpret(NodeInterpreter.FLOAT);
        if (f != null) {
            return f;
        }
        return function0.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier function0) {
        Checker.Arg.notNull(function0, "def");
        Double d = this.interpret(NodeInterpreter.DOUBLE);
        if (d != null) {
            return d;
        }
        return function0.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Checker.Arg.notNull(def, "def");
        Boolean b = this.interpret(NodeInterpreter.BOOLEAN);
        if (b != null) {
            return b;
        }
        return def.getAsBoolean();
    }

    @Override
    public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> biConsumer) {
        Checker.Arg.notNull(c, "");
        Checker.Arg.notNull(biConsumer, "");
        final Node a = this.node;
        Intrinsics.checkNotNull(a);
        for (final Node node : ((SequenceNode) a).getValue()) {
            Intrinsics.checkNotNull(node);
            biConsumer.accept(c, new YamlNodeDataProvider(node));
        }
        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull final M m, @NotNull final TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Checker.Arg.notNull(m, "");
        Checker.Arg.notNull(dataProcessor, "");
        final Node a = this.node;
        Intrinsics.checkNotNull(a);
        for (final NodeTuple nodePair : ((MappingNode) a).getValue()) {
            ScalarNode key = (ScalarNode) nodePair.getKeyNode();
            Intrinsics.checkNotNullExpressionValue(key, "");
            YamlNodeDataProvider yamlNodeDataProvider = new YamlNodeDataProvider(key);
            Node value = nodePair.getValueNode();
            Intrinsics.checkNotNullExpressionValue(value, "");
            dataProcessor.accept(m, yamlNodeDataProvider, new YamlNodeDataProvider(value));
        }
        return m;
    }

    private void a(Node node) {
        if (this.node instanceof SequenceNode) {
            ((SequenceNode) this.node).getValue().add(node);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setString(@NotNull String value) {
        Objects.requireNonNull(value, "value");
        this.a(new ScalarNode(Tag.STR, value, ScalarStyle.DOUBLE_QUOTED));
    }

    @Override
    public void setInt(int value) {
        this.a(YamlNodeDataProvider.plainNode(Tag.INT, value));
    }

    @Override
    public void setLong(long value) {
        this.a(YamlNodeDataProvider.plainNode(Tag.INT, value));
    }

    @Override
    public void setFloat(float value) {
        this.a(YamlNodeDataProvider.plainNode(Tag.FLOAT, value));
    }

    @Override
    public void setDouble(double value) {
        this.a(YamlNodeDataProvider.plainNode(Tag.FLOAT, value));
    }

    @Override
    public void setBoolean(boolean value) {
        this.a(YamlNodeDataProvider.plainNode(Tag.BOOL, value));
    }

    @Override
    public void setSimpleLocation(@Nullable SimpleBlockLocation value) {
        if (value == null) return;
        this.setString(value.toString());
    }

    @Override
    public void setSimpleChunkLocation(@Nullable SimpleChunkLocation value) {
        if (value == null) return;
        this.setString(value.toString());
    }

    @Override
    public void setLocation(@Nullable SimpleLocation location) {
        if (location == null) return;
        this.setString(location.asDataString());
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> collection, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> map, @NotNull MappingSetterHandler<K, V> handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUUID(@NotNull UUID uuid) {
        this.setString(FastUUID.toString(uuid));
    }

    public static @NotNull ScalarNode plainNode(@NotNull Tag tag, @NotNull Object obj) {
        Checker.Arg.notNull(tag, "tag");
        Checker.Arg.notNull(obj, "obj");
        return new ScalarNode(tag, obj.toString(), ScalarStyle.PLAIN);
    }
}
