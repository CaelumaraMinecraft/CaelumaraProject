package top.auspice.data.database.flatfile.json;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.base.KeyedAuspiceObject;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.flatfile.KeyedFlatFileDatabase;
import top.auspice.data.handlers.abstraction.KeyedDataHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;

public final class KeyedJsonDatabase<K, T extends KeyedAuspiceObject<K>>
        extends KeyedFlatFileDatabase<K, T> {
    public KeyedJsonDatabase(@NotNull Path path, @NotNull KeyedDataHandler<K, T> keyedDataHandler) {
        super("json", path, keyedDataHandler);
    }

    @Override
    public @NotNull DatabaseType getDatabaseType() {
        return DatabaseType.JSON;
    }

    @Override
    public @Nullable T load(@NotNull K key, @NotNull BufferedReader bufferedReader) {
        Intrinsics.checkNotNullParameter(key, "");
        Intrinsics.checkNotNullParameter(bufferedReader, "");
        Path path = this.fileFromKey(key);
        return JsonDatabase.load(key.toString(), path, this.getDataHandler(), bufferedReader, jsonObject -> KeyedJsonDatabase.this.getDataHandler().load(new JsonObjectDataProvider(null, jsonObject), key));
    }

    @Override
    public void save(@NotNull T obj, @NotNull BufferedWriter bufferedWriter) {
        Intrinsics.checkNotNullParameter(obj, "");
        Intrinsics.checkNotNullParameter(bufferedWriter, "");
        JsonDatabase.save(obj, this.getDataHandler(), bufferedWriter);
    }
}
 