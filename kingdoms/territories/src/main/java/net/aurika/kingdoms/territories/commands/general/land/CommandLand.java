package net.aurika.kingdoms.territories.commands.general.land;

import net.aurika.kingdoms.territories.commands.general.land.category.CommandLandCategory;
import net.aurika.kingdoms.territories.commands.general.land.contraction.CommandLandContraction;
import org.kingdoms.commands.KingdomsParentCommand;

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
