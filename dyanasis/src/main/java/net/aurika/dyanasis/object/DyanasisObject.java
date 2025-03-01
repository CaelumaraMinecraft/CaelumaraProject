package net.aurika.dyanasis.object;

import net.aurika.dyanasis.declaration.doc.DyanasisDocOwner;
import net.aurika.dyanasis.declaration.doc.DyanasisDocProvider;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionOwner;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionsProvider;
import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisPropertiesProvider;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisPropertyOwner;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import org.jetbrains.annotations.NotNull;

public interface DyanasisObject extends
        DyanasisPropertiesProvider, DyanasisPropertyOwner,  // property
        DyanasisFunctionsProvider, DyanasisFunctionOwner,   // function
        DyanasisDocProvider, DyanasisDocOwner {             // doc

    @Override
    @NotNull DyanasisProperties dyanasisProperties();

    @Override
    @NotNull DyanasisFunctions dyanasisFunctions();

    /**
     * 将这个 {@linkplain DyanasisObject} 与一个配置中的字符串相比较.
     *
     * @param cfgStr 配置字符串
     * @return 是否相同
     */
    boolean equals(@NotNull String cfgStr);

    /**
     * Gets the value as a java object.
     *
     * @return the value
     */
    @NotNull Object valueAsJava();
}
