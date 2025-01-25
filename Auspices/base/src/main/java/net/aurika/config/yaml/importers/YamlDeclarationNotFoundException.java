package net.aurika.config.yaml.importers;

import org.jetbrains.annotations.NotNull;
import net.aurika.config.adapters.YamlContainer;

public final class YamlDeclarationNotFoundException extends RuntimeException {
    @NotNull
    private final YamlContainer importingModule;
    @NotNull
    private final String name;
    @NotNull
    private final YamlImporter importer;

    public YamlDeclarationNotFoundException(@NotNull YamlContainer var1, @NotNull String var2, @NotNull YamlImporter var3) {
        super("Cannot find YAML declaration named '" + var2 + "' for " + var1.getFile() + " using " + var3);
        this.importingModule = var1;
        this.name = var2;
        this.importer = var3;
    }

    @NotNull
    public YamlContainer getImportingModule() {
        return this.importingModule;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public YamlImporter getImporter() {
        return this.importer;
    }
}