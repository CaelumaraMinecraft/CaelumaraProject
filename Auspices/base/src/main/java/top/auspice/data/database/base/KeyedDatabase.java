package top.auspice.data.database.base;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import top.auspice.data.object.KeyedDataObject;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface KeyedDatabase<K, T extends KeyedDataObject<K>> extends Database<T> {
    @Nullable T load(@NonNull K key);

    void load(@NonNull Collection<K> var1, @NonNull Consumer<T> var2);

    @NonNull
    Collection<T> loadAllData(@Nullable Predicate<K> var1);

    void save(@NonNull @Unmodifiable Collection<T> var1);

    void delete(@NonNull K var1);

    boolean hasData(@NonNull K var1);

    @NonNull
    Collection<K> getAllDataKeys();
}
