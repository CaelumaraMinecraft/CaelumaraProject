package top.mckingdom.powerful_territory.commands.general.land.category

import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.KingdomsCommand
import org.kingdoms.commands.KingdomsParentCommand
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.player.KingdomPermission
import org.kingdoms.constants.player.KingdomPlayer
import top.mckingdom.powerful_territory.GroupExt
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryLang
import top.mckingdom.powerful_territory.data.getCategory

class CommandLandCategoryGet(parent: KingdomsParentCommand): KingdomsCommand("get", parent) {
    override fun execute(context: CommandContext): CommandResult {
        val player = context.senderAsPlayer()
        val kp = KingdomPlayer.getKingdomPlayer(player)
        val lang = kp.language
        val land = Land.getLand(player.location)

        if (land == null || !land.isClaimed()) {
            PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_GET_FAILED_NOT_CLAIMED.sendMessage(player)
            return CommandResult.FAILED
        }
        val x = land.location.x
        val z = land.location.z
        if (land.getKingdom() != kp.getKingdom()) {
            PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_GET_FAILED_OTHER_KINGDOM.sendMessage(player)
            return CommandResult.FAILED
        }

        if (kp.hasPermission(GroupExt.PERMISSION_MANAGE_LAND_CATEGORIES as KingdomPermission)
            || kp.isAdmin()
            ) {
            PowerfulTerritoryLang.COMMAND_DOMAIN_CATEGORY_GET_SUCCESS.sendMessage(player, "location", "$x $z", "category", land.getCategory()!!.getName(lang))

            return CommandResult.SUCCESS
        }


        println("++++++++++++++++++++++出现了意外的情况! 请联系插件作者(Attaccer)============================")
        return CommandResult.FAILED
    }
}