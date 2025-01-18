package top.auspice.config.macro;

import org.jetbrains.annotations.NotNull;
import top.auspice.abstraction.ScopedObject;
import top.auspice.config.functional.ConfigFunctional;
import top.auspice.config.functional.invoking.ConfigFunctionalInvokingData;
import top.auspice.config.scope.ConfigScope;
import top.auspice.key.nested.NamespaceNested;
import top.auspice.key.nested.NestedNamespace;

public interface ConfigMacro extends ConfigFunctional, ScopedObject, NamespaceNested {

    Object processInvoke(Object operationObject, ConfigFunctionalInvokingData invokingData);

    @NotNull String getName();

    @NotNull ConfigScope getScope();

    @Override
    default @NotNull NestedNamespace getNestedNamespace() {
        return this.getScope().getNestedNamespace();   // 宏默认遵守配置这个宏的配置作用域的嵌套命名空间
    }
}
