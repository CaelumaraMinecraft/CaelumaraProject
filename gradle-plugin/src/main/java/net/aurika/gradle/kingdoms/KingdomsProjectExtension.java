package net.aurika.gradle.kingdoms;

import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class KingdomsProjectExtension {

  public static final String EXTENSION_NAME = "kingdoms";

  private final @NotNull Project project;

  public KingdomsProjectExtension(@NotNull Project project) {
    Objects.requireNonNull(project, "project");
    this.project = project;
  }

}
