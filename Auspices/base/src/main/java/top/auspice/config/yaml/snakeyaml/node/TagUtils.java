package top.auspice.config.yaml.snakeyaml.node;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.Tag;
import top.auspice.utils.Checker;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

public final class TagUtils {
    public static @Nullable Tag standardTypeToTag(@NotNull Class<?> type) {
        Checker.Argument.checkNotNull(type, "type");

        if (int.class == type || long.class == type || byte.class == type || short.class == type) {
            return Tag.INT;
        }

        if (float.class == type || double.class == type) {
            return Tag.FLOAT;
        }

        if (boolean.class == type || Boolean.class == type) {
            return Tag.BOOL;
        }

        if (byte[].class == type) {
            return Tag.BINARY;
        }

        if (
                short[].class == type
                        || int[].class == type
                        || long[].class == type
                        || float[].class == type
                        || double[].class == type
                        || char[].class == type
                        || boolean[].class == type
        ) {
            return Tag.SEQ;
        }

        if (String.class == type) {
            return Tag.STR;
        }
        if (Number.class.isAssignableFrom(type)) {
            if (
                    Byte.class.isAssignableFrom(type)
                            || Short.class.isAssignableFrom(type)
                            || Integer.class.isAssignableFrom(type)
                            || Long.class.isAssignableFrom(type)
                            || BigInteger.class.isAssignableFrom(type)
            ) {
                return Tag.INT;
            }
            return Tag.FLOAT;
        }
        if (Collection.class.isAssignableFrom(type)) {
            return Tag.SEQ;
        }
        if (Map.class.isAssignableFrom(type)) {
            return Tag.MAP;
        }

        return null;
    }
}
