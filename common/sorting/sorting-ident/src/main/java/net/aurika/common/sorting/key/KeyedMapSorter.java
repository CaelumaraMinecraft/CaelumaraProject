package net.aurika.common.sorting.key;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import net.aurika.common.sorting.api.Sorter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public interface KeyedMapSorter<T extends Identified, M extends Map<Ident, T>> extends Sorter<T, M> {

  static <T extends Identified, M extends LinkedHashMap<Ident, T>> @NotNull KeyedMapSorter<T, M> after(@NotNull Ident afterIdent, @NotNull Ident targetIdent) {
    return new KeyedMapSorter<T, M>() {
      @Override
      public void sort(@NotNull M container) {
        int i = 0;
        T prev = null;
        boolean foundTarget = false;
        Iterator<Map.Entry<Ident, T>> iterator = container.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry<Ident, T> entry = iterator.next();
          if (foundTarget) {
          } else {
            if (targetIdent.equals(entry.getKey())) {
              foundTarget = true;
              T after = container.remove(afterIdent);
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
