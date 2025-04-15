package net.aurika.auspice.service.mypet;

import de.Keyle.MyPet.api.entity.MyPet;
import de.Keyle.MyPet.api.entity.MyPetBukkitEntity;
import net.aurika.auspice.service.api.BukkitServicePet;
import net.aurika.auspice.service.api.PetInfo;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public final class ServiceMyPet implements BukkitServicePet {

  @Override
  public PetInfo petInfo(@NotNull Entity entity) {
    if (entity instanceof MyPetBukkitEntity) {
      MyPet pet = ((MyPetBukkitEntity) entity).getMyPet();
      return new PetInfo(pet.getOwner().getPlayerUUID(), true);
    } else {
      return null;
    }
  }

}
