package top.auspice.utils.arrays;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class CursorList<T> {
    @NotNull
    private final List<T> value;
    private int cursor;

    public CursorList(@NotNull List<T> list) {
        Objects.requireNonNull(list);
        this.value = list;
    }

    @NotNull
    public final List<T> getList() {
        return this.value;
    }

    public final int getCursor() {
        return this.cursor;
    }

    public final void setCursor(int n) {
        this.cursor = this.checkIndex(n);
    }

    public final void offset(int offset) {
        this.setCursor(this.cursor + offset);
    }

    public final int checkIndex(int index) {
        if (!(index >= 0 && index < this.value.size())) {
            new RuntimeException("Cursor " + index + " out of bounds: size=" + this.value.size()).printStackTrace();
            String string = "Cursor " + index + " out of bounds: size=" + this.value.size();
            throw new IllegalArgumentException(string);
        }
        return index;
    }

    public final T next() {
        int n = this.cursor;
        this.cursor = n + 1;
        return this.value.get(this.checkIndex(n));
    }

    public final T current() {
        return this.value.get(this.checkIndex(this.hasNext(1) ? this.cursor : this.cursor - 1));
    }

    public final T peekNext(int offset) {
        return this.value.get(this.checkIndex(this.cursor + offset));
    }

    public final T previous() {
        this.cursor += -1;
        return this.value.get(this.checkIndex(this.cursor));
    }

    public final boolean hasNext(int n) {
        return this.value.size() >= this.cursor + n;
    }

    public final T get(int index) {
        return this.value.get(this.checkIndex(index));
    }

    public final T peekNext() {
        return this.peekNext(1);
    }

    public final boolean hasNext() {
        return this.hasNext(1);
    }
}
