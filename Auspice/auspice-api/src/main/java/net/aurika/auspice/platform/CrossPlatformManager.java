package net.aurika.auspice.platform;

import net.aurika.auspice.utils.reflection.Reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class CrossPlatformManager {

  public CrossPlatformManager() {
  }

  public static boolean isRunningForge() {
    String fml = "net.minecraftforge.fml.";
    return Stream.of(
        "common.Mod",
        "common.Loader",
        "common.FMLContainer",
        "ModLoader",
        "client.FMLClientHandler",
        "server.ServerMain"
    ).anyMatch((String x) -> Reflect.classExists(fml + x));
  }

  public static boolean isRunningGeyser() {
    String geyser = "org.geysermc.";
    return Stream.of(
        "geyser.GeyserMain",
        "geyser.Constants",
        "connector.GeyserConnector",
        "connector.network.session.GeyserSession",
        "api.Geyser",
        "api.connection.Connection"
    ).anyMatch((String x) -> Reflect.classExists(geyser + x));
  }

  public static boolean isRunningFloodGate() {
    return Reflect.classExists("org.geysermc.floodgate.api.FloodgateApi");
  }

  public static boolean isRunningBukkit() {
    return Reflect.classExists("org.bukkit.entity.Player")
        && Reflect.classExists("org.bukkit.Bukkit");
  }

  public static boolean isRunningPaper() {
    return Reflect.classExists("com.destroystokyo.paper.PaperConfig")
        || Reflect.classExists("io.papermc.paper.configuration.Configuration");
  }

  public static boolean isRunningFolia() {
    if (Reflect.classExists("io.papermc.paper.threadedregions.RegionizedServerInitEvent")) {
      try {
        Class.forName("org.bukkit.plugin.PluginDescriptionFile").getDeclaredMethod("isFoliaSupported");
        return true;
      } catch (Throwable throwable) {
        return false;
      }
    } else {
      return false;
    }
  }

  public static boolean isRunningSpigot() {
    return Reflect.classExists("org.spigotmc.SpigotConfig");
  }

  public static List<String> warn() {
    List<String> warnings = new ArrayList<>();
    if (Platform.FORGE.isAvailable()) {
      warnings.add("Your server is running on a platform that supports Forge. The plugin may not function properly.");
    }

    if (Platform.BEDROCK.isAvailable()) {
      warnings.add(
          "Your server is running on a platform that supports Bedrock Edition. The plugin may not function properly.");
    }

    if (Platform.FOLIA.isAvailable()) {
      warnings.add(
          "Your server is running on Folia. The plugin has not added support for this software, and the plugin will most likely not work.");
    }

    return warnings;
  }

}
