package net.aurika.kingdoms.territories.manager;

import net.aurika.kingdoms.territories.constant.land.structure.object.SturdyCore;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.land.Land;

import java.util.List;

public class ExplosionManager implements Listener {

  public static final ExplosionManager INSTANCE = new ExplosionManager();

  private ExplosionManager() {
  }

  @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.LOW
  )
  public void onBlockExplode(@NotNull BlockExplodeEvent event) {
    if (SturdyCore.isProtectedBySturdyCore(event.getBlock().getLocation())) {
      event.setCancelled(true);
      return;
    }
    removeProtectedBlocks(event.getBlock().getLocation(), event.blockList());
    System.out.println("block explode at " + event.getBlock().getLocation());
  }

  @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.LOW
  )
  public void onEntityExplode(@NotNull EntityExplodeEvent event) {
    if (SturdyCore.isProtectedBySturdyCore(event.getLocation())) {
      event.setCancelled(true);
      return;
    }
    removeProtectedBlocks(event.getLocation(), event.blockList());
    System.out.println("entity explode at " + event.getEntity().getLocation());
  }

  public void onBlockBreak(@NotNull BlockBreakEvent event) {
    System.out.println("block break at " + event.getBlock().getLocation());
  }

  @Contract(mutates = "param1")
  protected void removeProtectedBlocks(@NotNull Location original, @NotNull List<Block> blocks) {
//    Set<@Nullable Land> effectedLands = new HashSet<>();
    for (Block block : blocks) {
      @Nullable Land effectedLand = Land.getLand(block);
//      effectedLands.add(effectedLand);
      if (effectedLand != null) {
        if (SturdyCore.isProtectedBySturdyCore(effectedLand)) {
          blocks.remove(block);
          System.out.println("Removed protected block in event" + block.getLocation());
        }
      }
    }
  }

}
