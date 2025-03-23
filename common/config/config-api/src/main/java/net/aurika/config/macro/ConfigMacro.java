package net.aurika.config.macro;

import org.jetbrains.annotations.NotNull;
import net.aurika.abstraction.ScopedObject;
import net.aurika.config.functional.ConfigFunctional;
import net.aurika.config.functional.invoking.ConfigFunctionalInvokingData;
import net.aurika.config.scope.ConfigScope;
import net.aurika.common.key.namespace.nested.NamespaceNested;
import net.aurika.common.key.namespace.nested.NestedNamespace;

public interface ConfigMacro extends ConfigFunctional, ScopedObject, NamespaceNested {

    Object processInvoke(Object operationObject, ConfigFunctionalInvokingData invokingData);

    @NotNull String getName();

    @NotNull ConfigScope getScope();

    @Override
    default @NotNull NestedNamespace getNestedNamespace() {
        return this.getScope().getNestedNamespace();   // 宏默认遵守配置这个宏的配置作用域的嵌套命名空间
    }
}
