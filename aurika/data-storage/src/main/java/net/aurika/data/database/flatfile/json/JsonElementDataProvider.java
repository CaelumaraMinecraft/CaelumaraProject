package net.aurika.data.database.flatfile.json;

import com.google.gson.*;
import net.aurika.checker.Checker;
import net.aurika.data.api.dataprovider.*;
import net.aurika.data.api.structure.SimpleData;
import net.aurika.data.api.structure.SimpleDataObjectTemplate;
import net.aurika.data.api.structure.PlainSimpleDataUtils;
import net.aurika.utils.function.FloatSupplier;
import net.aurika.utils.function.TriConsumer;
import net.aurika.utils.uuid.FastUUID;
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
        Checker.Arg.notNull(jsonElement, "");
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
        Checker.Arg.notNull(key, "key");
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
        Checker.Arg.notNull(def, "");
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
    public <T> T asObject(SimpleDataObjectTemplate<T> template) {
        Checker.Arg.notNull(template, "template");
        String s = this.asString();
        return s != null ? PlainSimpleDataUtils.serializePlainMapData(s, template) : null;
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
    public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> var2) {
        Checker.Arg.notNull(c, "c");
        Checker.Arg.notNull(var2, "dataProcessor");

        for (JsonElement element : (JsonArray) this.element) {
            Objects.requireNonNull(element);
            var2.accept(c, new JsonElementDataProvider(element));
        }

        return c;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Checker.Arg.notNull(m, "m");
        Checker.Arg.notNull(dataProcessor, "dataProcessor");

        for (Map.Entry<String, JsonElement> entry : ((JsonObject) this.element).entrySet()) {
            Objects.requireNonNull(entry);
            String entryKey = entry.getKey();
            JsonElement entryValue = entry.getValue();
            JsonElementDataProvider var10002 = new JsonElementDataProvider(new JsonPrimitive(entryKey));
            dataProcessor.accept(m, var10002, new JsonElementDataProvider(entryValue));
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
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> biConsumer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> mappingSetterHandler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        this.setString(FastUUID.toString(value));
    }

    @Override
    public void setObject(@NotNull SimpleData value) {
        Checker.Arg.notNull(value, "value");
        setString(PlainSimpleDataUtils.compressPlainMapData(value));
    }
}
 