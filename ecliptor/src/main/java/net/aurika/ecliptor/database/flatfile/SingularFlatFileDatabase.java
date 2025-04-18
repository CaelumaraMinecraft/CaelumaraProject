package net.aurika.ecliptor.database.flatfile;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.utils.filesystem.FSUtil;
import net.aurika.auspice.utils.unsafe.CloseableUtils;
import net.aurika.ecliptor.api.DataObject;
import net.aurika.ecliptor.database.base.SingularDatabase;
import net.aurika.ecliptor.handler.SingularDataHandler;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public abstract class SingularFlatFileDatabase<T extends DataObject> implements SingularDatabase<T> {

  private final @NotNull Path file;
  private final @NotNull SingularDataHandler<T> dataHandler;

  public SingularFlatFileDatabase(@NotNull Path file, @NotNull SingularDataHandler<T> dataHandler) {
    Checker.Arg.notNull(file, "file");
    Checker.Arg.notNull(dataHandler, "dataHandler");
    this.file = file;
    this.dataHandler = dataHandler;
  }

  public final @NotNull Path getFile() {
    return this.file;
  }

  public final @NotNull SingularDataHandler<T> getDataHandler() {
    return this.dataHandler;
  }

  public final @Nullable T load() {
    if (!Files.exists(this.file, new LinkOption[0])) {
      return null;
    } else {
      BufferedReader reader = null;
      try {
        reader = Files.newBufferedReader(this.file, StandardCharsets.UTF_8);
      } catch (IOException e) {  // TODO
        throw new RuntimeException(e);
      }
      Throwable var2 = null;
      boolean var6 = false;

      T data;
      try {
        var6 = true;
        Intrinsics.checkNotNull(reader);
        data = this.load(reader);
        var6 = false;
      } catch (Throwable var7) {
        var2 = var7;
        throw var7;
      } finally {
        if (var6) {
          CloseableUtils.closeFinally(reader, var2);
        }
      }

      CloseableUtils.closeFinally(reader, null);
      return data;
    }
  }

  public abstract @Nullable T load(@NotNull BufferedReader reader);

  public abstract void save(@NotNull T data, @NotNull BufferedWriter writer);

  public final void save(@NotNull T data) {
    Checker.Arg.notNull(data, "data");

    try {
      BufferedWriter writer = FSUtil.standardWriter(this.file);
      Throwable thr = null;
      boolean var8 = false;

      try {
        var8 = true;
        Intrinsics.checkNotNull(writer);
        this.save(data, writer);
        var8 = false;
      } catch (Throwable var9) {
        thr = var9;
        throw var9;
      } finally {
        if (var8) {
          CloseableUtils.closeFinally(writer, thr);
        }
      }

      CloseableUtils.closeFinally(writer, null);
    } catch (IOException var11) {
      var11.printStackTrace();
    }
  }

  public final boolean hasData() {
    return Files.exists(this.file, new LinkOption[0]);
  }

  public final void deleteAllData() {
    try {
      Files.delete(this.file);
    } catch (IOException e) {  // TODO
      throw new RuntimeException(e);
    }
  }

  public final void close() {
  }

}
