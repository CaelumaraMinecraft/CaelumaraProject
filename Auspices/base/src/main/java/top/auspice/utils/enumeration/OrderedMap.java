package top.auspice.utils.enumeration;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.utils.Checker;

import java.util.*;

public class OrderedMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {
    private OrderedMap<K, V>.Node @NotNull [] elements;
    private int size;
    private int modCount;

    public OrderedMap(int size) {
        this.elements = new OrderedMap.Node[size];
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int n) {
        this.size = n;
    }

    public @NotNull Set<Map.Entry<K, V>> entrySet() {
        String string = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + string);
    }

    public @NotNull Set<K> getKeys() {
        String string = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + string);
    }

    public @NotNull Collection<V> getValues() {
        String string = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + string);
    }

    @Override
    public void clear() {
        int n = this.modCount;
        this.modCount = n + 1;
        this.setSize(0);
        Arrays.fill(this.elements, null);
    }

    @Override
    public @Nullable V remove(Object key) {
        Node node = this.elements[key != null ? key.hashCode() : 0];
        if (node == null) {
            return null;
        }
        this.elements[key != null ? key.hashCode() : 0] = null;
        int n = this.size();
        this.setSize(n + -1);
        n = this.modCount;
        this.modCount = n + 1;
        return node.getValue();
    }

    @Override
    public @Nullable V get(Object key) {
        Node node = this.elements[key != null ? key.hashCode() : 0];
        return node != null ? node.getValue() : null;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.elements[key != null ? key.hashCode() : 0] != null;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        throw new UnsupportedOperationException();
    }

    public void putAll(@NotNull Map<? extends K, ? extends V> from) {
        Checker.Argument.checkNotNull(from, "from");
        String string = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + string);
    }

    @Override
    public @Nullable V put(K key, V value) {
        int hash = key != null ? key.hashCode() : 0;
        V previous = this.get(key);
        this.ensureCapacity(hash);
        this.elements[hash] = new Node(key, value);
        int n = this.modCount;
        this.modCount = n + 1;
        n = this.size();
        this.setSize(n + 1);
        return previous;
    }

    public void ensureCapacity(int elementHash) {
        if (elementHash > 1000000) {
            throw new IllegalStateException("Element hash exceeded a million");
        }
        if (elementHash < this.elements.length) {
            return;
        }
        OrderedMap<K, V>.Node[] newElements = new OrderedMap.Node[elementHash + 1];
        System.arraycopy(this.elements, 0, newElements, 0, this.elements.length);
        this.elements = newElements;
    }

    public class Node {
        private final K key;
        private final V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }
    }
}
