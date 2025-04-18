package net.aurika.auspice.platform.attribute;

import org.jetbrains.annotations.NotNull;

public interface AttributeObject extends AttributeTypeAware {

  @Override
  @NotNull AttributeType attributeType();

}
