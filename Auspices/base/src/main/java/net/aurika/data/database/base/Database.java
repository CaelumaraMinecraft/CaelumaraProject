package net.aurika.data.database.base;

import org.jetbrains.annotations.NotNull;
import net.aurika.data.database.DatabaseType;

import java.io.Closeable;

public interface Database<T> extends Closeable {
    @NotNull DatabaseType getDatabaseType();

    void save(T data);

    void deleteAllData();

    void close();
}
