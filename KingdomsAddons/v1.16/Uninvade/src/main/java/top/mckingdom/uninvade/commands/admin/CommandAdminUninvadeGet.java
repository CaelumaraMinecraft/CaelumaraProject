package top.mckingdom.uninvade.commands.admin;

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
import org.kingdoms.locale.SupportedLanguage;
import top.mckingdom.uninvade.config.UninvadeAddonLang;
import top.mckingdom.uninvade.data.LandInvadeProtectionDataManager;

public class CommandAdminUninvadeGet extends KingdomsCommand {
    public CommandAdminUninvadeGet(KingdomsParentCommand parent) {
        super("get", parent);
    }

    @Override
    public @NotNull CommandResult executeX(@NonNull CommandContext context) {
        if (!(context.getSender() instanceof Player)) {
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
            UninvadeAddonLang.COMMAND_ADMIN_UNINVADE_GET_USAGES.sendMessage(player);
            return CommandResult.FAILED;
        }

        if (land == null || !land.isClaimed()) {
            UninvadeAddonLang.COMMAND_ADMIN_UNINVADE_GET_FAILED_NOT_CLAIMED.sendError(player);
            return CommandResult.FAILED;
        }

        KingdomPlayer kp = KingdomPlayer.getKingdomPlayer(player);
        SupportedLanguage language = kp.getLanguage();

        UninvadeAddonLang.COMMAND_ADMIN_UNINVADE_GET_SUCCESS.sendMessage(player, "status", LandInvadeProtectionDataManager.getInvadeProtectionStatus(land).getName(language));

        return CommandResult.SUCCESS;
    }



}
