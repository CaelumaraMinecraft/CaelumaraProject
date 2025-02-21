package net.aurika.util.collection;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * There are many ways to do this with the standard library, but this is the most direct and efficient method.
 */
public final class ArrayIterator<E> implements Iterator<E> {
    private final E @NotNull [] array;
    private final int end;
    private int index;

    private ArrayIterator(E @NotNull [] array, int start, int end) {
        Validate.Arg.notNull(array, "array");
        this.array = array;
        this.index = start;
        this.end = end;
    }

    @Contract("_, _, _ -> new")
    public static <E> @NotNull ArrayIterator<E> of(E[] array, int start, int end) {
        Objects.requireNonNull(array, "Array is null");

        if (end > array.length)
            throw new IllegalArgumentException("End index is greater than the array length: " + end + " > " + array.length);
        if (start < 0)
            throw new IllegalArgumentException("Start index is negative: " + start);
        if (start > end)
            throw new IllegalArgumentException("Start index is greater than the array length: " + start + " > " + end);

        return new ArrayIterator<>(array, 0, array.length);
    }

    @Contract("_ -> new")
    public static <E> @NotNull ArrayIterator<E> of(E @NotNull [] array) {
        Validate.Arg.notNull(array, "array");
        return new ArrayIterator<>(array, 0, array.length);
    }

    @Override
    public boolean hasNext() {
        return index < end;
    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoSuchElementException();
        return array[index++];
    }
}
