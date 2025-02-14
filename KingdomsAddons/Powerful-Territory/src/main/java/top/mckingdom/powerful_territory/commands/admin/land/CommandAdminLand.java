package top.mckingdom.powerful_territory.commands.admin.land;

import org.kingdoms.commands.KingdomsParentCommand;
import top.mckingdom.powerful_territory.commands.admin.land.category.CommandAdminLandCategory;
import top.mckingdom.powerful_territory.commands.admin.land.invade_protection.CommandAdminLandInvadeProtection;

public class CommandAdminLand extends KingdomsParentCommand {

    private static CommandAdminLand instance;
    public CommandAdminLand(KingdomsParentCommand parent) {
        super("domain", parent);
        new CommandAdminLandCategory(this);
        new CommandAdminLandInvadeProtection(this);
        new CommandAdminLandClearData(this);
    }

    public static CommandAdminLand getInstance() {
        return instance;
    }

    public static void setInstance(CommandAdminLand instance) {
        CommandAdminLand.instance = instance;
    }
}
