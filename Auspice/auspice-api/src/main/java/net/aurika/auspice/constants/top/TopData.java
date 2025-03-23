package net.aurika.auspice.constants.top;

import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface TopData<V> extends Comparator<V> {
    Optional<V> getTopPosition(int var1);

    @IntRange(from = 1L) Optional<Integer> getPositionOf(@NotNull V var1);

    default boolean isIncluded(V var1) {
        return this.getPositionOf(var1).isPresent();
    }

    @NotNull @Unmodifiable List<V> getTop(int var1, int var2, @Nullable Predicate<V> var3);

    @NotNull @Unmodifiable List<V> getTop();

    @IntRange(from = 0L) int size();

    void update(@NotNull @Unmodifiable Collection<V> var1);
}
