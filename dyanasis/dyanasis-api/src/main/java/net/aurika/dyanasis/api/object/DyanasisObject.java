package net.aurika.dyanasis.api.object;

import net.aurika.dyanasis.api.declaration.doc.DyanasisDoc;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAnchor;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAware;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionAnchor;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionsAware;
import net.aurika.dyanasis.api.declaration.invokable.function.container.DyanasisFunctionContainer;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisPropertiesAware;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisPropertyAnchor;
import net.aurika.dyanasis.api.declaration.invokable.property.container.DyanasisPropertyContainer;
import net.aurika.dyanasis.api.typedata.DyanasisTypeData;
import net.aurika.dyanasis.api.typedata.DyanasisTypeDataAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DyanasisObject
        extends
        DyanasisPropertiesAware, DyanasisPropertyAnchor,
        DyanasisFunctionsAware, DyanasisFunctionAnchor,
        DyanasisDocAware, DyanasisDocAnchor,
        DyanasisTypeDataAware {

    @Override
    @NotNull ObjectPropertyContainer<? extends ObjectProperty> dyanasisProperties();

    @Override
    @NotNull ObjectFunctionContainer<? extends ObjectFunction> dyanasisFunctions();

    @Override
    @Nullable ObjectDoc dyanasisDoc();

    @Override
    @NotNull DyanasisTypeData dyanasisTypeData();

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

    interface ObjectPropertyContainer<P extends ObjectProperty> extends DyanasisPropertyContainer<P> {
        @NotNull DyanasisTypeData objectTypeData();
    }

    interface ObjectFunctionContainer<F extends ObjectFunction> extends DyanasisFunctionContainer<F> {
        @NotNull DyanasisTypeData objectTypeData();
    }

    interface ObjectProperty extends DyanasisProperty {
        @Override
        @NotNull DyanasisObject owner();
    }

    interface ObjectFunction extends DyanasisFunction {
        @Override
        @NotNull DyanasisObject owner();
    }

    interface ObjectDoc extends DyanasisDoc {
        @Override
        @NotNull DyanasisObject owner();
    }
}
