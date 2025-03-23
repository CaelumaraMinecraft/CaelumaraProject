package net.aurika.config.annotations;

import net.aurika.config.sections.ConfigSection;
import net.aurika.snakeyaml.extension.nodes.NodesKt;
import org.snakeyaml.engine.v2.nodes.Node;

import java.lang.annotation.*;

/**
 * 代表这个方法会隐式操作一段配置的构造值
 * <p>
 * see {@linkplain ConfigSection#setParsedValue(Object)}.
 * <p>
 * see {@linkplain NodesKt#cacheConstructed(Node, Object)}
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ImplicitOperateCached {
}
