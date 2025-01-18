package top.auspice.craftbukkit.services.pets.mypet;

import de.Keyle.MyPet.api.entity.MyPet;
import de.Keyle.MyPet.api.entity.MyPetBukkitEntity;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import top.auspice.bukkit.services.PetInfo;
import top.auspice.bukkit.services.ServicePet;

public final class ServiceMyPet implements ServicePet {
    public PetInfo getPetInfo(@NotNull Entity entity) {
        if (entity instanceof MyPetBukkitEntity) {
            MyPet pet = ((MyPetBukkitEntity) entity).getMyPet();
            return new PetInfo(pet.getOwner().getPlayerUUID(), true);
        } else {
            return null;
        }
    }
}
