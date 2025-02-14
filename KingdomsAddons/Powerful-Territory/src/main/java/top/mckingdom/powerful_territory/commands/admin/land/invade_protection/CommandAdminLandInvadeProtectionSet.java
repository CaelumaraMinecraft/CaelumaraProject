package top.mckingdom.powerful_territory.commands.admin.land.invade_protection;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.*;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.location.SimpleChunkLocation;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.locale.Language;
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryLang;
import top.mckingdom.powerful_territory.data.InvadeProtections;
import top.mckingdom.powerful_territory.constants.invade_protection.InvadeProtection;
import top.mckingdom.powerful_territory.constants.invade_protection.InvadeProtectionRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CommandAdminLandInvadeProtectionSet extends KingdomsCommand {
    public CommandAdminLandInvadeProtectionSet(KingdomsParentCommand parent) {
        super("set", parent);
    }

    @Override
    public @NotNull CommandResult execute(@NonNull CommandContext context) {
        if (!(context.getMessageReceiver() instanceof Player)) {
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
            PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_SET_USAGES.sendMessage(player);
            return CommandResult.FAILED;
        }


        if (land == null || !land.isClaimed()) {
            PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_SET_FAILED_NOT_CLAIMED.sendError(player);
            return CommandResult.FAILED;
        }

        KingdomPlayer kp = KingdomPlayer.getKingdomPlayer(player);
        Language language = kp.getLanguage();
        InvadeProtection oldStatus = InvadeProtections.getInvadeProtection(land);
        InvadeProtection newStatus = null;

        for (InvadeProtection status : InvadeProtectionRegistry.get().getRegistry().values()) {
            if (Objects.equals(status.getName(language), context.arg(0))) {
                newStatus = status;
            }
        }

        if (newStatus != null) {
            PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_SET_SUCCESS.sendMessage(
                    player,
                    "old-status", oldStatus.getName(language),
                    "new-status", newStatus.getName(language)
            );
            InvadeProtections.setInvadeProtection(land, newStatus);
            return CommandResult.SUCCESS;
        } else {
            PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_INVADEPROTECTION_SET_FAILED_UNKNOWN_STATUS.sendError(player);
            return CommandResult.FAILED;
        }

    }

    @Override
    public @NonNull List<String> tabComplete(@NonNull CommandTabContext context) {
        Language language = context.getKingdomPlayer().getLanguage();
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

        return Collections.emptyList();
    }


}
