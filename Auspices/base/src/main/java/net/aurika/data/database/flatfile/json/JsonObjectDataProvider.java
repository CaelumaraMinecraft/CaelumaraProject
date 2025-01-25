package net.aurika.data.database.flatfile.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.data.database.dataprovider.*;
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
    private @NotNull JsonObject obj;

    public JsonObjectDataProvider(@Nullable String string, @NotNull JsonObject jsonObject) {
        Intrinsics.checkNotNullParameter(jsonObject, "");
        this.a = string;
        this.obj = jsonObject;
    }

    public String getName() {
        return this.a;
    }

    public void setName(@Nullable String string) {
        this.a = string;
    }

    public @NotNull JsonObject getObj() {
        return this.obj;
    }

    public void setObj(@NotNull JsonObject jsonObject) {
        Intrinsics.checkNotNullParameter(jsonObject, "jsonObject");
        this.obj = jsonObject;
    }

    @Override
    public @NotNull JsonElement getElement() {
        return this.obj;
    }

    private JsonElement a() {
        return this.obj.get(this.b());
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
        return new JsonObjectDataProvider(key, this.obj);
    }

    @Override
    public @NotNull DataProvider asSection() {
        JsonObject jsonMap;
        JsonObjectDataProvider jsonObjectDataProvider = this;
        if (jsonObjectDataProvider.a == null) {
            jsonMap = jsonObjectDataProvider.obj;
        } else {
            JsonElement jsonElement = jsonObjectDataProvider.obj.get(jsonObjectDataProvider.a);
            JsonObject getJsonMap = jsonElement != null ? jsonElement.getAsJsonObject() : null;
            if (getJsonMap == null) {
                getJsonMap = new JsonObject();
                jsonObjectDataProvider.obj.add(jsonObjectDataProvider.a, getJsonMap);
            }
            jsonMap = getJsonMap;
        }
        return new JsonObjectDataProvider(null, jsonMap);
    }

    @Override
    public String asString(@NotNull Supplier<String> def) {
        Intrinsics.checkNotNullParameter(def, "");
        JsonElement je = this.a();
        String s;
        if (je == null || (s = je.getAsString()) == null) {
            s = def.get();
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
    public int asInt(@NotNull IntSupplier def) {
        Intrinsics.checkNotNullParameter(def, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsInt();
        }
        return ((Number) def.getAsInt()).intValue();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Intrinsics.checkNotNullParameter(def, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsLong();
        }
        return ((Number) def.getAsLong()).longValue();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Intrinsics.checkNotNullParameter(def, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsFloat();
        }
        return ((Number) def.getAsFloat()).floatValue();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Intrinsics.checkNotNullParameter(def, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsDouble();
        }
        return ((Number) def.getAsDouble()).doubleValue();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Intrinsics.checkNotNullParameter(def, "");
        JsonElement jsonElement = this.a();
        if (jsonElement != null) {
            return jsonElement.getAsBoolean();
        }
        return def.getAsBoolean();
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
    public void setString(@NotNull String value) {
        if (value != null) {
            this.obj.addProperty(this.b(), value);
        }
    }

    @Override
    public void setInt(int value) {
        this.obj.addProperty(this.b(), value);
    }

    @Override
    public void setSimpleLocation(@NotNull SimpleBlockLocation value) {
        this.setString(value != null ? value.asDataString() : null);
    }

    @Override
    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation value) {
        Intrinsics.checkNotNullParameter(value, "");
        this.setString(value.asDataString());
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value != null) {
            this.obj.addProperty(this.b(), FastUUID.toString(value));
        }
    }

    @Override
    public void setLong(long value) {
        this.obj.addProperty(this.b(), value);
    }

    @Override
    public void setFloat(float value) {
        this.obj.addProperty(this.b(), value);
    }

    @Override
    public void setDouble(double value) {
        this.obj.addProperty(this.b(), value);
    }

    @Override
    public void setBoolean(boolean value) {
        this.obj.addProperty(this.b(), value);
    }

    @Override
    public void setLocation(@NotNull SimpleLocation value) {
        if (value == null) {
            return;
        }
        this.setString(value.asDataString());
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> biConsumer) {
        Intrinsics.checkNotNullParameter(value, "");
        Intrinsics.checkNotNullParameter(biConsumer, "");
        JsonArray jsonArray = new JsonArray();
        for (V e : value) {
            biConsumer.accept(new JsonElementDataProvider(jsonArray), e);
        }
        this.obj.add(this.b(), jsonArray);
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> mappingSetterHandler) {
        Intrinsics.checkNotNullParameter(value, "");
        Intrinsics.checkNotNullParameter(mappingSetterHandler, "");
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<K, ? extends V> entry : value.entrySet()) {
            K k = entry.getKey();
            V entry2 = entry.getValue();
            mappingSetterHandler.map(k, new StringMappedIdSetter(s -> new JsonObjectDataProvider(s, jsonObject)), entry2);
        }
        this.obj.add(this.b(), jsonObject);
    }

    @Override
    @NotNull
    public SectionableDataSetter createSection() {
        JsonObject jsonObject = new JsonObject();
        this.obj.add(this.a, jsonObject);
        return new JsonObjectDataProvider(null, jsonObject);
    }

    @Override
    @NotNull
    public DataProvider createSection(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "");
        if (this.a != null) {
            throw new IllegalStateException("Previous name not handled: " + this.a + " -> " + key);
        }
        JsonObject jsonObject = new JsonObject();
        this.obj.add(key, jsonObject);
        return new JsonObjectDataProvider(null, jsonObject);
    }
}
 