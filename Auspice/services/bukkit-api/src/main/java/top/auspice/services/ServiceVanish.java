package top.auspice.services;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface ServiceVanish extends BukkitService {
    boolean isVanished(Player player);

    boolean isInGodMode(Player player);

    boolean isIgnoring(Player ignorant, UUID ignoring);
}
