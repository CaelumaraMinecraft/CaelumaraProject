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

/**
 * 作为 Collection 或 只读的 Map键或值 使用
 */
public class JsonElementDataProvider implements DataProvider, SectionCreatableDataSetter, JsonDataProvider {

    private final @NotNull JsonElement element;  // The root

    public JsonElementDataProvider(@NotNull JsonElement element) {
        Validate.Arg.notNull(element, "element");
        this.element = element;
    }

    public final @NotNull JsonElement getElement$core() {
        return this.element;
    }

    @Override
    public @NotNull DataProvider createSection(@NotNull String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull SectionableDataSetter createSection() {
        if (this.element instanceof JsonArray) {
            JsonObject arrElement = new JsonObject();
            ((JsonArray) this.element).add(arrElement);
            return new JsonObjectDataProvider(null, arrElement);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public @NotNull JsonElement jsonElement() {
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
            throw new IllegalArgumentException("Failed requirement, the element is not a JsonObject: " + element.getClass().getSimpleName());
        }
        return this;
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
    public String asString(@NotNull Supplier<String> def) {
        Validate.Arg.notNull(def, "def");
        if (this.element instanceof JsonNull) {
            return null;
        }
        String string = this.element.getAsString();
        return string != null ? string : def.get();
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
    public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Validate.Arg.notNull(c, "c");
        Validate.Arg.notNull(handler, "handler");

        for (JsonElement arrElement : this.element.getAsJsonArray()) {
            Objects.requireNonNull(arrElement);
            handler.accept(c, new JsonElementDataProvider(arrElement));
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
    public void setString(@Nullable String value) {
        if (this.element instanceof JsonArray) {
            ((JsonArray) this.element).add(value);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value != null) setString(FastUUID.toString(value));
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
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        throw new UnsupportedOperationException();
    }
}
 