package net.aurika.ecliptor.database.flatfile.yaml;

import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.common.function.FloatSupplier;
import net.aurika.common.function.TriConsumer;
import net.aurika.common.snakeyaml.nodes.NodeUtils;
import net.aurika.common.snakeyaml.nodes.interpret.NodeInterpreter;
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
    private @NotNull MappingNode obj;

    public YamlMappingDataProvider(@Nullable String name, @NotNull MappingNode obj) {
        Validate.Arg.notNull(obj, "obj");
        this.name = name;
        this.obj = obj;
    }

    public final @Nullable String getName$core() {
        return this.name;
    }

    public final void setName$core(@Nullable String name) {
        this.name = name;
    }

    public final @NotNull MappingNode getObj$core() {
        return this.obj;
    }

    public final void setObj$core(@NotNull MappingNode obj) {
        Validate.Arg.notNull(obj, "obj");
        this.obj = obj;
    }

    private @NotNull MappingNode __root() {
        String _name = this.name;
        if (_name == null) {
            return this.obj;
        }
        Node root = NodeUtils.getNode(this.obj, _name);
        if (!(root instanceof MappingNode)) {
            root = NodeUtils.emptyMapping();
            NodeUtils.putNode(this.obj, _name, root);
        }
        return (MappingNode) root;
    }

    private @NotNull String __require_name() {
        String string = this.name;
        if (string == null) {
            throw new IllegalStateException("No key name set");
        }
        return string;
    }

    @Override
    public @NotNull MappingNode getNode() {
        return this.obj;
    }

    @Override
    public @NotNull YamlMappingDataProvider get(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        return new YamlMappingDataProvider(key, this.__root());
    }

    @Override
    public @NotNull YamlMappingDataProvider asSection() {
        return new YamlMappingDataProvider(null, this.__root());
    }

    @Override
    public @Nullable String asString(@NotNull Supplier<String> def) {
        Validate.Arg.notNull(def, "def");
        String s = NodeInterpreter.STRING.parse(NodeUtils.getNode(this.obj, __require_name()));
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
        Integer i = NodeInterpreter.INT.parse(NodeUtils.getNode(obj, __require_name()));
        return i != null ? i : def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Validate.Arg.notNull(def, "def");
        Long l = NodeInterpreter.LONG.parse(NodeUtils.getNode(obj, __require_name()));
        return l != null ? l : def.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Validate.Arg.notNull(def, "def");
        Float f = NodeInterpreter.FLOAT.parse(NodeUtils.getNode(obj, __require_name()));
        return f != null ? f : def.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Validate.Arg.notNull(def, "def");
        Double d = NodeInterpreter.DOUBLE.parse(NodeUtils.getNode(obj, __require_name()));
        return d != null ? d : def.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Validate.Arg.notNull(def, "def");
        Boolean b = NodeInterpreter.BOOLEAN.parse(NodeUtils.getNode(obj, __require_name()));
        return b != null ? b : def.getAsBoolean();
    }

    @Override
    public @NotNull <E, C extends Collection<E>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Validate.Arg.notNull(c, "c");
        Validate.Arg.notNull(handler, "handler");
        Node root = NodeUtils.getNode(obj, __require_name());
        SequenceNode seqRoot = root instanceof SequenceNode ? (SequenceNode) root : null;
        if (seqRoot != null) {
            for (Node rootElement : seqRoot.getValue()) {
                handler.accept(c, new YamlNodeDataProvider(rootElement));
            }
        }
        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
        Validate.Arg.notNull(m, "m");
        Validate.Arg.notNull(handler, "handler");
        Node root = NodeUtils.getNode(obj, __require_name());
        MappingNode mappingRoot = root instanceof MappingNode ? (MappingNode) root : null;
        if (mappingRoot != null) {
            for (NodeTuple tuple : mappingRoot.getValue()) {
                handler.accept(
                        m,
                        new YamlNodeDataProvider(tuple.getKeyNode()),
                        new YamlNodeDataProvider(tuple.getValueNode())
                );
            }
        }
        return m;
    }

    private void setPlain(Tag tag, Serializable serializable) {
        NodeUtils.putNode(obj, __require_name(), YamlNodeDataProvider.plainNode(tag, serializable));
    }

    @Override
    public void setString(@Nullable String value) {
        if (value == null) return;
        NodeUtils.putNode(obj, __require_name(), new ScalarNode(Tag.STR, value, ScalarStyle.DOUBLE_QUOTED));
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
            DataStructSchema<?> schema = value.dataStructSchema();
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
        NodeUtils.putNode(obj, __require_name(), sequenceNode);
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
            handler.map(k, new StringMappedIdSetter(name -> new YamlMappingDataProvider(name, new MappingNode(mappingNode))), v);
        }
        this.obj.putNode(__require_name(), mappingNode);
    }

    @Override
    public @NotNull YamlMappingDataProvider createSection() {
        MappingNode mappingNode = NodeUtils.emptyMapping();
        NodeUtils.putNode(obj, this.__require_name(), mappingNode);
        return new YamlMappingDataProvider(null, mappingNode);
    }

    @Override
    public @NotNull SectionableDataSetter createSection(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        if (name != null) {
            throw new IllegalStateException("Previous name not handled: " + this.name + " -> " + key);
        }
        MappingNode mappingNode = NodeUtils.emptyMapping();
        NodeUtils.putNode(obj, key, mappingNode);
        return new YamlMappingDataProvider(null,  mappingNode);
    }

    public static @NotNull DataProvider createProvider$core(@NotNull Node node) {
        Validate.Arg.notNull(node, "node");
        if (node instanceof MappingNode) {
            List<NodeTuple> value = new ArrayList<>(((MappingNode) node).getValue());
            return new YamlMappingDataProvider(null, new MappingNode(Tag.MAP, value, FlowStyle.BLOCK));
        }
        return new YamlNodeDataProvider(node);
    }
}
