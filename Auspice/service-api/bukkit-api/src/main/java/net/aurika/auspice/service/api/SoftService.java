package net.aurika.auspice.service.api;

import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.configs.texts.MessageHandler;
import net.aurika.auspice.utils.logging.AuspiceLogger;
import net.aurika.auspice.utils.string.Strings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.function.Supplier;

public enum SoftService {
  CITIZENS("Citizens"),
  MY_PET("MyPet", "net.aurika.auspice.services.mypet.ServiceMyPet"),
  MC_PETS("MCPets", "net.aurika.auspice.services.mcpets.ServiceMCPets"),
  COMBAT_PETS("CombatPets", "net.aurika.auspice.services.combatpets.ServiceCombatPets"),
  SIMPLE_PETS("SimplePets", "net.aurika.auspice.services.simplepet.ServiceSimplePet"),
  MVDWPLACEHOLDERAPI("MVdWPlaceholderAPI", "net.aurika.auspice.services.placeholders.ServiceMVdWPlaceholder"),
  PLACEHOLDERAPI("PlaceholderAPI", "net.aurika.auspice.services.placeholders.ServicePlaceholderAPI"),
  VAULT("Vault", "net.aurika.auspice.services.ServiceVault"),
  DISCORDSRV("DiscordSRV", "net.aurika.auspice.services.ServiceDiscordSRV"),
  LUCKPERMS("LuckPerms", "net.aurika.auspice.services.ServiceLuckPerms"),
  ESSENTIALS("Essentials", "net.aurika.auspice.services.vanish.ServiceEssentialsX"),
  CMI("CMI", "net.aurika.auspice.services.vanish.ServiceCMI"),
  SLIMEFUN("Slimefun", "net.aurika.auspice.services.ServiceSlimefun"),

  ;
  private final String name;
  private final Service service;
  private boolean available;

  SoftService(String name) {
    this(name, () -> EmptyService.INSTANCE);
  }

  SoftService(String name, Supplier<Service> serviceSupplier) {
    this.name = name;
    this.available = this.checkAvailable();
    if (!this.available) {
      this.service = EmptyService.INSTANCE;
    } else {
      this.service = serviceSupplier.get();
      this.available = a(null, this.service);
    }
  }

  SoftService(String var3, String... var4) {
    this(var3, null, var4);
  }

  SoftService(String var3, Runnable var4, String... paths) {
    this.name = var3;
    this.available = this.checkAvailable();
    this.service = this.available ? a(paths) : null;
    this.available = a(var4, this.service);
  }

  private static boolean a(Runnable var0, Service var1) {
    if (var1 == null) {
      return false;
    } else {
      Throwable var2;
      if ((var2 = var1.checkAvailability()) != null) {
        if (AuspiceLogger.isDebugging()) {
          (new RuntimeException("Failed to load service " + var1.serviceName(), var2)).printStackTrace();
        }

        return false;
      } else {
        try {
          if (var0 != null) {
            var0.run();
          }

          return true;
        } catch (Throwable var3) {
          throw new RuntimeException("Additional checks has failed while loading service " + var1.serviceName(), var3);
        }
      }
    }
  }

  private static Service a(String... var0) {

    for (String var3 : var0) {
      try {
        Service var4 = (Service) Class.forName(var3).getConstructor().newInstance();
        if (a(null, var4)) {
          return var4;
        }
      } catch (Throwable var5) {
        if (AuspiceLogger.isDebugging()) {
          AuspiceLogger.error("Error while initializing service with class '" + var3 + "':");
          var5.printStackTrace();
        }
      }
    }

    return null;
  }

  public Service getService() {
    return this.service;
  }

  public String getName() {
    return this.name;
  }

  public static void reportAvailability() {
    SoftService[] var0;
    int var1 = (var0 = values()).length;

    for (int var2 = 0; var2 < var1; ++var2) {
      SoftService var3;
      if ((var3 = var0[var2]).available) {
        MessageHandler.sendConsolePluginMessage("&6" + var3.name + " &2found and hooked.");
      } else {
        MessageHandler.sendConsolePluginMessage("&e" + var3.name + " &cnot found.");
      }
    }
  }

  public void available(boolean var1) {
    this.available = var1;
  }

  public static boolean anyAvailable(SoftService... var0) {
    return Arrays.stream(var0).anyMatch(SoftService::isAvailable);
  }

  public Plugin getPlugin() {
    return Bukkit.getPluginManager().getPlugin(this.name);
  }

  public boolean isAvailable() {
    return this.available;
  }

  private boolean checkAvailable() {
    if (AuspiceGlobalConfig.INTEGRATIONS.getManager().withProperty(Strings.configOption(this.name)).getBoolean()) {
      Plugin var1 = Bukkit.getPluginManager().getPlugin(this.name);
      return var1 != null && var1.isEnabled();
    } else {
      return false;
    }
  }
}
