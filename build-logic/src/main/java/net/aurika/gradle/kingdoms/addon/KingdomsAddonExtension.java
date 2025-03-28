package net.aurika.gradle.kingdoms.addon;

import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Objects;

public class KingdomsAddonExtension {

  public static final String EXTENSION_NAME = "kingdomsAddon";

  private final Project project;
  private boolean isAddonBody = false;

  @Inject
  public KingdomsAddonExtension(@NotNull Project project) {
    Objects.requireNonNull(project, "project");
    this.project = project;
  }

  public boolean getIsAddonBody() {
    return isAddonBody;
  }

  public void setIsAddonBody(boolean isAddonBody) {
    this.isAddonBody = isAddonBody;
  }

}
