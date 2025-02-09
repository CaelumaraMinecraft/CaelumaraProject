package net.aurika.data.database.flatfile.yaml;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.data.database.DatabaseType;
import net.aurika.data.database.flatfile.KeyedFlatFileDatabase;
import net.aurika.data.api.handler.KeyedDataHandler;
import net.aurika.data.api.KeyedDataObject;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;

public class KeyedYamlDatabase<K, T extends KeyedDataObject<K>> extends KeyedFlatFileDatabase<K, T> {
    public KeyedYamlDatabase(@NotNull Path var1, @NotNull KeyedDataHandler<K, T> var2) {
        super("yml", var1, var2);
    }

    @NotNull
    public DatabaseType getDatabaseType() {
        return DatabaseType.YAML;
    }

    @NotNull
    public T load(@NotNull K key, @NotNull BufferedReader var2) {
        Intrinsics.checkNotNullParameter(key, "");
        Intrinsics.checkNotNullParameter(var2, "");
        YamlMappingDataProvider var4 = YamlDatabase.load(this.getDataHandler().getIdHandler().toString(key), var2);
        return this.getDataHandler().load(var4, key);
    }

    public void save(@NotNull T obj, @NotNull BufferedWriter var2) {
        Intrinsics.checkNotNullParameter(obj, "");
        Intrinsics.checkNotNullParameter(var2, "");
        YamlDatabase.save(obj, this.getDataHandler(), var2);
    }
}
