package net.aurika.auspice.bukkit.utils;

import com.google.common.base.Strings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

public final class PlayerUtils {

  public static final UUID CONSOLE_UUID = new UUID(0L, 0L);
  private static final Map<String, OfflinePlayer> b;
  private static final boolean c;

  public static UUID getUniqueId(CommandSender var0) {
    return var0 instanceof Player ? ((Player) var0).getUniqueId() : CONSOLE_UUID;
  }

  public static void cachePlayer(Player var0) {
    b.put(var0.getName(), var0);
  }

  public static String name(OfflinePlayer var0) {
    String var1;
    return Strings.isNullOrEmpty(var1 = var0.getName()) ? var0.getUniqueId().toString() : var1;
  }

  public static double getMaxPlayerHealth(@NotNull LivingEntity var0) {
    return var0.getAttribute(Attribute.MAX_HEALTH).getValue();
  }

  public static boolean invulnerableGameMode(@NonNull Player var0) {
    GameMode var1;
    return (var1 = var0.getGameMode()) == GameMode.CREATIVE || var1 == GameMode.SPECTATOR;
  }

  public static int findEmptyHotbarSlot(Player var0) {
    PlayerInventory var2 = var0.getInventory();

    for (int var1 = 0; var1 < 8; ++var1) {
      if (var2.getItem(var1) == null) {
        return var1;
      }
    }

    return -1;
  }

  public static ItemStack getHotbarItem(Player var0, int var1) {
    if ((var1 < 0 || var1 > 8) && var1 != -1) {
      throw new IllegalArgumentException("Unknown hotbar slot: " + var1 + " (from " + var0.getName() + ')');
    } else {
      PlayerInventory var2 = var0.getInventory();
      return var1 == -1 ? var2.getItemInOffHand() : var2.getItem(var1);
    }
  }

  public static OfflinePlayer getFirstPlayerThat(Predicate<OfflinePlayer> var0) {
    Objects.requireNonNull(var0);
    Iterator<OfflinePlayer> var1 = b.values().iterator();

    OfflinePlayer var2;
    do {
      if (!var1.hasNext()) {
        return null;
      }

      var2 = var1.next();
    } while (!var0.test(var2));

    return var2;
  }

  public static @Nullable OfflinePlayer getOfflinePlayer(@NonNull String var0) {
    return var0.length() <= 16 ? b.get(var0) : null;
  }

  public static @Nullable Player getPlayer(@NonNull String var0, boolean var1) {
    Player var2 = Bukkit.getPlayerExact(var0);
    if (!var1 && var2 == null) {
      int var7;
      if ((var7 = (var0 = net.aurika.util.string.Strings.toLatinLowerCase(var0)).length()) > 16) {
        return null;
      } else {
        int var3 = Integer.MAX_VALUE;

        for (Player player : Bukkit.getOnlinePlayers()) {
          Player var5;
          if (net.aurika.util.string.Strings.toLatinLowerCase((var5 = player).getName()).startsWith(var0)) {
            int var6;
            if ((var6 = Math.abs(var5.getName().length() - var7)) < var3) {
              var2 = var5;
              var3 = var6;
            }

            if (var6 == 0) {
              break;
            }
          }
        }

        return var2;
      }
    } else {
      return var2;
    }
  }

  public static boolean hasFullHealth(@NonNull LivingEntity var0) {
    return var0.getHealth() == getMaxPlayerHealth(var0);
  }

  public static boolean isEffectivelyInvisible(Player var0) {
    if (!var0.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
      return false;
    } else {
      ItemStack[] var4;
      int var1 = (var4 = var0.getInventory().getArmorContents()).length;

      for (int var2 = 0; var2 < var1; ++var2) {
        if (var4[var2] != null) {
          return false;
        }
      }

      return true;
    }
  }

  public static LivingEntity getTargetedEntity(Player player, double vector) {
    BlockIterator var3 = new BlockIterator(player, 20);

    while (vector-- > 0.0 && var3.hasNext()) {
      var3.next();
    }

    while (var3.hasNext()) {
      Block var17;
      int var2 = (var17 = var3.next()).getX();
      int var4 = var17.getY();
      int var18 = var17.getZ();

      for (Entity entity : player.getNearbyEntities(20.0, 20.0, 20.0)) {
        Entity var6;
        if ((var6 = entity) instanceof LivingEntity) {
          Location var19;
          double var11 = (var19 = var6.getLocation()).getX();
          double var13 = var19.getY();
          double var15 = var19.getZ();
          if ((double) var2 - 0.75 <= var11 && var11 <= (double) var2 + 1.75 && (double) var18 - 0.75 <= var15 && var15 <= (double) var18 + 1.75 && (double) (var4 - 1) <= var13 && var13 <= (double) var4 + 2.5) {
            return null;
          }
        }
      }
    }

    return null;
  }

  static {
    OfflinePlayer[] var0 = Bukkit.getOfflinePlayers();
    b = new HashMap<>(var0.length);

    for (OfflinePlayer offlinePlayer : var0) {
      OfflinePlayer var3;
      if ((var3 = offlinePlayer).getName() != null) {
        b.put(var3.getName(), offlinePlayer);
      }
    }

    boolean var5 = false;

    try {
      Player.class.getDeclaredMethod("getPing");
      var5 = true;
    } catch (NoSuchMethodException ignored) {
    }

    c = var5;
  }
}

