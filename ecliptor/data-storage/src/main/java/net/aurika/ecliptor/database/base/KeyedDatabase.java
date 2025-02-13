package net.aurika.ecliptor.database.base;

import net.aurika.ecliptor.api.KeyedDataObject;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface KeyedDatabase<K, T extends KeyedDataObject<K>> extends Database<T> {
    @Nullable T load(@NotNull K key);

    void load(@NonNull Collection<K> keys, @NonNull Consumer<T> dataConsumer);

    @NonNull
    Collection<T> loadAllData(@Nullable Predicate<K> keyFilter);

    void save(@NonNull @Unmodifiable Collection<T> data);

    void delete(@NonNull K key);

    boolean hasData(@NonNull K key);

    @NonNull
    Collection<K> getAllDataKeys();
}
