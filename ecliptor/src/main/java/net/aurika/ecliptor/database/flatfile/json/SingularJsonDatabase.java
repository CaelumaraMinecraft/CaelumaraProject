package net.aurika.ecliptor.database.flatfile.json;

import net.aurika.ecliptor.api.DataObject;
import net.aurika.ecliptor.database.DatabaseType;
import net.aurika.ecliptor.database.flatfile.SingularFlatFileDatabase;
import net.aurika.ecliptor.handler.SingularDataHandler;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;

public class SingularJsonDatabase<T extends DataObject> extends SingularFlatFileDatabase<T> {

  public SingularJsonDatabase(@NotNull Path path, @NotNull SingularDataHandler<T> singularDataHandler) {
    super(path, singularDataHandler);
  }

  @Override
  public @NotNull DatabaseType getDatabaseType() {
    return DatabaseType.JSON;
  }

  @Override
  public @Nullable T load(@NotNull BufferedReader reader) {
    Checker.Arg.notNull(reader, "bufferedReader");
    return JsonDatabase.load(
        ((Object) this.getFile().toAbsolutePath()).toString(), this.getFile(), this.getDataHandler(), reader,
        (jsonObject -> SingularJsonDatabase.this.getDataHandler().load(new JsonObjectDataProvider(null, jsonObject)))
    );
  }

  @Override
  public void save(@NotNull T data, @NotNull BufferedWriter writer) {
    Checker.Arg.notNull(data, "t");
    Checker.Arg.notNull(writer, "bufferedWriter");
    JsonDatabase.save(data, this.getDataHandler(), writer);
  }

}
 