package net.aurika.ecliptor.database.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.flatfile.json.JsonElementDataProvider;
import net.aurika.ecliptor.database.flatfile.json.JsonObjectDataProvider;
import net.aurika.ecliptor.database.sql.statements.setters.PreparedNamedSetterStatement;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;

public class SQLDataSetterProvider extends SQLDataProvider implements SectionableDataSetter {

    private final @NotNull PreparedNamedSetterStatement a;

    public SQLDataSetterProvider(@NotNull DatabaseType var1, @NotNull String var2, @Nullable String var3, boolean var4, boolean var5, @NotNull PreparedNamedSetterStatement var6) {
        super(var1, var2, var3, var4, var5);
        Objects.requireNonNull(var6, "");
        this.a = var6;
    }

    private void a() {
        if (this.getName$core() != null && !this.getNameIsSection$core()) {
            throw new IllegalStateException("Specified name is not given a type: " + this.getTable$core() + " -> " + this.getName$core());
        }
    }

    public @NotNull SectionableDataSetter get(@NotNull String key) {
        Objects.requireNonNull(key, "");
        this.a();
        return new SQLDataSetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.nameSepOrEmpty$core() + key, this.isInsideSingularEntity$core(), false, this.a);
    }

    public void setInt(int value) {
        this.a.setInt(this.getNamed$core(), value);
    }

    public void setLong(long value) {
        this.a.setLong(this.getNamed$core(), value);
    }

    public void setFloat(float value) {
        this.a.setFloat(this.getNamed$core(), value);
    }

    public void setDouble(double value) {
        this.a.setDouble(this.getNamed$core(), value);
    }

    public void setBoolean(boolean value) {
        this.a.setBoolean(this.getNamed$core(), value);
    }

    public void setString(@NotNull String value) {
        this.a.setString(this.getNamed$core(), value);
    }

    public void setUUID(@Nullable UUID value) {
        this.a.setUUID(this.getNamed$core(), value);
    }

    @Override
    public void setStruct(@NotNull StructuredDataObject value) {
        Validate.Arg.notNull(value, "value");
        for (var entry : value.structuredData().data().entrySet()) {
            setStructScalar(entry.getKey(), entry.getValue());
        }
    }

    void setStructScalar(String key, DataScalar scalar) {
        PreparedNamedSetterStatement ss = this.a;
        String setterKey = this.getNamed$core() + "_" + key;
        switch (scalar.type()) {
            case INT -> ss.setInt(setterKey, ((IntDataScalar) scalar).value());
            case LONG -> ss.setLong(setterKey, ((LongDataScalar) scalar).value());
            case FLOAT -> ss.setFloat(setterKey, ((FloatDataScalar) scalar).value());
            case DOUBLE -> ss.setDouble(setterKey, ((DoubleDataScalar) scalar).value());
            case BOOLEAN -> ss.setBoolean(setterKey, ((BooleanDataScalar) scalar).value());
            case STRING -> ss.setString(setterKey, ((StringDataScalar) scalar).value());
        }
    }

    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        Objects.requireNonNull(value, "c");
        Objects.requireNonNull(handler, "consumer");
        if (value.isEmpty()) {
            this.a.addParameterIfNotExist(this.getNamed$core());
            this.a.setJson(this.getNamed$core(), SQLDataProvider.EMPTY_JSON_ARRAY);
        } else {
            JsonArray var3 = new JsonArray();
            JsonElementDataProvider var4 = new JsonElementDataProvider(var3);

            for (V var5 : value) {
                handler.accept(var4, var5);
            }

            this.a.setJson(this.getNamed$core(), var3);
        }
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        Objects.requireNonNull(value, "m");
        Objects.requireNonNull(handler, "handler");
        if (value.isEmpty()) {
            this.a.addParameterIfNotExist(this.getNamed$core());
            this.a.setJson(this.getNamed$core(), SQLDataProvider.EMPTY_JSON_OBJECT);
        } else {
            final JsonObject var3 = new JsonObject();

            for (Map.Entry<K, ? extends V> entry : value.entrySet()) {
                K var5 = entry.getKey();
                V var7 = entry.getValue();

                handler.map(var5, new StringMappedIdSetter(s -> new JsonObjectDataProvider(s, var3)), var7);
            }

            this.a.setJson(this.getNamed$core(), var3);
        }
    }

    public @NotNull SectionableDataSetter createSection(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        this.a();
        return new SQLDataSetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.nameSepOrEmpty$core() + key, this.isInsideSingularEntity$core(), true, this.a);
    }

    public class SQLDynamicSection implements DynamicSection {
        @NotNull
        private final String a;
        @NotNull
        private final JsonObjectDataProvider b;

        public SQLDynamicSection(@NotNull String var2, @NotNull JsonObjectDataProvider var3) {
            Intrinsics.checkNotNullParameter(var2, "");
            Intrinsics.checkNotNullParameter(var3, "");
            this.a = var2;
            this.b = var3;
        }

        public @NotNull JsonObjectDataProvider setter() {
            return this.b;
        }

        public void close() {
            SQLDataSetterProvider.this.a.setJson(this.a, this.setter().getObj());
        }
    }
}
