package net.aurika.ecliptor.database.sql;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.util.reflection.Reflect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SQLDataProvider {

    static final @NotNull JsonObject EMPTY_JSON_OBJECT = new JsonObject();
    static final @NotNull JsonArray EMPTY_JSON_ARRAY = new JsonArray();

    private final @NotNull DatabaseType databaseType;
    private final @NotNull String table;
    private final @Nullable String name;
    private final boolean isInsideSingularEntity;
    private final boolean nameIsSection;

    public SQLDataProvider(@NotNull DatabaseType databaseType,
                           @NotNull String table,
                           @Nullable String name,
                           boolean isInsideSingularEntity,
                           boolean nameIsSection
    ) {
        Intrinsics.checkNotNullParameter(databaseType, "databaseType");
        Intrinsics.checkNotNullParameter(table, "");
        this.databaseType = databaseType;
        this.table = table;
        this.name = name;
        this.isInsideSingularEntity = isInsideSingularEntity;
        this.nameIsSection = nameIsSection;
    }

    public final @NotNull DatabaseType getDatabaseType$core() {
        return this.databaseType;
    }

    public final @NotNull String getTable$core() {
        return this.table;
    }

    public final @Nullable String getName$core() {
        return this.name;
    }

    public final boolean isInsideSingularEntity$core() {
        return this.isInsideSingularEntity;
    }

    public final boolean getNameIsSection$core() {
        return this.nameIsSection;
    }

    public final @NotNull String getNamed$core() {
        String var10000 = this.name;
        if (var10000 == null) {
            throw new IllegalStateException("No name set: " + Reflect.toString(this));
        } else {
            return var10000;
        }
    }

    public final @NotNull String nameSepOrEmpty$core() {
        String var10000 = this.name;
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
