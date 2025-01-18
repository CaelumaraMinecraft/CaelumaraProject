package top.auspice.utils.map;

import org.jetbrains.annotations.Contract;

import java.util.Map;

public class MapUtils {

    @SuppressWarnings("unchecked")
    @Contract("_, _, _, _, _, _ -> param2")
    public static <K, V> Map<K, V> filter(Map<?, ?> map, Map<K, V> to,  Class<K> keyType, boolean nonNullKey, Class<V> valueType, boolean nonNullValue) {

        for (Map.Entry<?, ?> entry : map.entrySet()) {

            Object key = entry.getKey();
            Object value = entry.getValue();

            boolean putValue = true;

            if (key == null) {
                if (nonNullKey) {
                    continue;
                }
            } else {
                if (!keyType.isInstance(key)) {
                    continue;
                }
            }

            if (value == null) {
                if (nonNullValue) {
                    putValue = false;
                }
            } else {
                if (!valueType.isInstance(value)) {
                    putValue = false;
                }
            }

            if (putValue) {
                to.put((K) key, (V) value);
            }

        }

        return (Map<K, V>) map;

    }

    @SuppressWarnings("unchecked")
    @Contract("_, _, _, _, _ -> param1")
    public static <K, V> Map<K, V> filter(Map<?, ?> map, Class<K> keyType, boolean nonNullKey, Class<V> valueType, boolean nonNullValue) {

        for (Map.Entry<?, ?> entry : map.entrySet()) {

            Object key = entry.getKey();
            if (key == null) {
                if (nonNullKey) {
                    map.remove(null);
                    continue;
                }
            } else {
                if (!keyType.isInstance(key)) {
                    map.remove(key);
                    continue;
                }
            }

            Object value = entry.getValue();
            if (value == null) {
                if (nonNullValue) {
                    map.remove(key, null);

                }
            } else {
                if (!valueType.isInstance(value)) {
                    map.remove(key, value);
                }
            }


        }

        return (Map<K, V>) map;

    }


}
