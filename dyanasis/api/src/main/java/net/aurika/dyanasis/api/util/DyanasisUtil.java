package net.aurika.dyanasis.api.util;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class DyanasisUtil {

  /**
   * @param srcMap    the source map
   * @param destMap   the destination map
   * @param keyType   the key type
   * @param valueType the value map
   * @param <K>       the key type
   * @param <V>       the value type
   * @param <M>       the destination map type
   * @return the destination map
   */
  @Contract("_, _, _, _ -> param2")
  public static <K, V, M extends Map<K, V>> @NotNull M pickKVType(@NotNull Map<?, ?> srcMap, @NotNull M destMap, @NotNull Class<K> keyType, @NotNull Class<V> valueType) {
    Validate.Arg.notNull(srcMap, "srcMap");
    Validate.Arg.notNull(destMap, "destMap");
    Validate.Arg.notNull(keyType, "keyType");
    Validate.Arg.notNull(valueType, "valueType");

    for (Map.Entry<?, ?> entry : srcMap.entrySet()) {
      Object key = entry.getKey();
      Object value = entry.getValue();
      if (keyType.isInstance(key) && valueType.isInstance(value)) {
        destMap.put((K) key, (V) value);
      }
    }

    return destMap;
  }

  /**
   * @param srcMap  the source map
   * @param destMap the destination map
   * @param keyType the key type
   * @param <K>     the key type
   * @param <V>     the value type
   * @param <M>     the destination map type
   * @return the destination map
   */
  @Contract("_, _, _ -> param2")
  public static <K, V, M extends Map<K, V>> @NotNull M pickKeyType(@NotNull Map<?, V> srcMap, @NotNull M destMap, @NotNull Class<K> keyType) {
    Validate.Arg.notNull(srcMap, "srcMap");
    Validate.Arg.notNull(destMap, "destMap");
    Validate.Arg.notNull(keyType, "keyType");

    for (Map.Entry<?, V> entry : srcMap.entrySet()) {
      Object key = entry.getKey();
      V value = entry.getValue();
      if (keyType.isInstance(key)) {
        destMap.put((K) key, value);
      }
    }

    return destMap;
  }

  /**
   * @param srcMap    the source map
   * @param destMap   the destination map
   * @param valueType the value map
   * @param <K>       the key type
   * @param <V>       the value type
   * @param <M>       the destination map type
   * @return the destination map
   */
  @Contract("_, _, _ -> param2")
  public static <K, V, M extends Map<K, V>> @NotNull M pickValueType(@NotNull Map<K, ?> srcMap, @NotNull M destMap, @NotNull Class<V> valueType) {
    Validate.Arg.notNull(srcMap, "srcMap");
    Validate.Arg.notNull(destMap, "destMap");
    Validate.Arg.notNull(valueType, "valueType");

    for (Map.Entry<K, ?> entry : srcMap.entrySet()) {
      K key = entry.getKey();
      Object value = entry.getValue();
      if (valueType.isInstance(value)) {
        destMap.put(key, (V) value);
      }
    }

    return destMap;
  }

}
