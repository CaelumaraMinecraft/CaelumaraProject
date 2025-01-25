package net.aurika.config.yaml.importers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.adapters.YamlImportDeclaration;
import net.aurika.config.adapters.YamlModule;

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
