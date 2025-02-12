package net.aurika.util.iterator;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class RangedIterable<E> implements Iterable<E> {
    private final @NotNull Iterable<E> iterable;
    private final int limit, skip;

    public RangedIterable(@NotNull Iterable<E> iterable, int skip, int limit) {
        Checker.Arg.notNull(iterable, "iterable");
        this.iterable = iterable;
        this.limit = limit;
        this.skip = skip;
    }

    @Override
    public @NotNull Iterator<E> iterator() {
        return new RangedIterator<>(iterable.iterator(), skip, limit);
    }
}
