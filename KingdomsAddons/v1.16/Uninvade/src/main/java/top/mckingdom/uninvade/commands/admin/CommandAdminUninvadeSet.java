package top.mckingdom.uninvade.commands.admin;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.*;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.location.SimpleChunkLocation;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.locale.SupportedLanguage;
import top.mckingdom.uninvade.config.UninvadeAddonLang;
import top.mckingdom.uninvade.data.InvadeProtection;
import top.mckingdom.uninvade.data.InvadeProtectionRegistry;
import top.mckingdom.uninvade.data.LandInvadeProtectionDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandAdminUninvadeSet extends KingdomsCommand {
    public CommandAdminUninvadeSet(KingdomsParentCommand parent) {
        super("set", parent);
    }

    @Override
    public @NotNull CommandResult executeX(@NonNull CommandContext context) {
        if (!(context.getSender() instanceof Player)) {
            return CommandResult.FAILED;
        }
        Land land;
        Player player = context.senderAsPlayer();
        if (context.isAtArg(2)) {
            int x = Integer.parseInt(context.arg(1));
            int z = Integer.parseInt(context.arg(2));
            land = Land.getLand(new SimpleChunkLocation(player.getWorld().getName(), x, z));
        } else if (context.isAtArg(0)) {
            land = Land.getLand(player.getLocation());
        } else  {
            UninvadeAddonLang.COMMAND_ADMIN_UNINVADE_SET_USAGES.sendMessage(player);
            return CommandResult.FAILED;
        }


        if (land == null || !land.isClaimed()) {
            UninvadeAddonLang.COMMAND_ADMIN_UNINVADE_SET_FAILED_NOT_CLAIMED.sendError(player);
            return CommandResult.FAILED;
        }

        KingdomPlayer kp = KingdomPlayer.getKingdomPlayer(player);
        SupportedLanguage language = kp.getLanguage();
        InvadeProtection oldStatus = LandInvadeProtectionDataManager.getInvadeProtectionStatus(land);
        InvadeProtection newStatus = null;

        for (InvadeProtection status : InvadeProtectionRegistry.get().getRegistry().values()) {
            if (Objects.equals(status.getName(language), context.arg(0))) {
                newStatus = status;
            }
        }

        if (newStatus != null) {
            LandInvadeProtectionDataManager.setInvadeProtectionStatus(land, newStatus);
            UninvadeAddonLang.COMMAND_ADMIN_UNINVADE_SET_SUCCESS.sendMessage(
                    player,
                    "old-status", oldStatus.getName(language),
                    "new-status", newStatus.getName(language)
            );
            return CommandResult.SUCCESS;
        } else {
            UninvadeAddonLang.COMMAND_ADMIN_UNINVADE_SET_FAILED_UNKNOWN_STATUS.sendError(player);
            return CommandResult.FAILED;
        }

    }

    @Override
    public @NonNull List<String> tabComplete(@NonNull CommandTabContext context) {
        SupportedLanguage language = context.getKingdomPlayer().getLanguage();
        List<String> out = new ArrayList<>();
        if (context.isAtArg(0)) {
            for (InvadeProtection status : InvadeProtectionRegistry.get().getRegistry().values()) {
                out.add(status.getName(language));
            }
            return out;
        }

        if (context.isAtArg(1)) {
            return List.of("<x>");
        }

        if (context.isAtArg(2)) {
            return List.of("<z>");
        }

        return emptyTab();
    }


}
