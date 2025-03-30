package net.aurika.gradle.repository.multistorage;

import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.jetbrains.annotations.NotNull;

public class PaperMCRepositoryExtension extends MultiComponentsRepositoryExtension {

  public PaperMCRepositoryExtension(@NotNull RepositoryHandler repositoryHandler) {
    super(repositoryHandler, "https://repo.papermc.io/repository/", "PaperMC");
  }

  public MavenArtifactRepository maven_public() {
    return super.named("maven-public");
  }

}
