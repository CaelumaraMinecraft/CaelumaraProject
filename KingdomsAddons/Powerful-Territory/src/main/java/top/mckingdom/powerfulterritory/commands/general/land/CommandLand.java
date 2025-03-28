package top.mckingdom.powerfulterritory.commands.general.land;

import org.kingdoms.commands.KingdomsParentCommand;
import top.mckingdom.powerfulterritory.commands.general.land.category.CommandLandCategory;
import top.mckingdom.powerfulterritory.commands.general.land.contraction.CommandLandContraction;

public class CommandLand extends KingdomsParentCommand {

  private static CommandLand instance;

  public CommandLand() {
    super("domain", true);
    new CommandLandCategory(this);
    new CommandLandContraction(this);
  }

  public static CommandLand getInstance() {
    return instance;
  }

  public static void setInstance(CommandLand instance) {
    CommandLand.instance = instance;
  }

}
