package net.aurika.gradle.kingdoms.dependency;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class KingdomsDependencyPlugin implements Plugin<Project> {

  @Override
  public void apply(@NotNull Project project) {
    project.getDependencies().getExtensions().create(
        KingdomsDependencyHandlerExtension.class,
        KingdomsDependencyHandlerExtension.EXTENSION_NAME,
        KingdomsDependencyHandlerExtension.class,
        new Object[]{project}
    );
    project.getExtensions().create(
        KingdomsDependencyRelocateExtension.class,
        KingdomsDependencyRelocateExtension.EXTENSION_NAME,
        KingdomsDependencyRelocateExtension.class,
        new Object[]{project}
    );
  }

}
