package net.aurika.gradle.repository.multistorage;

import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenRepositoryContentDescriptor;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class OraxenRepositoryExtension extends MultiComponentsRepositoryExtension {
    public static final String EXTENSION_NAME = "oraxen";

    @Inject
    public OraxenRepositoryExtension(@NotNull RepositoryHandler repositoryHandler) {
        super(repositoryHandler, "https://repo.oraxen.com/", "Oraxen");
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

    public MavenArtifactRepository mirror() {
        return super.named("mirror");
    }
}
