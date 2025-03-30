package net.aurika.gradle.dependency;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.ConfigurableFileTree;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

import static net.aurika.gradle.util.AurikaGradleUtil.rootGradle;

public class AurikaDependencyExtension {

  public static final String EXTENSION_NAME = "aurikaDependency";

  private final @NotNull Project project;

  public AurikaDependencyExtension(@NotNull Project project) {
    Objects.requireNonNull(project, "project");
    this.project = project;
  }

  public @NotNull File libsFile(@NotNull Object path) {
    return rootGradle(this.project).getRootProject().file("libs/" + path);
  }

  public @NotNull ConfigurableFileCollection libsFiles(@NotNull Object path) {
    return rootGradle(this.project).getRootProject().files("libs/" + path);
  }

  public @NotNull ConfigurableFileTree libsFileTree(@NotNull Object path) {
    return rootGradle(this.project).getRootProject().fileTree("libs/" + path);
  }

  public @NotNull ConfigurableFileTree libsFileTree(@NotNull Object baseDir, @NotNull Action<? super ConfigurableFileTree> configureAction) {
    return rootGradle(this.project).getRootProject().fileTree("libs/" + baseDir, configureAction);
  }

}
