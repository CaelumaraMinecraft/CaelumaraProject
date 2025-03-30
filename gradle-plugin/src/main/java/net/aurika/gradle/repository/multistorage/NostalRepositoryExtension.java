package net.aurika.gradle.repository.multistorage;

import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.jetbrains.annotations.NotNull;

public class NostalRepositoryExtension extends MultiComponentsRepositoryExtension {

  public NostalRepositoryExtension(@NotNull RepositoryHandler repositoryHandler) {
    super(repositoryHandler, "https://maven.nostal.ink/repository/", "Nostal");
  }

  public MavenArtifactRepository maven_snapshots() {
    return super.named("maven-snapshots");
  }

}
