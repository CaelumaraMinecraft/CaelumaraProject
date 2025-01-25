package top.auspice.utils.container;

import net.aurika.annotations.data.Immutable;

@Immutable
public final class TriContainer<T, U, V> {
    private final T first;
    private final U second;
    private final V third;

    public TriContainer(final T first, final U second, final V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

    public V getThird() {
        return this.third;
    }

    @Override
    public String toString() {
        return "TriContainer [first=" + first + ", second=" + second + ", third=" + third + "]";
    }

    public static <T, U, V> TriContainer<T, U, V> of(final T first, final U second, final V third) {
        return new TriContainer<>(first, second, third);
    }
}
