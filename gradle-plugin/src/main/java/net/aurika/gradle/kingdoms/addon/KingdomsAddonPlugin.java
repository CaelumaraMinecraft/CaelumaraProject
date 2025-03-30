package net.aurika.gradle.kingdoms.addon;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class KingdomsAddonPlugin implements Plugin<Project> {

  @Override
  public void apply(@NotNull Project project) {
    project.getExtensions().create(
        KingdomsAddonExtension.class,
        KingdomsAddonExtension.EXTENSION_NAME,
        KingdomsAddonExtension.class,
        new Object[]{project}
    );
  }

}
