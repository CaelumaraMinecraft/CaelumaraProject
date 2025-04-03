package net.aurika.gradle.repository;

import groovy.lang.Closure;
import net.aurika.gradle.repository.multistorage.*;
import org.gradle.api.Action;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenRepositoryContentDescriptor;
import org.gradle.api.plugins.ExtensionAware;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.net.URI;
import java.util.Objects;

/**
 * Aurika repositories.
 */
public abstract class AurikaRepositoriesExtension implements ExtensionAware {

  public static final String EXTENSION_NAME = "aurikaRepos";

  private final @NotNull RepositoryHandler repositories;
  private final @NotNull SpigotMCRepositoryExtension spigotMC;
  private final @NotNull PaperMCRepositoryExtension paperMC;
  private final @NotNull PurpurMCRepositoryExtension purpurMC;
  private final @NotNull CodeMCRepositoryExtension codeMC;
  private final @NotNull OraxenRepositoryExtension oraxen;

  @Inject
  public AurikaRepositoriesExtension(@NotNull RepositoryHandler repositories) {
    Objects.requireNonNull(repositories, "repositories");
    this.repositories = repositories;
    this.spigotMC = getExtensions().create(
        SpigotMCRepositoryExtension.class,
        SpigotMCRepositoryExtension.EXTENSION_NAME,
        SpigotMCRepositoryExtension.class,
        new Object[]{repositories}
    );
    this.paperMC = new PaperMCRepositoryExtension(repositories);
    this.purpurMC = new PurpurMCRepositoryExtension(repositories);
    this.codeMC = new CodeMCRepositoryExtension(repositories);
    this.oraxen = new OraxenRepositoryExtension(repositories);
  }

  public void codeMC(@NotNull Closure codeMCRepoClosure) {
    codeMCRepoClosure.setDelegate(codeMC);
    codeMCRepoClosure.call();
  }

  public void codeMC(@NotNull Action<? super @NotNull CodeMCRepositoryExtension> action) {
    action.execute(codeMC);
  }

  public @NotNull CodeMCRepositoryExtension getCodeMC() {
    return codeMC;
  }

  public void spigotMC(@NotNull Action<? super @NotNull SpigotMCRepositoryExtension> action) {
    action.execute(spigotMC);
  }

  public @NotNull SpigotMCRepositoryExtension getSpigotMC() {
    return spigotMC;
  }

  public void paperMC(@NotNull Action<? super PaperMCRepositoryExtension> action) {
    action.execute(paperMC);
  }

  public @NotNull PaperMCRepositoryExtension getPaperMC() {
    return paperMC;
  }

  public void purpurMC(@NotNull Action<? super PurpurMCRepositoryExtension> action) {
    action.execute(purpurMC);
  }

  public @NotNull PurpurMCRepositoryExtension getPurpurMC() {
    return purpurMC;
  }

  public void oraxen(@NotNull Action<? super @NotNull OraxenRepositoryExtension> action) {
    action.execute(oraxen);
  }

  public @NotNull OraxenRepositoryExtension getOraxen() {
    return oraxen;
  }

  public MavenArtifactRepository fabricMC() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://maven.fabricmc.net/"));
      repo.setName("FabricMC");
    });
  }

  public MavenArtifactRepository minecraft() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://libraries.minecraft.net"));
      repo.setName("Minecraft");
    });
  }

  @Deprecated
  public MavenArtifactRepository nostal_snapshots() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://maven.nostal.ink/repository/maven-snapshots/"));
      repo.setName("Nostal-snapshots");
      repo.mavenContent(MavenRepositoryContentDescriptor::snapshotsOnly);
    });
  }

  public MavenArtifactRepository enginehub() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://maven.enginehub.org/repo/"));
      repo.setName("EngineHub");
    });
  }

  public MavenArtifactRepository auxilor() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://repo.auxilor.io/repository/maven-public/"));
      repo.setName("Auxilor");
    });
  }

  public MavenArtifactRepository thenextlvl_releases() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://repo.thenextlvl.net/releases"));
      repo.setName("TheNextLvl-releases");
    });
  }

  public MavenArtifactRepository thenextlvl_snapshots() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://repo.thenextlvl.net/snapshots"));
      repo.setName("TheNextLvl-snapshots");
    });
  }

  public MavenArtifactRepository essentials_releases() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://repo.essentialsx.net/releases/"));
      repo.setName("Essentials-releases");
    });
  }

  public MavenArtifactRepository essentials_snapshots() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://nexus.scarsz.me/content/groups/public/"));
      repo.setName("Essentials-snapshots");
    });
  }

  /**
   * Used for ItemsAdder.
   */
  public MavenArtifactRepository matteodev() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://maven.devs.beer/"));
      repo.setName("Matteodev");
    });
  }

  public MavenArtifactRepository placeholderAPI() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://repo.extendedclip.com/content/repositories/placeholderapi/"));
      repo.setName("PlaceholderAPI");
    });
  }

  public MavenArtifactRepository mvdw_software() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://repo.mvdw-software.be/content/groups/public"));
      repo.setName("MVdW-software");
    });
  }

  /**
   * Used for fairy.
   */
  public MavenArtifactRepository imanity() {
    return repositories.maven(repo -> {
      repo.setUrl(URI.create("https://repo.imanity.dev/imanity-libraries"));
      repo.setName("Imanity");
    });
  }

}
