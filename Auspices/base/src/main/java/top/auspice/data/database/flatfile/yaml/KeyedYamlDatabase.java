package top.auspice.data.database.flatfile.yaml;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.data.object.DataObject;
import top.auspice.data.object.KeyedDataObject;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.dataprovider.SectionableDataGetter;
import top.auspice.data.database.flatfile.KeyedFlatFileDatabase;
import top.auspice.data.handlers.abstraction.DataHandler;
import top.auspice.data.handlers.abstraction.KeyedDataHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;


public final class KeyedYamlDatabase<K, T extends KeyedDataObject.Impl<K>> extends KeyedFlatFileDatabase<K, T> {
    public KeyedYamlDatabase(@NotNull Path var1, @NotNull KeyedDataHandler<K, T> var2) {
        super("yml", var1, var2);
    }

    @NotNull
    public DatabaseType getDatabaseType() {
        return DatabaseType.YAML;
    }

    @NotNull
    public T load(@NotNull K var1, @NotNull BufferedReader var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        YamlMappingDataProvider var4 = YamlDatabase.load(this.getDataHandler().getIdHandler().toString(var1), var2);
        return (T)(((KeyedFlatFileDatabase)this).getDataHandler().load((SectionableDataGetter)var4, var1));
    }

    public void save(@NotNull T var1, @NotNull BufferedWriter var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        YamlDatabase.save((DataObject.Impl)var1, (DataHandler)((KeyedFlatFileDatabase)this).getDataHandler(), var2);
    }
}
