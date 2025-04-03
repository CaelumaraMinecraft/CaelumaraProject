package net.aurika.gradle.kingdoms.addon;

import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Objects;

public class KingdomsAddonExtension {

  public static final String EXTENSION_NAME = "addon";

  private final Project project;
  private boolean isAddonInterface = false;
  private @NotNull String addonName = "Unknown";

  @Inject
  public KingdomsAddonExtension(@NotNull Project project) {
    Objects.requireNonNull(project, "project");
    this.project = project;
  }

  public boolean getIsAddonInterface() { return isAddonInterface; }

  public void setIsAddonInterface(boolean isAddonInterface) { this.isAddonInterface = isAddonInterface; }

  public @NotNull String getAddonName() { return addonName; }

  public void setAddonName(@NotNull String addonName) { this.addonName = addonName; }

}
