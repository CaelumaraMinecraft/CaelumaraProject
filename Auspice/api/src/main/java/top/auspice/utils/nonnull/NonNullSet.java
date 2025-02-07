//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package top.auspice.utils.nonnull;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
public  class NonNullSet<V> implements Set<V>, KMutableSet {
    @NotNull
    private final Set<V> set;
    private final int size;

    public NonNullSet(@NotNull Set<V> set) {
        Objects.requireNonNull(set);
        this.set = set;
        this.size = this.set.size();
    }

    @NotNull
    public final Set<V> getSet() {
        return this.set;
    }

    public void clear() {
        this.set.clear();
    }

    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    @NotNull
    public Iterator<V> iterator() {
        return this.set.iterator();
    }

    public int getSize() {
        return this.size;
    }

    public boolean addAll(@NotNull Collection<? extends V> elements) {
        Objects.requireNonNull(elements, "elements");
        return this.set.addAll(Nullability.assertNonNullElements(elements));
    }

    public boolean add(V element) {
        return this.set.add(Nullability.assertNonNull((Collection)this, element));
    }

    public boolean remove(Object element) {
        return this.set.remove(Nullability.assertNonNull((Collection)this, element));
    }

    public boolean contains(Object element) {
        return this.set.contains(Nullability.assertNonNull((Collection)this, element));
    }

    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        Objects.requireNonNull(elements, "elements");
        return this.set.containsAll(Nullability.assertNonNullElements(elements));
    }

    public boolean retainAll(@NotNull Collection<? extends Object> elements) {
        Objects.requireNonNull(elements, "elements");
        return this.set.retainAll((Collection)CollectionsKt.toSet((Iterable)Nullability.assertNonNullElements(elements)));
    }

    public boolean removeAll(@NotNull Collection<? extends Object> elements) {
        Objects.requireNonNull(elements, "elements");
        return this.set.removeAll((Collection)CollectionsKt.toSet((Iterable)Nullability.assertNonNullElements(elements)));
    }

    public NonNullSet() {
        this((Set)null, 1, (DefaultConstructorMarker)null);
    }

    public <T> T[] toArray(T[] array) {
        Objects.requireNonNull(array, "array");
        return CollectionToArray.toArray((Collection)this, array);
    }

    public Object[] toArray() {
        return CollectionToArray.toArray((Collection)this);
    }
}
