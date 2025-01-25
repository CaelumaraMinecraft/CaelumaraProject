package top.auspice.utils.unsafe.string;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.namespace.NSedKey;

import java.util.*;

public final class PrettyStringFactory {

    @NotNull
    public static String toPrettyString(@Nullable Object $this$toPrettyString) {
        PrettyStringContext context = new PrettyStringContext(new StringBuilder(), 0);
        context.delegate($this$toPrettyString);
        return context.getString().toString();
    }
    
    @NotNull
    private static final Map<Class<?>, PrettyString<?>> REGISTRY = new IdentityHashMap<>();

    private PrettyStringFactory() {
        throw new RuntimeException("Can not get constants of class: " + this.getClass().getName());
    }

    @NotNull
    public static Map<Class<?>, PrettyString<?>> getRegistry() {
        return REGISTRY;
    }

    @Nullable
    public static PrettyString<?> findSpecialized(@Nullable Class<?> clazz) {
        if (clazz != null && !Intrinsics.areEqual(clazz, Object.class)) {
            PrettyString<?> var6 = REGISTRY.get(clazz);
            if (var6 == null) {
                var6 = findSpecialized(clazz.getSuperclass());
                if (var6 == null) {
                    Class<?>[] var7 = clazz.getInterfaces();
                    Objects.requireNonNull(var7, "getInterfaces(...)");
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
        Objects.requireNonNull(map);
        Objects.requireNonNull(context);
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

    @NotNull
    public static String toDefaultPrettyString(@Nullable Object any) {
        return PrettyStringFactory.toPrettyString(any);
    }

    public static final PrettyString<Map<?, ?>> MAP = new PrettyString<>() {
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
