package net.aurika.kingdoms.territories.commands.general.land.category

import net.aurika.kingdoms.territories.config.PowerfulTerritoryLang
import net.aurika.kingdoms.territories.data.Categories
import net.aurika.kingdoms.territories.data.category
import net.aurika.kingdoms.territories.GroupExt
import org.bukkit.entity.Player
import org.kingdoms.commands.*
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.player.KingdomPlayer

class CommandLandCategorySet(parent: KingdomsParentCommand) : KingdomsCommand("set", parent) {
  override fun execute(context: CommandContext): CommandResult {
    val sender = context.getMessageReceiver()
    if (sender !is Player) {
      PowerfulTerritoryLang.SENDER_ONLY_PLAYER.sendMessage(sender)
      return CommandResult.FAILED
    }
    if (!context.isAtArg(0)) {
      PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_SET_USAGE.sendMessage(sender)
      return CommandResult.FAILED
    }
    val player = context.senderAsPlayer()
    val land = Land.getLand(player.location)
    if (land == null || !land.isClaimed) {
      PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_SET_FAILED_NOT_CLAIMED.sendMessage(sender)
      return CommandResult.FAILED
    }
    val x = land.location.x
    val z = land.location.z
    val kp = KingdomPlayer.getKingdomPlayer(player)
    val lang = kp.language
    if (kp.kingdom != land.kingdom) {
      PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_GET_FAILED_OTHER_KINGDOM.sendMessage(sender)
      return CommandResult.FAILED
    }
    val category = Categories.getLandCategory(context.arg(0), lang, false)
    if (category == null) {
      PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_SET_USAGE.sendMessage(sender)
      return CommandResult.FAILED
    }
    if (kp.hasPermission(GroupExt.PERMISSION_MANAGE_LAND_CATEGORIES)


    ) {
      val oldCategory = land.category
      land.category = category
      PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_SET_SUCCESS.sendMessage(
        sender,
        "location", "$x $z",
        "old-category", oldCategory!!.getName(lang),
        "new-category", category.getName(lang)
      )
    }

    println("++++++++++++++++++++++出现了意外的情况! 请联系插件作者============================")
    return CommandResult.FAILED
  }

  override fun tabComplete(context: CommandTabContext): List<String> {
//        if (!context.assertPlayer()) {
//            return emptyList()
//        }
    val lang = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer()).language
    if (context.isAtArg(0)) {
      return Categories.getCategories(context.arg(0), lang, false)
    }
    return emptyList()
  }
}