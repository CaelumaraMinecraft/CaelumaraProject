package net.aurika.util.collection;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CursorList<T> {

  private final @NotNull List<T> list;
  private int cursor;

  public CursorList(@NotNull List<T> list) {
    Validate.Arg.notNull(list, "list");
    this.list = list;
  }

  public final @NotNull List<T> getList() {
    return list;
  }

  public final int getCursor() {
    return cursor;
  }

  public final void setCursor(int n) {
    cursor = checkIndex(n);
  }

  public final void offset(int offset) {
    setCursor(cursor + offset);
  }

  public final int checkIndex(int index) {
    if (!(index >= 0 && index < list.size())) {
      new RuntimeException("Cursor " + index + " out of bounds: size=" + list.size()).printStackTrace();
      String string = "Cursor " + index + " out of bounds: size=" + list.size();
      throw new IllegalArgumentException(string);
    }
    return index;
  }

  public final T next() {
    int n = cursor;
    cursor = n + 1;
    return list.get(checkIndex(n));
  }

  public final T current() {
    return list.get(checkIndex(hasNext(1) ? cursor : cursor - 1));
  }

  public final T peekNext(int offset) {
    return list.get(checkIndex(cursor + offset));
  }

  public final T previous() {
    cursor -= 1;
    return list.get(checkIndex(cursor));
  }

  public final boolean hasNext(int n) {
    return list.size() >= cursor + n;
  }

  public final T get(int index) {
    return list.get(checkIndex(index));
  }

  public final T peekNext() {
    return peekNext(1);
  }

  public final boolean hasNext() {
    return hasNext(1);
  }

}
