package net.aurika.gradle.repository.multistorage;

import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenRepositoryContentDescriptor;
import org.gradle.api.plugins.ExtensionAware;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public abstract class SpigotMCRepositoryExtension extends MultiComponentsRepositoryExtension implements ExtensionAware {

  public static final String EXTENSION_NAME = "spigotMC";

  @Inject
  public SpigotMCRepositoryExtension(@NotNull RepositoryHandler repositoryHandler) {
    super(repositoryHandler, "https://hub.spigotmc.org/nexus/content/repositories/", "SpigotMC");
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
