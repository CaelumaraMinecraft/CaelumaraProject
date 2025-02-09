package net.aurika.data.database.flatfile.json;

import net.aurika.data.database.DatabaseType;
import net.aurika.data.database.flatfile.KeyedFlatFileDatabase;
import net.aurika.data.api.handler.KeyedDataHandler;
import net.aurika.data.api.KeyedDataObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;
import java.util.Objects;

public class KeyedJsonDatabase<K, T extends KeyedDataObject<K>> extends KeyedFlatFileDatabase<K, T> {
    public KeyedJsonDatabase(@NotNull Path path, @NotNull KeyedDataHandler<K, T> keyedDataHandler) {
        super("json", path, keyedDataHandler);
    }

    @Override
    public @NotNull DatabaseType getDatabaseType() {
        return DatabaseType.JSON;
    }

    @Override
    public @Nullable T load(@NotNull K key, @NotNull BufferedReader bufferedReader) {
        Objects.requireNonNull(key, "");
        Objects.requireNonNull(bufferedReader, "");
        Path path = this.fileFromKey(key);
        return JsonDatabase.load(key.toString(), path, this.getDataHandler(), bufferedReader, jsonObject -> KeyedJsonDatabase.this.getDataHandler().load(new JsonObjectDataProvider(null, jsonObject), key));
    }

    @Override
    public void save(@NotNull T obj, @NotNull BufferedWriter bufferedWriter) {
        Objects.requireNonNull(obj, "");
        Objects.requireNonNull(bufferedWriter, "");
        JsonDatabase.save(obj, this.getDataHandler(), bufferedWriter);
    }
}
 