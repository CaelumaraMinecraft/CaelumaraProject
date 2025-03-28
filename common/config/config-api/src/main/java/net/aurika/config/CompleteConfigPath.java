package net.aurika.config;

import net.aurika.config.path.ConfigEntry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class CompleteConfigPath {

  @NonNull
  private final String[] profilePath;
  @NotNull
  private final ConfigEntry configPath;

  public CompleteConfigPath(@NonNull String[] profilePath, @NotNull ConfigEntry configPath) {
    this.profilePath = profilePath;
    this.configPath = configPath;
  }

  public String[] getProfilePath() {
    return profilePath;
  }

  public ConfigEntry getConfigPath() {
    return configPath;
  }

}
