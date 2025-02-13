package net.aurika.ecliptor.database.flatfile.json;

import com.google.gson.*;
import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.database.dataprovider.*;
import net.aurika.util.function.FloatSupplier;
import net.aurika.util.function.TriConsumer;
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

public class JsonElementDataProvider implements DataProvider, SectionCreatableDataSetter, JsonDataProvider {

    private final @NotNull JsonElement element;

    public JsonElementDataProvider(@NotNull JsonElement jsonElement) {
        Validate.Arg.notNull(jsonElement, "");
        this.element = jsonElement;
    }

    public JsonElement getElement$core() {
        return this.element;
    }

    @Override
    public @NotNull DataProvider createSection(@NotNull String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull SectionableDataSetter createSection() {
        JsonObject jsonObject = new JsonObject();
        if (!(this.element instanceof JsonArray)) {
            throw new UnsupportedOperationException();
        }
        ((JsonArray) this.element).add(jsonObject);
        return new JsonObjectDataProvider(null, jsonObject);
    }

    @Override
    public @NotNull JsonElement getElement() {
        return this.element;
    }

    @Override
    public @NotNull DataProvider get(@NotNull String key) {
        Validate.Arg.notNull(key, "key");
        return new JsonObjectDataProvider(key, element.getAsJsonObject());
    }

    @Override
    public @NotNull DataProvider asSection() {
        if (!(this.element instanceof JsonObject)) {
            String string = "Failed requirement.";
            throw new IllegalArgumentException(string);
        }
        return this;
    }

    @Override
    public String asString(@NotNull Supplier<String> def) {
        Validate.Arg.notNull(def, "");
        if (this.element instanceof JsonNull) {
            return null;
        }
        String string = this.element.getAsString();
        if (string == null) {
            string = def.get();
        }
        return string;
    }

    @Override
    public @Nullable UUID asUUID() {
        String s = this.asString();
        return s == null ? null : FastUUID.fromString(s);
    }

    @Override
    public <T extends StructuredDataObject> T asStruct(@NotNull DataStructSchema<T> template) {
        Validate.Arg.notNull(template, "template");
        String s = this.asString();
        return s != null ? template.plainToObject(s) : null;
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        return this.element.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        return this.element.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        return this.element.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        return this.element.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        return this.element.getAsBoolean();
    }

    @Override
    public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Validate.Arg.notNull(c, "c");
        Validate.Arg.notNull(handler, "handler");

        for (JsonElement element : (JsonArray) this.element) {
            Objects.requireNonNull(element);
            handler.accept(c, new JsonElementDataProvider(element));
        }

        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
        Validate.Arg.notNull(m, "m");
        Validate.Arg.notNull(handler, "dataProcessor");

        for (Map.Entry<String, JsonElement> entry : ((JsonObject) this.element).entrySet()) {
            Objects.requireNonNull(entry);
            String entryKey = entry.getKey();
            JsonElement entryValue = entry.getValue();
            JsonElementDataProvider var10002 = new JsonElementDataProvider(new JsonPrimitive(entryKey));
            handler.accept(m, var10002, new JsonElementDataProvider(entryValue));
        }

        return m;
    }

    @Override
    public void setString(@Nullable String value) {
        if (this.element instanceof JsonArray) {
            ((JsonArray) this.element).add(value);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInt(int value) {
        if (this.element instanceof JsonArray) {
            ((JsonArray) this.element).add(value);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLong(long value) {
        if (this.element instanceof JsonArray) {
            ((JsonArray) this.element).add(value);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFloat(float value) {
        if (this.element instanceof JsonArray) {
            ((JsonArray) this.element).add(value);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDouble(double value) {
        if (this.element instanceof JsonArray) {
            ((JsonArray) this.element).add(value);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBoolean(boolean value) {
        if (this.element instanceof JsonArray) {
            ((JsonArray) this.element).add(value);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value != null) setString(FastUUID.toString(value));
    }

    @Override
    public void setStruct(@NotNull StructuredDataObject value) {
        Validate.Arg.notNull(value, "value");
        DataStructSchema<?> schema = value.DataStructSchema();
        Objects.requireNonNull(schema, "schema");
        setString(schema.objectToPlain(Fn.cast(value)));
    }
}
 