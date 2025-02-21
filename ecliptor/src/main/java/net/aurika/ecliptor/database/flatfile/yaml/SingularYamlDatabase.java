package net.aurika.ecliptor.database.flatfile.yaml;

import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.flatfile.SingularFlatFileDatabase;
import net.aurika.ecliptor.handler.SingularDataHandler;
import net.aurika.ecliptor.api.DataObject;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;

public class SingularYamlDatabase<T extends DataObject> extends SingularFlatFileDatabase<T> {
    public SingularYamlDatabase(@NotNull Path var1, @NotNull SingularDataHandler<T> var2) {
        super(var1, var2);
    }

    public @NotNull DatabaseType getDatabaseType() {
        return DatabaseType.YAML;
    }

    public @NotNull T load(@NotNull BufferedReader reader) {
        Checker.Arg.notNull(reader, "reader");
        YamlMappingDataProvider yamlDataProvider = YamlDatabase.load(this.getFile().toAbsolutePath().toString(), reader);
        return this.getDataHandler().load(yamlDataProvider);
    }

    public void save(@NotNull T data, @NotNull BufferedWriter writer) {
        Checker.Arg.notNull(data, "data");
        Checker.Arg.notNull(writer, "writer");
        YamlDatabase.save(data, this.getDataHandler(), writer);
    }
}
