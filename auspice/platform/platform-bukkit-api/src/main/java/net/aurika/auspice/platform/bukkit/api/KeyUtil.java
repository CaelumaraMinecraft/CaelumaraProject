package net.aurika.auspice.platform.bukkit.api;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public final class KeyUtil {

  public static @NotNull Key adaptBukkitKey(org.bukkit.@NotNull NamespacedKey bukkitKey) {
    Validate.Arg.notNull(bukkitKey, "bukkitKey");
    // noinspection ConstantValue  部分 bukkit 平台的 NamespacedKey 已经实现 adventure Key
    if (Key.class.isInstance(bukkitKey)) {
      return Key.class.cast(bukkitKey);
    }
    // noinspection PatternValidation
    return Key.key(bukkitKey.getNamespace(), bukkitKey.getKey());
  }

  public static org.bukkit.@NotNull NamespacedKey adaptBukkitKey(@NotNull Key key) {
    Validate.Arg.notNull(key, "key");
    return new NamespacedKey(key.namespace(), key.value());
  }

}
