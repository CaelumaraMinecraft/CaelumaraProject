package net.aurika.gradle.kingdoms.dependency;

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin;
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
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
    ShadowJar shadowJar = (ShadowJar) project.getTasks().getByName("shadowJar");
    for (Map.Entry<String, String> relocate : KingdomsDependency.RELOCATES.entrySet()) {
      shadowJar.relocate(relocate.getKey(), relocate.getValue());
    }
  }

}
