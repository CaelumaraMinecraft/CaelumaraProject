package net.aurika.util.string;

import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;
import net.aurika.namespace.NSedKey;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

public final class PrettyStringFactory {

    public static @NotNull String toPrettyString(@Nullable Object $this$toPrettyString) {
        PrettyStringContext context = new PrettyStringContext(new StringBuilder(), 0);
        context.delegate($this$toPrettyString);
        return context.getString().toString();
    }

    private static final @NotNull Map<Class<?>, PrettyString<?>> REGISTRY = new IdentityHashMap<>();

    private PrettyStringFactory() {
        throw new RuntimeException("Can not get constants of class: " + this.getClass().getName());
    }

    public static @NotNull Map<Class<?>, PrettyString<?>> getRegistry() {
        return REGISTRY;
    }

    public static @Nullable PrettyString<?> findSpecialized(@Nullable Class<?> clazz) {
        if (clazz != null && !Objects.equals(clazz, Object.class)) {
            PrettyString<?> var6 = REGISTRY.get(clazz);
            if (var6 == null) {
                var6 = findSpecialized(clazz.getSuperclass());
                if (var6 == null) {
                    Class<?>[] var7 = clazz.getInterfaces();
                    Checker.Expr.notNull(var7, "Class<?>.getInterfaces()");
                    int var2 = 0;
                    int var3 = var7.length;

                    while (true) {
                        if (var2 >= var3) {
                            var6 = null;
                            break;
                        }

                        Class<?> it = var7[var2];
                        var6 = findSpecialized(it);
                        if (var6 != null) {
                            break;
                        }

                        ++var2;
                    }
                }
            }

            return var6;
        } else {
            return null;
        }
    }

    public static <K, V> void associatedArrayMap(@NotNull Map<K, ? extends V> map, @NotNull PrettyStringContext context) {
        Checker.Arg.notNull(map, "map");
        Checker.Arg.notNull(context, "context");
        StringBuilder builder = context.getString();
        int nestLevel = context.getNestLevel();
        builder.append(map.getClass().getSimpleName()).append('(');

        for (Map.Entry<K, ? extends V> kEntry : map.entrySet()) {
            Object key = kEntry.getKey();
            Object value = kEntry.getValue();
            builder.append('\n');
            builder.append(StringsKt.repeat(" ", nestLevel * 2));
            builder.append('(').append(nestLevel).append(')');
            context.delegate(key);
            builder.append(" => ");
            context.delegate(value);
            builder.append('\n');
        }

        builder.append(')');
        if (nestLevel > 1) {
            builder.append('\n');
        }
    }

    public static @NotNull String toDefaultPrettyString(@Nullable Object any) {
        return PrettyStringExtensionKt.toPrettyString(any);
    }

    public static final PrettyString<Map<?, ?>> MAP = new PrettyString<Map<?, ?>>() {
        @Override
        public void toPrettyString(Map<?, ?> obj, @NotNull PrettyStringContext context) {
            associatedArrayMap(obj, context);
        }
    };

    public static final PrettyString<NSedKey> NAMESPACE = new PrettyString<NSedKey>() {
        @Override
        public void toPrettyString(NSedKey obj, @NotNull PrettyStringContext context) {
            context.getString().append(obj.asString());
        }
    };

    public static final PrettyString<Collection<?>> COLLECTION = new PrettyString<Collection<?>>() {
        @Override
        public void toPrettyString(Collection<?> obj, @NotNull PrettyStringContext context) {
            context.getString()
                    .append(obj.getClass().getSimpleName())
                    .append('(')
                    .append(CollectionsKt.joinToString(obj, ", ", "", "", -1, "...", null))
                    .append(')')
            ;
        }
    };

    static {
        REGISTRY.put(Map.class, PrettyStringFactory.MAP);
        REGISTRY.put(NSedKey.class, PrettyStringFactory.NAMESPACE);
        REGISTRY.put(Collection.class, PrettyStringFactory.COLLECTION);
    }
}
