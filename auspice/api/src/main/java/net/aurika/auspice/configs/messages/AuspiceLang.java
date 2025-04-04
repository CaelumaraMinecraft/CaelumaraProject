package net.aurika.auspice.configs.messages;

import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.server.Platform;
import net.aurika.auspice.platform.entity.Player;
import net.aurika.auspice.platform.player.OfflinePlayer;
import net.aurika.auspice.translation.TranslationEntry;
import net.aurika.auspice.translation.message.manager.AuspiceMessageManager;
import net.aurika.auspice.translation.message.manager.MessageManager;
import net.aurika.auspice.translation.messenger.EnumDefinedMessenger;
import net.aurika.configuration.annotations.Comment;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public enum AuspiceLang implements EnumDefinedMessenger {
  PREFIX,

  UNKNOWN("&4Unknown"),

  PLACEHOLDERS_TOP_NOT_FOUND("{$e}No kingdom in top position{$colon} {$es}%position%", 1),

  @Comment({"", "Used for absence of a value."})
  NONE("&4None"),
  TRUE("Yes"),
  FALSE("No"),
  ENABLED("enabled"),
  DISABLED("disabled"),
  INVALID_UUID(
      "{$es}%arg% {$e}is not a valid &nhover:{UUID;&7Click to open Wikipedia page;@https://en.wikipedia.org/wiki/Universally_unique_identifier#Format}",
      1
  ),
  INVALID_NUMBER("{$es}%arg% {$e}is not a number.", 1),
  INVALID_NUMBER_TOO_LARGE("{$es}%arg% {$e}number is too large.", 1),
  INVALID_NUMBER_INTEGER_ONLY("{$es}%arg% {$e}must be an integer.", 1),
  INVALID_NUMBER_NEGATIVE("{$e}Must be a positive number.", 1),
  INVALID_AMOUNT("{$e}Invalid amount{$colon} {$es}%amount%", 1),
  INVALID_MATERIAL("{$e}Unknown material{$colon} {$es}%material%", 1),
  INVALID_TIME(
      "{$e}Invalid time{$colon} {$es}%time% {$sep}({$e}Correct format{$colon} {$es}<amount><time-suffix> {$e}e.g.{$colon} {$es}1s{$sep}, {$es}50days{$sep})",
      1
  ),
  INVALID_BOOLEAN("{$es}%arg% {$e}is not a valid option. Please either use '&2true{$e}' or '&4false{$e}'", 1),
  INVALID_PATH("{$es}%arg% {$e}is not a valid path.'", 1),

  SETTERS_TYPE_ADD("add increase plus +", 1, 2),
  SETTERS_TYPE_SUBTRACT("remove decrease subtract minus -", 1, 2),
  SETTERS_TYPE_SET("set =", 1, 2),
  SETTERS_TYPE_UNKNOWN("{$e}Unknown setter type{$colon} {$es}%setter_type%", 1, 2),

  NOT_FOUND_PLAYER("{$e}The specified player was not found.", 2),

  COMMAND_ADMIN_TRACK_ENABLED(
      "&9Tracking Mode{$colon} {$p}Enabled {$sep}(&7You might not be able to see the path of all texts sent to you{$sep})"),
  COMMAND_ADMIN_TRACK_DISABLED("&9Tracking Mode{$colon} {$e}Disabled"),
  COMMAND_ADMIN_TRACK_TRACKED(
      "{$p}The following message is sent from {$s}hover:{%path%;&7Click to open the file;/k admin openfile %file%} {$p}at line {$s}%line%\n{$p}The raw message{$colon} &fhover:{%raw%;Click to copy;|%raw%}"),


  ;

  private final String defaultValue;
  private final TranslationEntry path;
  public static final AuspiceLang[] VALUES = values();

  AuspiceLang(int... group) {
    this(null, group);
  }

  AuspiceLang(String defaultValue, int... group) {
    this.path = EnumDefinedMessenger.getEntry(null, this, group);
    this.defaultValue = defaultValue;
  }

  @Override
  public @NotNull MessageManager messageManager() {
    return AuspiceMessageManager.;
  }

  @Override
  public @NonNull TranslationEntry translationEntry() {
    return this.path;
  }

  @Deprecated
  public void sendMessage(CommandSender commandSender, OfflinePlayer offlinePlayer, Object... edits) {
    this.sendMessage(commandSender, (new MessageContextImpl()).placeholders(edits).withContext(offlinePlayer));
  }

  public void sendConsoleMessage(Object... edits) {
    this.sendMessage(Bukkit.getConsoleSender(), edits);
  }

  public void sendEveryoneMessage(Object... edits) {
    this.sendConsoleMessage(edits);

    for (Player player : Platform.get().playerManager().getOnlinePlayers()) {
      this.sendMessage(player, edits);
    }
  }

  @Override
  public String defaultValue() {
    return this.defaultValue;
  }
}
