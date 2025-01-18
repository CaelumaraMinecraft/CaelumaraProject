package top.auspice.data.database.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.dataprovider.MappingSetterHandler;
import top.auspice.data.database.dataprovider.SectionCreatableDataSetter;
import top.auspice.data.database.dataprovider.SectionableDataSetter;
import top.auspice.data.database.dataprovider.StringMappedIdSetter;
import top.auspice.data.database.sql.statements.setters.PreparedNamedSetterStatement;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public  class SQLDataSetterProvider extends SQLDataProvider implements SectionableDataSetter {
    @NotNull
    private final PreparedNamedSetterStatement a;

    public SQLDataSetterProvider(@NotNull DatabaseType var1, @NotNull String var2, @Nullable String var3, boolean var4, boolean var5, @NotNull PreparedNamedSetterStatement var6) {
        super(var1, var2, var3, var4, var5);
        Intrinsics.checkNotNullParameter(var6, "");
        this.a = var6;
    }

    private void a() {
        if (this.getName$core() != null && !this.getNameIsSection$core()) {
            throw new IllegalStateException("Specified name is not given a type: " + this.getTable$core() + " -> " + this.getName$core());
        }
    }

    @NotNull
    public SectionableDataSetter get(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.a();
        return new SQLDataSetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.nameSepOrEmpty$core() + var1, this.isInsideSingularEntity$core(), false, this.a);
    }

    public void setString(@Nullable String var1) {
        this.a.setString(this.getNamed$core(), var1);
    }

    public void setInt(int var1) {
        this.a.setInt(this.getNamed$core(), var1);
    }

    public void setLocation(@Nullable Location var1) {
        if (var1 != null) {
            PreparedNamedSetterStatement var2;
            PreparedNamedSetterStatement var10000 = var2 = this.a;
            String var10001 = this.getNamed$core() + "_world";
            World var10002 = var1.getWorld();
            Intrinsics.checkNotNull(var10002);
            var10000.setString(var10001, var10002.getName());
            var2.setDouble(this.getNamed$core() + "_x", var1.getX());
            var2.setDouble(this.getNamed$core() + "_y", var1.getY());
            var2.setDouble(this.getNamed$core() + "_z", var1.getZ());
            var2.setFloat(this.getNamed$core() + "_yaw", var1.getYaw());
            var2.setFloat(this.getNamed$core() + "_pitch", var1.getPitch());
        }
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation var1) {
        if (var1 != null) {
            PreparedNamedSetterStatement var2;
            PreparedNamedSetterStatement var10000 = var2 = this.a;
            String var10001 = this.getNamed$core() + "_world";
            String var10002 = var1.getWorld();
            Intrinsics.checkNotNull(var10002);
            var10000.setString(var10001, var10002);
            var2.setInt(this.getNamed$core() + "_x", var1.getX());
            var2.setInt(this.getNamed$core() + "_y", var1.getY());
            var2.setInt(this.getNamed$core() + "_z", var1.getZ());
        }
    }

    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        PreparedNamedSetterStatement var2;
        (var2 = this.a).setString(this.getNamed$core() + "_world", var1.getWorld());
        var2.setInt(this.getNamed$core() + "_x", var1.getX());
        var2.setInt(this.getNamed$core() + "_z", var1.getZ());
    }

    public void setLong(long var1) {
        this.a.setLong(this.getNamed$core(), var1);
    }

    public void setFloat(float var1) {
        this.a.setFloat(this.getNamed$core(), var1);
    }

    public void setDouble(double var1) {
        this.a.setDouble(this.getNamed$core(), var1);
    }

    public void setBoolean(boolean var1) {
        this.a.setBoolean(this.getNamed$core(), var1);
    }

    public <V> void setCollection(@NotNull Collection<? extends V> var1, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        if (var1.isEmpty()) {
            this.a.addParameterIfNotExist(this.getNamed$core());
            this.a.setJson(this.getNamed$core(), SQLDataProvider.EMPTY_JSON_ARRAY);
        } else {
            JsonArray var3 = new JsonArray();
            JsonElementDataProvider var4 = new JsonElementDataProvider((JsonElement)var3);
            Iterator var6 = var1.iterator();

            while(var6.hasNext()) {
                Object var5 = var6.next();
                var2.accept(var4, var5);
            }

            this.a.setJson(this.getNamed$core(), var3);
        }
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> var1, @NotNull MappingSetterHandler<K, V> var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        if (var1.isEmpty()) {
            this.a.addParameterIfNotExist(this.getNamed$core());
            this.a.setJson(this.getNamed$core(), SQLDataProvider.EMPTY_JSON_OBJECT);
        } else {
            final JsonObject var3 = new JsonObject();
            Iterator var6 = var1.entrySet().iterator();

            while(var6.hasNext()) {
                Map.Entry var4;
                Object var5 = (var4 = (Map.Entry)var6.next()).getKey();
                Object var7 = var4.getValue();

                final class NamelessClass_1 extends Lambda implements Function1<String, SectionCreatableDataSetter> {
                    NamelessClass_1() {
                        super(1);
                    }

                    public SectionCreatableDataSetter a(String var1) {
                        Intrinsics.checkNotNullParameter(var1, "");
                        return (SectionCreatableDataSetter)(new JsonObjectDataProvider(var1, var3));
                    }
                }

                var2.map(var5, (MappedIdSetter)(new StringMappedIdSetter((Function1)(new NamelessClass_1()))), var7);
            }

            this.a.setJson(this.getNamed$core(), var3);
        }
    }

    @NotNull
    public SectionableDataSetter createSection(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        this.a();
        return new SQLDataSetterProvider(this.getDatabaseType$core(), this.getTable$core(), this.nameSepOrEmpty$core() + var1, this.isInsideSingularEntity$core(), true, this.a);
    }

    public void setUUID(@Nullable UUID var1) {
        this.a.setUUID(this.getNamed$core(), var1);
    }
}
