package net.aurika.ecliptor.database.base;

import net.aurika.ecliptor.api.KeyedDataObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface KeyedDatabase<K, T extends KeyedDataObject<K>> extends Database<T> {
    @Nullable T load(@NotNull K key);

    void load(@NotNull Collection<K> keys, @NotNull Consumer<T> dataConsumer);

    @NotNull Collection<T> loadAllData(@Nullable Predicate<K> keyFilter);

    void save(@NotNull @Unmodifiable Collection<T> data);

    void delete(@NotNull K key);

    boolean hasData(@NotNull K key);

    @NotNull Collection<K> getAllDataKeys();
}
