package net.aurika.gradle.repository.multistorage;

import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenRepositoryContentDescriptor;
import org.jetbrains.annotations.NotNull;

public class PurpurMCRepositoryExtension extends MultiComponentsRepositoryExtension {
    public PurpurMCRepositoryExtension(@NotNull RepositoryHandler repositoryHandler) {
        super(repositoryHandler, "https://repo.purpurmc.org/", "PurpurMC");
    }

    public MavenArtifactRepository releases() {
        MavenArtifactRepository repo = super.named("releases");
        repo.mavenContent(MavenRepositoryContentDescriptor::releasesOnly);
        return repo;
    }

    public MavenArtifactRepository snapshots() {
        MavenArtifactRepository repo = super.named("snapshots");
        repo.mavenContent(MavenRepositoryContentDescriptor::snapshotsOnly);
        return repo;
    }
}
