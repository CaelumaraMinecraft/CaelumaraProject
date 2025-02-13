package net.aurika.ecliptor.database.flatfile.yaml;

import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.Node;
import net.aurika.ecliptor.database.flatfile.ObjectDataProvider;

public interface YamlDataProvider extends ObjectDataProvider {
    @NotNull Node getNode();

    @Override
    default @NotNull Object getRawDataObject() {
        return this.getNode();
    }
}
