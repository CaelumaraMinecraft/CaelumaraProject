package net.aurika.auspice.platform.entity.abstraction;

import net.aurika.auspice.platform.Platform;
import net.aurika.auspice.platform.UUIDIdentified;
import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.entity.type.EntityType;
import net.aurika.auspice.platform.entity.type.EntityTypeAware;
import net.aurika.auspice.platform.location.PreciseLocation;
import net.aurika.auspice.platform.location.PreciseLocationAware;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.keyed.Identified;
import net.aurika.common.metalang.flow.Instance;
import net.aurika.common.metalang.flow.Invoke;
import net.aurika.common.metalang.flow.MethodFind;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Identified(by = "uniqueId", in = @Identified.In(@Instance(type = Instance.Type.INVOKE, invoke = @Invoke(find = @MethodFind(name = "get", inClass = Platform.class)))))
public interface Entity extends CommandSender, PreciseLocationAware, UUIDIdentified, EntityTypeAware {

  String VAL_ID = "id";
  String VAL_TYPE = "type";
  String VAL_LOCATION = "location";

  /**
   * Gets the unique id of the entity.
   *
   * @return the unique id
   */
  @Override
  @ExaminablePropertyGetter(VAL_ID)
  @NotNull UUID uniqueId();

  @Override
  @ExaminablePropertyGetter(VAL_TYPE)
  @NotNull EntityType entityType();

  @Override
  @NotNull World world();

  @Override
  double preciseX();

  @Override
  double preciseY();

  @Override
  double preciseZ();

  @Override
  float pitch();

  @Override
  float yaw();

  @ExaminablePropertyGetter(VAL_LOCATION)
  default @NotNull PreciseLocation entityLocation() { return PreciseLocation.preciseLocation(this); }

  @ApiStatus.Experimental
  interface Adapter<AE extends Entity, PE> extends net.aurika.auspice.platform.adapter.Adapter<AE, PE> { }

}
