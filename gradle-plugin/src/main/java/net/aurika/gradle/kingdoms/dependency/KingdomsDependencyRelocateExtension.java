package net.aurika.gradle.kingdoms.dependency;

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin;
import net.aurika.gradle.kingdoms.AurikaKingdomsPlugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class KingdomsDependencyRelocateExtension {

  public static final String EXTENSION_NAME = "kingdomsRelocates";

  protected final @NotNull Project project;

  public KingdomsDependencyRelocateExtension(@NotNull Project project) {
    Objects.requireNonNull(project, "project");
    this.project = project;
    project.getPlugins().apply(ShadowPlugin.class);
  }

  public void kingdomsRelocates() {
    AurikaKingdomsPlugin.kingdomsRelocates(project);
  }

}
