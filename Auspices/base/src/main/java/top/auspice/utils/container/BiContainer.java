package top.auspice.utils.container;

import top.auspice.api.annotations.data.Immutable;

@Immutable
public final class BiContainer<T, U> {
    private final T first;
    private final U second;

    public BiContainer(final T first, final U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

    @Override
    public String toString() {
        return "BiContainer [first=" + this.first + ", second=" + this.second + "]";
    }

    public static <T, U> BiContainer<T, U> of(final T first, final U second) {
        return new BiContainer<>(first, second);
    }
}
