package net.aurika.gradle.dependency.relocation;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.FileCollectionDependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.Directory;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.ExtensionAware;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

public class PreRelocationsPlugin implements Plugin<Project> {

  public static final @NotNull String ATTRIBUTE_DEPENDENCY_IS_RELOCATED = "net.aurika.gradle.dependency.relocated";

  @Override
  public void apply(@NotNull Project project) {

    System.out.println("Applying pre-relocations plugin");

    DependencyHandler dependencies = project.getDependencies();
    Directory defaultRelocatedPath = project.getLayout().getProjectDirectory().dir("relocated-libs");  // the default
    PreRelocationsSettingsExtension handlerExtension = new PreRelocationsSettingsExtension(
        defaultRelocatedPath.getAsFile());
    dependencies.getExtensions().add(PreRelocationsSettingsExtension.class, "preRelocationSettings", handlerExtension);

//        Attribute<Boolean> isPreRelocatedAttribute = Attribute.of(ATTRIBUTE_DEPENDENCY_IS_RELOCATED, Boolean.class);
//        AttributesSchema attributesSchema = dependencies.getAttributesSchema();
//        attributesSchema.attribute(isPreRelocatedAttribute);

    ConfigurationContainer configurations = project.getConfigurations();
//        configurations.all(configuration -> {
//            configuration.attributes(attributeContainer -> {
//                attributeContainer.attribute(isPreRelocatedAttribute, true);
//            });
//        });

    configurations.all(cfg -> {

      String cfgName = cfg.getName();
      String relocatedCfgPath = handlerExtension.getRelocatedFolder().getPath() + "\\" + cfgName;

      AtomicBoolean cfgHasRelocatedDep = new AtomicBoolean(false);

      cfg.withDependencies((DependencySet dependencySet) -> {
        dependencySet.forEach((Dependency dependency) -> {
          if (dependency instanceof ExtensionAware) {
            ExtensionAware extendableDep = (ExtensionAware) dependency;
            PreRelocationsExtension depExt = extendableDep.getExtensions().findByType(PreRelocationsExtension.class);
            if (depExt != null) {
              if (!cfgHasRelocatedDep.get()) {  // Only create once for each configuration
                dependencies.add(cfgName, project.fileTree(relocatedCfgPath));
                dependencySet.remove(extendableDep);
              }
              cfgHasRelocatedDep.set(true);
              if (extendableDep instanceof FileCollectionDependency) {
                FileCollectionDependency filesDependency = (FileCollectionDependency) extendableDep;
                if (!depExt.relocated) {
                  depExt.relocated = true;
                  System.out.println("Relocating files dependency: " + System.identityHashCode(dependency));
                  System.out.println(filesDependency);
                  System.out.println(filesDependency.getName());
                  FileCollection files = filesDependency.getFiles();
                  for (File file : files) {

//                                        Path outputPath = Path.of(relocatedCfgPath + "\\" + "relocated-" + file.getName());

                    Path outputPath = FileSystems.getDefault().getPath(
                        relocatedCfgPath + "\\" + "relocated-" + file.getName());

                    System.out.println("Relocating file: " + file + " to: " + outputPath);

                    RelocationHandler.remap(file.toPath(), outputPath, depExt.relocates());
                  }
                }
              } else {
                project.getLogger().warn("Skipping unsupported dependency: " + dependency);
              }
            }
          }
        });
      });
    });
  }

}
