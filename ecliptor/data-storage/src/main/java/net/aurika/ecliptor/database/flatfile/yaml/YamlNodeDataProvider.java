package net.aurika.ecliptor.database.flatfile.yaml;

import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.database.dataprovider.*;
import net.aurika.util.function.FloatSupplier;
import net.aurika.util.function.TriConsumer;
import net.aurika.util.snakeyaml.nodes.MapNode;
import net.aurika.util.snakeyaml.nodes.NodeUtils;
import net.aurika.util.snakeyaml.nodes.interpret.NodeInterpretContext;
import net.aurika.util.snakeyaml.nodes.interpret.NodeInterpreter;
import net.aurika.util.unsafe.fn.Fn;
import net.aurika.util.uuid.FastUUID;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.*;

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
        Validate.Arg.notNull(node, "node");
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
        Validate.Arg.notNull(key, "key");
        return new YamlMappingDataProvider(key, new MapNode((MappingNode) this.node));
    }

    @Override
    public @NotNull DataProvider asSection() {
        return this;
    }

    @Override
    public String asString(@NotNull Supplier<String> def) {
        Validate.Arg.notNull(def, "def");
        String s = this.interpret(NodeInterpreter.STRING);
        return s == null ? def.get() : s;
    }

    @Override
    public @Nullable UUID asUUID() {
        String s = this.asString();
        return s == null ? null : FastUUID.fromString(s);
    }

    @Override
    public <T extends StructuredDataObject> T asStruct(@NotNull DataStructSchema<T> template) {
        Validate.Arg.notNull(template, "template");
        String s = asString();
        return s != null ? template.plainToObject(s) : null;
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Validate.Arg.notNull(def, "def");
        Integer i = this.interpret(NodeInterpreter.INT);
        if (i != null) {
            return i;
        }
        return def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier function0) {
        Validate.Arg.notNull(function0, "def");
        Long l = this.interpret(NodeInterpreter.LONG);
        if (l != null) {
            return l;
        }
        return function0.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier function0) {
        Validate.Arg.notNull(function0, "def");
        Float f = this.interpret(NodeInterpreter.FLOAT);
        if (f != null) {
            return f;
        }
        return function0.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier function0) {
        Validate.Arg.notNull(function0, "def");
        Double d = this.interpret(NodeInterpreter.DOUBLE);
        if (d != null) {
            return d;
        }
        return function0.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Validate.Arg.notNull(def, "def");
        Boolean b = this.interpret(NodeInterpreter.BOOLEAN);
        if (b != null) {
            return b;
        }
        return def.getAsBoolean();
    }

    @Override
    public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Validate.Arg.notNull(c, "c");
        Validate.Arg.notNull(handler, "handler");
        for (final Node node : ((SequenceNode) this.node).getValue()) {
            Objects.requireNonNull(node);
            handler.accept(c, new YamlNodeDataProvider(node));
        }
        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull final M m, @NotNull final TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
        Validate.Arg.notNull(m, "m");
        Validate.Arg.notNull(handler, "handler");
        final Node node = this.node;
        for (final NodeTuple tuple : ((MappingNode) node).getValue()) {
            YamlNodeDataProvider yamlNodeDataProvider = new YamlNodeDataProvider(tuple.getKeyNode());
            handler.accept(m, yamlNodeDataProvider, new YamlNodeDataProvider(tuple.getValueNode()));
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

    @Override
    public void setStruct(@NotNull StructuredDataObject value) {
        Validate.Arg.notNull(value, "value");
        DataStructSchema<?> schema = value.DataStructSchema();
        Objects.requireNonNull(schema, "schema");
        setString(schema.objectToPlain(Fn.cast(value)));
    }

    public static @NotNull ScalarNode plainNode(@NotNull Tag tag, @NotNull Object obj) {
        Validate.Arg.notNull(tag, "tag");
        Validate.Arg.notNull(obj, "obj");
        return new ScalarNode(tag, obj.toString(), ScalarStyle.PLAIN);
    }
}
