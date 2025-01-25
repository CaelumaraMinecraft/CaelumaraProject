package net.aurika.data.database.flatfile.json;

import com.google.gson.*;
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

public class JsonElementDataProvider implements DataProvider, SectionCreatableDataSetter, JsonDataProvider {

    private final @NotNull JsonElement element;

    public JsonElementDataProvider(@NotNull JsonElement jsonElement) {
        Intrinsics.checkNotNullParameter(jsonElement, "");
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
    @NotNull
    public SectionableDataSetter createSection() {
        JsonObject jsonObject = new JsonObject();
        if (!(this.element instanceof JsonArray)) {
            throw new UnsupportedOperationException();
        }
        ((JsonArray) this.element).add(jsonObject);
        return new JsonObjectDataProvider(null, jsonObject);
    }

    @Override
    @NotNull
    public JsonElement getElement() {
        return this.element;
    }

    @Override
    @NotNull
    public DataProvider get(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "");
        JsonObject jsonObject = this.element.getAsJsonObject();
        Intrinsics.checkNotNullExpressionValue(jsonObject, "");
        return new JsonObjectDataProvider(key, jsonObject);
    }

    @Override
    @NotNull
    public DataProvider asSection() {
        if (!(this.element instanceof JsonObject)) {
            String string = "Failed requirement.";
            throw new IllegalArgumentException(string);
        }
        return this;
    }

    @Override
    public String asString(@NotNull Supplier<String> def) {
        Intrinsics.checkNotNullParameter(def, "");
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
    @NotNull
    public UUID asUUID() {
        UUID uUID = FastUUID.fromString(this.asString(() -> {
            throw new IllegalArgumentException();
        }));
        Intrinsics.checkNotNullExpressionValue(uUID, "");
        return uUID;
    }

    @Override
    public @Nullable SimpleBlockLocation asSimpleLocation() {
        String string = this.asString();
        if (string != null) {
            return SimpleBlockLocation.fromDataString(string);
        }
        return null;
    }

    @Override
    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        String string = this.asString(() -> null);
        if (string != null) {
            return SimpleChunkLocation.fromDataString(string);
        }
        return null;
    }

    @Override
    public @Nullable SimpleLocation asLocation() {
        String string = this.asString(() -> null);
        if (string != null) {
            return SimpleLocation.fromDataString(string);
        }
        return null;
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
    public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C var1, @NotNull BiConsumer<C, SectionableDataGetter> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        JsonElement var10000 = this.element;
        Intrinsics.checkNotNull(var10000);

        for (JsonElement var4 : (JsonArray) var10000) {
            Intrinsics.checkNotNull(var4);
            var2.accept(var1, new JsonElementDataProvider(var4));
        }

        return var1;
    }

    @Override
    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M var1, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(dataProcessor, "");
        JsonElement var10000 = this.element;
        Intrinsics.checkNotNull(var10000);

        for (Map.Entry<String, JsonElement> stringJsonElementEntry : ((JsonObject) var10000).entrySet()) {
            Intrinsics.checkNotNull(stringJsonElementEntry);
            String var5 = stringJsonElementEntry.getKey();
            JsonElement var6 = stringJsonElementEntry.getValue();
            JsonElementDataProvider var10002 = new JsonElementDataProvider(new JsonPrimitive(var5));
            Intrinsics.checkNotNull(var6);
            dataProcessor.accept(var1, var10002, new JsonElementDataProvider(var6));
        }

        return var1;
    }

    @Override
    public void setString(@NotNull String value) {
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
    public void setSimpleLocation(@NotNull SimpleBlockLocation value) {
        this.setString(value != null ? value.asDataString() : null);
    }

    @Override
    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation value) {
        Intrinsics.checkNotNullParameter(value, "");
        this.setString(value.asDataString());
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
    public void setLocation(@NotNull SimpleLocation value) {
        if (value == null) {
            return;
        }
        this.setString(value.asDataString());
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        this.setString(FastUUID.toString(value));
    }
}
 