package net.aurika.kingdoms.territories.commands.general.land.category

import net.aurika.kingdoms.territories.config.PowerfulTerritoryLang
import net.aurika.kingdoms.territories.data.category
import net.aurika.kingdoms.territories.GroupExt
import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.KingdomsCommand
import org.kingdoms.commands.KingdomsParentCommand
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.player.KingdomPlayer
import org.kingdoms.locale.Language

class CommandLandCategoryGet(parent: KingdomsParentCommand) : KingdomsCommand("get", parent) {
  override fun execute(context: CommandContext): CommandResult {
    val player = context.senderAsPlayer()
    val kp = KingdomPlayer.getKingdomPlayer(player)
    val lang: Language = kp.language
    val land = Land.getLand(player.location)

    if (land == null || !land.isClaimed) {  // When land not claimed
      PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_GET_FAILED_NOT_CLAIMED.sendMessage(player)
      return CommandResult.FAILED
    }
    val x = land.location.x
    val z = land.location.z
    if (land.kingdom != kp.kingdom) {
      PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_GET_FAILED_OTHER_KINGDOM.sendMessage(player)
      return CommandResult.FAILED
    }

    if (kp.hasPermission(GroupExt.PERMISSION_MANAGE_LAND_CATEGORIES)
      || kp.isAdmin
    ) {
      PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_GET_SUCCESS.sendMessage(
        player,
        "location",
        "$x $z",
        "category",
        land.category!!.getName(lang)
      )

      return CommandResult.SUCCESS
    }

    println("++++++++++++++++++++++出现了意外的情况! 请联系插件作者(Attaccer)============================")
    return CommandResult.FAILED
  }
}