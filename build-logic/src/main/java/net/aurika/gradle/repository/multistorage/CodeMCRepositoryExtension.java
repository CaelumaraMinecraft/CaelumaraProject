package net.aurika.gradle.repository.multistorage;

import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.jetbrains.annotations.NotNull;

public class CodeMCRepositoryExtension extends MultiComponentsRepositoryExtension {
    public static final String EXTENSION_NAME = "codeMC";

    public CodeMCRepositoryExtension(@NotNull RepositoryHandler repositoryHandler) {
        super(repositoryHandler, "https://repo.codemc.io/repository/", "CodeMC");
    }

    public MavenArtifactRepository maven_public() {
        return super.named("maven-public");
    }

    public MavenArtifactRepository nms() {
        return super.named("nms");
    }

    public MavenArtifactRepository mohistmc() {
        return super.named("mohistmc");
    }

    public MavenArtifactRepository authme() {
        return super.named("authme");
    }

    public MavenArtifactRepository codemc() {
        return super.named("codemc");
    }

    public MavenArtifactRepository mrog() {
        return super.named("mrog");
    }
}
