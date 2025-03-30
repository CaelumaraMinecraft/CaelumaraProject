package net.aurika.gradle.kingdoms;

import net.aurika.gradle.kingdoms.addon.KingdomsAddonExtension;
import net.aurika.gradle.kingdoms.dependency.KingdomsDependencyHandlerExtension;
import net.aurika.gradle.kingdoms.dependency.KingdomsDependencyRelocateExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class KingdomsPlugin implements Plugin<Project> {

  @Override
  public void apply(@NotNull Project project) {
    // main extension
    project.getExtensions().create(
        KingdomsProjectExtension.class,
        KingdomsProjectExtension.EXTENSION_NAME,
        KingdomsProjectExtension.class,
        new Object[]{project}
    );
    // addon extension
    project.getExtensions().create(
        KingdomsAddonExtension.class,
        KingdomsAddonExtension.EXTENSION_NAME,
        KingdomsAddonExtension.class,
        new Object[]{project}
    );

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
