package top.auspice.config.yaml.snakeyaml.constructor;

import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.constructor.StandardConstructor;
import org.snakeyaml.engine.v2.nodes.Node;
import top.auspice.config.yaml.snakeyaml.node.NodesKt;

import java.util.Objects;

public class AuspiceConstructor extends StandardConstructor {

    public AuspiceConstructor(LoadSettings settings) {
        super(settings);
    }

    public Object constructObject(Node node) {
        Objects.requireNonNull(node, "Node cannot be null");
        Object constructed = super.constructObject(node);
        NodesKt.cacheConstructed(node, constructed);                      // 存储构造完的对象
        return constructed;
    }

}
