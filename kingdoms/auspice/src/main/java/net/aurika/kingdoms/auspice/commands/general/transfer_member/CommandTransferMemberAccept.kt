package net.aurika.kingdoms.auspice.commands.general.transfer_member

import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.KingdomsCommand
import org.kingdoms.commands.KingdomsParentCommand

class CommandTransferMemberAccept(parent: KingdomsParentCommand) : KingdomsCommand("accept", parent) {
  override fun execute(p0: CommandContext): CommandResult {
    return CommandResult.SUCCESS
  }
}
