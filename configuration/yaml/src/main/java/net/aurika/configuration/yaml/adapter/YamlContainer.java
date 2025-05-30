package net.aurika.configuration.yaml.adapter;

import net.aurika.auspice.main.Auspice;
import net.aurika.common.snakeyaml.constructor.AuspiceConstructor;
import net.aurika.common.snakeyaml.validation.NodeValidator;
import net.aurika.common.snakeyaml.validation.Validator;
import net.aurika.configuration.accessor.YamlClearlyConfigAccessor;
import net.aurika.configuration.adapter.Profile;
import net.aurika.configuration.sections.YamlNodeSection;
import net.aurika.configuration.yaml.importers.YamlDeclarationNotFoundException;
import net.aurika.configuration.yaml.importers.YamlImporter;
import net.aurika.configuration.yaml.importers.YamlModuleLoader;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Optional;

public interface YamlContainer extends Profile {

  YamlNodeSection getConfig();

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
      MappingNode var4 = (MappingNode) this.getConfig().getRootNode();
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


    Composer composer = new Composer(
        loadSettings,
        new ParserImpl(loadSettings, new StreamReader(loadSettings, new YamlUnicodeReader(parseContext.getStream())))
    );

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

    Composer composer = new Composer(
        loadSettings, new ParserImpl(loadSettings, new StreamReader(loadSettings, new YamlUnicodeReader(inputStream))));

    Optional<Node> rootOptional = composer.getSingleNode();
    MappingNode root;

    if (rootOptional.isPresent()) {
      root = (MappingNode) rootOptional.get();
      return Validator.parseSchema(root);
    }

    return null;
  }

}
