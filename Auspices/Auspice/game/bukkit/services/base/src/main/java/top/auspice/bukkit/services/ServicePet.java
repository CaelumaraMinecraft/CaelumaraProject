package top.auspice.bukkit.services;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ServicePet extends BukkitService {
    @Nullable PetInfo getPetInfo(@NotNull Entity entity);
}
