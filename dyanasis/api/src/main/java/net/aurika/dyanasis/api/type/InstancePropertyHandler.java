package net.aurika.dyanasis.api.type;

import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface InstancePropertyHandler<O extends DyanasisObject> extends InstanceMemberHandler<O, String, DyanasisObject.ObjectProperty> {

  @Override
  @NotNull Map<String, ? extends DyanasisObject.ObjectProperty> members(@NotNull O object);

}
