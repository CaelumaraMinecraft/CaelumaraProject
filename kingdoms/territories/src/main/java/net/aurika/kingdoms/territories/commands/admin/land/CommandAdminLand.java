package net.aurika.kingdoms.territories.commands.admin.land;

import net.aurika.kingdoms.territories.commands.admin.land.category.CommandAdminLandCategory;
import net.aurika.kingdoms.territories.commands.admin.land.invade_protection.CommandAdminLandInvadeProtection;
import org.kingdoms.commands.KingdomsParentCommand;

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
