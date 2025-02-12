package net.aurika.data.database.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.data.database.DatabaseType;
import net.aurika.data.database.dataprovider.*;
import net.aurika.data.database.flatfile.json.JsonElementDataProvider;
import net.aurika.data.database.flatfile.json.JsonObjectDataProvider;
import net.aurika.data.database.sql.statements.setters.PreparedNamedSetterStatement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;

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

    public void setString(@NotNull String value) {
        this.a.setString(this.getNamed$core(), value);
    }

    public void setInt(int value) {
        this.a.setInt(this.getNamed$core(), value);
    }

    public void setLocation(@Nullable SimpleLocation value) {
        if (value == null) return;
        PreparedNamedSetterStatement var2 = this.a;
        var2.setString(this.getNamed$core() + "_world", value.getWorld());
        var2.setDouble(this.getNamed$core() + "_x", value.getX());
        var2.setDouble(this.getNamed$core() + "_y", value.getY());
        var2.setDouble(this.getNamed$core() + "_z", value.getZ());
        var2.setFloat(this.getNamed$core() + "_yaw", value.getYaw());
        var2.setFloat(this.getNamed$core() + "_pitch", value.getPitch());
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation value) {
        if (value == null) return;
        PreparedNamedSetterStatement var2;
        PreparedNamedSetterStatement var10000 = var2 = this.a;
        String var10001 = this.getNamed$core() + "_world";
        String var10002 = value.getWorld();
        Intrinsics.checkNotNull(var10002);
        var10000.setString(var10001, var10002);
        var2.setInt(this.getNamed$core() + "_x", value.getX());
        var2.setInt(this.getNamed$core() + "_y", value.getY());
        var2.setInt(this.getNamed$core() + "_z", value.getZ());
    }

    public void setSimpleChunkLocation(@Nullable SimpleChunkLocation value) {
        if (value == null) return;
        PreparedNamedSetterStatement var2 = this.a;
        var2.setString(this.getNamed$core() + "_world", value.getWorld());
        var2.setInt(this.getNamed$core() + "_x", value.getX());
        var2.setInt(this.getNamed$core() + "_z", value.getZ());
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
        Objects.requireNonNull(key, "");
        this.a();
        return new SQLDataSetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.nameSepOrEmpty$core() + key, this.isInsideSingularEntity$core(), true, this.a);
    }

    public void setUUID(@Nullable UUID value) {
        this.a.setUUID(this.getNamed$core(), value);
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

        public @NotNull JsonObjectDataProvider getSetter() {
            return this.b;
        }

        public void close() {
            SQLDataSetterProvider.this.a.setJson(this.a, this.getSetter().getObj());
        }
    }
}
