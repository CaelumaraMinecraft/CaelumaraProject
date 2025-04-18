package net.aurika.kingdoms.territories.managers;

import net.aurika.kingdoms.territories.TerritoriesAddon;
import net.aurika.kingdoms.territories.constant.land.structure.object.SturdyCore;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.Land;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ExplosionManager implements Listener {

  public static final ExplosionManager INSTANCE = new ExplosionManager();

  private ExplosionManager() {
  }

  @EventHandler()
  public void onBlockExplode(@NotNull BlockExplodeEvent event) {
    List<Block> blocks = event.blockList();
    Set<@Nullable Land> effectedLands = new HashSet<>();
    for (Block block : blocks) {
      @Nullable Land effectedLand = Land.getLand(block);
      effectedLands.add(effectedLand);
      if (effectedLand != null) {
        if (SturdyCore.isProtectedBySturdyCore(effectedLand)) {
          blocks.remove(block);
        }
      }
    }
    System.out.println("block explode at " + event.getBlock().getLocation());
  }

  @EventHandler()
  public void onEntityExplode(@NotNull EntityExplodeEvent event) {
    System.out.println("entity explode at " + event.getEntity().getLocation());


    TerritoriesAddon.printStructures();
  }

}
