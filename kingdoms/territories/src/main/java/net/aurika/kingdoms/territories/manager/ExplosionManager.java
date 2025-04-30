package net.aurika.kingdoms.territories.manager;

import net.aurika.kingdoms.territories.constant.land.structure.object.SturdyCore;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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

  @EventHandler(priority = EventPriority.HIGH)
  public void onBlockExplode(@NotNull BlockExplodeEvent event) {
    List<Block> blocks = event.blockList();
    removeProtectedBlocks(blocks);
    System.out.println("block explode at " + event.getBlock().getLocation());
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onEntityExplode(@NotNull EntityExplodeEvent event) {
    List<Block> blocks = event.blockList();
    removeProtectedBlocks(blocks);
    System.out.println("entity explode at " + event.getEntity().getLocation());
  }

  @Contract(mutates = "param1")
  protected void removeProtectedBlocks(@NotNull List<Block> blocks) {
//    Set<@Nullable Land> effectedLands = new HashSet<>();
    for (Block block : blocks) {
      @Nullable Land effectedLand = Land.getLand(block);
//      effectedLands.add(effectedLand);
      if (effectedLand != null) {
        if (SturdyCore.isProtectedBySturdyCore(effectedLand)) {
          blocks.remove(block);
          System.out.println("Removed protected block " + block.getLocation());
        }
      }
    }
  }

}
