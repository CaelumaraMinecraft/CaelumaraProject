package net.aurika.util.enumeration;

import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class OrderedSet<E> extends AbstractSet<E> implements Set<E> {

  private @Nullable Object @NotNull [] elements;
  private int size;
  private int modCount;

  public OrderedSet(int size) {
    this.elements = new Object[size];
  }

  public int size() {
    return this.size;
  }

  public void setSize(int var1) {
    this.size = var1;
  }

  public OrderedSet(@NotNull Collection<? extends E> collection) {
    this(Checker.Arg.notNull(collection, "collection").size());
    this.addAll(collection);
  }

  public boolean add(E element) {
    int hash = element != null ? element.hashCode() : 0;
    if (hash < this.elements.length && this.elements[hash] != null) {
      return true;
    } else {
      int this_$iv = this.modCount++;
      this.ensureCapacity(hash);
      this.elements[hash] = element;
      this_$iv = this.size();
      this.setSize(this_$iv + 1);
      return false;
    }
  }

  public void ensureCapacity(int elementHash) {
    if (elementHash >= this.elements.length) {
      Object[] newElements = new Object[elementHash + 1];
      System.arraycopy(this.elements, 0, newElements, 0, this.elements.length);
      this.elements = newElements;
    }
  }

  public boolean addAll(@NotNull Collection<? extends E> elements) {
    Checker.Arg.notNull(elements, "elements");
    this.ensureCapacity(elements.size());
    this.modCount++;

    for (E element : elements) {
      this.add(element);
    }

    return true;
  }

  public void clear() {
    this.modCount++;
    Arrays.fill(this.elements, null);
    this.setSize(0);
  }

  public @NotNull java.util.Iterator<E> iterator() {
    return new Iterator();
  }

  public boolean remove(Object element) {
    int hash = element != null ? element.hashCode() : 0;
    int $i$f$internalContains = 0;
    boolean contained = hash < this.elements.length && this.elements[hash] != null;
    if (contained) {
      int this_$iv = this.modCount++;
      this.elements[hash] = null;
      this_$iv = this.size();
      this.setSize(this_$iv + -1);
    }

    return contained;
  }

  public boolean removeAll(@NotNull Collection<? extends Object> elements) {
    Checker.Arg.notNull(elements, "elements");
    this.modCount++;

    for (Object element : elements) {
      this.remove(element);
    }

    return true;
  }

  public boolean retainAll(@NotNull Collection<? extends Object> elements) {
    Checker.Arg.notNull(elements, "elements");
    java.util.Iterator<E> iter = this.iterator();
    this.modCount++;

    while (iter.hasNext()) {
      E next = iter.next();
      if (!elements.contains(next)) {
        iter.remove();
      }
    }

    return true;
  }

  public boolean contains(Object element) {
    int hash$iv = element != null ? element.hashCode() : 0;
    int $i$f$internalContains = 0;
    return hash$iv < this.elements.length && this.elements[hash$iv] != null;
  }

  private boolean internalContains(int hash) {
    int $i$f$internalContains = 0;
    return hash < this.elements.length && this.elements[hash] != null;
  }

  public boolean containsAll(@NotNull Collection<? extends Object> elements) {
    Checker.Arg.notNull(elements, "elements");

    for (Object element : elements) {
      if (!this.contains(element)) {
        return false;
      }
    }

    return true;
  }

  public boolean isEmpty() {
    return this.size() == 0;
  }

  public final class Iterator implements java.util.Iterator<E> {

    private int cursor;
    private int iterModCount;

    public Iterator() {
      this.iterModCount = OrderedSet.this.modCount;
    }

    public int getCursor() {
      return this.cursor;
    }

    public void setCursor(int var1) {
      this.cursor = var1;
    }

    public boolean hasNext() {
      this.checkModCount();

      while (this.cursor != OrderedSet.this.elements.length) {
        Object element = OrderedSet.this.elements[this.cursor];
        if (element != null) {
          return true;
        }

        int var2 = this.cursor++;
      }

      return false;
    }

    public E next() {
      if (!this.hasNext()) {
        throw new NoSuchElementException("Size=" + OrderedSet.this.size());
      } else {
        Object[] var10000 = OrderedSet.this.elements;
        int var1 = this.cursor++;
        return (E) var10000[var1];
      }
    }

    private void checkModCount() {
      if (this.iterModCount != OrderedSet.this.modCount) {
        throw new ConcurrentModificationException();
      }
    }

    public void remove() {
      this.checkModCount();
      if (OrderedSet.this.elements[this.cursor] == null) {
        throw new IllegalStateException("Element already removed, next() not called");
      } else {
        OrderedSet.this.elements[this.cursor] = null;
        OrderedSet<E> var1 = OrderedSet.this;
        var1.modCount = var1.modCount + 1;
        this.iterModCount = var1.modCount;
        int var2 = var1.size();
        var1.setSize(var2 + -1);
      }
    }

  }

}
