package net.aurika.data.database.flatfile.yaml;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import net.aurika.data.object.DataObject;
import net.aurika.data.database.DatabaseType;
import net.aurika.data.database.flatfile.SingularFlatFileDatabase;
import net.aurika.data.handlers.abstraction.SingularDataHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;

public final class SingularYamlDatabase<T extends DataObject> extends SingularFlatFileDatabase<T> {
    public SingularYamlDatabase(@NotNull Path var1, @NotNull SingularDataHandler<T> var2) {
        super(var1, var2);
    }

    public @NotNull DatabaseType getDatabaseType() {
        return DatabaseType.YAML;
    }

    public @NotNull T load(@NotNull BufferedReader reader) {
        Intrinsics.checkNotNullParameter(reader, "");
        YamlMappingDataProvider var2 = YamlDatabase.load(this.getFile().toAbsolutePath().toString(), reader);
        return this.getDataHandler().load(var2);
    }

    public void save(@NotNull T var1, @NotNull BufferedWriter writer) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(writer, "");
        YamlDatabase.save(var1, this.getDataHandler(), writer);
    }
}
