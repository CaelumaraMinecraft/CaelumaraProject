package net.aurika.gradle.repository;

import net.aurika.gradle.repository.multistorage.CodeMCRepositoryExtension;
import net.aurika.gradle.repository.multistorage.OraxenRepositoryExtension;
import net.kyori.indra.internal.SonatypeRepositoriesImpl;
import net.kyori.indra.repository.SonatypeRepositories;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.plugins.ExtensionAware;
import org.jetbrains.annotations.NotNull;

public class AurikaRepositoriesPlugin implements Plugin<Project> {

    @Override
    public void apply(@NotNull Project project) {
        createAurikaRepositoriesExtension(project);
    }

    public static AurikaRepositoriesExtension createAurikaRepositoriesExtension(@NotNull Project project) {
        RepositoryHandler repositories = project.getRepositories();
        project.getExtensions().create(
                SonatypeRepositories.class,
                SonatypeRepositories.EXTENSION_NAME,
                SonatypeRepositoriesImpl.class,
                new Object[]{repositories}
        );
        AurikaRepositoriesExtension extension = project.getExtensions().create(
                AurikaRepositoriesExtension.class,
                AurikaRepositoriesExtension.EXTENSION_NAME,
                AurikaRepositoriesExtension.class,
                new Object[]{repositories}
        );
        return extension;
    }
}
