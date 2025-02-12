package net.aurika.nbt.internal;

import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractIterator<T> implements Iterator<T> {
    private boolean needNext = true;
    private boolean end;
    private T next;

    public AbstractIterator() {
    }

    protected abstract @Nullable T computeNext();

    protected final @Nullable T end() {
        this.end = true;
        return null;
    }

    public final T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        } else {
            T next = this.next;
            this.needNext = true;
            this.next = null;
            return next;
        }
    }

    public final boolean hasNext() {
        if (!this.end && this.needNext) {
            this.needNext = false;
            this.next = (T) this.computeNext();
        }

        return !this.end;
    }

    public final void forEachRemaining(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        if (!this.end) {
            if (!this.needNext) {
                action.accept(this.next);
                this.next = null;
            }

            while (true) {
                T next = (T) this.computeNext();
                if (this.end) {
                    return;
                }

                action.accept(next);
            }
        }
    }

    public final void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
