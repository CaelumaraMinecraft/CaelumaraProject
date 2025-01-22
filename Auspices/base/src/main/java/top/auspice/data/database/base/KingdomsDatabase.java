package top.auspice.data.database.base;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.DatabaseType;

import java.io.Closeable;

public interface KingdomsDatabase<T> extends Closeable {
    @NotNull DatabaseType getDatabaseType();

    void save(T obj);

    void deleteAllData();

    void close();
}
