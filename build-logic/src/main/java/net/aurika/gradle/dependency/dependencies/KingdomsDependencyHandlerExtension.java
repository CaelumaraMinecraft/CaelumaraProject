package net.aurika.gradle.dependency.dependencies;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.io.File;
import java.util.Objects;

public class KingdomsDependencyHandlerExtension {
    public static final String EXTENSION_NAME = "kingdoms";

    protected final @NotNull Project project;

    protected @NotNull File rawFile;
    protected @NotNull File relocatedFile;

    @Inject
    public KingdomsDependencyHandlerExtension(@NotNull Project project) {
        Objects.requireNonNull(project, "project");
        this.project = project;
        File rootDir = project.getRootDir();
        rawFile = new File(rootDir.getPath() + "/libs/plugins/Kingdoms/");
        relocatedFile = new File(rootDir.getPath() + "/libs/plugins/Kingdoms_relocated/");
    }

    public Dependency raw() {
        return project.getDependencies().create(rawFile);
    }

    public Dependency relocated() {
        return project.getDependencies().create(relocatedFile);
    }
}
