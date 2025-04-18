package net.aurika.auspice.platform.registry;

import net.aurika.auspice.platform.attribute.AttributeType;
import net.aurika.auspice.platform.biome.Biome;
import net.aurika.common.validate.Validate;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.key.Keyed;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static net.aurika.auspice.platform.registry.Companion.create;

/**
 * The key to a {@link Registry registry}.
 *
 * @param <T> the registry item type
 */
public interface RegistryKey<T> extends Key {

  RegistryKey<Biome> BIOME = create("worldgen/biome", Biome.class);
  RegistryKey<AttributeType> ATTRIBUTE = create("attribute", AttributeType.class);

  @Contract("_, _ -> new")
  @ApiStatus.Internal
  static <T> @NotNull RegistryKey<T> registryKey(@NotNull Key key, @NotNull Class<T> itemType) {
    return new RegistryKeyImpl<>(key, itemType);
  }

  /**
   * Gets the item type in the registry.
   *
   * @return the item type
   */
  @ApiStatus.Experimental
  @NotNull Class<? extends T> itemType();

  @Override
  @KeyPattern.Namespace
  @NotNull String namespace();

  @Override
  @KeyPattern.Value
  @NotNull String value();

  @Override
  @NotNull String asString();

  /**
   * Gets the key of the registry key.
   *
   * @return the key
   */
  @Override
  @NotNull Key key();

}

@SuppressWarnings("PatternValidation")
class RegistryKeyImpl<T> implements RegistryKey<T> {

  private final @NotNull Key key;
  private final @NotNull Class<T> itemType;

  RegistryKeyImpl(@NotNull Key key, @NotNull Class<T> itemType) {
    Validate.Arg.notNull(key, "key");
    Validate.Arg.notNull(itemType, "itemType");
    this.key = key;
    this.itemType = itemType;
  }

  @Override
  public @NotNull Class<T> itemType() { return this.itemType; }

  @Override
  @KeyPattern.Namespace
  public @NotNull String namespace() { return key.namespace(); }

  @Override
  @KeyPattern.Value
  public @NotNull String value() { return key.value(); }

  @Override
  public @NotNull String asString() { return key.asString(); }

  @Override
  public @NotNull Key key() { return key; }

  @Override
  public int hashCode() { return System.identityHashCode(this); }

  @Override
  public boolean equals(Object obj) { return super.equals(obj); }

  @Override
  public String toString() { return key.toString(); }

}

class Companion {

  private Companion() { }

  @Contract("_, _ -> new")
  static <T extends Keyed> @NotNull RegistryKey<T> create(@Subst("some_key") final @NotNull String key, Class<T> itemType) {
    return new RegistryKeyImpl<>(Key.key(Key.MINECRAFT_NAMESPACE, key), itemType);
  }

}
