package net.aurika.auspice.platform.bukkit.api;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"ConstantValue", "PatternValidation"})
public final class KeyUtil {

  public static @NotNull Key adaptBukkitKey(org.bukkit.@NotNull NamespacedKey bukkitKey) {
    Validate.Arg.notNull(bukkitKey, "bukkitKey");
    if (Key.class.isInstance(bukkitKey)) {
      return Key.class.cast(bukkitKey);
    }
    return Key.key(bukkitKey.getNamespace(), bukkitKey.getKey());
  }

  public static org.bukkit.@NotNull NamespacedKey adaptBukkitKey(@NotNull Key key) {
    Validate.Arg.notNull(key, "key");
    if (NamespacedKey.class.isInstance(key)) {
      return NamespacedKey.class.cast(key);
    }
    return new NamespacedKey(key.namespace(), key.value());
  }

}
