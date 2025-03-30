package net.aurika.gradle.dependency;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class AurikaDependencyPlugin implements Plugin<Project> {

  @Override
  public void apply(@NotNull Project project) {
    project.getExtensions().create(
        AurikaDependencyExtension.class,
        AurikaDependencyExtension.EXTENSION_NAME,
        AurikaDependencyExtension.class,
        new Object[]{project}
    );
  }

}
