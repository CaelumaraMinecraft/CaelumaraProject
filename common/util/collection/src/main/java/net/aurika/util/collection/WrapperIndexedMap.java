package net.aurika.util.collection;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class WrapperIndexedMap<K, V> implements IndexedMap<K, V> {

  static final Object emptyKey = new Object();

  private K emptyKey() { return (K) emptyKey; }

  private final @NotNull List<K> keys;
  private final @NotNull Map<K, V> map;

  public WrapperIndexedMap(@NotNull List<K> keys, @NotNull Map<K, V> map) {
    Validate.Arg.notNull(keys, "keys");
    Validate.Arg.notNull(map, "map");
    this.keys = keys;
    this.map = map;
  }

  public int getIndex(K key) {
    return keys.indexOf(key);
  }

  @Override
  public Entry<K, V> get(int index) {
    K key = keys.get(index);
    return new DefaultEntry(key);
  }

  @Override
  public void insert(int index, K key, V value) {
    keys.add(index, key);
    map.put(key, value);
  }

  @Override
  public Entry<K, V> remove(int index) {
    K key = keys.remove(index);
    V value = map.remove(key);
    return new AbstractMap.SimpleEntry<>(key, value);
  }

  @Override
  public Entry<K, V> replace(int index, K key, V value) {
    K oldKey = keys.remove(index);
    keys.set(index, key);
    V oldValue = map.remove(oldKey);
    map.put(key, value);
    return new AbstractMap.SimpleEntry<>(oldKey, oldValue);
  }

  @Override
  public boolean hasEntry(int index) {
    K key = keys.get(index);         // TODO
    return map.containsKey(key);
  }

  @Override
  public int size() {
    return keys.size();
  }

  @Override
  public boolean isEmpty() {
    return keys.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return keys.contains(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  @Override
  public V get(Object key) {
    return map.get(key);
  }

  @Override
  public @Nullable V put(K key, V value) {
    if (!keys.contains(key)) {
      keys.add(key);
    }
    return map.put(key, value);
  }

  @Override
  public V remove(Object key) {
    keys.remove(key);
    return map.remove(key);
  }

  @Override
  public void putAll(@NotNull Map<? extends K, ? extends V> m) {
    for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void clear() {
    keys.clear();
    map.clear();
  }

  @Override
  public @NotNull Set<K> keySet() {
    return new KeySet();
  }

  @Override
  public @NotNull Collection<V> values() {
    return new Values();
  }

  @Override
  public @NotNull Set<Entry<K, V>> entrySet() {
    return new EntrySet();
  }

  public class DefaultEntry implements Entry<K, V> {

    private final K key;

    public DefaultEntry(K key) { this.key = key; }

    @Override
    public K getKey() {
      return key;
    }

    @Override
    public V getValue() {
      return map.get(key);
    }

    @Override
    public V setValue(V value) {
      return map.put(key, value);
    }

  }

  public class KeySet extends AbstractSet<K> implements Set<K> {

    @Override
    public int size() {
      return WrapperIndexedMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return WrapperIndexedMap.this.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
      return WrapperIndexedMap.this.containsKey(o);
    }

    @Override
    public @NotNull Iterator<K> iterator() {
      return WrapperIndexedMap.this.keys.iterator();
    }

    @Override
    public @NotNull Object @NotNull [] toArray() {
      return WrapperIndexedMap.this.keys.toArray();
    }

    @Override
    public @NotNull <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {
      return WrapperIndexedMap.this.keys.toArray(a);
    }

    @Override
    public boolean add(K k) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
      return WrapperIndexedMap.this.remove(o) != null;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
      return WrapperIndexedMap.this.keys.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends K> c) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
      WrapperIndexedMap.this.clear();
    }

  }

  public class Values extends AbstractCollection<V> implements Collection<V> {

    @Override
    public int size() {
      return WrapperIndexedMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return WrapperIndexedMap.this.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
      return WrapperIndexedMap.this.containsValue(o);
    }

    @Override
    public @NotNull Iterator<V> iterator() {
      return WrapperIndexedMap.this.map.values().iterator();
    }

    @Override
    public @NotNull Object @NotNull [] toArray() {
      return WrapperIndexedMap.this.map.values().toArray();
    }

    @Override
    public @NotNull <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {
      return WrapperIndexedMap.this.map.values().toArray(a);
    }

    @Override
    public boolean add(V v) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
      return map.values().containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends V> c) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
      WrapperIndexedMap.this.clear();
    }

  }

  public class EntrySet extends AbstractSet<Entry<K, V>> implements Set<Entry<K, V>> {

    @Override
    public int size() {
      return WrapperIndexedMap.this.size();
    }

    @Override
    public boolean isEmpty() {
      return WrapperIndexedMap.this.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
      return WrapperIndexedMap.this.containsKey(o);
    }

    @Override
    public @NotNull Iterator<Map.Entry<K, V>> iterator() {
      return new Itr();
    }

    @Override
    public @NotNull Object[] toArray() {
      return super.toArray();
    }

    @Override
    public @NotNull <T> T[] toArray(@NotNull T[] a) {
      return super.toArray(a);
    }

    @Override
    public boolean add(Entry<K, V> kvEntry) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
      return super.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
      return super.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Entry<K, V>> c) {
      return super.addAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
      return super.retainAll(c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
      return super.removeAll(c);
    }

    @Override
    public void clear() {
      WrapperIndexedMap.this.clear();
    }

    public class Itr implements Iterator<Map.Entry<K, V>> {

      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < WrapperIndexedMap.this.size();
      }

      @Override
      public Entry<K, V> next() {
        return WrapperIndexedMap.this.get(index++);
      }

    }

  }

  @Override
  public @NotNull String toString() {
    StringBuilder builder = new StringBuilder("{\n");
    for (Map.Entry<K, V> entry : entrySet()) {
      builder.append(entry.getKey()).append("=").append(entry.getValue()).append(",\n");
    }
    builder.delete(builder.length() - 2, builder.length());
    builder.append("\n}");
    return builder.toString();
  }

}
