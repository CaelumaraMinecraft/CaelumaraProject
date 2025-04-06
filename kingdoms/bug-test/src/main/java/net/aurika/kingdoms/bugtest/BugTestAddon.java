package net.aurika.kingdoms.bugtest;

import net.aurika.kingdoms.bugtest.constant.StructureTypeTest;
import net.aurika.kingdoms.bugtest.manager.BugTestHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.constants.land.structures.StructureRegistry;

import java.io.File;

public class BugTestAddon extends JavaPlugin implements Addon {

  private static BugTestAddon instance;

  public static @NotNull BugTestAddon get() {
    if (instance == null) {
      throw new IllegalStateException("BugTestAddon is not initialized");
    } else {
      return instance;
    }
  }

  public BugTestAddon() {
    super();
    instance = this;
  }

  @Override
  public void onLoad() {
    registerAddon();
  }

  @Override
  public void onEnable() {
    registerTestListener();
    registerTestStructure();
  }

  @Override
  public void onDisable() {
  }

  @Override
  public void reloadAddon() {
    registerTestStructure();
  }

  @Override
  public @NotNull String getAddonName() {
    return "bug-test";
  }

  @Override
  public @NotNull File getFile() {
    return super.getFile();
  }

  public static void registerTestStructure() {
    System.out.println();
    System.out.println("Previews structure types:");
    printStructures();
    StructureRegistry.get().registerType(new StructureTypeTest());
    System.out.println("Registered test structure. The current structures: ");
    printStructures();
    System.out.println();
  }

  public static void printStructures() {
    System.out.println(StructureRegistry.get().getTypes().keySet());
  }

  public static void registerTestListener() {
    Bukkit.getPluginManager().registerEvents(new BugTestHandler(), get());
  }

}
