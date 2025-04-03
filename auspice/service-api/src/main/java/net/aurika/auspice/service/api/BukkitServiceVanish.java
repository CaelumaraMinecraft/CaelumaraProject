package net.aurika.auspice.service.api;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface BukkitServiceVanish extends BukkitService {

  boolean isVanished(Player player);

  boolean isInGodMode(Player player);

  boolean isIgnoring(Player ignorant, UUID ignoring);

}
