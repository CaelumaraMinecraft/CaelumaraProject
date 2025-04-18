package net.aurika.auspice.platform.registry;

import org.jetbrains.annotations.NotNull;

public class NoSuchRegistryException extends Exception {

  private final @NotNull RegistryKey<?> registryKey;

  public NoSuchRegistryException(@NotNull RegistryKey<?> key) {
    super("No such registry: " + key);
    this.registryKey = key;
  }

  public @NotNull RegistryKey<?> registryKey() {
    return this.registryKey;
  }

}
