package net.aurika.kingdoms.bugtest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.structures.StructureRegistry;

public class BugTestHandler implements Listener {

  @EventHandler
  public void onPlayerChat(@NotNull PlayerChatEvent event) {
    String message = event.getMessage();

    if ("register test".equals(message)) {
      System.out.println("Registered test structure.");
      BugTestAddon.registerTestStructure();
    }

    if ("init registry".equals(message)) {
      System.out.println("Old structure registry.");
      BugTestAddon.printStructures();
      System.out.println("Init structure registry.");
      StructureRegistry.get().init();
      BugTestAddon.printStructures();
    }

    System.out.println("The current structure types:");
    BugTestAddon.printStructures();
  }

}
