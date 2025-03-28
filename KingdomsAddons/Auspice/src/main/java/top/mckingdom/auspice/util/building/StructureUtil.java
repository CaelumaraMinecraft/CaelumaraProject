package top.mckingdom.auspice.util.building;

import net.aurika.validate.Validate;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.location.SimpleChunkLocation;
import org.kingdoms.constants.land.location.SimpleLocation;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;
import org.kingdoms.server.location.BlockVector3;

import java.util.UUID;

public final class StructureUtil {

  public static Structure placeStructure(@NotNull StructureStyle style, @NotNull Location location) {
    Validate.Arg.notNull(style, "style");
    Validate.Arg.notNull(location, "location");
    @Nullable Land land = Land.getLand(location);
    if (land == null) {  // will auto save this land
      land = new Land((UUID) null, SimpleChunkLocation.of(location));
    }
    Structure structure = new Structure(style, SimpleLocation.of(location));
    land.unsafeGetStructures().put(BlockVector3.of(location.getBlockX(), location.getBlockY(), location.getBlockZ()), structure);
    return structure;
  }

}
