package net.aurika.config.profile;

import net.aurika.auspice.utils.Validate;
import net.aurika.config.sections.ConfigSection;
import net.aurika.config.sections.YamlNodeSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.api.YamlUnicodeReader;
import org.snakeyaml.engine.v2.composer.Composer;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.parser.Parser;
import org.snakeyaml.engine.v2.parser.ParserImpl;
import org.snakeyaml.engine.v2.scanner.StreamReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Optional;

public class LocalFileProfile implements Profile {

  @NotNull
  protected final File file;
  @NotNull
  protected final ProfileType type;
  @Nullable
  protected ConfigSection root;

  public LocalFileProfile(@NotNull File file, @NotNull ProfileType type) {
    this.file = Objects.requireNonNull(file);
    this.type = Objects.requireNonNull(type);
  }

  @NotNull
  public ConfigSection getRoot() {
    Validate.notNull(this.file, "");

    if (this.root != null) {
      return this.root;
    } else {
      File file = this.file;
      if (file.isFile() && file.getName().endsWith(".yml")) {

        LoadSettings loadSettings = LoadSettings.builder().build();

        Parser parser;
        try {
          parser = new ParserImpl(
              loadSettings, new StreamReader(loadSettings, new YamlUnicodeReader(new FileInputStream(this.file))));
        } catch (FileNotFoundException e) {
          throw new RuntimeException("Invalid file when read the profile.", e);
        }

        Composer composer = new Composer(loadSettings, parser);

        ConfigSection newRoot;

        Optional<Node> root = composer.getSingleNode();
        if (root.isPresent()) {
          newRoot = YamlNodeSection.root(root.get());
        } else {
          throw new RuntimeException("Multiple documents YAML profile is not supported.");
        }

        this.root = newRoot;
        return newRoot;
      } else {
        throw new IllegalStateException("Unsupported profile type: " + file.getName());
      }
    }
  }

  @NotNull
  public File getFile() {
    return this.file;
  }

  @NotNull
  public ProfileType getType() {
    return this.type;
  }

}
