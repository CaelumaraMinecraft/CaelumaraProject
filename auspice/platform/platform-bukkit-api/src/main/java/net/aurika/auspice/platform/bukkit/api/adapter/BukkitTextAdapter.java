package net.aurika.auspice.platform.bukkit.api.adapter;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextFormat;
import net.kyori.adventure.text.serializer.legacy.Reset;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.format.NamedTextColor.*;

public interface BukkitTextAdapter {

  public static @NotNull BukkitTextAdapter get() {
    if (BukkitTextAdapter$Companion.INSTANCE == null) {
      throw new IllegalStateException("The text adapter has not been initialized yet.");
    } else {
      return BukkitTextAdapter$Companion.INSTANCE;
    }
  }

  @ApiStatus.Internal
  static void set(@NotNull BukkitTextAdapter adapter) {
    BukkitTextAdapter$Companion.INSTANCE = adapter;
  }

  default org.bukkit.@NotNull ChatColor adaptTextFormat(@NotNull TextFormat textFormat) {
    if (textFormat instanceof TextColor) {
      return adaptTextColor((TextColor) textFormat);
    }
    if (textFormat instanceof TextDecoration) {
      return adaptTextDecoration((TextDecoration) textFormat);
    }
    if (textFormat == Reset.INSTANCE) return org.bukkit.ChatColor.RESET;
    throw new UnsupportedOperationException("Unsupported textFormat: " + textFormat);
  }

  default org.bukkit.@NotNull ChatColor adaptTextColor(@NotNull TextColor textColor) {
    Validate.Arg.notNull(textColor, "textColor");
    if (textColor.value() == BLACK.value()) return org.bukkit.ChatColor.BLACK;
    if (textColor.value() == DARK_BLUE.value()) return org.bukkit.ChatColor.DARK_BLUE;
    if (textColor.value() == DARK_GREEN.value()) return org.bukkit.ChatColor.DARK_GREEN;
    if (textColor.value() == DARK_AQUA.value()) return org.bukkit.ChatColor.DARK_AQUA;
    if (textColor.value() == DARK_RED.value()) return org.bukkit.ChatColor.DARK_RED;
    if (textColor.value() == DARK_PURPLE.value()) return org.bukkit.ChatColor.DARK_PURPLE;
    if (textColor.value() == GOLD.value()) return org.bukkit.ChatColor.GOLD;
    if (textColor.value() == GRAY.value()) return org.bukkit.ChatColor.GRAY;
    if (textColor.value() == DARK_GRAY.value()) return org.bukkit.ChatColor.DARK_GRAY;
    if (textColor.value() == BLUE.value()) return org.bukkit.ChatColor.BLUE;
    if (textColor.value() == GREEN.value()) return org.bukkit.ChatColor.GREEN;
    if (textColor.value() == AQUA.value()) return org.bukkit.ChatColor.AQUA;
    if (textColor.value() == RED.value()) return org.bukkit.ChatColor.RED;
    if (textColor.value() == LIGHT_PURPLE.value()) return org.bukkit.ChatColor.LIGHT_PURPLE;
    if (textColor.value() == YELLOW.value()) return org.bukkit.ChatColor.YELLOW;
    if (textColor.value() == WHITE.value()) return org.bukkit.ChatColor.WHITE;
    throw new UnsupportedOperationException("Unsupported textColor: " + textColor);
  }

  default org.bukkit.@NotNull ChatColor adaptTextDecoration(@NotNull TextDecoration textDecoration) {
    switch (textDecoration) {
      case OBFUSCATED:
        return org.bukkit.ChatColor.MAGIC;
      case BOLD:
        return org.bukkit.ChatColor.BOLD;
      case STRIKETHROUGH:
        return org.bukkit.ChatColor.STRIKETHROUGH;
      case UNDERLINED:
        return org.bukkit.ChatColor.UNDERLINE;
      case ITALIC:
        return org.bukkit.ChatColor.ITALIC;
      default:
        throw new UnsupportedOperationException("Unsupported textDecoration: " + textDecoration);
    }
  }

  interface ChatColor { }

}

final class BukkitTextAdapter$Companion {

  static BukkitTextAdapter INSTANCE;

}
