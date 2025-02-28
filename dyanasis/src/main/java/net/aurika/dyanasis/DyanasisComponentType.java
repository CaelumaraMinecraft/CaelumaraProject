package net.aurika.dyanasis;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;

public enum DyanasisComponentType {
    /**
     * @see DyanasisNamespace
     */
    NAMESPACE(),
    /**
     * @see DyanasisFunction
     */
    FUNCTION(),
    /**
     * @see DyanasisProperty
     */
    PROPERTY(),
    ;
    private final Object[] value;

    DyanasisComponentType(Object... value) {
        this.value = value;
    }

    public Object[] get() {
        return value.clone();
    }
}
