package net.aurika.config.yaml.importers;

import net.aurika.common.snakeyaml.nodes.NodeUtils;
import net.aurika.config.yaml.adapter.YamlContainer;
import net.aurika.config.yaml.adapter.YamlImportDeclaration;
import net.aurika.config.yaml.adapter.YamlModule;
import net.aurika.config.yaml.common.NodeReplacer;
import org.snakeyaml.engine.v2.common.Anchor;
import org.snakeyaml.engine.v2.nodes.MappingNode;

import java.util.Map;

public interface YamlImporter {

  YamlModule getDeclaration(YamlImportDeclaration declaration);

  static void importTo(YamlContainer to, Map<String, Anchor> anchors, YamlImporter importer, YamlImportDeclaration importDeclaration) {
    MappingNode root = (MappingNode) to.getConfig().getRootNode();
    String var5 = importDeclaration.getName();

    try {
      YamlModule declaration = importer.getDeclaration(importDeclaration);
      if (declaration == null) {
        throw new YamlDeclarationNotFoundException(to, var5, importer);
      } else {

        for (YamlImportDeclaration var8 : declaration.getImports().values()) {
          importTo(to, anchors, importer, var8);
        }

        if (importDeclaration.getExtends()) {
//                    root.copyIfDoesntExist(declaration.getAdapter().getConfig().getNode(), "", (var0x) -> false);
          NodeUtils.copyIfDoesntExist(
              root, (MappingNode) declaration.getAdapter().getConfig().getRootNode(), "", (var0x) -> false);
        }

        for (String var11 : importDeclaration.getImportedAnchors()) {
          Anchor var10;
          if ((var10 = declaration.getAnchors().get(var11)) == null) {
            throw new IllegalArgumentException("Unknown imported anchor " + var11 + " for " + var5);
          }

          anchors.put(var10.getValue(), var10);
        }

        if (importDeclaration.getExtends()) {
          (new NodeReplacer(root, declaration.getParameterInputFrom(importDeclaration), null)).ignore(
              YamlImportComposer.INSTANCE).replaceAll();
        }
      }
    } catch (Throwable var9) {
      throw new RuntimeException("Error while importing '" + var5 + "' for " + to.getFile(), var9);
    }
  }

}

