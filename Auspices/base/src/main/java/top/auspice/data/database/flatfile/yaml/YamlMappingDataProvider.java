package top.auspice.data.database.flatfile.yaml;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.nodes.*;
import top.auspice.config.yaml.snakeyaml.node.NodeUtils;
import top.auspice.data.database.dataprovider.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class YamlMappingDataProvider implements DataProvider, SectionCreatableDataSetter, YamlDataProvider {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private String name;
    @NotNull
    private MappingNode map;

    public YamlMappingDataProvider(@Nullable String name, @NotNull MappingNode mappingNode) {
        Intrinsics.checkNotNullParameter(mappingNode, "");
        this.name = name;
        this.map = mappingNode;
    }

    @Nullable
    public String getName$core() {
        return this.name;
    }

    public void setName$core(@Nullable String string) {
        this.name = string;
    }

    @NotNull
    public MappingNode getObj$core() {
        return this.map;
    }

    public void setObj$core(@NotNull MappingNode MappingNode) {
        Intrinsics.checkNotNullParameter(MappingNode, "");
        this.map = MappingNode;
    }

    private MappingNode a() {
        if (this.name == null) {
            return this.map;
        }
        String stringArray = new String[1];
        Object object = stringArray;
        String string = this.name;
        Intrinsics.checkNotNull(string);
        stringArray[0] = string;
        Node node = NodeUtils.getNode(this.map, (String) object);
        if (node == null) {
            String[] stringArray2 = new String[1];
            object = stringArray2;
            stringArray2[0] = this.name;
            object = this.map.createSection((String) object);
        }
        Intrinsics.checkNotNull(object);
        return object;
    }

    @Override
    @NotNull
    public Node getNode() {
        return this.map;
    }

    private String b() {
        String string = this.name;
        if (string == null) {
            throw new IllegalStateException("No key name set");
        }
        return string;
    }

    @Override
    @NotNull
    public DataProvider get(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "");
        return new YamlMappingDataProvider(key, this.a());
    }

    @Override
    public @NotNull DataProvider asSection() {
        return new YamlMappingDataProvider(null, this.a());
    }

    @Override
    @Nullable
    public String asString(@NotNull Supplier<String> function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        String string = this.map.getString(this.b());
        if (string == null) {
            string = (String) function0.invoke();
        }
        return string;
    }

    @Override
    @Nullable
    public UUID asUUID() {
        String string = this.asString();
        if (string != null) {
            String string2 = string;
            return FastUUID.fromString(string);
        }
        return null;
    }

    @Override
    @Nullable
    public SimpleLocation asSimpleLocation() {
        String string = this.asString();
        if (string != null) {
            String string2 = string;
            return SimpleLocation.fromString(string);
        }
        return null;
    }

    @Override
    @NotNull
    public SimpleChunkLocation asSimpleChunkLocation() {
        String string = this.asString((Function0<String>) ((Function0) 1.name))
        Intrinsics.checkNotNull(string);
        SimpleChunkLocation simpleChunkLocation = SimpleChunkLocation.fromString(string);
        Intrinsics.checkNotNullExpressionValue(simpleChunkLocation, "");
        return simpleChunkLocation;
    }

    @Override
    @Nullable
    public Location asLocation() {
        String string = this.asString();
        if (string != null) {
            String string2 = string;
            return LocationUtils.fromString(string);
        }
        return null;
    }

    @Override
    public int asInt(@NotNull Function0<Integer> function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        Integer n = this.map.get(NodeInterpreter.INT, NodeInterpretContext.Companion.empty(), this.b());
        if (n != null) {
            return n;
        }
        return ((Number) function0.invoke()).intValue();
    }

    @Override
    public long asLong(@NotNull Function0<Long> stringArray) {
        Intrinsics.checkNotNullParameter(stringArray, "");
        String[] stringArray2 = new String[1];
        stringArray = stringArray2;
        stringArray2[0] = this.b();
        return this.map.getLong(stringArray);
    }

    @Override
    public float asFloat(@NotNull Function0<Float> stringArray) {
        Intrinsics.checkNotNullParameter(stringArray, "");
        String[] stringArray2 = new String[1];
        stringArray = stringArray2;
        stringArray2[0] = this.b();
        return this.map.getFloat(stringArray);
    }

    @Override
    public double asDouble(@NotNull Function0<Double> stringArray) {
        Intrinsics.checkNotNullParameter(stringArray, "");
        String[] stringArray2 = new String[1];
        stringArray = stringArray2;
        stringArray2[0] = this.b();
        return this.map.getDouble(stringArray);
    }

    @Override
    public boolean asBoolean(@NotNull Function0<Boolean> stringArray) {
        Intrinsics.checkNotNullParameter(stringArray, "");
        String[] stringArray2 = new String[1];
        stringArray = stringArray2;
        stringArray2[0] = this.b();
        return this.map.getBoolean(stringArray);
    }

    @Override
    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> biConsumer) {
        Intrinsics.checkNotNullParameter(c, "");
        Intrinsics.checkNotNullParameter(biConsumer, "");
        Node node2 = this.map.getNode(this.b());
        SequenceNode sequenceNode = node2 instanceof SequenceNode ? (SequenceNode) node2 : null;
        if (sequenceNode == null) {
            return c;
        }
        SequenceNode sequenceNode2 = sequenceNode;
        for (Node node2 : sequenceNode.getValue()) {
            Intrinsics.checkNotNull(node2);
            biConsumer.accept(c, new YamlNodeDataProvider(node2));
        }
        return c;
    }

    @Override
    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> triConsumer) {
        Intrinsics.checkNotNullParameter(m, "");
        Intrinsics.checkNotNullParameter(triConsumer, "");
        Node object2 = this.map.getNode(this.b());
        MappingNode mappingNode = object2 instanceof MappingNode ? (MappingNode) object2 : null;
        if (mappingNode == null) {
            return m;
        }
        MappingNode mappingNode2 = mappingNode;
        for (NodePair nodePair : mappingNode.getValue()) {
            ScalarNode scalarNode = nodePair.getKey();
            Intrinsics.checkNotNullExpressionValue(scalarNode, "");
            YamlNodeDataProvider yamlNodeDataProvider = new YamlNodeDataProvider(scalarNode);
            Node node = nodePair.getValue();
            Intrinsics.checkNotNullExpressionValue(node, "");
            triConsumer.accept(m, yamlNodeDataProvider, new YamlNodeDataProvider(node));
        }
        return m;
    }

    private void a(Tag tag, Serializable serializable) {
        this.map.set(this.b(), (Object) YamlNodeDataProvider.Companion.literalNode(tag, serializable));
    }

    @Override
    public void setString(@Nullable String s) {
        if (s == null) {
            return;
        }
        this.map.set(this.b(), (Object) new ScalarNode(Tag.STR, s, ScalarStyle.DOUBLE_QUOTED));
    }

    @Override
    public void setInt(int n) {
        Tag tag = Tag.INT;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.a(tag, Integer.valueOf(n));
    }

    @Override
    public void setLong(long l) {
        Tag tag = Tag.INT;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.a(tag, Long.valueOf(l));
    }

    @Override
    public void setFloat(float f) {
        Tag tag = Tag.FLOAT;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.a(tag, Float.valueOf(f));
    }

    @Override
    public void setDouble(double d) {
        Tag tag = Tag.FLOAT;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.a(tag, Double.valueOf(d));
    }

    @Override
    public void setBoolean(boolean b) {
        Tag tag = Tag.BOOL;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        this.a(tag, Boolean.valueOf(b));
    }

    @Override
    public void setUUID(@Nullable UUID uuid) {
        if (uuid == null) {
            return;
        }
        Tag tag = Tag.STR;
        Intrinsics.checkNotNullExpressionValue(tag, "");
        String string = FastUUID.toString(uuid);
        Intrinsics.checkNotNullExpressionValue(string, "");
        this.a(tag, string);
    }

    @Override
    public void setSimpleLocation(@Nullable SimpleLocation simpleLocation) {
        if (simpleLocation == null) {
            return;
        }
        this.setString(simpleLocation.asDataString());
    }

    @Override
    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation simpleChunkLocation) {
        Intrinsics.checkNotNullParameter(simpleChunkLocation, "");
        this.setString(simpleChunkLocation.asDataString());
    }

    @Override
    public void setLocation(@Nullable Location location) {
        if (location == null) {
            return;
        }
        this.setString(LocationUtils.toString(location));
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> c, @NotNull BiConsumer<SectionCreatableDataSetter, V> biConsumer) {
        Intrinsics.checkNotNullParameter(c, "");
        Intrinsics.checkNotNullParameter(biConsumer, "");
        if (c.isEmpty()) {
            return;
        }
        SequenceNode sequenceNode = new SequenceNode(Tag.SEQ, new ArrayList(), FlowStyle.AUTO);
        c = c.iterator();
        while (c.hasNext()) {
            Object e = c.next();
            biConsumer.accept(Companion.createProvider$core(sequenceNode), e);
        }
        this.map.set(this.b(), (Object) sequenceNode);
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> m, @NotNull MappingSetterHandler<K, V> mappingSetterHandler) {
        Intrinsics.checkNotNullParameter(m, "");
        Intrinsics.checkNotNullParameter(mappingSetterHandler, "");
        if (m.isEmpty()) {
            return;
        }
        MappingNode mappingNode = new MappingNode();
        MappingNode MappingNode = new MappingNode(mappingNode);
        for (Map.Entry<K, ? extends V> entry : m.entrySet()) {
            K k = entry.getKey();
            V entry2 = entry.getValue();
            mappingSetterHandler.map(k, new StringMappedIdSetter( string ->new YamlMappingDataProvider(string, mappingNode) ), entry2);
        }
        this.map.set(this.b(), (Object) mappingNode);
    }

    @Override
    @NotNull
    public SectionableDataSetter createSection() {
        MappingNode mappingNode = new MappingNode();
        this.map.set(this.name, (Object) mappingNode);
        return new YamlMappingDataProvider(null, new MappingNode(mappingNode));
    }

    @Override
    @NotNull
    public SectionableDataSetter createSection(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "");
        if (this.name != null) {
            throw new IllegalStateException("Previous name not handled: " + this.name + " -> " + string);
        }
        MappingNode mappingNode = new MappingNode();
        this.map.set(string, (Object) mappingNode);
        return new YamlMappingDataProvider(null, new MappingNode(mappingNode));
    }

    @NotNull
    public static DataProvider createProvider$core(@NotNull Node node) {
        return Companion.createProvider$core(node);
    }

    @Override
    public SectionableDataSetter get(String string) {
        return this.get(string);
    }

    @Override
    public SectionableDataProvider get(String string) {
        return this.get(string);
    }

    @Override
    public SectionableDataGetter get(String string) {
        return this.get(string);
    }

    @Override
    public SectionableDataGetter asSection() {
        return this.asSection();
    }

    @Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\n\n\u0000\n\n\u0000\n\n\b\b\u000020B\t\b¢\b\bJ020H¢\b"}, d2 = {"Lorg/kingdoms/data/database/flatfile/yaml/YamlMappingDataProvider$Companion;", "", "Lorg/kingdoms/libs/snakeyaml/nodes/Node;", "p0", "Lorg/kingdoms/data/database/dataprovider/DataProvider;", "createProvider$core", "(Lorg/snakeyaml/nodes/Node;)Lorg/kingdoms/data/database/dataprovider/DataProvider;", "<init>", "()V"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public DataProvider createProvider$core(@NotNull Node node) {
            Intrinsics.checkNotNullParameter(node, "");
            if (node instanceof MappingNode) {
                return new YamlMappingDataProvider(null, new MappingNode((MappingNode) node));
            }
            return new YamlNodeDataProvider(node);
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
