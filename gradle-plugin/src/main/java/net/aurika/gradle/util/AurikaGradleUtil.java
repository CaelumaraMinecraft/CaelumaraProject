package net.aurika.gradle.util;

import org.gradle.api.Project;
import org.gradle.api.invocation.Gradle;
import org.jetbrains.annotations.NotNull;

public final class AurikaGradleUtil {

  public static @NotNull Gradle rootGradle(@NotNull Project project) {
    return rootGradle(project.getGradle());
  }

  public static @NotNull Gradle rootGradle(@NotNull Gradle gradle) {
    if (gradle.getParent() != null) {
      gradle = gradle.getParent();
    }
    return gradle;
  }

}
