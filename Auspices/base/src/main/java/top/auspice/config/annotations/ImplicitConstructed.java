package top.auspice.config.annotations;

import org.snakeyaml.engine.v2.nodes.Node;
import top.auspice.config.sections.ConfigSection;
import top.auspice.config.yaml.snakeyaml.node.NodesKt;

import java.lang.annotation.*;

/**
 * 代表这个方法会隐式操作一段配置的构造值
 * <p>
 * see {@linkplain ConfigSection#setParsedValue(Object)}.
 * <p>
 * see {@linkplain NodesKt#cacheConstructed(Node, Object)}
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Documented
public @interface ImplicitConstructed {
}
