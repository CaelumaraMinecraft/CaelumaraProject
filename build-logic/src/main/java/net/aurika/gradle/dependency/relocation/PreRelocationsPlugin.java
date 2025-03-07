package net.aurika.gradle.dependency.relocation;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.FileCollectionDependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.Directory;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.ExtensionAware;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

public class PreRelocationsPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project project) {

        System.out.println("Applying pre-relocations plugin");

        DependencyHandler dependencyHandler = project.getDependencies();
        Directory defRelocatedPath = project.getLayout().getProjectDirectory().dir("relocated-libs");  // the default
        PreRelocationsSettingsExtension handlerExtension = new PreRelocationsSettingsExtension(defRelocatedPath.getAsFile());
        dependencyHandler.getExtensions().add(PreRelocationsSettingsExtension.class, "preRelocationSettings", handlerExtension);

//        System.out.println(handlerExtension.relocatedFolder().getPath());

        project.getConfigurations().all(cfg -> {

            String cfgName = cfg.getName();
            String relocatedCfgPath = handlerExtension.relocatedFolder().getPath() + "\\" + cfgName;

            AtomicBoolean cfgHasPreRelocations = new AtomicBoolean(false);

            cfg.getIncoming().beforeResolve((rabDeps) -> {
                rabDeps.getDependencies();

                System.out.println("Incoming before resolve: " + rabDeps);
            });

            cfg.withDependencies((DependencySet dependencySet) -> {
                dependencySet.forEach(dependency -> {
                    if (dependency instanceof ExtensionAware extendableDep) {
                        PreRelocationsExtension depExt = extendableDep.getExtensions().findByType(PreRelocationsExtension.class);
                        if (depExt != null) {
                            if (!cfgHasPreRelocations.get()) {  // Only create once
                                dependencyHandler.add(cfgName, project.fileTree(relocatedCfgPath));
                                dependencySet.remove(extendableDep);
                            }
                            cfgHasPreRelocations.set(true);
                            if (extendableDep instanceof FileCollectionDependency filesDependency) {
                                if (!depExt.relocated) {
                                    depExt.relocated = true;
                                    System.out.println("Relocating files dependency: " + System.identityHashCode(dependency));
                                    System.out.println(filesDependency);
                                    System.out.println(filesDependency.getName());
                                    FileCollection files = filesDependency.getFiles();
                                    for (File file : files) {

                                        Path outputPath = Path.of(relocatedCfgPath + "\\" + "relocated-" + file.getName());

                                        System.out.println("Relocating file: " + file + " to: " + outputPath);

//                                        PreRelocateKt.remap(file, outputPath, depExt.relocates());

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
