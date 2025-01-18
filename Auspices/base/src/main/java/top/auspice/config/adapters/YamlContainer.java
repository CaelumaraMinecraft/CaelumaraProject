package top.auspice.config.adapters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.api.YamlUnicodeReader;
import org.snakeyaml.engine.v2.common.Anchor;
import org.snakeyaml.engine.v2.composer.Composer;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.parser.ParserImpl;
import org.snakeyaml.engine.v2.scanner.StreamReader;
import top.auspice.config.accessor.YamlClearlyConfigAccessor;
import top.auspice.config.sections.YamlConfigSection;
import top.auspice.config.yaml.importers.YamlDeclarationNotFoundException;
import top.auspice.config.yaml.importers.YamlImporter;
import top.auspice.config.yaml.importers.YamlModuleLoader;
import top.auspice.config.yaml.snakeyaml.constructor.AuspiceConstructor;
import top.auspice.config.yaml.snakeyaml.validation.NodeValidator;
import top.auspice.config.yaml.snakeyaml.validation.Validator;
import top.auspice.main.Auspice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Optional;

public interface YamlContainer extends Profile {

    YamlConfigSection getConfig();

    YamlClearlyConfigAccessor accessor();

    void saveConfig();

    default boolean isLoaded() {
        return this.getConfig() != null;
    }

    @Nullable
    File getFile();

    default void createFile() {
        try {
            this.getFile().createNewFile();
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    YamlContainer setImporter(YamlImporter importer);

    YamlImporter getImporter();

    YamlContainer load();

    void reload();

    @NotNull
    default YamlContainer importDeclarations() {
        YamlImporter var1 = this.getImporter();
        if (var1 == null) {
            return this;
        } else if (this.getConfig() == null) {
            return this;
        } else {
            LoadSettings loadSettings = LoadSettings.builder()  // builder
                    .setLabel("Importer")                       // yaml module importer
                    .build();                                   // build

            AuspiceConstructor constructor = new AuspiceConstructor(loadSettings);

            Load load = new Load(loadSettings);
            MappingNode var4 = (MappingNode) this.getConfig().getRoot();
            LinkedHashMap<String, Anchor> anchors = new LinkedHashMap<>();

            for (YamlImportDeclaration declaration : YamlModuleLoader.loadImports(this).values()) {
                if (var1.getDeclaration(declaration) == null) {
                    throw new YamlDeclarationNotFoundException(this, declaration.getName(), var1);
                }

                YamlImporter.importTo(this, anchors, var1, declaration);
            }

            Composer.collectAnchors(var4, anchors);
            Composer.resolveAliases(var4, loadSettings, anchors);
            load.construct(var4);

            constructor.constructObject(var4);

            return this;
        }
    }

    @NotNull
    static MappingNode parse(YamlParseContext parseContext) {


        LoadSettings loadSettings = LoadSettings.builder()  //
                .setLabel(parseContext.getName())           //
                //  TODO setLoadAliases(parseContext.shouldLoadAliases())
                .build();                                   //


        Composer composer = new Composer(loadSettings, new ParserImpl(loadSettings, new StreamReader(loadSettings, new YamlUnicodeReader(parseContext.getStream()))));

        Optional<Node> rootOptional = composer.getSingleNode();

        MappingNode root;

        if (rootOptional.isPresent()) {
            root = (MappingNode) rootOptional.get();
            return root;
        } else {
            throw new IllegalStateException();   //
        }


    }

    static NodeValidator parseValidator(String var0, String var1) {
        return parseValidator(Auspice.get().getResource(var1), var0);
    }

    @Nullable
    static NodeValidator parseValidator(InputStream inputStream, String schemaName) {
        LoadSettings loadSettings = LoadSettings.builder()  // builder
                .setLabel(schemaName + " Schema")           // is a schema
                .build();                                   // build

        AuspiceConstructor constructor = new AuspiceConstructor(loadSettings);

        Load load = new Load(loadSettings, constructor);

        load.loadFromInputStream(inputStream);

        Composer composer = new Composer(loadSettings, new ParserImpl(loadSettings, new StreamReader(loadSettings, new YamlUnicodeReader(inputStream))));

        Optional<Node> rootOptional = composer.getSingleNode();
        MappingNode root;

        if (rootOptional.isPresent()) {
            root = (MappingNode) rootOptional.get();
            return Validator.parseSchema(root);
        }

        return null;
    }
}
