package top.auspice.data.database.flatfile.json;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.object.DataObject;
import top.auspice.data.database.DatabaseType;
import top.auspice.data.database.flatfile.SingularFlatFileDatabase;
import top.auspice.data.handlers.abstraction.SingularDataHandler;
import top.auspice.utils.Checker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;

public final class SingularJsonDatabase<T extends DataObject.Impl> extends SingularFlatFileDatabase<T> {
    public SingularJsonDatabase(@NotNull Path path, @NotNull SingularDataHandler<T> singularDataHandler) {
        super(path, singularDataHandler);
    }

    @Override
    public @NotNull DatabaseType getDatabaseType() {
        return DatabaseType.JSON;
    }

    @Override
    public @Nullable T load(@NotNull BufferedReader bufferedReader) {
        Checker.Argument.checkNotNull(bufferedReader, "bufferedReader");
        return JsonDatabase.load(((Object) this.getFile().toAbsolutePath()).toString(), this.getFile(), this.getDataHandler(), bufferedReader, (jsonObject -> SingularJsonDatabase.this.getDataHandler().load(new JsonObjectDataProvider(null, jsonObject))));
    }

    @Override
    public void save(@NotNull T t, @NotNull BufferedWriter bufferedWriter) {
        Checker.Argument.checkNotNull(t, "t");
        Checker.Argument.checkNotNull(bufferedWriter, "bufferedWriter");
        JsonDatabase.save(t, this.getDataHandler(), bufferedWriter);
    }
}
 