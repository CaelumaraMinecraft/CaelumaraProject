package net.aurika.auspice.utils.debug;

import net.aurika.auspice.commands.CommandContext;
import net.aurika.auspice.platform.command.CommandSender;
import net.aurika.auspice.platform.entity.Player;
import net.aurika.util.collection.nonnull.NonNullMap;
import net.aurika.util.uuid.FastUUID;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DebugSettings {

  protected boolean isWightList;
  @NotNull
  protected final Set<DebugIdent> debugs;
  @NotNull
  public static final Map<UUID, DebugSettings> SETTINGS = new NonNullMap<>();

  public DebugSettings() {
    this(false, new HashSet<>());
  }

  public DebugSettings(boolean isWhiteList, @NotNull Set<DebugIdent> debugs) {
    Objects.requireNonNull(debugs);
    this.isWightList = isWhiteList;
    this.debugs = debugs;
  }

  public final boolean isWhitelist() {
    return this.isWightList;
  }

  public final void setWhitelist(boolean isWhitelist) {
    this.isWightList = isWhitelist;
  }

  @NotNull
  public final Set<DebugIdent> getList() {
    return this.debugs;
  }

  @NotNull
  public static DebugSettings getSettings(@NotNull CommandContext context) {
    Objects.requireNonNull(context, "");
    CommandSender var10001 = context.messageReceiver();
    Objects.requireNonNull(var10001, "");
    return getSettings(var10001);
  }

  @NotNull
  public static DebugSettings getSettings(@NotNull CommandSender var1) {
    Objects.requireNonNull(var1, "");
    UUID var2 = var1 instanceof Player ? ((Player) var1).getUniqueId() : FastUUID.ZERO;
    DebugSettings var10000 = DebugSettings.SETTINGS.computeIfAbsent(var2, (uuid -> new DebugSettings()));
    Objects.requireNonNull(var10000, "");
    return var10000;
  }

}
