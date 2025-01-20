package top.mckingdom.powerful_territory.commands.admin.land.category

import org.bukkit.permissions.PermissionDefault
import org.kingdoms.commands.*
import org.kingdoms.constants.land.Land
import org.kingdoms.locale.Language
import org.kingdoms.locale.LanguageManager
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryLang
import top.mckingdom.powerful_territory.data.Categories
import top.mckingdom.powerful_territory.data.Categories.categoriesString
import top.mckingdom.powerful_territory.data.getCategory
import top.mckingdom.powerful_territory.data.setCategory
import top.mckingdom.powerful_territory.constants.land_categories.LandCategory


class CommandAdminLandCategorySet(parent: KingdomsParentCommand) : KingdomsCommand("set", parent, PermissionDefault.OP) {


    override fun execute(context: CommandContext): CommandResult {

        val sender = context.getMessageReceiver()
        if (context.assertPlayer()) {
            return CommandResult.FAILED
        }

        val lang : Language = context.kingdomPlayer.language ?: LanguageManager.getDefaultLanguage()
        val category : LandCategory? = categoriesString.get(lang)?.get(context.arg(0))

        if (category == null) {

        } else {

            val land = Land.getLand(context.senderAsPlayer().location)

            if (land != null) {

                PowerfulTerritoryLang.COMMAND_ADMIN_DOMAIN_CATEGORY_SET_SUCCESS.sendMessage(sender,
                    "location", "${land.location.x} ${land.location.z}",
                    "old-category", land.getCategory()!!.getName(lang),
                    "new-category", category.getName(lang)
                )
                land.setCategory(category)

            }
            return CommandResult.SUCCESS

        }

       return CommandResult.FAILED

    }

    override fun tabComplete(context: CommandTabContext): List<String> {

        if (context.assertPlayer()) {
            return emptyList()
        }

        val lang : Language = context.getKingdomPlayer().getLanguage() ?: LanguageManager.getDefaultLanguage()

        if (context.isAtArg(0)) {
            return Categories.getCategories(context.arg(0), lang, true)
        }

        return emptyList()
    }





}