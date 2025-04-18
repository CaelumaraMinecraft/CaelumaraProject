package net.aurika.ecliptor.database.base;

import net.aurika.ecliptor.database.DatabaseType;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

public interface Database<T> extends Closeable {

  @NotNull DatabaseType getDatabaseType();

  void save(T data);

  void deleteAllData();

  void close();

}
