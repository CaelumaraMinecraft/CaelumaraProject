package top.mckingdom.powerful_territory.commands.general.land.category

import org.bukkit.entity.Player
import org.kingdoms.commands.*
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.player.KingdomPlayer
import top.mckingdom.powerful_territory.GroupExt
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryLang
import top.mckingdom.powerful_territory.data.Categories
import top.mckingdom.powerful_territory.data.getCategory
import top.mckingdom.powerful_territory.data.setCategory

class CommandLandCategorySet(parent: KingdomsParentCommand): KingdomsCommand("set", parent) {
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
        val land = Land.getLand(player.getLocation())
        if (land == null || !land.isClaimed()) {
            PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_SET_FAILED_NOT_CLAIMED.sendMessage(sender)
            return CommandResult.FAILED
        }
        val x = land.location.x
        val z = land.location.z
        val kp = KingdomPlayer.getKingdomPlayer(player)
        val lang = kp.language
        if (kp.getKingdom() != land.getKingdom()) {
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
            val oldCategory = land.getCategory()
            land.setCategory(category)
            PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_SET_SUCCESS.sendMessage(sender,
                "location", "$x $z",
                "old-category", oldCategory!!.getName(lang),
                "new-category", category.getName(lang)
                )
        }

        println("++++++++++++++++++++++出现了意外的情况! 请联系插件作者(Attaccer)============================")
        return CommandResult.FAILED
    }

    override fun tabComplete(context: CommandTabContext): List<String> {
//        if (!context.assertPlayer()) {
//            return emptyList()
//        }
        val lang = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer()).getLanguage()
        if (context.isAtArg(0)) {
            return Categories.getCategories(context.arg(0), lang, false)
        }
        return emptyList()
    }


}