package top.auspice.data.database.flatfile.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.data.database.dataprovider.*;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.*;

public class JsonObjectDataProvider implements DataProvider, SectionCreatableDataSetter, JsonDataProvider {
    private @Nullable String a;
    private @NotNull JsonObject b;

    public JsonObjectDataProvider(@Nullable String string, @NotNull JsonObject jsonObject) {
        Intrinsics.checkNotNullParameter(jsonObject, "");
        this.a = string;
        this.b = jsonObject;
    }

    public String getName() {
        return this.a;
    }

    public void setName(@Nullable String string) {
        this.a = string;
    }

    public JsonObject getObj() {
        return this.b;
    }

    public void setObj(@NotNull JsonObject jsonObject) {
        Intrinsics.checkNotNullParameter(jsonObject, "");
        this.b = jsonObject;
    }

    @Override
    public @NotNull JsonElement getElement() {
        return this.b;
    }

    private JsonElement a() {
        return this.b.get(this.b());
    }

    private String b() {
        String string = this.a;
        if (string == null) {
            throw new IllegalStateException("No key name set");
        }
        return string;
    }

    @Override
    public @NotNull DataProvider get(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "");
        return new JsonObjectDataProvider(key, this.b);
    }

    @Override
    public @NotNull DataProvider asSection() {
        JsonObject jsonMap;
        JsonObjectDataProvider jsonObjectDataProvider = this;
        if (jsonObjectDataProvider.a == null) {
            jsonMap = jsonObjectDataProvider.b;
        } else {
            JsonElement jsonElement = jsonObjectDataProvider.b.get(jsonObjectDataProvider.a);
            JsonObject getJsonMap = jsonElement != null ? jsonElement.getAsJsonObject() : null;
            if (getJsonMap == null) {
                getJsonMap = new JsonObject();
                jsonObjectDataProvider.b.add(jsonObjectDataProvider.a, getJsonMap);
            }
            jsonMap = getJsonMap;
        }
        return new JsonObjectDataProvider(null, jsonMap);
    }

    @Override
    public String asString(@NotNull Supplier<String> function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        JsonElement je = this.a();
        String s;
        if (je == null || (s = je.getAsString()) == null) {
            s = function0.get();
        }
        return s;
    }

    @Override
    public UUID asUUID() {
        String string = this.asString();
        if (string != null) {
            return FastUUID.fromString(string);
        }
        return null;
    }

    @Override
    public @Nullable SimpleBlockLocation asSimpleLocation() {
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return SimpleBlockLocation.fromString(jsonElement.getAsString());
        }
        return null;
    }

    @Override
    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        JsonElement jsonElement = this.a();
        Intrinsics.checkNotNull(jsonElement);
        SimpleChunkLocation simpleChunkLocation = SimpleChunkLocation.fromDataString(jsonElement.getAsString());
        Intrinsics.checkNotNullExpressionValue(simpleChunkLocation, "");
        return simpleChunkLocation;
    }

    @Override
    public SimpleLocation asLocation() {
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return SimpleLocation.fromDataString(jsonElement.getAsString());
        }
        return null;
    }

    @Override
    public int asInt(@NotNull IntSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsInt();
        }
        return ((Number) function0.getAsInt()).intValue();
    }

    @Override
    public long asLong(@NotNull LongSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsLong();
        }
        return ((Number) function0.getAsLong()).longValue();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsFloat();
        }
        return ((Number) function0.getAsFloat()).floatValue();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsDouble();
        }
        return ((Number) function0.getAsDouble()).doubleValue();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsBoolean();
        }
        return function0.getAsBoolean();
    }

    @Override
    public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Intrinsics.checkNotNullParameter(c, "");
        Intrinsics.checkNotNullParameter(dataProcessor, "");
        JsonElement je = this.a();
        JsonArray jsonArr = je != null ? je.getAsJsonArray() : null;
        if (jsonArr == null) {
            return c;
        }
        for (JsonElement e : jsonArr) {
            Intrinsics.checkNotNull(e);
            dataProcessor.accept(c, new JsonElementDataProvider(e));
        }
        return c;
    }

    @Override
    public @NotNull <K, V, M extends Map<K, V>> M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Intrinsics.checkNotNullParameter(m, "m");
        Intrinsics.checkNotNullParameter(dataProcessor, "");
        JsonElement je = this.a();
        JsonObject jsonMap = je != null ? je.getAsJsonObject() : null;
        if (jsonMap == null) {
            return m;
        }
        for (Map.Entry<String, JsonElement> entry : jsonMap.entrySet()) {
            Intrinsics.checkNotNull(entry);
            String string = entry.getKey();
            JsonElement e = entry.getValue();
            JsonElementDataProvider jsonElementDataProvider = new JsonElementDataProvider(new JsonPrimitive(string));
            Intrinsics.checkNotNull(entry);
            dataProcessor.accept(m, jsonElementDataProvider, new JsonElementDataProvider(e));
        }
        return m;
    }

    @Override
    public void setString(@Nullable String s) {
        if (s != null) {
            this.b.addProperty(this.b(), s);
        }
    }

    @Override
    public void setInt(int n) {
        this.b.addProperty(this.b(), n);
    }

    @Override
    public void setSimpleLocation(@Nullable SimpleBlockLocation blockLocation) {
        this.setString(blockLocation != null ? blockLocation.asDataString() : null);
    }

    @Override
    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation chunkLocation) {
        Intrinsics.checkNotNullParameter(chunkLocation, "");
        this.setString(chunkLocation.asDataString());
    }

    @Override
    public void setUUID(@Nullable UUID uuid) {
        if (uuid != null) {
            this.b.addProperty(this.b(), FastUUID.toString(uuid));
        }
    }

    @Override
    public void setLong(long l) {
        this.b.addProperty(this.b(), l);
    }

    @Override
    public void setFloat(float f) {
        this.b.addProperty(this.b(), f);
    }

    @Override
    public void setDouble(double d) {
        this.b.addProperty(this.b(), d);
    }

    @Override
    public void setBoolean(boolean b) {
        this.b.addProperty(this.b(), b);
    }

    @Override
    public void setLocation(@Nullable SimpleLocation location) {
        if (location == null) {
            return;
        }
        this.setString(location.asDataString());
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> c, @NotNull BiConsumer<SectionCreatableDataSetter, V> biConsumer) {
        Intrinsics.checkNotNullParameter(c, "");
        Intrinsics.checkNotNullParameter(biConsumer, "");
        JsonArray jsonArray = new JsonArray();
        for (V e : c) {
            biConsumer.accept(new JsonElementDataProvider(jsonArray), e);
        }
        this.b.add(this.b(), jsonArray);
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> m, @NotNull MappingSetterHandler<K, V> mappingSetterHandler) {
        Intrinsics.checkNotNullParameter(m, "");
        Intrinsics.checkNotNullParameter(mappingSetterHandler, "");
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<K, ? extends V> entry : m.entrySet()) {
            K k = entry.getKey();
            V entry2 = entry.getValue();
            mappingSetterHandler.map(k, new StringMappedIdSetter(s -> new JsonObjectDataProvider(s, jsonObject)), entry2);
        }
        this.b.add(this.b(), jsonObject);
    }

    @Override
    @NotNull
    public SectionableDataSetter createSection() {
        JsonObject jsonObject = new JsonObject();
        this.b.add(this.a, jsonObject);
        return new JsonObjectDataProvider(null, jsonObject);
    }

    @Override
    @NotNull
    public DataProvider createSection(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "");
        if (this.a != null) {
            throw new IllegalStateException("Previous name not handled: " + this.a + " -> " + string);
        }
        JsonObject jsonObject = new JsonObject();
        this.b.add(string, jsonObject);
        return new JsonObjectDataProvider(null, jsonObject);
    }
}
 