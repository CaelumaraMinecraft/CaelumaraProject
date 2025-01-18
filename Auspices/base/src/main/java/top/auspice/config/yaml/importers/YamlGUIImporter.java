package top.auspice.config.yaml.importers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.config.adapters.YamlImportDeclaration;
import top.auspice.config.adapters.YamlModule;
import top.auspice.config.adapters.YamlResource;
import top.auspice.diversity.Diversity;
import top.auspice.main.Auspice;

import java.io.File;
import java.util.Objects;

public final class YamlGUIImporter implements YamlImporter {
    @NotNull
    private final Diversity diversity;

    public YamlGUIImporter(@NotNull Diversity diversity) {
        Objects.requireNonNull(diversity);
        this.diversity = diversity;
    }

    @Nullable
    public YamlModule getDeclaration(@NotNull YamlImportDeclaration declaration) {
        Objects.requireNonNull(declaration);
        String localTemplatePath = "guis\\" + this.diversity.getFolderName() + "\\templates\\" + declaration.getName() + ".yml";
        String globalTemplatePath = "guis\\templates\\" + declaration.getName() + ".yml";
        File var5 = new File(Auspice.get().getDataFolder(), localTemplatePath);
        YamlResource var4 = (new YamlResource(Auspice.get(), var5, globalTemplatePath));
        if (!var4.load().defaultExists()) {
            File var10000 = var4.getFile();
            Objects.requireNonNull(var10000);
            if (!var10000.exists()) {
                return YamlGlobalImporter.INSTANCE.getDeclaration(declaration);
            }
        }

        String var10002 = declaration.getName();
        Objects.requireNonNull(var4);
        return new YamlModule(var10002, YamlModuleLoader.loadImports(var4), var4);
    }
}
