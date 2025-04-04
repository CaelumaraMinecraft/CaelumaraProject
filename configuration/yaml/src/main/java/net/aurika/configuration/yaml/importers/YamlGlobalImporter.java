package net.aurika.configuration.yaml.importers;

import net.aurika.configuration.yaml.adapter.YamlImportDeclaration;
import net.aurika.configuration.yaml.adapter.YamlModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class YamlGlobalImporter implements YamlImporter {

  @NotNull
  public static final YamlGlobalImporter INSTANCE = new YamlGlobalImporter();

  private YamlGlobalImporter() {
  }

  @Nullable
  public YamlModule getDeclaration(@NotNull YamlImportDeclaration declaration) {
    Objects.requireNonNull(declaration);
    return YamlModuleLoader.get(declaration.getName());
  }

}
