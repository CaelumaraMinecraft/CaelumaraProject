package top.auspice.data.database.flatfile.json;

import com.google.gson.*;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.data.database.dataprovider.*;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.internal.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.*;

public final class JsonElementDataProvider
        implements DataProvider,
        SectionCreatableDataSetter,
        JsonDataProvider {
    @NotNull
    private final JsonElement a;

    public JsonElementDataProvider(@NotNull JsonElement jsonElement) {
        Intrinsics.checkNotNullParameter(jsonElement, "");
        this.a = jsonElement;
    }

    @NotNull
    public JsonElement getElement$core() {
        return this.a;
    }

    @Override
    @NotNull
    public DataProvider createSection(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "");
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public SectionableDataSetter createSection() {
        JsonObject jsonObject = new JsonObject();
        if (!(this.a instanceof JsonArray)) {
            throw new UnsupportedOperationException();
        }
        ((JsonArray) this.a).add(jsonObject);
        return new JsonObjectDataProvider(null, jsonObject);
    }

    @Override
    @NotNull
    public JsonElement getElement() {
        return this.a;
    }

    @Override
    @NotNull
    public DataProvider get(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "");
        JsonObject jsonObject = this.a.getAsJsonObject();
        Intrinsics.checkNotNullExpressionValue(jsonObject, "");
        return new JsonObjectDataProvider(key, jsonObject);
    }

    @Override
    @NotNull
    public DataProvider asSection() {
        if (!(this.a instanceof JsonObject)) {
            String string = "Failed requirement.";
            throw new IllegalArgumentException(string);
        }
        return this;
    }

    @Override
    public String asString(@NotNull Supplier<String> function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        if (this.a instanceof JsonNull) {
            return null;
        }
        String string = this.a.getAsString();
        if (string == null) {
            string = (String) function0.invoke();
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
            String string2 = string;
            return SimpleBlockLocation.fromString(string);
        }
        return null;
    }

    @Override
    public SimpleChunkLocation asSimpleChunkLocation() {
        String string = this.asString(() -> {
            throw new IllegalStateException();
        });
        Intrinsics.checkNotNull(string);
        SimpleChunkLocation simpleChunkLocation = SimpleChunkLocation.fromString(string);
        Intrinsics.checkNotNullExpressionValue(simpleChunkLocation, "");
        return simpleChunkLocation;
    }

    @Override
    public SimpleLocation asLocation() {
        String string = this.asString((Function0<String>) ((Function0) 1.a))
        if (string != null) {
            String string2 = string;
            return LocationUtils.fromString(string);
        }
        return null;
    }

    @Override
    public int asInt(@NotNull IntSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        return this.a.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        return this.a.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        return this.a.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        return this.a.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier function0) {
        Intrinsics.checkNotNullParameter(function0, "");
        return this.a.getAsBoolean();
    }

    @Override
    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> biConsumer) {
        Intrinsics.checkNotNullParameter(c, "");
        Intrinsics.checkNotNullParameter(biConsumer, "");
        JsonElement jsonElement = this.a;
        Intrinsics.checkNotNull(jsonElement);
        Object object = jsonElement;
        object = object.iterator();
        while (object.hasNext()) {
            JsonElement jsonElement2 = (JsonElement) object.next();
            Intrinsics.checkNotNull(jsonElement2);
            biConsumer.accept(c, new JsonElementDataProvider(jsonElement2));
        }
        return c;
    }

    @Override
    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> triConsumer) {
        Intrinsics.checkNotNullParameter(m, "");
        Intrinsics.checkNotNullParameter(triConsumer, "");
        JsonElement jsonElement = this.a;
        Intrinsics.checkNotNull(jsonElement);
        JsonObject jsonObject = (JsonObject) jsonElement;
        for (Map.Entry entry : jsonObject.entrySet()) {
            Intrinsics.checkNotNull(entry);
            String string = (String) entry.getKey();
            entry = (JsonElement) entry.getValue();
            JsonElementDataProvider jsonElementDataProvider = new JsonElementDataProvider(new JsonPrimitive(string));
            Intrinsics.checkNotNull(entry);
            triConsumer.accept(m, jsonElementDataProvider, new JsonElementDataProvider((JsonElement) entry));
        }
        return m;
    }

    @Override
    public void setString( String s) {
        if (this.a instanceof JsonArray) {
            ((JsonArray) this.a).add(s);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInt(int n) {
        if (this.a instanceof JsonArray) {
            ((JsonArray) this.a).add(n);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSimpleLocation( SimpleBlockLocation simpleLocation) {
        SimpleBlockLocation simpleLocation2 = simpleLocation;
        this.setString(simpleLocation2 != null ? simpleLocation2.asDataString() : null);
    }

    @Override
    public void setSimpleChunkLocation( SimpleChunkLocation simpleChunkLocation) {
        Intrinsics.checkNotNullParameter(simpleChunkLocation, "");
        this.setString(simpleChunkLocation.asDataString());
    }

    @Override
    public void setLong(long l) {
        if (this.a instanceof JsonArray) {
            ((JsonArray) this.a).add(l);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFloat(float f) {
        if (this.a instanceof JsonArray) {
            ((JsonArray) this.a).add(Float.valueOf(f));
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDouble(double d) {
        if (this.a instanceof JsonArray) {
            ((JsonArray) this.a).add(d);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBoolean(boolean b) {
        if (this.a instanceof JsonArray) {
            ((JsonArray) this.a).add(Boolean.valueOf(b));
            return;
        }
        throw new UnsupportedOperationException();
    }

    public <V> void setCollection(@NotNull Collection<? extends V> c, @NotNull BiConsumer<SectionCreatableDataSetter, V> biConsumer) {
        throw new UnsupportedOperationException();
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> m, @NotNull MappingSetterHandler<K, V> mappingSetterHandler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocation( SimpleLocation location) {
        if (location == null) {
            return;
        }
        this.setString(LocationUtils.toString(location));
    }

    @Override
    public void setUUID(@Nullable UUID uuid) {
        this.setString(FastUUID.toString(uuid));
    }
}
 