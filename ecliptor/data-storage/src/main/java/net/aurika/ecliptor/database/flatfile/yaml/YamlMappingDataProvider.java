package net.aurika.ecliptor.database.flatfile.yaml;

import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.database.dataprovider.*;
import net.aurika.util.function.FloatSupplier;
import net.aurika.util.function.TriConsumer;
import net.aurika.util.snakeyaml.nodes.MapNode;
import net.aurika.util.snakeyaml.nodes.NodeUtils;
import net.aurika.util.snakeyaml.nodes.interpret.NodeInterpreter;
import net.aurika.util.unsafe.fn.Fn;
import net.aurika.util.uuid.FastUUID;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;
import org.snakeyaml.engine.v2.nodes.*;

import java.io.Serializable;
import java.util.*;
import java.util.function.*;

public class YamlMappingDataProvider implements DataProvider, SectionCreatableDataSetter, YamlDataProvider {

    private @Nullable String name;
    private @NotNull MapNode obj;

    public YamlMappingDataProvider(@Nullable String name, @NotNull MapNode obj) {
        Validate.Arg.notNull(obj, "obj");
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
        Validate.Arg.notNull(MappingNode, "mappingNode");
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
        Validate.Arg.notNull(key, "key");
        return new YamlMappingDataProvider(key, this.a());
    }

    @Override
    public @NotNull YamlMappingDataProvider asSection() {
        return new YamlMappingDataProvider(null, this.a());
    }

    @Override
    public @Nullable String asString(@NotNull Supplier<String> def) {
        Validate.Arg.notNull(def, "def");
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
    public <T extends StructuredDataObject> @Nullable T asStruct(@NotNull DataStructSchema<T> template) {
        Validate.Arg.notNull(template, "template");
        String s = asString();
        return s != null ? template.plainToObject(s) : null;
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Validate.Arg.notNull(def, "def");
        Integer i = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.INT, null);
        return i != null ? i : def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Validate.Arg.notNull(def, "def");
        Long l = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.LONG, null);
        return l != null ? l : def.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Validate.Arg.notNull(def, "def");
        Float f = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.FLOAT, null);
        return f != null ? f : def.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Validate.Arg.notNull(def, "def");
        Double d = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.DOUBLE, null);
        return d != null ? d : def.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Validate.Arg.notNull(def, "def");
        Boolean b = this.obj.getAndInterpret(this.getKey(), NodeInterpreter.BOOLEAN, null);
        return b != null ? b : def.getAsBoolean();
    }

    @Override
    public @NotNull <E, C extends Collection<E>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Validate.Arg.notNull(c, "c");
        Validate.Arg.notNull(handler, "dataProcessor");
        Node node = this.obj.getNode(this.getKey());
        SequenceNode sequenceNode = node instanceof SequenceNode ? (SequenceNode) node : null;
        if (sequenceNode == null) {
            return c;
        }
        for (Node e : sequenceNode.getValue()) {
            handler.accept(c, new YamlNodeDataProvider(e));
        }
        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
        Validate.Arg.notNull(m, "m");
        Validate.Arg.notNull(handler, "triConsumer");
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
            handler.accept(m, keyData, valueData);
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
        this.setPlain(Tag.INT, value);
    }

    @Override
    public void setLong(long value) {
        this.setPlain(Tag.INT, value);
    }

    @Override
    public void setFloat(float value) {
        this.setPlain(Tag.FLOAT, value);
    }

    @Override
    public void setDouble(double value) {
        this.setPlain(Tag.FLOAT, value);
    }

    @Override
    public void setBoolean(boolean value) {
        this.setPlain(Tag.BOOL, value);
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value == null) return;
        this.setPlain(Tag.STR, FastUUID.toString(value));
    }

    @Override
    public void setStruct(@Nullable StructuredDataObject value) {
        if (value != null) {
            DataStructSchema<?> schema = value.DataStructSchema();
            setString(schema.objectToPlain(Fn.cast(value)));
        }
    }

    @Override
    public <E> void setCollection(@NotNull Collection<? extends E> value, @NotNull BiConsumer<SectionCreatableDataSetter, E> handler) {
        Validate.Arg.notNull(value, "value");
        Validate.Arg.notNull(handler, "handler");
        if (value.isEmpty()) return;
        SequenceNode sequenceNode = new SequenceNode(Tag.SEQ, new ArrayList<>(), FlowStyle.AUTO);
        for (E e : value) {
            handler.accept(createProvider$core(sequenceNode), e);
        }
        this.obj.putNode(this.getKey(), sequenceNode);
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        Validate.Arg.notNull(value, "value");
        Validate.Arg.notNull(handler, "handler");
        if (value.isEmpty()) return;
        MappingNode mappingNode = NodeUtils.emptyMapping();
        for (Map.Entry<K, ? extends V> entry : value.entrySet()) {
            K k = entry.getKey();
            V v = entry.getValue();
            handler.map(k, new StringMappedIdSetter(name -> new YamlMappingDataProvider(name, new MapNode(mappingNode))), v);
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
        Validate.Arg.notNull(key, "key");
        if (this.name != null) {
            throw new IllegalStateException("Previous name not handled: " + this.name + " -> " + key);
        }
        MappingNode mappingNode = NodeUtils.emptyMapping();
        this.obj.putNode(key, mappingNode);
        return new YamlMappingDataProvider(null, new MapNode(mappingNode));
    }

    public static @NotNull DataProvider createProvider$core(@NotNull Node node) {
        Validate.Arg.notNull(node, "node");
        if (node instanceof MappingNode) {
            List<NodeTuple> value = new ArrayList<>(((MappingNode) node).getValue());
            return new YamlMappingDataProvider(null, new MapNode(new MappingNode(Tag.MAP, value, FlowStyle.BLOCK)));
        }
        return new YamlNodeDataProvider(node);
    }
}
