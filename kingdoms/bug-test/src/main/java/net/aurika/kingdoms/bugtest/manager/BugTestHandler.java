package net.aurika.kingdoms.bugtest.manager;

import net.aurika.kingdoms.bugtest.BugTestAddon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public final class BugTestHandler implements Listener {

  @EventHandler
  public void onPlayerChat(@NotNull AsyncPlayerChatEvent event) {
    System.out.println("The current structure types:");
    BugTestAddon.printStructures();
  }

}
