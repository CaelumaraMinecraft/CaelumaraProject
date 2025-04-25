package net.aurika.auspice.platform.bukkit.api.world;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.aurika.auspice.platform.bukkit.world.BukkitWorldAdapter$Companion.ADAPTER_CONTAINER;

public interface BukkitWorldAdapter {

  static @NotNull BukkitWorldAdapter get() {
    synchronized (ADAPTER_CONTAINER) {
      BukkitWorldAdapter adapter = ADAPTER_CONTAINER[0];
      if (adapter == null) {
        throw new IllegalStateException(BukkitWorldAdapter.class.getName() + " not initialized");
      }
      return adapter;
    }
  }

  static @Nullable BukkitWorldAdapter init(@NotNull BukkitWorldAdapter newAdapter) {
    synchronized (ADAPTER_CONTAINER) {
      BukkitWorldAdapter oleAdapter = ADAPTER_CONTAINER[0];
      ADAPTER_CONTAINER[0] = newAdapter;
      return oleAdapter;
    }
  }

  @NotNull BukkitWorld adapt(@NotNull org.bukkit.World bukkitWorld);

}

final class BukkitWorldAdapter$Companion {

  static final BukkitWorldAdapter[] ADAPTER_CONTAINER = new BukkitWorldAdapter[1];

  private BukkitWorldAdapter$Companion() { }

}
