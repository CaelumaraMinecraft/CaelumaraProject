package net.aurika.text.placeholders.transformer;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.ecomony.balance.Balance;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.server.location.BlockPoint3D;
import top.auspice.server.location.Location;
import top.auspice.server.player.OfflinePlayer;
import net.aurika.util.Checker;
import top.auspice.utils.map.HierarchalClassMap;
import top.auspice.utils.ItemUtil;
import top.auspice.utils.compiler.base.Expression;
import top.auspice.utils.unsafe.Fn;
import top.auspice.utils.unsafe.stacktrace.StackTraces;
import top.auspice.utils.string.Strings;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public final class PlaceholderTransformerRegistry {
    @NotNull
    public static final PlaceholderTransformerRegistry INSTANCE = new PlaceholderTransformerRegistry();

    private final @NotNull HierarchalClassMap<PlaceholderTransformer<?, ?>> registry = new HierarchalClassMap<>();

    private PlaceholderTransformerRegistry() {
    }

    public <I, O> void register(@NotNull Class<? extends I> type, @NotNull PlaceholderTransformer<? extends I, O> transformer) {
        Checker.Arg.notNull(type, "type");
        Checker.Arg.notNull(transformer, "transformer");
        this.registry.put(type, transformer);
    }

    public @Nullable PlaceholderTransformer<?, ?> getTransformer(@NotNull Class<?> type) {
        Checker.Arg.notNull(type, "type");
        return this.registry.get(type);
    }

    public @Nullable Object applyTransformation(@Nullable Object obj) {
        if (obj == null) {
            return null;
        } else {
            PlaceholderTransformer<?, ?> transformer = this.getTransformer(obj.getClass());
            if (transformer != null) {
                return transformer.apply(Fn.cast(obj));
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

