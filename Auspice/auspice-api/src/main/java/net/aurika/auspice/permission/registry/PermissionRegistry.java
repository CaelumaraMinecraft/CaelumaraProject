package net.aurika.auspice.permission.registry;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.permission.Permission;
import net.aurika.util.unsafe.fn.Fn;

import java.util.*;

@SuppressWarnings("unused")
public interface PermissionRegistry<Perm extends Permission, SysPerm, Player> {
    @NotNull
    Map<Class<PermissionRegistry<?, ?, ?>>, PermissionRegistry<?, ?, ?>> REGISTRIES = new HashMap<>();

    Perm convert0(@NotNull Permission var1);

    @NotNull
    Perm convert(@NotNull SysPerm var1);

    @NotNull
    SysPerm convert(@NotNull Perm var1);

    void register(@NotNull Perm var1);

    void unregister(@NotNull Perm var1);

    @Nullable
    Perm getRegistered(@NotNull Permission var1);

    boolean hasPermission(@NotNull Player var1, @NotNull Perm var2);

    @NotNull
    List<Permission> getAllPermissions(@NotNull Player var1);

    static void registerAllRegistries() {
        registerRegistry(BukkitPermissionRegistry.INSTANCE);
    }

    static <Perm extends Permission, SysPerm, Player> void registerRegistry(@NotNull PermissionRegistry<Perm, SysPerm, Player> registry) {
        Objects.requireNonNull(registry, "");
        PermissionRegistry.REGISTRIES.put((Class<PermissionRegistry<?, ?, ?>>) registry.getClass(), registry);
    }

    static void registerToAllRegistries(@NotNull Permission var1, boolean var2) {
        Objects.requireNonNull(var1, "");
        Iterator<PermissionRegistry<?, ?, ?>> var3 = PermissionRegistry.REGISTRIES.values().iterator();

        while (true) {
            while (var3.hasNext()) {
                PermissionRegistry var4 = var3.next();
                Permission var5 = var4.getRegistered(var1);
                if (var5 != null) {
                    if (!var2) {
                        var1.getSystematicObjects().put(var5.getClass(), var5);
                        continue;
                    }

                    Permission var10001 = Fn.cast(var5);
                    Intrinsics.checkNotNullExpressionValue(var10001, "");
                    var4.unregister(var10001);
                }

                Permission var6 = var4.convert0(var1);
                var1.getSystematicObjects().put(var6.getClass(), var6);
                var4.register(var6);
            }

            return;
        }
    }


}
