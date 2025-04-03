package net.aurika.gradle.kingdoms;

import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionAware;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class KingdomsExtension implements ExtensionAware {

  public static final String EXTENSION_NAME = "kingdoms";

  private final @NotNull Project project;

  public KingdomsExtension(@NotNull Project project) {
    Objects.requireNonNull(project, "project");
    this.project = project;
  }

}
