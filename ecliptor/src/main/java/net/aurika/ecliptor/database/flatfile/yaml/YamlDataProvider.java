package net.aurika.ecliptor.database.flatfile.yaml;

import net.aurika.ecliptor.database.flatfile.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.Node;

public interface YamlDataProvider extends ObjectDataProvider {

  @NotNull Node getNode();

  @Override
  default @NotNull Object rawDataObject() {
    return this.getNode();
  }

}
