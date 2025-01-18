package top.auspice.data.database.base;

import top.auspice.data.database.DatabaseType;

import java.io.Closeable;

public interface KingdomsDatabase<T> extends Closeable {
    DatabaseType getDatabaseType();

    void save(T var1);

    void deleteAllData();

    void close();
}
