package net.aurika.ecliptor.database.flatfile.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.common.function.FloatSupplier;
import net.aurika.common.function.TriConsumer;
import net.aurika.util.unsafe.fn.Fn;
import net.aurika.util.uuid.FastUUID;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.*;

public class JsonObjectDataProvider implements DataProvider, SectionCreatableDataSetter, JsonDataProvider {
    private @Nullable String name;
    private @NotNull JsonObject obj;

    public JsonObjectDataProvider(@Nullable String name, @NotNull JsonObject obj) {
        Validate.Arg.notNull(obj, "obj");
        this.name = name;
        this.obj = obj;
    }

    public @Nullable String getName() {
        return this.name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public @NotNull JsonObject getObj() {
        return this.obj;
    }

    public void setObj(@NotNull JsonObject obj) {
        Validate.Arg.notNull(obj, "obj");
        this.obj = obj;
    }

    @Override
    public @NotNull JsonElement jsonElement() {
        return this.obj;
    }

    private @Nullable JsonElement __root() {
        return obj.get(__require_name());
    }

    private @NotNull String __require_name() {
        String _name = this.name;
        if (_name == null) {
            throw new IllegalStateException("No key name set");
        }
        return _name;
    }

    @Override
    public @NotNull DataProvider get(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        return new JsonObjectDataProvider(key, this.obj);
    }

    @Override
    public @NotNull DataProvider asSection() {
        JsonObject jsonMap;
        JsonObjectDataProvider jsonObjectDataProvider = this;
        if (jsonObjectDataProvider.name == null) {
            jsonMap = jsonObjectDataProvider.obj;
        } else {
            JsonElement jsonElement = jsonObjectDataProvider.obj.get(jsonObjectDataProvider.name);
            JsonObject getJsonMap = jsonElement != null ? jsonElement.getAsJsonObject() : null;
            if (getJsonMap == null) {
                getJsonMap = new JsonObject();
                jsonObjectDataProvider.obj.add(jsonObjectDataProvider.name, getJsonMap);
            }
            jsonMap = getJsonMap;
        }
        return new JsonObjectDataProvider(null, jsonMap);
    }

    @Override
    public String asString(@NotNull Supplier<String> def) {
        Validate.Arg.notNull(def, "def");
        JsonElement root = __root();
        String s;
        if (root == null || (s = root.getAsString()) == null) {
            return def.get();
        } else {
            return s;
        }
    }

    @Override
    public UUID asUUID() {
        String s = this.asString();
        return s != null ? FastUUID.fromString(s) : null;
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
        JsonElement root = __root();
        return root != null ? root.getAsInt() : def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Validate.Arg.notNull(def, "def");
        JsonElement root = __root();
        return root != null ? root.getAsLong() : def.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Validate.Arg.notNull(def, "");
        JsonElement root = __root();
        return root != null ? root.getAsFloat() : def.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Validate.Arg.notNull(def, "");
        JsonElement root = __root();
        return root != null ? root.getAsDouble() : def.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Validate.Arg.notNull(def, "");
        JsonElement root = __root();
        return root != null ? root.getAsBoolean() : def.getAsBoolean();
    }

    @Override
    public <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Validate.Arg.notNull(c, "c");
        Validate.Arg.notNull(handler, "handler");
        JsonElement root = __root();
        JsonArray rootAsArray = root != null ? root.getAsJsonArray() : null;
        if (rootAsArray == null) {
            return c;
        }
        for (JsonElement subElement : rootAsArray) {
            Objects.requireNonNull(subElement);
            handler.accept(c, new JsonElementDataProvider(subElement));
        }
        return c;
    }

    @Override
    public @NotNull <K, V, M extends Map<K, V>> M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
        Validate.Arg.notNull(m, "m");
        Validate.Arg.notNull(handler, "handler");
        JsonElement root = this.__root();
        JsonObject rootAsMap = root != null ? root.getAsJsonObject() : null;
        if (rootAsMap == null) {
            return m;
        }
        for (Map.Entry<String, JsonElement> keyToSubEntry : rootAsMap.entrySet()) {
            Objects.requireNonNull(keyToSubEntry);
            handler.accept(
                    m,  // map
                    new JsonElementDataProvider(new JsonPrimitive(keyToSubEntry.getKey())),  // key data provider
                    new JsonElementDataProvider(keyToSubEntry.getValue())  // value data provider
            );
        }
        return m;
    }

    @Override
    public void setInt(int value) {
        this.obj.addProperty(this.__require_name(), value);
    }

    @Override
    public void setLong(long value) {
        this.obj.addProperty(this.__require_name(), value);
    }

    @Override
    public void setFloat(float value) {
        this.obj.addProperty(this.__require_name(), value);
    }

    @Override
    public void setDouble(double value) {
        this.obj.addProperty(this.__require_name(), value);
    }

    @Override
    public void setBoolean(boolean value) {
        this.obj.addProperty(this.__require_name(), value);
    }

    @Override
    public void setString(@Nullable String value) {
        if (value != null) {
            this.obj.addProperty(this.__require_name(), value);
        }
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value != null) this.obj.addProperty(this.__require_name(), FastUUID.toString(value));
    }

    @Override
    public void setStruct(@NotNull StructuredDataObject value) {
        Validate.Arg.notNull(value, "value");
        DataStructSchema<?> schema = value.dataStructSchema();
        Objects.requireNonNull(schema, "schema");
        setString(schema.objectToPlain(Fn.cast(value)));
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        Validate.Arg.notNull(value, "value");
        Validate.Arg.notNull(handler, "handler");
        JsonArray jsonArray = new JsonArray();
        for (V e : value) {
            handler.accept(new JsonElementDataProvider(jsonArray), e);
        }
        this.obj.add(this.__require_name(), jsonArray);
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        Validate.Arg.notNull(value, "value");
        Validate.Arg.notNull(handler, "handler");
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<K, ? extends V> entry : value.entrySet()) {
            K k = entry.getKey();
            V entry2 = entry.getValue();
            handler.map(k, new StringMappedIdSetter(s -> new JsonObjectDataProvider(s, jsonObject)), entry2);
        }
        this.obj.add(this.__require_name(), jsonObject);
    }

    @Override
    public @NotNull SectionableDataSetter createSection() {
        JsonObject jsonObject = new JsonObject();
        this.obj.add(this.name, jsonObject);
        return new JsonObjectDataProvider(null, jsonObject);
    }

    @Override
    public @NotNull DataProvider createSection(@NotNull String key) {
        Validate.Arg.notNull(key, "");
        if (this.name != null) {
            throw new IllegalStateException("Previous name not handled: " + this.name + " -> " + key);
        }
        JsonObject jsonObject = new JsonObject();
        this.obj.add(key, jsonObject);
        return new JsonObjectDataProvider(null, jsonObject);
    }
}
 