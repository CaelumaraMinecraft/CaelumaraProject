package top.mckingdom.powerful_territory.commands.admin.land;

import org.bukkit.entity.Player;
import org.kingdoms.commands.*;
import org.kingdoms.constants.land.Land;
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryLang;
import top.mckingdom.powerful_territory.data.InvadeProtectionMetaHandler;
import top.mckingdom.powerful_territory.data.LandCategoryMetaHandler;
import top.mckingdom.powerful_territory.data.LandContractionsMetaHandler;
import top.mckingdom.powerful_territory.PowerfulTerritoryLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class CommandAdminLandClearData extends KingdomsCommand {

    public CommandAdminLandClearData(KingdomsParentCommand parent) {
        super("cleardata", parent);
    }
    private static final HashMap<String, Consumer<Land>> a = new HashMap<>();

    public static void register(String key, Consumer<Land> consumer) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(consumer);
        Consumer<Land> r;
        if ((r = a.put(key, consumer)) != null) {
            PowerfulTerritoryLogger.warn("data cleaner naming conflicts: " + key);
        }

    }

    static {
        register("PowerfulTerritory:land-contractions", (land) -> land.getMetadata().remove(LandContractionsMetaHandler.INSTANCE));
        register("PowerfulTerritory:land-category", (land -> land.getMetadata().remove(LandCategoryMetaHandler.INSTANCE)));
        register("PowerfulTerritory:invade-protection", (land -> land.getMetadata().remove(InvadeProtectionMetaHandler.INSTANCE)));
    }

    @Override
    public CommandResult execute(CommandContext context) {
        if (context.assertPlayer()) {
            return CommandResult.FAILED;
        }

        Player player = context.senderAsPlayer();

        Land land = Land.getLand(player.getLocation());
        if (land == null) {
            PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_CLEARDATA_FAILED_NULL_LAND.sendError(player);
            return CommandResult.FAILED;
        }

        Consumer<Land> c;
        if ((c = a.get(context.arg(0))) != null) {
            c.accept(land);
            PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_CLEARDATA_SUCCESS.sendMessage(player, "type", context.arg(0));
            return CommandResult.SUCCESS;
        }

        return CommandResult.FAILED;
    }

    @Override
    public List<String> tabComplete(CommandTabContext context) {
        return new ArrayList<>(a.keySet());
    }
}
