package net.aurika.gradle.kingdoms.addon;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class KingdomsAddon implements Serializable {

  public @NotNull String name;
  public @NotNull String version;
  public @NotNull String description;

  public KingdomsAddon(@NotNull String name, @NotNull String version, @NotNull String description) {
    this.name = name;
    this.version = version;
    this.description = description;
  }

}
