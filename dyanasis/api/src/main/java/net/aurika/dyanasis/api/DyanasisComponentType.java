package net.aurika.dyanasis.api;

import net.aurika.dyanasis.api.declaration.doc.DyanasisDoc;
import net.aurika.dyanasis.api.declaration.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.property.DyanasisProperty;
import net.aurika.dyanasis.api.type.DyanasisType;

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
  /**
   * @see DyanasisType
   */
  TYPE(DyanasisType.class),
  ;
  private final Object[] value;

  DyanasisComponentType(Object... value) {
    this.value = value;
  }

  public Object[] get() {
    return value.clone();
  }
}
