package top.auspice.utils.nonnull;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.markers.KMutableList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class NonNullList<V> implements List<V>, KMutableList {
    @NotNull
    private final List<V> list;
    private final int size;

    public NonNullList(int size) {
        this(new ArrayList<>(size));
    }

    public NonNullList(@NotNull List<V> list) {
        Objects.requireNonNull(list);
        this.list = list;
        this.size = this.list.size();
    }

    @NotNull
    public final List<V> getList() {
        return this.list;
    }

    public void clear() {
        this.list.clear();
    }

    public V get(int index) {
        return this.list.get(index);
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @NotNull
    public Iterator<V> iterator() {
        return this.list.iterator();
    }

    @NotNull
    public ListIterator<V> listIterator() {
        return this.list.listIterator();
    }

    @NotNull
    public ListIterator<V> listIterator(int index) {
        return this.list.listIterator(index);
    }

    public V removeAt(int index) {
        return this.list.remove(index);
    }

    @NotNull
    public List<V> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    public V set(int index, V element) {
        return this.list.set(index, Nullability.assertNonNull(this, element));
    }

    public boolean add(V element) {
        return this.list.add(Nullability.assertNonNull(this, element));
    }

    public void add(int index, V element) {
        this.list.add(index, Nullability.assertNonNull(this, element));
    }

    @Override
    public V remove(int index) {
        return this.list.remove(index);
    }

    public boolean addAll(int index, @NotNull Collection<? extends V> elements) {
        Objects.requireNonNull(elements, "elements");
        return this.list.addAll(index, Nullability.assertNonNullElements(elements));
    }

    public boolean addAll(@NotNull Collection<? extends V> elements) {
        Objects.requireNonNull(elements, "elements");
        return this.list.addAll(Nullability.assertNonNullElements(elements));
    }

    public boolean remove(Object element) {
        return this.list.remove(Nullability.assertNonNull(this, element));
    }

    public int indexOf(Object element) {
        return this.list.indexOf(Nullability.assertNonNull(this, element));
    }

    public int lastIndexOf(Object element) {
        return this.list.lastIndexOf(Nullability.assertNonNull(this, element));
    }

    public boolean contains(Object element) {
        return this.list.contains(Nullability.assertNonNull(this, element));
    }

    public boolean containsAll(@NotNull Collection<?> elements) {
        Objects.requireNonNull(elements, "elements");
        return new HashSet<>(this.list).containsAll(Nullability.assertNonNullElements(elements));
    }

    public boolean retainAll(@NotNull Collection<?> elements) {
        Objects.requireNonNull(elements, "elements");
        return this.list.retainAll(CollectionsKt.toSet(Nullability.assertNonNullElements(elements)));
    }

    public boolean removeAll(@NotNull Collection<?> elements) {
        Objects.requireNonNull(elements, "elements");
        return this.list.removeAll(CollectionsKt.toSet(Nullability.assertNonNullElements(elements)));
    }

    public <T> T @NotNull [] toArray(T @NotNull [] array) {
        Objects.requireNonNull(array, "array");
        return this.list.toArray(array);
    }

    public Object @NotNull [] toArray() {
        return this.list.toArray();
    }
}
