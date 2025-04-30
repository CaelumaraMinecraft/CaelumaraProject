package net.aurika.kingdoms.territories.commands.admin.land.category

import net.aurika.kingdoms.territories.config.PowerfulTerritoryLang
import net.aurika.kingdoms.territories.data.category
import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.KingdomsCommand
import org.kingdoms.commands.KingdomsParentCommand
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.player.KingdomPlayer

class CommandAdminLandCategoryGet(parent: KingdomsParentCommand) : KingdomsCommand("get", parent) {
  override fun execute(context: CommandContext): CommandResult {
    context.getMessageReceiver()
    if (context.assertPlayer()) {
      return CommandResult.FAILED
    }
    val player = context.senderAsPlayer()
    val land = Land.getLand(player.location)
    if (land == null || !land.isClaimed) {
      PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_CATEGORY_GET_FAILED_NOT_CLAIMED.sendMessage(player)
      return CommandResult.FAILED
    }
    val x = land.location.x
    val z = land.location.z
    PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_CATEGORY_GET_SUCCESS.sendMessage(
      player,
      "location", "$x $z",
      "category", land.category?.getName(KingdomPlayer.getKingdomPlayer(player).language)
    )

    return CommandResult.FAILED
  }
}