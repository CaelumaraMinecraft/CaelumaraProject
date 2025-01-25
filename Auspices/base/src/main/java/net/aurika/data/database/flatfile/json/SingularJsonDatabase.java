package net.aurika.data.database.flatfile.json;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.data.database.DatabaseType;
import net.aurika.data.database.flatfile.SingularFlatFileDatabase;
import net.aurika.data.handlers.abstraction.SingularDataHandler;
import net.aurika.data.object.DataObject;
import net.aurika.utils.Checker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;

public final class SingularJsonDatabase<T extends DataObject> extends SingularFlatFileDatabase<T> {
    public SingularJsonDatabase(@NotNull Path path, @NotNull SingularDataHandler<T> singularDataHandler) {
        super(path, singularDataHandler);
    }

    @Override
    public @NotNull DatabaseType getDatabaseType() {
        return DatabaseType.JSON;
    }

    @Override
    public @Nullable T load(@NotNull BufferedReader reader) {
        Checker.Argument.checkNotNull(reader, "bufferedReader");
        return JsonDatabase.load(((Object) this.getFile().toAbsolutePath()).toString(), this.getFile(), this.getDataHandler(), reader, (jsonObject -> SingularJsonDatabase.this.getDataHandler().load(new JsonObjectDataProvider(null, jsonObject))));
    }

    @Override
    public void save(@NotNull T t, @NotNull BufferedWriter writer) {
        Checker.Argument.checkNotNull(t, "t");
        Checker.Argument.checkNotNull(writer, "bufferedWriter");
        JsonDatabase.save(t, this.getDataHandler(), writer);
    }
}
 