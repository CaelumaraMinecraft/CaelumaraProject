package net.aurika.auspice.platform.registry;

/**
 * Represents a key for a value in a specific registry.
 *
 * @param <T> the value type for the registry
 */

import net.aurika.common.validate.Validate;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface TypedKey<T> extends Key {

  /**
   * Gets the key for the value in the registry.
   *
   * @return the value's key
   */
  @Override
  @NotNull Key key();

  /**
   * Gets the registry key for the value this key
   * represents.
   *
   * @return the registry key
   */
  @NotNull RegistryKey<T> registryKey();

  /**
   * Create a typed key from a key and a registry key.
   *
   * @param registryKey the registry this key is for
   * @param key         the key for the value in the registry
   * @param <T>         value type
   * @return a new key for the value key and registry key
   */
  @Contract("_, _ -> new")
  @ApiStatus.Experimental
  static <T> @NotNull TypedKey<T> typedKey(@NotNull RegistryKey<T> registryKey, @NotNull Key key) {
    return new TypedKeyImpl<>(key, registryKey);
  }

}

class TypedKeyImpl<T> implements TypedKey<T> {

  private final @NotNull Key key;
  private final @NotNull RegistryKey<T> registryKey;

  TypedKeyImpl(@NotNull Key key, @NotNull RegistryKey<T> registryKey) {
    Validate.Arg.notNull(key, "key");
    Validate.Arg.notNull(registryKey, "registryKey");
    this.key = key;
    this.registryKey = registryKey;
  }

  @Override
  public @NotNull Key key() {
    return this.key;
  }

  @Override
  public @NotNull RegistryKey<T> registryKey() {
    return this.registryKey;
  }

  @Override
  @KeyPattern.Namespace
  public @NotNull String namespace() {
    return this.key.namespace();
  }

  @Override
  @KeyPattern.Value
  public @NotNull String value() {
    return this.key.value();
  }

  @Override
  public @NotNull String asString() {
    return this.key.asString();
  }

}
