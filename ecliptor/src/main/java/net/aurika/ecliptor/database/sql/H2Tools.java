package net.aurika.ecliptor.database.sql;

import org.h2.tools.Recover;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public final class H2Tools {

  public H2Tools() {
  }

  public static void recover(@NotNull Path dbPath) {
    String folder = dbPath.getParent().toAbsolutePath().toString();
    String dbFileName = dbPath.getFileName().toString();

    try {
      Recover.execute(folder, dbFileName);
    } catch (Throwable thr) {
      throw new RuntimeException("Failed to run the recovery tool for '" + folder + "' -> '" + dbFileName + "'", thr);
    }
  }

}
