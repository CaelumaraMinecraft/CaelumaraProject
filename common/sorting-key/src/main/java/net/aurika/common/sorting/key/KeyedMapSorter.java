package net.aurika.common.sorting.key;

import net.aurika.common.key.Key;
import net.aurika.common.key.Keyed;
import net.aurika.common.sorting.api.Sorter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public interface KeyedMapSorter<T extends Keyed, M extends Map<Key, T>> extends Sorter<T, M> {

  static <T extends Keyed, M extends LinkedHashMap<Key, T>> @NotNull KeyedMapSorter<T, M> after(@NotNull Key afterKey, @NotNull Key targetKey) {
    return new KeyedMapSorter<T, M>() {
      @Override
      public void sort(@NotNull M container) {
        int i = 0;
        T prev = null;
        boolean foundTarget = false;
        Iterator<Map.Entry<Key, T>> iterator = container.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry<Key, T> entry = iterator.next();
          if (foundTarget) {
          } else {
            if (targetKey.equals(entry.getKey())) {
              foundTarget = true;
              T after = container.remove(afterKey);
            }
          }
          i++;
        }
      }
    };
  }

  @Override
  void sort(@NotNull M container);

  @Override
  default @NotNull Collection<T> elements(M container) {
    return container.values();
  }

}
