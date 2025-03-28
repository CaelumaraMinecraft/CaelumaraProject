package net.aurika.gradle.repository.base;

import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.jetbrains.annotations.NotNull;

public abstract class RepositoryExtension {

  protected final @NotNull RepositoryHandler repositoryHandler;
  protected final @NotNull String urlPrefix;
  protected final @NotNull String repositoryName;

  public RepositoryExtension(@NotNull RepositoryHandler repositoryHandler,
                             @NotNull String urlPrefix,
                             @NotNull String repositoryName) {
    this.repositoryHandler = repositoryHandler;
    this.urlPrefix = processURLPrefix(urlPrefix);
    this.repositoryName = repositoryName;
  }

  private static @NotNull String processURLPrefix(@NotNull String webId) {
    if (!webId.endsWith("/")) {
      return webId + "/";
    } else {
      return webId;
    }
  }

}
