package net.aurika.data.database.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.data.database.DatabaseType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.utils.reflection.Reflect;

public abstract class SQLDataProvider {

    static final @NotNull JsonObject EMPTY_JSON_OBJECT = new JsonObject();
    static final @NotNull JsonArray EMPTY_JSON_ARRAY = new JsonArray();

    private final @NotNull DatabaseType databaseType;
    private final @NotNull String table;
    private final @Nullable String c;
    private final boolean d;
    private final boolean nameIsSection;

    public SQLDataProvider(@NotNull DatabaseType var1, @NotNull String var2, @Nullable String var3, boolean var4, boolean var5) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        this.databaseType = var1;
        this.table = var2;
        this.c = var3;
        this.d = var4;
        this.nameIsSection = var5;
    }

    @NotNull
    public final DatabaseType getDatabaseType$core() {
        return this.databaseType;
    }

    public final @NotNull String getTable$core() {
        return this.table;
    }

    public final @Nullable String getName$core() {
        return this.c;
    }

    public final boolean isInsideSingularEntity$core() {
        return this.d;
    }

    public final boolean getNameIsSection$core() {
        return this.nameIsSection;
    }

    @NotNull
    public final String getNamed$core() {
        String var10000 = this.c;
        if (var10000 == null) {
            throw new IllegalStateException("No name set: " + Reflect.toString(this));
        } else {
            return var10000;
        }
    }

    @NotNull
    public final String nameSepOrEmpty$core() {
        String var10000 = this.c;
        if (var10000 != null) {
            String var1 = var10000;
            var10000 = var1 + '_';
        } else {
            var10000 = null;
        }

        if (var10000 == null) {
            var10000 = "";
        }

        return var10000;
    }
}
