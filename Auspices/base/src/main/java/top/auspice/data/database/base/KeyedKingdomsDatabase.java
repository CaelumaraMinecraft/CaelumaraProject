package top.auspice.data.database.base;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import top.auspice.constants.base.KeyedAuspiceObject;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface KeyedKingdomsDatabase<K, T extends KeyedAuspiceObject<K>> extends KingdomsDatabase<T> {
    @Nullable T load(@NonNull K key);

    void load(@NonNull Collection<K> var1, @NonNull Consumer<T> var2);

    @NonNull Collection<T> loadAllData(@Nullable Predicate<K> var1);

    void save(@NonNull @Unmodifiable Collection<T> var1);

    void delete(@NonNull K var1);

    boolean hasData(@NonNull K var1);

    @NonNull Collection<K> getAllDataKeys();
}
