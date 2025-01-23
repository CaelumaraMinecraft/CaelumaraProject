package top.auspice.data.database.flatfile.yaml;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.data.object.DataObject;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.flatfile.SingularFlatFileDatabase;
import top.auspice.data.handlers.abstraction.SingularDataHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;

public final class SingularYamlDatabase<T extends DataObject.Impl> extends SingularFlatFileDatabase<T> {
    public SingularYamlDatabase(@NotNull Path var1, @NotNull SingularDataHandler<T> var2) {
        super(var1, var2);
    }

    public @NotNull DatabaseType getDatabaseType() {
        return DatabaseType.YAML;
    }

    public @NotNull T load(@NotNull BufferedReader var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        YamlMappingDataProvider var2 = YamlDatabase.load(this.getFile().toAbsolutePath().toString(), var1);
        return this.getDataHandler().load(var2);
    }

    public void save(@NotNull T var1, @NotNull BufferedWriter var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        YamlDatabase.save(var1, this.getDataHandler(), var2);
    }
}
