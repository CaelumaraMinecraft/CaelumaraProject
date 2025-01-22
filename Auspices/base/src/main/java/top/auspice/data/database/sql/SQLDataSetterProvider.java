package top.auspice.data.database.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.dataprovider.MappingSetterHandler;
import top.auspice.data.database.dataprovider.SectionCreatableDataSetter;
import top.auspice.data.database.dataprovider.SectionableDataSetter;
import top.auspice.data.database.dataprovider.StringMappedIdSetter;
import top.auspice.data.database.flatfile.json.JsonElementDataProvider;
import top.auspice.data.database.flatfile.json.JsonObjectDataProvider;
import top.auspice.data.database.sql.statements.setters.PreparedNamedSetterStatement;

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

    public void setString(@Nullable String s) {
        this.a.setString(this.getNamed$core(), s);
    }

    public void setInt(int var1) {
        this.a.setInt(this.getNamed$core(), var1);
    }

    public void setLocation(@Nullable SimpleLocation var1) {
        if (var1 == null) return;
        PreparedNamedSetterStatement var2 = this.a;
        var2.setString(this.getNamed$core() + "_world", var1.getWorld());
        var2.setDouble(this.getNamed$core() + "_x", var1.getX());
        var2.setDouble(this.getNamed$core() + "_y", var1.getY());
        var2.setDouble(this.getNamed$core() + "_z", var1.getZ());
        var2.setFloat(this.getNamed$core() + "_yaw", var1.getYaw());
        var2.setFloat(this.getNamed$core() + "_pitch", var1.getPitch());
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation blockLocation) {
        if (blockLocation == null) return;
        PreparedNamedSetterStatement var2;
        PreparedNamedSetterStatement var10000 = var2 = this.a;
        String var10001 = this.getNamed$core() + "_world";
        String var10002 = blockLocation.getWorld();
        Intrinsics.checkNotNull(var10002);
        var10000.setString(var10001, var10002);
        var2.setInt(this.getNamed$core() + "_x", blockLocation.getX());
        var2.setInt(this.getNamed$core() + "_y", blockLocation.getY());
        var2.setInt(this.getNamed$core() + "_z", blockLocation.getZ());
    }

    public void setSimpleChunkLocation(@Nullable SimpleChunkLocation chunkLocation) {
        if (chunkLocation == null) return;
        PreparedNamedSetterStatement var2 = this.a;
        var2.setString(this.getNamed$core() + "_world", chunkLocation.getWorld());
        var2.setInt(this.getNamed$core() + "_x", chunkLocation.getX());
        var2.setInt(this.getNamed$core() + "_z", chunkLocation.getZ());
    }

    public void setLong(long l) {
        this.a.setLong(this.getNamed$core(), l);
    }

    public void setFloat(float f) {
        this.a.setFloat(this.getNamed$core(), f);
    }

    public void setDouble(double d) {
        this.a.setDouble(this.getNamed$core(), d);
    }

    public void setBoolean(boolean b) {
        this.a.setBoolean(this.getNamed$core(), b);
    }

    public <V> void setCollection(@NotNull Collection<? extends V> c, @NotNull BiConsumer<SectionCreatableDataSetter, V> consumer) {
        Objects.requireNonNull(c, "c");
        Objects.requireNonNull(consumer, "consumer");
        if (c.isEmpty()) {
            this.a.addParameterIfNotExist(this.getNamed$core());
            this.a.setJson(this.getNamed$core(), SQLDataProvider.EMPTY_JSON_ARRAY);
        } else {
            JsonArray var3 = new JsonArray();
            JsonElementDataProvider var4 = new JsonElementDataProvider(var3);

            for (V var5 : c) {
                consumer.accept(var4, var5);
            }

            this.a.setJson(this.getNamed$core(), var3);
        }
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> m, @NotNull MappingSetterHandler<K, V> handler) {
        Objects.requireNonNull(m, "m");
        Objects.requireNonNull(handler, "handler");
        if (m.isEmpty()) {
            this.a.addParameterIfNotExist(this.getNamed$core());
            this.a.setJson(this.getNamed$core(), SQLDataProvider.EMPTY_JSON_OBJECT);
        } else {
            final JsonObject var3 = new JsonObject();

            for (Map.Entry<K, ? extends V> entry : m.entrySet()) {
                K var5 = entry.getKey();
                V var7 = entry.getValue();

                handler.map(var5, new StringMappedIdSetter(s -> new JsonObjectDataProvider(s, var3)), var7);
            }

            this.a.setJson(this.getNamed$core(), var3);
        }
    }

    public @NotNull SectionableDataSetter createSection(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        this.a();
        return new SQLDataSetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.nameSepOrEmpty$core() + var1, this.isInsideSingularEntity$core(), true, this.a);
    }

    public void setUUID(@Nullable UUID uuid) {
        this.a.setUUID(this.getNamed$core(), uuid);
    }
}
