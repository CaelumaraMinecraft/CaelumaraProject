package net.aurika.configuration.yaml.adapter;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.configuration.sections.YamlNodeSection;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.common.Anchor;
import org.snakeyaml.engine.v2.nodes.Node;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class YamlModule {

  @NotNull
  private final String name;
  @NotNull
  private final Map<String, YamlImportDeclaration> imports;
  @NotNull
  private final YamlContainer adapter;
  @NotNull
  private final Map<String, Parameter> d;
  @NotNull
  private final LinkedHashMap<String, Anchor> anchors;

  public YamlModule(@NotNull String var1, @NotNull Map<String, YamlImportDeclaration> var2, @NotNull YamlContainer var3) {
    Objects.requireNonNull(var1, "");
    Objects.requireNonNull(var2, "");
    Objects.requireNonNull(var3, "");
    this.name = var1;
    this.imports = var2;
    this.adapter = var3;
    this.d = new LinkedHashMap<>();
    YamlNodeSection var10000 = this.adapter.getConfig();
    if (var10000 == null) {
      throw new IllegalStateException("Config is null for " + this.name + " -> " + this.adapter);
    } else {
      YamlNodeSection var5 = var10000;
      LinkedHashMap<String, Anchor> var10001 = var5.getRootNode().getAnchors();
      Objects.requireNonNull(var10001, "");
      this.anchors = var10001;
      if ((var5 = var10000.getSection(new String[]{"(module)"})) != null) {
        String[] var7;
        (var7 = new String[1])[0] = "parameters";
        if ((var5 = var5.getSection(var7)) != null) {

          for (String var9 : var5.getKeys()) {
            String var10 = var5.getString(var9);
            Intrinsics.checkNotNull(var10);
            String var4 = var10;
            Intrinsics.checkNotNull(var9);
            this.d.put(var9, new Parameter(var9, var4));
          }
        }
      }
    }
  }

  @NotNull
  public String getName() {
    return this.name;
  }

  @NotNull
  public Map<String, YamlImportDeclaration> getImports() {
    return this.imports;
  }

  @NotNull
  public YamlContainer getAdapter() {
    return this.adapter;
  }

  @NotNull
  public LinkedHashMap<String, Anchor> getAnchors() {
    return this.anchors;
  }

  @NotNull
  public Map<String, Node> getParameterInputFrom(@NotNull YamlImportDeclaration var1) {
    Objects.requireNonNull(var1, "");
    Map<String, Node> var2 = new HashMap<>();
    YamlNodeSection var10000 = var1.getInfo();
    String[] var3;
    (var3 = new String[1])[0] = "parameters";
    YamlNodeSection var7;
    if ((var7 = var10000.getSection(var3)) != null) {

      for (String var4 : var7.getKeys()) {
        Intrinsics.checkNotNull(var4);
        String var6 = var4;
        Node var10 = var7.getNode(var4);
        if (var10 == null) {
          throw new IllegalStateException(("Missing parameter " + var4 + " for '" + this.name + "' YAML module"));
        }

        Node var9 = var10;
        var2.put(var6, var9);
      }
    }

    return var2;
  }

  public static final class Parameter {

    @NotNull
    private final String name;
    @NotNull
    private final String type;

    public Parameter(@NotNull String var1, @NotNull String var2) {
      Objects.requireNonNull(var1, "");
      Objects.requireNonNull(var2, "");
      this.name = var1;
      this.type = var2;
    }

    @NotNull
    public String getName() {
      return this.name;
    }

    @NotNull
    public String getType() {
      return this.type;
    }

  }

}
