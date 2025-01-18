package top.auspice.data.database.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.dataprovider.DataGetter;
import top.auspice.data.database.dataprovider.SectionableDataGetter;
import top.auspice.data.database.sql.statements.getters.SimpleResultSetQuery;
import top.auspice.server.location.OldLocation;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.gson.KingdomsGson;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class SQLDataGetterProvider extends SQLDataProvider implements SectionableDataGetter {
    @NotNull
    private final SimpleResultSetQuery a;

    public SQLDataGetterProvider(@NotNull DatabaseType var1, @NotNull String var2, @Nullable String var3, boolean var4, boolean var5, @NotNull SimpleResultSetQuery var6) {
        super(var1, var2, var3, var4, var5);
        Intrinsics.checkNotNullParameter(var6, "");
        this.a = var6;
    }

    @NotNull
    public SectionableDataGetter asSection() {
        return new SQLDataGetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.getName$core(), false, true, this.a);
    }

    @NotNull
    public SectionableDataGetter get(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        if (this.getName$core() != null && !this.getNameIsSection$core()) {
            throw new IllegalStateException("Attempting to get() without specifying the previous ones type: " + this.getTable$core() + " -> " + this.getName$core() + " -> " + var1);
        } else {
            return new SQLDataGetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.nameSepOrEmpty$core() + var1, false, false, this.a);
        }
    }

    @Nullable
    public String asString(@NotNull Function0<String> var1) throws SQLException {
        Intrinsics.checkNotNullParameter(var1, "");
        String var10000 = this.a.getString(this.getNamed$core());
        if (var10000 == null) {
            var10000 = var1.invoke();
        }

        return var10000;
    }

    @Nullable
    public UUID asUUID() throws SQLException {
        return this.a.getUUID((this).getNamed$core());
    }

    @Nullable
    public SimpleBlockLocation asSimpleLocation() {
        SectionableDataGetter var1 = this.asSection();
        String var10002 = var1.getString("world");
        if (var10002 == null) {
            return null;
        } else {
            return new SimpleBlockLocation(var10002, var1.getInt("x"), var1.getInt("y"), var1.getInt("z"));
        }
    }

    @NotNull
    public SimpleChunkLocation asSimpleChunkLocation() {
        SectionableDataGetter var1 = this.asSection();
        String var10002 = var1.getString("world");
        Intrinsics.checkNotNull(var10002);
        return new SimpleChunkLocation(var10002, var1.getInt("x"), var1.getInt("z"));
    }

    @Nullable
    public OldLocation asLocation() {
        SectionableDataGetter var1;
        String worldName = (var1 = this.asSection()).getString("world");
        if (worldName == null) {
            return null;
        } else {
            return new OldLocation(BukkitWorld.getWorld(worldName, var1), var1.getDouble("x"), var1.getDouble("y"), var1.getDouble("z"), var1.getFloat("yaw"), var1.getFloat("pitch"));
        }
    }

    public int asInt(@NotNull Function0<Integer> var1) throws SQLException {
        Intrinsics.checkNotNullParameter(var1, "");
        return this.a.getInt(this.getNamed$core());
    }

    public long asLong(@NotNull Function0<Long> var1) throws SQLException {
        Intrinsics.checkNotNullParameter(var1, "");
        return this.a.getLong(this.getNamed$core());
    }

    public float asFloat(@NotNull Function0<Float> var1) throws SQLException {
        Intrinsics.checkNotNullParameter(var1, "");
        return this.a.getFloat(this.getNamed$core());
    }

    public double asDouble(@NotNull Function0<Double> var1) throws SQLException {
        Intrinsics.checkNotNullParameter(var1, "");
        return this.a.getDouble(this.getNamed$core());
    }

    public boolean asBoolean(@NotNull Function0<Boolean> var1) throws SQLException {
        Intrinsics.checkNotNullParameter(var1, "");
        return this.a.getBoolean(this.getNamed$core());
    }

    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C var1, @NotNull BiConsumer<C, SectionableDataGetter> var2) throws SQLException {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        String var10000 = this.a.getString(this.getNamed$core());
        if (var10000 == null) {
            return var1;
        } else {
            JsonElement var5 = KingdomsGson.fromString(var10000);
            Intrinsics.checkNotNull(var5);
            Iterator var3 = ((JsonArray) var5).iterator();

            while (var3.hasNext()) {
                JsonElement var4 = (JsonElement) var3.next();
                Intrinsics.checkNotNull(var4);
                var2.accept(var1, new JsonElementDataProvider(var4));
            }

            return var1;
        }
    }

    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M var1, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> var2) throws SQLException {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        String var10000 = this.a.getString(this.getNamed$core());
        if (var10000 == null) {
            return var1;
        } else {
            JsonElement var6 = KingdomsGson.fromString(var10000);
            Intrinsics.checkNotNull(var6);
            Iterator var3 = ((JsonObject) var6).entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry var4;
                Intrinsics.checkNotNull(var4 = (Map.Entry) var3.next());
                String var5 = (String) var4.getKey();
                JsonElement var7 = (JsonElement) var4.getValue();
                JsonElementDataProvider var10002 = new JsonElementDataProvider((JsonElement) (new JsonPrimitive(var5)));
                Intrinsics.checkNotNull(var7);
                var2.accept(var1, var10002, new JsonElementDataProvider(var7));
            }

            return var1;
        }
    }
}
