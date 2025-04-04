package net.aurika.kingdoms.powerfulterritory.commands.admin.land.category

import net.aurika.kingdoms.powerfulterritory.configs.PowerfulTerritoryLang
import net.aurika.kingdoms.powerfulterritory.constant.land.category.LandCategory
import net.aurika.kingdoms.powerfulterritory.data.Categories
import net.aurika.kingdoms.powerfulterritory.data.Categories.categoriesString
import net.aurika.kingdoms.powerfulterritory.data.category
import org.bukkit.entity.Player
import org.bukkit.permissions.PermissionDefault
import org.kingdoms.commands.*
import org.kingdoms.constants.land.Land
import org.kingdoms.locale.Language
import org.kingdoms.locale.LanguageManager

class CommandAdminLandCategorySet(parent: KingdomsParentCommand) :
  KingdomsCommand("set", parent, PermissionDefault.OP) {


  override fun execute(context: CommandContext): CommandResult {

    val sender = context.getMessageReceiver()
    if (context.assertPlayer()) {
      return CommandResult.FAILED
    }

    val lang: Language = context.kingdomPlayer.language ?: LanguageManager.getDefaultLanguage()
    val category: LandCategory? = categoriesString.get(lang)?.get(context.arg(0))

    if (category == null) {

    } else {

      val land: Land? = Land.getLand(context.senderAsPlayer().location)

      if (land != null) {

        PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_CATEGORY_SET_SUCCESS.sendMessage(
          sender,
          "location", "${land.location.x} ${land.location.z}",
          "old-category", land.category!!.getName(lang),
          "new-category", category.getName(lang)
        )
        land.category = category
      }
      return CommandResult.SUCCESS
    }

    return CommandResult.FAILED
  }

  override fun tabComplete(context: CommandTabContext): List<String> {
    val messageReceiver = context.getMessageReceiver()

    if (messageReceiver !is Player) {
      return emptyList()
    }

    val lang: Language = context.kingdomPlayer.language ?: LanguageManager.getDefaultLanguage()

    if (context.isAtArg(0)) {
      return Categories.getCategories(context.arg(0), lang, true)
    }

    return emptyList()
  }
}