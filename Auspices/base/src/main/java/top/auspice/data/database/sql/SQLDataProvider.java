package top.auspice.data.database.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.database.DatabaseType;
import top.auspice.utils.reflection.Reflect;

public abstract class SQLDataProvider {

    @NotNull
    static final JsonObject EMPTY_JSON_OBJECT = new JsonObject();
    @NotNull
    static final JsonArray EMPTY_JSON_ARRAY = new JsonArray();

    @NotNull
    private final DatabaseType a;
    @NotNull
    private final String b;
    @Nullable
    private final String c;
    private final boolean d;
    private final boolean e;

    public SQLDataProvider(@NotNull DatabaseType var1, @NotNull String var2, @Nullable String var3, boolean var4, boolean var5) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = var5;
    }

    @NotNull
    public final DatabaseType getDatabaseType$core() {
        return this.a;
    }

    @NotNull
    public final String getTable$core() {
        return this.b;
    }

    @Nullable
    public final String getName$core() {
        return this.c;
    }

    public final boolean isInsideSingularEntity$core() {
        return this.d;
    }

    public final boolean getNameIsSection$core() {
        return this.e;
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
