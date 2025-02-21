package net.aurika.common.snakeyaml.constructor;

import net.aurika.common.snakeyaml.nodes.NodesKt;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.constructor.StandardConstructor;
import org.snakeyaml.engine.v2.nodes.Node;

import java.util.Objects;

public class AuspiceConstructor extends StandardConstructor {

    public AuspiceConstructor(@NotNull LoadSettings settings) {
        super(settings);
    }

    public Object constructObject(@NotNull Node node) {
        Objects.requireNonNull(node, "Node cannot be null");
        Object constructed = super.constructObject(node);
        NodesKt.cacheConstructed(node, constructed);                      // 缓存构造完的对象
        return constructed;
    }
}
