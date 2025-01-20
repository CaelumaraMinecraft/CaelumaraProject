package top.mckingdom.uninvade.commands.admin;

import org.kingdoms.commands.KingdomsParentCommand;

public class CommandAdminUninvade extends KingdomsParentCommand {
    public CommandAdminUninvade(KingdomsParentCommand parent) {
        super("uninvade", parent);
        new CommandAdminUninvadeSet(this);
        new CommandAdminUninvadeGet(this);
    }


}
