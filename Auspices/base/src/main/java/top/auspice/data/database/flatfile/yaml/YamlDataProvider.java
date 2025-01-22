package top.auspice.data.database.flatfile.yaml;

import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.Node;
import top.auspice.data.database.flatfile.ObjectDataProvider;

public interface YamlDataProvider extends ObjectDataProvider {
    @NotNull Node getNode();

    default @NotNull Object getRawDataObject() {
        return this.getNode();
    }
}
