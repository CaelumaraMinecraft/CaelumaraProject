package net.aurika.gradle.kingdoms;

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin;
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;
import net.aurika.gradle.dependency.AurikaDependencyExtension;
import net.aurika.gradle.kingdoms.addon.KingdomsAddon;
import net.aurika.gradle.kingdoms.addon.KingdomsAddonExtension;
import net.aurika.gradle.kingdoms.dependency.KingdomsDependency;
import net.aurika.gradle.kingdoms.dependency.KingdomsDependencyHandlerExtension;
import net.aurika.gradle.kingdoms.dependency.KingdomsDependencyRelocateExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.language.jvm.tasks.ProcessResources;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AurikaKingdomsPlugin implements Plugin<Project> {

  @Override
  public void apply(@NotNull Project project) {
    // main extension
    KingdomsExtension mainExt = project.getExtensions().create(
        KingdomsExtension.class,
        KingdomsExtension.EXTENSION_NAME,
        KingdomsExtension.class,
        new Object[]{project}
    );
    // addon extension
    KingdomsAddonExtension addonExt = ((ExtensionAware) mainExt).getExtensions().create(
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

    project.getDependencies().add(
        JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        AurikaDependencyExtension.libsFileTree(project, "plugins/Kingdoms_relocated")
    );

    project.getPlugins().apply(ShadowPlugin.class);


    project.afterEvaluate(p -> {

      if (addonExt.getIsAddonInterface()) {

        String fullName = "Kingdoms-Addon-" + addonExt.getAddonName();

        // config shadow jar
        ShadowJar shadowJar = (ShadowJar) project.getTasks().getByName("shadowJar");

        kingdomsRelocates(shadowJar);
        shadowJar.getArchiveBaseName().set(fullName);
        shadowJar.getArchiveClassifier().set((String) null);

        shadowJar.exclude("**/kotlin-stdlib**");
        shadowJar.exclude("**/annotations**");

        // add precess resources configs
        ProcessResources task_processResources = (ProcessResources) project.getTasks().getByName("processResources");
        Map<String, Object> props = new HashMap<>();
        String description = project.getDescription();
        KingdomsAddon addon = new KingdomsAddon(
            fullName,
            ((String) project.getVersion()),
            description == null ? "" : description
        );
        props.put("project", addon);
        task_processResources.setFilteringCharset("UTF-8");
        task_processResources.getInputs().properties(props);
        task_processResources.filesMatching(
            "plugin.yml",
            cd -> cd.expand(props)
        );
      }
    });
  }

  public static void kingdomsRelocates(@NotNull Project project) {
    ShadowJar shadowJar = (ShadowJar) project.getTasks().getByName("shadowJar");
    kingdomsRelocates(shadowJar);
  }

  public static void kingdomsRelocates(@NotNull ShadowJar shadowJar) {
    for (Map.Entry<String, String> relocate : KingdomsDependency.RELOCATES.entrySet()) {
      shadowJar.relocate(relocate.getKey(), relocate.getValue());
    }
  }

}
