package net.aurika.data.database.flatfile.yaml;

import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.Node;
import net.aurika.data.database.flatfile.ObjectDataProvider;

public interface YamlDataProvider extends ObjectDataProvider {
    @NotNull Node getNode();

    @Override
    default @NotNull Object getRawDataObject() {
        return this.getNode();
    }
}
