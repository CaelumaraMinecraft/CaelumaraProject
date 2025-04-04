package net.aurika.configuration.yaml.adapter;

import net.aurika.configuration.sections.YamlNodeSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class YamlImportDeclaration {

  @NotNull
  private final String name;
  @NotNull
  private final YamlNodeSection info;
  private final boolean Extends;
  @NotNull
  private final List<String> importedAnchors;

  public YamlImportDeclaration(@NotNull String name, @NotNull YamlNodeSection info) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(info);
    YamlNodeSection var10001;
    boolean findExtend;
    label12:
    {
      this.name = name;
      this.info = info;
      if (this.info.isSet(new String[]{"extend"})) {
        var10001 = this.info;
        if (Boolean.FALSE.equals(var10001.getBoolean(new String[]{"extend"}))) {
          findExtend = false;
          break label12;
        }
      }

      findExtend = true;
    }

    this.Extends = findExtend;
    var10001 = this.info;
    List<String> anchors = var10001.getStringList(new String[]{"anchors"});
    Objects.requireNonNull(anchors, "");
    this.importedAnchors = anchors;
  }

  @NotNull
  public String getName() {
    return this.name;
  }

  @NotNull
  public YamlNodeSection getInfo() {
    return this.info;
  }

  public boolean getExtends() {
    return this.Extends;
  }

  @NotNull
  public List<String> getImportedAnchors() {
    return this.importedAnchors;
  }

}

