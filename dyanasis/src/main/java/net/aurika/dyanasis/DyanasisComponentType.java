package net.aurika.dyanasis;

import net.aurika.dyanasis.declaration.doc.DyanasisDoc;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.declaration.namespace.DyanasisNamespace;

public enum DyanasisComponentType {
    /**
     * @see DyanasisNamespace
     */
    NAMESPACE(DyanasisNamespace.class),
    /**
     * @see DyanasisFunction
     */
    FUNCTION(DyanasisFunction.class),
    /**
     * @see DyanasisProperty
     */
    PROPERTY(DyanasisProperty.class),
    /**
     * @see DyanasisDoc
     */
    DOC(DyanasisDoc.class),
    ;
    private final Object[] value;

    DyanasisComponentType(Object... value) {
        this.value = value;
    }

    public Object[] get() {
        return value.clone();
    }
}
