package top.mckingdom.powerfulterritory.commands.admin.land.invade_protection;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.CommandContext;
import org.kingdoms.commands.CommandResult;
import org.kingdoms.commands.KingdomsCommand;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.location.SimpleChunkLocation;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.locale.Language;
import top.mckingdom.powerfulterritory.configs.PowerfulTerritoryLang;
import top.mckingdom.powerfulterritory.data.InvadeProtections;

public class CommandAdminLandInvadeProtectionGet extends KingdomsCommand {

  public CommandAdminLandInvadeProtectionGet(KingdomsParentCommand parent) {
    super("get", parent);
  }

  @Override
  public @NotNull CommandResult execute(@NonNull CommandContext context) {
    if (!(context.getMessageReceiver() instanceof Player)) {
      return CommandResult.FAILED;
    }
    Land land;
    Player player = context.senderAsPlayer();
    if (context.isAtArg(1)) {
      int x = Integer.parseInt(context.arg(1));
      int z = Integer.parseInt(context.arg(2));
      land = Land.getLand(new SimpleChunkLocation(player.getWorld().getName(), x, z));
    } else if (context.isAtArg(-1)) {
      land = Land.getLand(player.getLocation());
    } else {
      PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_GET_USAGES.sendMessage(player);
      return CommandResult.FAILED;
    }

    if (land == null || !land.isClaimed()) {
      PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_GET_FAILED_NOT_CLAIMED.sendError(player);
      return CommandResult.FAILED;
    }

    KingdomPlayer kp = KingdomPlayer.getKingdomPlayer(player);
    Language language = kp.getLanguage();

    PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_GET_SUCCESS.sendMessage(
        player, "status", InvadeProtections.getInvadeProtection(land).getName(language));

    return CommandResult.SUCCESS;
  }

}
