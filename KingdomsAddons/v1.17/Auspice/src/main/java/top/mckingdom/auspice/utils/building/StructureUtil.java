package top.mckingdom.auspice.utils.building;

import org.bukkit.Location;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.location.SimpleChunkLocation;
import org.kingdoms.constants.land.location.SimpleLocation;
import org.kingdoms.constants.land.structures.Structure;
import org.kingdoms.constants.land.structures.StructureStyle;
import org.kingdoms.server.location.BlockVector3;

import java.util.UUID;

public final class StructureUtil {

    public static Structure placeStructure(StructureStyle style, Location location) {
        Land land = Land.getLand(location);
        if (land == null) {
            land = new Land((UUID)null, SimpleChunkLocation.of(location));
        }
        Structure structure = new Structure(style, SimpleLocation.of(location));
        land.getStructures().put(BlockVector3.of(location.getBlockX(), location.getBlockY(), location.getBlockZ()), structure);
        return structure;
    }

}
