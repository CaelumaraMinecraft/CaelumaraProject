package net.aurika.data.database.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.data.database.DatabaseType;
import net.aurika.data.database.dataprovider.DataGetter;
import net.aurika.data.database.dataprovider.SectionableDataGetter;
import net.aurika.data.database.flatfile.json.JsonElementDataProvider;
import net.aurika.data.database.sql.statements.getters.SimpleResultSetQuery;
import net.aurika.util.Checker;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.gson.KingdomsGson;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.*;

public class SQLDataGetterProvider extends SQLDataProvider implements SectionableDataGetter {

    private final @NotNull SimpleResultSetQuery a;

    public SQLDataGetterProvider(@NotNull DatabaseType var1, @NotNull String var2, @Nullable String var3, boolean var4, boolean var5, @NotNull SimpleResultSetQuery var6) {
        super(var1, var2, var3, var4, var5);
        Checker.Arg.notNull(var6, "");
        this.a = var6;
    }

    @Override
    public @NotNull SectionableDataGetter asSection() {
        return new SQLDataGetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.getName$core(), false, true, this.a);
    }

    @Override
    public @NotNull SectionableDataGetter get(@NotNull String key) {
        Checker.Arg.notNull(key, "");
        if (this.getName$core() != null && !this.getNameIsSection$core()) {
            throw new IllegalStateException("Attempting to get() without specifying the previous ones type: " + this.getTable$core() + " -> " + this.getName$core() + " -> " + key);
        } else {
            return new SQLDataGetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.nameSepOrEmpty$core() + key, false, false, this.a);
        }
    }

    @Override
    public @Nullable String asString(@NotNull Supplier<String> def) {
        Checker.Arg.notNull(def, "");
        String var10000 = null;
        try {
            var10000 = this.a.getString(this.getNamed$core());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000;
    }

    @Override
    public @Nullable UUID asUUID() {
        try {
            return this.a.getUUID((this).getNamed$core());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @Nullable SimpleBlockLocation asSimpleLocation() {
        SectionableDataGetter var1 = this.asSection();
        String var10002 = var1.getString("world");
        if (var10002 == null) {
            return null;
        } else {
            return new SimpleBlockLocation(var10002, var1.getInt("x"), var1.getInt("y"), var1.getInt("z"));
        }
    }

    @Override
    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        SectionableDataGetter section = this.asSection();
        String world = section.getString("world");
        return new SimpleChunkLocation(world, section.getInt("x"), section.getInt("z"));
    }

    @Override
    public @Nullable SimpleLocation asLocation() {
        SectionableDataGetter var1 = this.asSection();
        String worldName = var1.getString("world");
        if (worldName == null) {
            return null;
        } else {
            return new SimpleLocation(worldName, var1.getDouble("x"), var1.getDouble("y"), var1.getDouble("z"), var1.getFloat("yaw"), var1.getFloat("pitch"));
        }
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Checker.Arg.notNull(def, "");
        try {
            return this.a.getInt(this.getNamed$core());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Checker.Arg.notNull(def, "");
        try {
            return this.a.getLong(this.getNamed$core());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Checker.Arg.notNull(def, "");
        try {
            return this.a.getFloat(this.getNamed$core());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Checker.Arg.notNull(def, "");
        try {
            return this.a.getDouble(this.getNamed$core());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Checker.Arg.notNull(def, "");
        try {
            return this.a.getBoolean(this.getNamed$core());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Checker.Arg.notNull(c, "");
        Checker.Arg.notNull(dataProcessor, "");
        String var10000 = null;
        try {
            var10000 = this.a.getString(this.getNamed$core());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (var10000 != null) {
            JsonElement var5 = KingdomsGson.fromString(var10000);
            Intrinsics.checkNotNull(var5);

            if (!(var5 instanceof JsonArray jsonArray)) {
                throw new IllegalStateException();
            }

            for (JsonElement var4 : jsonArray) {
                Intrinsics.checkNotNull(var4);
                dataProcessor.accept(c, new JsonElementDataProvider(var4));
            }
        }
        return c;
    }

    @Override
    @Contract("_, _ -> param1")
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Checker.Arg.notNull(m, "");
        Checker.Arg.notNull(dataProcessor, "");
        String var10000 = null;
        try {
            var10000 = this.a.getString(this.getNamed$core());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (var10000 != null) {
            JsonElement var6 = KingdomsGson.fromString(var10000);
            Intrinsics.checkNotNull(var6);

            for (Map.Entry<String, JsonElement> stringJsonElementEntry : ((JsonObject) var6).entrySet()) {
                Intrinsics.checkNotNull(stringJsonElementEntry);
                String var5 = (stringJsonElementEntry).getKey();
                JsonElement var7 = (stringJsonElementEntry).getValue();
                JsonElementDataProvider var10002 = new JsonElementDataProvider(new JsonPrimitive(var5));
                Intrinsics.checkNotNull(var7);
                dataProcessor.accept(m, var10002, new JsonElementDataProvider(var7));
            }
        }
        return m;
    }
}
