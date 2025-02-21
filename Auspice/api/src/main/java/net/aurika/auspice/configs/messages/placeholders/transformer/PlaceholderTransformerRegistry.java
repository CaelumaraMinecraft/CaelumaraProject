package net.aurika.auspice.configs.messages.placeholders.transformer;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.constants.location.SimpleBlockLocation;
import net.aurika.auspice.server.player.OfflinePlayer;
import net.aurika.util.map.HierarchalClassMap;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public final class PlaceholderTransformerRegistry {

    public static final @NotNull PlaceholderTransformerRegistry INSTANCE = new PlaceholderTransformerRegistry();

    private final @NotNull HierarchalClassMap<PlaceholderTransformer<?, ?>> registry = new HierarchalClassMap<>();

    private PlaceholderTransformerRegistry() {
    }

    public <I, O> void register(@NotNull Class<? extends I> type, @NotNull PlaceholderTransformer<? extends I, O> transformer) {
        Validate.Arg.notNull(type, "type");
        Validate.Arg.notNull(transformer, "transformer");
        this.registry.put(type, transformer);
    }

    public @Nullable PlaceholderTransformer<?, ?> getTransformer(@NotNull Class<?> type) {
        Validate.Arg.notNull(type, "type");
        return this.registry.get(type);
    }

    /**
     * Applies placeholder transformation.
     */
    @Contract("null -> null")
    public @Nullable Object applyTransformation(@Nullable Object obj) {
        if (obj == null) {
            return null;
        } else {
            PlaceholderTransformer transformer = this.getTransformer(obj.getClass());
            if (transformer != null) {
                return transformer.apply(obj);
            } else {
                return obj;
            }
        }
    }

    private static String a(ItemStack var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        String var1 = "x" + var0.getAmount();
        return ItemUtil.getName(var0) + ' ' + var1;
    }

    private static String a(Location var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        return LocationUtils.parseLocation(var0);
    }

    private static String a(SimpleBlockLocation var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        return LocationUtils.parseLocation(var0);
    }

    private static String a(BlockPoint3D var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        return LocationUtils.parseLocation(var0);
    }

    private static String a(SimpleChunkLocation var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        return LocationUtils.parseChunk(var0);
    }

    private static Object a(Supplier<?> supplier) {
        Intrinsics.checkNotNullParameter(supplier, "");
        return supplier.get();
    }

    private static Object a(Callable var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        return var0.call();
    }

    private static Double a(Balance var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        return var0.get();
    }

    private static Long a(Duration var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        return var0.toMillis();
    }

    private static String a(Expression var0) {
        Intrinsics.checkNotNullParameter(var0, "");
        String var10000 = var0.getOriginalString();
        if (var10000 == null) {
            var10000 = var0.asString(true);
        }

        return var10000;
    }

    static {
        INSTANCE.register(OfflinePlayer.class, (offlinePlayer -> {
            Checker.Arg.notNull(offlinePlayer, "offlinePlayer");
            String name = offlinePlayer.getName();
            if (!Strings.isNullOrEmpty(name)) {
                return name;
            } else {
                return offlinePlayer.getUniqueId();
            }
        }));
        INSTANCE.register(ItemStack.class, PlaceholderTransformerRegistry::a);
        INSTANCE.register(Location.class, PlaceholderTransformerRegistry::a);
        INSTANCE.register(SimpleBlockLocation.class, PlaceholderTransformerRegistry::a);
        INSTANCE.register(BlockPoint3D.class, PlaceholderTransformerRegistry::a);
        INSTANCE.register(SimpleChunkLocation.class, PlaceholderTransformerRegistry::a);
        INSTANCE.register(Supplier.class, supplier -> {
            Checker.Arg.notNull(supplier, "supplier");
            return supplier.get();
        });
        INSTANCE.register(Callable.class, callable -> {
            Checker.Arg.notNull(callable, "callable");
            try {
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        INSTANCE.register(Balance.class, PlaceholderTransformerRegistry::a);
        INSTANCE.register(Duration.class, PlaceholderTransformerRegistry::a);
        INSTANCE.register(Expression.class, PlaceholderTransformerRegistry::a);

        INSTANCE.register(Throwable.class, throwable -> {
            Checker.Arg.notNull(throwable, "throwable");
            List<Throwable> causations = StackTraces.getCausationChain(throwable);
            return CollectionsKt.joinToString(causations, " <- ", "", "", -1, "...", (Throwable x) -> x.getClass().getName() + ": " + x.getMessage());
        });
    }
}

