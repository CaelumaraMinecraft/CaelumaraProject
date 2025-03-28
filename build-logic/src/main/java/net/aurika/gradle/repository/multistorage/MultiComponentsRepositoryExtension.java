package net.aurika.gradle.repository.multistorage;

import net.aurika.gradle.repository.base.RepositoryExtension;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Objects;

public abstract class MultiComponentsRepositoryExtension extends RepositoryExtension {

  public MultiComponentsRepositoryExtension(@NotNull RepositoryHandler repositoryHandler,
                                            @NotNull String webPrefix,
                                            @NotNull String repositoryName) {
    super(repositoryHandler, webPrefix, repositoryName);
  }

  public MavenArtifactRepository named(@NotNull String name) {
    Objects.requireNonNull(name, "name");
    return repositoryHandler.maven((repo) -> {
      repo.setUrl(URI.create(urlPrefix + processComponentName(name)));
      repo.setName(repositoryName + "-" + name);
    });
  }

  /**
   * Returns a name that like: {@code maven-public/}
   */
  private static @NotNull String processComponentName(@NotNull String name) {
    String output = name;
    if (name.startsWith("/")) {
      output = name.substring(1);
    }
    if (!output.endsWith("/")) {
      output = output + "/";
    }
    return output;
  }

}
