package top.auspice.config.yaml.importers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.config.adapters.YamlContainer;
import top.auspice.config.adapters.YamlImportDeclaration;
import top.auspice.config.adapters.YamlModule;
import top.auspice.config.sections.YamlConfigSection;
import top.auspice.config.yaml.FolderYamlRegistry;

import java.util.*;

public final class YamlModuleLoader {
    @NotNull
    private static final Map<String, YamlModule> modules = new LinkedHashMap<>();

    private YamlModuleLoader() {
    }

    public static void loadAll() {
        (new FolderYamlRegistry("YAML Declaration", "declarations", YamlModuleLoader::a)).useDefaults(true).copyDefaults(true).register();

        for (YamlModule module : modules.values()) {
            module.getAdapter().setImporter(YamlGlobalImporter.INSTANCE);
        }

    }

    @NotNull
    public static Map<String, YamlImportDeclaration> loadImports(@NotNull YamlContainer container) {
        Objects.requireNonNull(container, "");
        YamlConfigSection root = container.getConfig();
        if (root == null) {
            throw new IllegalArgumentException("Adapter not loaded to load imports: " + container);
        } else {
            YamlConfigSection declarationSec = root.getSection("(import)");
            if (declarationSec == null) {
                return new HashMap<>();
            } else {
                Set<String> keys = declarationSec.getKeys();
                Objects.requireNonNull(keys);
//                LinkedHashMap<String, YamlImportDeclaration> declarations = new LinkedHashMap<>(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(keys, 10)), 16));
                LinkedHashMap<String, YamlImportDeclaration> declarations = new LinkedHashMap<>(16);

                for (String key : keys) {
                    YamlConfigSection var10003 = declarationSec.getSection(key);
                    Objects.requireNonNull(var10003);
                    YamlImportDeclaration declaration = new YamlImportDeclaration(key, var10003);
                    declarations.put(key, declaration);
                }

                return declarations;
            }
        }
    }

    @Nullable
    public static YamlModule get(@NotNull String moduleName) {
        Objects.requireNonNull(moduleName);
        return modules.get(moduleName);
    }

    private static void a(String moduleName, YamlContainer moduleYamlContainer) {
        Objects.requireNonNull(moduleName);
        Objects.requireNonNull(moduleYamlContainer);
        moduleYamlContainer.load();
        modules.put(moduleName, new YamlModule(moduleName, loadImports(moduleYamlContainer), moduleYamlContainer));
    }
}

