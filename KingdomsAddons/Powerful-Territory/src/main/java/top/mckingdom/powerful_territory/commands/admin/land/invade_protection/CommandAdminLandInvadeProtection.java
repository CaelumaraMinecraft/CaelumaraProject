package top.mckingdom.powerful_territory.commands.admin.land.invade_protection;

import org.kingdoms.commands.KingdomsParentCommand;

public class CommandAdminLandInvadeProtection extends KingdomsParentCommand {
    public CommandAdminLandInvadeProtection(KingdomsParentCommand parent) {
        super("invadeProtection", parent);
        new CommandAdminLandInvadeProtectionSet(this);
        new CommandAdminLandInvadeProtectionGet(this);
    }


}
