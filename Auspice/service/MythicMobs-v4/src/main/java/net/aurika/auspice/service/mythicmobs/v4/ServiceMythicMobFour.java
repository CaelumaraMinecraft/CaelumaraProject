package net.aurika.auspice.service.mythicmobs.v4;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import net.aurika.auspice.craftbukkit.services.mythicmobs.ServiceMythicMobs;
import net.aurika.auspice.craftbukkit.services.mythicmobs.UnknownMythicMobException;

public final class ServiceMythicMobFour implements ServiceMythicMobs {
    public Entity spawnMythicMob(Location location, String mob, int level) {
        try {
            return MythicMobs.inst().getAPIHelper().spawnMythicMob(mob, location, level);
        } catch (InvalidMobTypeException e) {
            throw new UnknownMythicMobException(mob, e);
        }
    }

    public Throwable checkAvailability() {
        try {
            MythicMobs.inst().getAPIHelper();
            return null;
        } catch (Throwable ex) {
            return ex;
        }
    }
}
