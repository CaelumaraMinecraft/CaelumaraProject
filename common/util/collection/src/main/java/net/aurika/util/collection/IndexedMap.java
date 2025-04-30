package net.aurika.util.collection;

import org.intellij.lang.annotations.Flow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IndexedMap<K, V> extends Map<K, V> {

  @Contract(pure = true)
  int getIndex(K key);

  @Contract(pure = true)
  Entry<K, V> get(int index);

  /**
   * Inserts a key-value pair and moves back the after entries and shifts entries after the index.
   *
   * @param index the index
   * @param key   the key
   * @param value the value
   */
  void insert(int index, K key, V value);

  Entry<K, V> remove(int index);

  /**
   * Replaces a key-value pair and returns the old entry.
   *
   * @param index the index
   * @param key   the key
   * @param value the value
   * @return the previous entry
   */
  Entry<K, V> replace(int index, K key, V value);

  boolean hasEntry(int index);

  @Override
  @Contract(pure = true)
  int size();

  @Override
  @Contract(pure = true)
  boolean isEmpty();

  @Override
  @Contract(pure = true)
  boolean containsKey(Object key);

  @Override
  @Contract(pure = true)
  boolean containsValue(Object value);

  @Override
  @Flow(source = "this.values", sourceIsContainer = true)
  V get(Object key);

  @Override
  @Nullable V put(K key, V value);

  @Override
  V remove(Object key);

  @Override
  void putAll(@NotNull Map<? extends K, ? extends V> m);

  @Override
  void clear();

  @Override
  @NotNull Set<K> keySet();

  @Override
  @NotNull Collection<V> values();

  @Override
  @NotNull Set<Entry<K, V>> entrySet();

  @Override
  int hashCode();

  @Override
  boolean equals(Object obj);

}
